package com.codefish.android.taskmanager;

import android.app.Application;

import com.codefish.android.taskmanager.activity.LoginActivity;
import com.codefish.android.taskmanager.component.AppComponent;
import com.codefish.android.taskmanager.component.DaggerAppComponent;

/**
 * Created by abedch on 2/11/2016.
 */
public class MyApplication extends Application {

    private static AppComponent component;

    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder().build();
    }


    public static void inject(LoginActivity target) {
        component.inject(target);
    }

}
