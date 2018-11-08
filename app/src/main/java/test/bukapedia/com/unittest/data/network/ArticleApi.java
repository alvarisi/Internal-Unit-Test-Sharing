package test.bukapedia.com.unittest.data.network;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import test.bukapedia.com.unittest.data.network.entity.ArticleResponse;

public interface ArticleApi {
    @GET("/v1/articles")
    Observable<ArticleResponse> getArticles(@QueryMap Map<String, Object> params);
}
