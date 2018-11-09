package test.bukapedia.com.unittest.data;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import test.bukapedia.com.unittest.UnitTestFileUtils;
import test.bukapedia.com.unittest.data.network.entity.ArticleResponse;
import test.bukapedia.com.unittest.domain.ArticleRepository;
import test.bukapedia.com.unittest.domain.model.Article;
import test.bukapedia.com.unittest.domain.model.mapper.ArticleMapper;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ArticleDataRepositoryTest {

    private static String SOURCE_BBC = "bbc-news";
    private static String SORT_TOP = "top";

    @Mock
    ArticleDataSourceFactory articleDataSourceFactory;
    @Mock
    ArticleMapper articleMapper;

    private UnitTestFileUtils unitTestFileUtils;

    ArticleRepository repository;

    @Before
    public void setUp() throws Exception {
        unitTestFileUtils = new UnitTestFileUtils();
        repository = new ArticleDataRepository(articleDataSourceFactory, articleMapper);
    }

    @Test
    public void getArticles_SuccessGetArticles_AssertNoErrors() {
        //given
        Gson gson = new Gson();
        ArticleResponse dataResponse = gson.fromJson(unitTestFileUtils.getJsonFromAsset("assets/article_success.json"), ArticleResponse.class);
        Observable<ArticleResponse> articleResponseObservable = Observable.just(dataResponse);
        Mockito.when(articleDataSourceFactory.getArticles(SOURCE_BBC, SORT_TOP))
                .thenReturn(articleResponseObservable);
        ArticleMapper assistMapper = new ArticleMapper();
        List<Article> articles = assistMapper.transform(dataResponse.getArticles());
        Mockito.when(articleMapper.transform(dataResponse.getArticles())).thenReturn(articles);

        //when
        TestObserver<List<Article>> testObserver = new TestObserver<>();
        repository.getArticles(SOURCE_BBC, SORT_TOP).subscribe(testObserver);

        //then
        testObserver.assertNoErrors();
        assertEquals(articles.size(), testObserver.values().get(0).size());
    }
}