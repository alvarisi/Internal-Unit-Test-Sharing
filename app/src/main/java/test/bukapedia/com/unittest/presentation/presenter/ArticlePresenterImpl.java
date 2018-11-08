package test.bukapedia.com.unittest.presentation.presenter;

import test.bukapedia.com.unittest.domain.ArticleRepository;
import test.bukapedia.com.unittest.presentation.ui.ArticleView;

public class ArticlePresenterImpl implements ArticlePresenter {
    private ArticleView articleView;
    private ArticleRepository articleRepository;

    public ArticlePresenterImpl(ArticleView articleView, ArticleRepository articleRepository) {
        this.articleView = articleView;
        this.articleRepository = articleRepository;
    }
}
