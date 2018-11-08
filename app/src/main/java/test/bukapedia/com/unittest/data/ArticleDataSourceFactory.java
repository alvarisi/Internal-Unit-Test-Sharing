package test.bukapedia.com.unittest.data;

import test.bukapedia.com.unittest.data.network.ArticleApi;

public class ArticleDataSourceFactory {
    private ArticleApi articleApi;

    public ArticleDataSourceFactory(ArticleApi articleApi) {
        this.articleApi = articleApi;
    }
}
