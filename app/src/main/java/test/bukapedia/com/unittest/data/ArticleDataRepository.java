package test.bukapedia.com.unittest.data;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import test.bukapedia.com.unittest.data.network.entity.ArticleResponse;
import test.bukapedia.com.unittest.domain.ArticleRepository;
import test.bukapedia.com.unittest.domain.model.Article;
import test.bukapedia.com.unittest.domain.model.mapper.ArticleMapper;

public class ArticleDataRepository implements ArticleRepository {
    private ArticleDataSourceFactory articleDataSourceFactory;
    private ArticleMapper articleMapper;

    public ArticleDataRepository(ArticleDataSourceFactory articleDataSourceFactory,
                                 ArticleMapper articleMapper) {
        this.articleDataSourceFactory = articleDataSourceFactory;
        this.articleMapper = articleMapper;
    }

    @Override
    public Observable<List<Article>> getArticles(String source, String top) {
        return articleDataSourceFactory.getArticles(source, top)
                .map(new Function<ArticleResponse, List<Article>>() {
                    @Override
                    public List<Article> apply(ArticleResponse articleResponse) throws Exception {
                        return articleMapper.transform(articleResponse.getArticles());
                    }
                });
    }
}
