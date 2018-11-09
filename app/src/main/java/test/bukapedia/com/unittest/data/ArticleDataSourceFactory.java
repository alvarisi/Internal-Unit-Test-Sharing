package test.bukapedia.com.unittest.data;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import retrofit2.HttpException;
import test.bukapedia.com.unittest.data.network.ArticleApi;
import test.bukapedia.com.unittest.data.network.ArticleUrl;
import test.bukapedia.com.unittest.data.network.entity.ArticleResponse;
import test.bukapedia.com.unittest.data.network.exception.GeneralErrorException;
import test.bukapedia.com.unittest.data.network.exception.UnathorizedException;

public class ArticleDataSourceFactory {
    private ArticleApi articleApi;

    public ArticleDataSourceFactory(ArticleApi articleApi) {
        this.articleApi = articleApi;
    }

    public Observable<ArticleResponse> getArticles(String source, String sort) {
        Map<String, Object> params = new HashMap<>();
        params.put("source", source);
        params.put("apiKey", ArticleUrl.APP_KEY);
        params.put("sort", sort);
        return articleApi.getArticles(params)
                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends ArticleResponse>>() {
                    @Override
                    public ObservableSource<? extends ArticleResponse> apply(Throwable e) throws Exception {
                        if (e instanceof HttpException && ((HttpException) e).code() == 401)
                            return Observable.error(new UnathorizedException());
                        else
                            return Observable.error(new GeneralErrorException());
                    }
                });
    }
}
