package com.movies.dag;

import android.app.Application;

public class MyApplication extends Application {
    AppComponent mApiComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mApiComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .appClient(new AppClient())
                .build();
    }

    public AppComponent getAppComponent() {
        return mApiComponent;
    }
}
