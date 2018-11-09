package test.bukapedia.com.unittest.data;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import io.reactivex.observers.TestObserver;
import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import test.bukapedia.com.unittest.ArticleAssetJson;
import test.bukapedia.com.unittest.UnitTestFileUtils;
import test.bukapedia.com.unittest.data.network.ArticleApi;
import test.bukapedia.com.unittest.data.network.ArticleUrl;
import test.bukapedia.com.unittest.data.network.entity.ArticleResponse;
import test.bukapedia.com.unittest.data.network.exception.GeneralErrorException;
import test.bukapedia.com.unittest.data.network.exception.UnathorizedException;

@RunWith(MockitoJUnitRunner.class)
public class ArticleDataSourceFactoryTest {
    private static String SOURCE_BBC = "bbc-news";
    private static String SORT_TOP = "top";

    private ArticleDataSourceFactory dataSourceFactory;
    private MockWebServer articleMockWebServer;
    private ArticleApi articleApi;
    private UnitTestFileUtils unitTestFileUtils;

    @Before
    public void setUp() throws Exception {
        unitTestFileUtils = new UnitTestFileUtils();
        articleMockWebServer = new MockWebServer();
        articleMockWebServer.start(8080);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10L, TimeUnit.SECONDS)
                .readTimeout(10L, TimeUnit.SECONDS)
                .writeTimeout(10L, TimeUnit.SECONDS)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://localhost:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        articleApi = retrofit.create(ArticleApi.class);

        dataSourceFactory = new ArticleDataSourceFactory(articleApi);
    }


    @After
    public void tearDown() throws Exception {
        articleMockWebServer.shutdown();
    }

    @Test
    public void getArticles_Success_noErrors() throws InterruptedException {
        //given
        TestObserver<ArticleResponse> testObserver = new TestObserver<>();
        MockResponse mockResponse = new MockResponse()
                .setResponseCode(200)
                .setBody(unitTestFileUtils.getJsonFromAsset(ArticleAssetJson.ARTICLE_SUCCESS));
        articleMockWebServer.enqueue(mockResponse);

        //when
        dataSourceFactory.getArticles(SOURCE_BBC, SORT_TOP).subscribe(testObserver);
        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS);

        //then
        testObserver.assertNoErrors();
        testObserver.assertValueCount(1);
        Assert.assertEquals(10, testObserver.values().get(0).getArticles().size());
    }

    @Test
    public void getArticles_KeyError_throwForbiddenException() {
        TestObserver<ArticleResponse> testObserver = new TestObserver<>();
        MockResponse mockResponse = new MockResponse()
                .setResponseCode(401);
        articleMockWebServer.enqueue(mockResponse);

        //when
        dataSourceFactory.getArticles(SOURCE_BBC, SORT_TOP).subscribe(testObserver);

        //then
        testObserver.assertError(UnathorizedException.class);
    }

    @Test
    public void getArticles_Timeout_throwSocketTimeoutException() {
        TestObserver<ArticleResponse> testObserver = new TestObserver<>();
        MockResponse mockResponse = new MockResponse()
                .setBodyDelay(11, TimeUnit.SECONDS)
                .setResponseCode(200)
                .setBody(unitTestFileUtils.getJsonFromAsset(ArticleAssetJson.ARTICLE_SUCCESS));
        articleMockWebServer.enqueue(mockResponse);

        //when
        dataSourceFactory.getArticles(SOURCE_BBC, SORT_TOP).subscribe(testObserver);

        //then
        testObserver.assertError(GeneralErrorException.class);
    }
}