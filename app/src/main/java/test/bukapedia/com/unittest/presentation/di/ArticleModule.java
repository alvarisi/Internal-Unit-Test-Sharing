package test.bukapedia.com.unittest.presentation.di;

import dagger.Module;
import dagger.Provides;
import test.bukapedia.com.unittest.data.network.ArticleApi;
import test.bukapedia.com.unittest.data.network.ArticleDataRepository;
import test.bukapedia.com.unittest.domain.ArticleRepository;
import test.bukapedia.com.unittest.presentation.presenter.ArticlePresenter;
import test.bukapedia.com.unittest.presentation.presenter.ArticlePresenterImpl;
import test.bukapedia.com.unittest.presentation.ui.ArticleView;

@Module
public class ArticleModule {
    private ArticleView articleView;

    public ArticleModule(ArticleView articleView) {
        this.articleView = articleView;
    }

    @Provides
    public ArticleRepository provideArticleRepository(ArticleApi articleApi) {
        return new ArticleDataRepository(articleApi);
    }

    @Provides
    public ArticlePresenter provideArticleView(ArticleRepository articleRepository) {
        return new ArticlePresenterImpl(articleView, articleRepository);
    }
}
