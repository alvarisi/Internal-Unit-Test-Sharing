package test.bukapedia.com.unittest.data.network;

import test.bukapedia.com.unittest.domain.ArticleRepository;

public class ArticleDataRepository implements ArticleRepository {
    private ArticleApi articleApi;

    public ArticleDataRepository(ArticleApi articleApi) {
        this.articleApi = articleApi;
    }
}
