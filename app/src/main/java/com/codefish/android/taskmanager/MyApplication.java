package com.codefish.android.taskmanager;

import android.app.Application;


import com.codefish.android.taskmanager.daggerConfig.component.AppComponent;
import com.codefish.android.taskmanager.daggerConfig.component.DaggerAppComponent;
import com.codefish.android.taskmanager.daggerConfig.module.NetModule;

import okhttp3.OkHttpClient;


/**
 * Created by abedch on 2/11/2016.
 */
public class MyApplication extends Application {

    private Boolean mock = false;

    private static AppComponent appComponent;


    public void onCreate() {
        super.onCreate();
        initializeInjector();

    }

    private void initializeInjector() {

        appComponent = DaggerAppComponent.builder()
                .netModule(new NetModule(mock?getResources().getString(R.string.baseUrlAchDev):
                        getResources().getString(R.string.baseUrlAcHTest),new OkHttpClient()))
                .build();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

}
