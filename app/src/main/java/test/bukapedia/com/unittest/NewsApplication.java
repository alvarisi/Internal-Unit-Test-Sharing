package test.bukapedia.com.unittest;

import android.app.Application;

import test.bukapedia.com.unittest.common.ApplicationComponent;
import test.bukapedia.com.unittest.common.DaggerApplicationComponent;

public class NewsApplication extends Application {
    protected static ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public ApplicationComponent getApplicationComponent(){
        if (applicationComponent == null){
            applicationComponent =  DaggerApplicationComponent.builder()
                    .build();
        }
        return applicationComponent;
    }
}
