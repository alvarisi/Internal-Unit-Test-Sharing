package test.bukapedia.com.unittest.presentation.ui;

import java.util.List;

import test.bukapedia.com.unittest.domain.model.Article;

public interface ArticleView {
    String getArticleSource();

    String getSortType();

    void hideArticleList();

    void showInitialLoading();

    void renderArticles(List<Article> articles);

    void hideInitialLoading();

    void showArticleList();

    void showErrorUnatorized();

    void showRetryView();
}
