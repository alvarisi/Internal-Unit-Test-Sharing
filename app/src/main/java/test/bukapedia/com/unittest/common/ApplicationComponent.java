package test.bukapedia.com.unittest.common;

import dagger.Component;
import test.bukapedia.com.unittest.data.network.ArticleApi;

@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    ArticleApi articleApi();
}
