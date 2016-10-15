package com.codefish.android.taskmanager;

import android.app.Application;


import com.codefish.android.taskmanager.daggerConfig.component.AppComponent;
import com.codefish.android.taskmanager.daggerConfig.component.DaggerAppComponent;
import com.codefish.android.taskmanager.daggerConfig.module.NetModule;

import com.crashlytics.android.Crashlytics;

import java.util.HashMap;


import io.fabric.sdk.android.Fabric;
import okhttp3.OkHttpClient;


public class MyApplication extends Application {

    private static AppComponent appComponent;


    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        initializeInjector();
    }

    private void initializeInjector() {

        HashMap<Integer,String> controllerMap = new HashMap<>();
        controllerMap.put(NetModule.TASK_MANAGER_MODULE,getResources().getString(R.string.frmworkUrlEP));
        controllerMap.put(NetModule.HR_MODULE,getResources().getString(R.string.hrUrlEP));


        appComponent = DaggerAppComponent.builder()
                .netModule(new NetModule(BuildConfig.HOST,controllerMap,new OkHttpClient()))
                .build();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

}
