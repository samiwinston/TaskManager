package com.codefish.android.taskmanager.presenter;

import com.codefish.android.taskmanager.activity.ILoginView;

/**
 * Created by abedch on 2/2/2016.
 */
public interface ILoginPresenter {

    void validateCredentials(String username, String password);
    void setLoginView(ILoginView loginView);
    void showErrorMsg(String msg);
    void onLoginSuccess();



}
