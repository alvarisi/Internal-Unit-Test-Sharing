package test.bukapedia.com.unittest.presentation.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import test.bukapedia.com.unittest.NewsApplication;
import test.bukapedia.com.unittest.R;
import test.bukapedia.com.unittest.domain.model.Article;
import test.bukapedia.com.unittest.presentation.adapter.ArticleAdapter;
import test.bukapedia.com.unittest.presentation.di.ArticleComponent;
import test.bukapedia.com.unittest.presentation.di.ArticleModule;
import test.bukapedia.com.unittest.presentation.di.DaggerArticleComponent;
import test.bukapedia.com.unittest.presentation.presenter.ArticlePresenter;

public class NewsActivity extends AppCompatActivity implements ArticleView {
    private RecyclerView articlesRecyclerView;
    private ProgressBar mainProgressBar;
    private AppCompatButton retryButton;
    private ArticleAdapter articleAdapter;

    private static String SOURCE_BBC = "bbc-news";
    private static String SORT_TOP = "top";


    @Inject
    ArticlePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        articlesRecyclerView = findViewById(R.id.rv_articles);
        mainProgressBar = findViewById(R.id.pb_main);
        retryButton = findViewById(R.id.btn_retry);
        ArticleComponent component = DaggerArticleComponent.builder()
                .applicationComponent(((NewsApplication) getApplication()).getApplicationComponent())
                .articleModule(new ArticleModule(this))
                .build();
        component.inject(this);
        articleAdapter = new ArticleAdapter();
        presenter.onViewInitialized();
    }

    @Override
    public String getArticleSource() {
        return SOURCE_BBC;
    }

    @Override
    public String getSortType() {
        return SORT_TOP;
    }

    @Override
    public void hideArticleList() {
        articlesRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showInitialLoading() {
        mainProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void renderArticles(List<Article> articles) {
        articlesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        articlesRecyclerView.setAdapter(articleAdapter);
        articleAdapter.addArticles(articles);
        articleAdapter.notifyDataSetChanged();
    }

    @Override
    public void hideInitialLoading() {
        mainProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showArticleList() {
        articlesRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorUnatorized() {
        Toast.makeText(this, "no access", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRetryView() {
        retryButton.setVisibility(View.VISIBLE);
    }
}
