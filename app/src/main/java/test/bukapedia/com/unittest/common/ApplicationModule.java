package test.bukapedia.com.unittest.common;


import android.util.Log;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import test.bukapedia.com.unittest.BuildConfig;
import test.bukapedia.com.unittest.data.network.ArticleApi;
import test.bukapedia.com.unittest.data.network.ArticleUrl;

@Module
public class ApplicationModule {
    private static final String TAG = "ArticleTag";

    @Provides
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(
                new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Log.v(TAG, message);
                    }
                }
        );
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @Provides
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor) {
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .connectTimeout(45L, TimeUnit.SECONDS)
                .readTimeout(45L, TimeUnit.SECONDS)
                .writeTimeout(45L, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            client.addInterceptor(httpLoggingInterceptor);
        }
        return client.build();
    }

    @Provides
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(ArticleUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    ArticleApi provideApi(Retrofit retrofit) {
        return retrofit.create(ArticleApi.class);
    }
}
