package test.bukapedia.com.unittest.presentation.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import javax.inject.Inject;

import test.bukapedia.com.unittest.NewsApplication;
import test.bukapedia.com.unittest.R;
import test.bukapedia.com.unittest.presentation.di.ArticleComponent;
import test.bukapedia.com.unittest.presentation.di.ArticleModule;
import test.bukapedia.com.unittest.presentation.di.DaggerArticleComponent;
import test.bukapedia.com.unittest.presentation.presenter.ArticlePresenter;

public class NewsActivity extends AppCompatActivity implements ArticleView {
    private RecyclerView articlesRecyclerView;
    private ProgressBar mainProgressBar;
    private AppCompatButton retryButton;

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
    }
}
