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

    public final static int DEV = 1;
    public final static int DEV_CLOUD = 2;
    public final static int PROD = 3;
    public final static int PROD_TEST = 4;
    public final static int DEV_HOT_SPOT = 5;

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
        String baseUrl = "";
        Integer buildType = DEV_HOT_SPOT;

        switch (buildType)
        {
            case DEV:
                baseUrl = getResources().getString(R.string.baseUrlDev);
                break;
            case DEV_HOT_SPOT:
                baseUrl = getResources().getString(R.string.baseUrlHotSpot);
                break;
            case DEV_CLOUD:
                baseUrl = getResources().getString(R.string.baseUrlCloudTest);
                break;
            case PROD:
                baseUrl = getResources().getString(R.string.baseUrlAcHProd);
                break;
            case PROD_TEST:
                baseUrl = getResources().getString(R.string.baseUrlAcHTest);
                break;
            default:

        }

        appComponent = DaggerAppComponent.builder()
                .netModule(new NetModule(baseUrl,controllerMap,new OkHttpClient()))
                .build();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

}
