package test.bukapedia.com.unittest.presentation.di;


import dagger.Component;
import test.bukapedia.com.unittest.common.ApplicationComponent;
import test.bukapedia.com.unittest.presentation.ui.NewsActivity;

@Component(modules = ArticleModule.class, dependencies = ApplicationComponent.class)
public interface ArticleComponent {

    void inject(NewsActivity newsActivity);
}
