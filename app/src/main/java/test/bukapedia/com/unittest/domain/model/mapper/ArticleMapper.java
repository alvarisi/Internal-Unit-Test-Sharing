package test.bukapedia.com.unittest.domain.model.mapper;

import java.util.ArrayList;
import java.util.List;

import test.bukapedia.com.unittest.data.network.entity.ArticleEntity;
import test.bukapedia.com.unittest.domain.model.Article;

public class ArticleMapper {

    public Article transform(ArticleEntity entity) {
        Article article = null;
        if (entity != null) {
            article = new Article();
            article.setAuthor(entity.getAuthor());
            article.setTitle(entity.getTitle());
            article.setUrl(entity.getUrl());
        }
        return article;
    }

    public List<Article> transform(List<ArticleEntity> entities) {
        List<Article> articles = new ArrayList<>();
        Article article;
        for (ArticleEntity entity : entities) {
            article = transform(entity);
            if (article != null) {
                articles.add(article);
            }
        }
        return articles;
    }
}
