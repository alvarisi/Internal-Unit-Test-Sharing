package test.bukapedia.com.unittest.presentation.presenter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import test.bukapedia.com.unittest.data.network.exception.UnathorizedException;
import test.bukapedia.com.unittest.domain.ArticleRepository;
import test.bukapedia.com.unittest.domain.model.Article;
import test.bukapedia.com.unittest.presentation.ui.ArticleView;

public class ArticlePresenterImpl implements ArticlePresenter {
    private ArticleView articleView;
    private ArticleRepository articleRepository;

    public ArticlePresenterImpl(ArticleView articleView, ArticleRepository articleRepository) {
        this.articleView = articleView;
        this.articleRepository = articleRepository;
    }

    @Override
    public void onViewInitialized() {
        articleView.hideArticleList();
        articleView.showInitialLoading();
        articleRepository.getArticles(articleView.getArticleSource(), articleView.getSortType())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<List<Article>>() {
                    @Override
                    public void onNext(List<Article> articles) {
                        articleView.hideInitialLoading();
                        articleView.showArticleList();
                        articleView.renderArticles(articles);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        articleView.hideInitialLoading();
                        if (e instanceof UnathorizedException){
                            articleView.showErrorUnatorized();
                        }else {
                            articleView.showRetryView();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
