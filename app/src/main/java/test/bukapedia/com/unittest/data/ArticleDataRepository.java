package test.bukapedia.com.unittest.data;

import test.bukapedia.com.unittest.data.network.ArticleApi;
import test.bukapedia.com.unittest.domain.ArticleRepository;

public class ArticleDataRepository implements ArticleRepository {
    private ArticleDataSourceFactory articleDataSourceFactory;

    public ArticleDataRepository(ArticleDataSourceFactory articleDataSourceFactory) {
        this.articleDataSourceFactory = articleDataSourceFactory;
    }
}
