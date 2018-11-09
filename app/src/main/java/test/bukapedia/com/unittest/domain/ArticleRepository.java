package test.bukapedia.com.unittest.domain;

import java.util.List;

import io.reactivex.Observable;
import test.bukapedia.com.unittest.data.network.entity.ArticleResponse;
import test.bukapedia.com.unittest.domain.model.Article;

public interface ArticleRepository {
    Observable<List<Article>> getArticles(String source, String top);
}
