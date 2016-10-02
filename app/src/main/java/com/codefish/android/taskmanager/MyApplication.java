package com.codefish.android.taskmanager;

import android.app.Application;


import com.codefish.android.taskmanager.daggerConfig.component.AppComponent;
import com.codefish.android.taskmanager.daggerConfig.component.DaggerAppComponent;
import com.codefish.android.taskmanager.daggerConfig.module.NetModule;

import com.crashlytics.android.Crashlytics;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import io.fabric.sdk.android.Fabric;
import okhttp3.OkHttpClient;


/**
 * Created by abedch on 2/11/2016.
 */
public class MyApplication extends Application {

    private Boolean mock = true;

    private static AppComponent appComponent;


    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        initializeInjector();
    }

    private void initializeInjector() {

        Map<String,String> map = new HashMap<>();
        map.put("frmwork",getResources().getString(R.string.frmworkUrlEP));
        map.put("hr",getResources().getString(R.string.hrUrlEP));

        appComponent = DaggerAppComponent.builder()
                .netModule(new NetModule(mock?getResources().getString(R.string.baseUrlDev):
                        getResources().getString(R.string.baseUrlAcH),getResources().getString(R.string.baseUrlHrDev),new OkHttpClient()))
                .build();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

}
