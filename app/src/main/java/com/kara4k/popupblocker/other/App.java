package com.kara4k.popupblocker.other;


import android.app.Application;

import com.kara4k.popupblocker.di.AppComponent;
import com.kara4k.popupblocker.di.DaggerAppComponent;
import com.kara4k.popupblocker.di.modules.AppModule;

public class App extends Application {

    AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initComponents();
    }

    private void initComponents() {
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(getApplicationContext()))
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
