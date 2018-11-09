package test.bukapedia.com.unittest.presentation.presenter;

import com.google.gson.Gson;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import test.bukapedia.com.unittest.ArticleAssetJson;
import test.bukapedia.com.unittest.UnitTestFileUtils;
import test.bukapedia.com.unittest.data.network.entity.ArticleResponse;
import test.bukapedia.com.unittest.data.network.exception.GeneralErrorException;
import test.bukapedia.com.unittest.data.network.exception.UnathorizedException;
import test.bukapedia.com.unittest.domain.ArticleRepository;
import test.bukapedia.com.unittest.domain.model.Article;
import test.bukapedia.com.unittest.domain.model.mapper.ArticleMapper;
import test.bukapedia.com.unittest.presentation.ui.ArticleView;

@RunWith(org.mockito.runners.MockitoJUnitRunner.class)
public class ArticlePresenterImplTest {
    private static String SOURCE_BBC = "bbc-news";
    private static String SORT_TOP = "top";

    @Mock
    private ArticleView articleView;
    @Mock
    private ArticleRepository articleRepository;

    ArticlePresenter presenter;
    private UnitTestFileUtils unitTestFileUtils;

    @BeforeClass
    public static void setUpClass() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
            @Override
            public Scheduler apply(Callable<Scheduler> schedulerCallable) throws Exception {
                return Schedulers.trampoline();
            }
        });
    }

    @Before
    public void setUp() throws Exception {
        unitTestFileUtils = new UnitTestFileUtils();
        presenter = new ArticlePresenterImpl(articleView, articleRepository);
    }

    @Test
    public void onViewInitialized_initial_showLoading() {
        //given
        Mockito.when(articleView.getArticleSource()).thenReturn(SOURCE_BBC);
        Mockito.when(articleView.getSortType()).thenReturn(SORT_TOP);
        //when
        presenter.onViewInitialized();

        //then
        Mockito.verify(articleView, Mockito.times(1)).hideArticleList();
        Mockito.verify(articleView, Mockito.times(1)).showInitialLoading();
    }

    @Test
    public void onViewInitialized_successGetArticles_renderList() {
        //given
        Gson gson = new Gson();
        ArticleResponse dataResponse = gson.fromJson(unitTestFileUtils.getJsonFromAsset(ArticleAssetJson.ARTICLE_SUCCESS), ArticleResponse.class);
        ArticleMapper assistMapper = new ArticleMapper();
        List<Article> articles = assistMapper.transform(dataResponse.getArticles());
        Observable<List<Article>> articlesObservable = Observable.just(articles);

        Mockito.when(articleView.getArticleSource()).thenReturn(SOURCE_BBC);
        Mockito.when(articleView.getSortType()).thenReturn(SORT_TOP);
        Mockito.when(articleRepository.getArticles(SOURCE_BBC, SORT_TOP)).thenReturn(articlesObservable);
        //when
        presenter.onViewInitialized();

        //then
        Mockito.verify(articleView, Mockito.times(1)).renderArticles(articles);
        Mockito.verify(articleView, Mockito.times(1)).showArticleList();
        Mockito.verify(articleView, Mockito.times(1)).hideInitialLoading();
    }

    @Test
    public void onViewInitialized_errorUnatorized_showErrorUnatorized() {
        Mockito.when(articleView.getArticleSource()).thenReturn(SOURCE_BBC);
        Mockito.when(articleView.getSortType()).thenReturn(SORT_TOP);
        Observable<List<Article>> articleObservable = Observable.error(new UnathorizedException());
        Mockito.when(articleRepository.getArticles(SOURCE_BBC, SORT_TOP)).thenReturn(articleObservable);

        presenter.onViewInitialized();

        Mockito.verify(articleView, Mockito.times(1)).hideInitialLoading();
        Mockito.verify(articleView, Mockito.times(1)).showErrorUnatorized();
    }

    @Test
    public void onViewInitialized_generalError_showRetry() {
        Mockito.when(articleView.getArticleSource()).thenReturn(SOURCE_BBC);
        Mockito.when(articleView.getSortType()).thenReturn(SORT_TOP);
        Observable<List<Article>> articleObservable = Observable.error(new GeneralErrorException());
        Mockito.when(articleRepository.getArticles(SOURCE_BBC, SORT_TOP)).thenReturn(articleObservable);

        presenter.onViewInitialized();

        Mockito.verify(articleView, Mockito.times(1)).hideInitialLoading();
        Mockito.verify(articleView, Mockito.times(1)).showRetryView();
    }

    @AfterClass
    public static void tearDownClass() {
        RxAndroidPlugins.reset();
    }
}