package com.codefish.android.taskmanager.component;

import com.codefish.android.taskmanager.activity.LoginActivity;
import com.codefish.android.taskmanager.module.AppModule;

import dagger.Component;

/**
 * Created by abedch on 2/11/2016.
 */
@Component
public interface AppComponent {



   void inject(LoginActivity loginActivity);

}
