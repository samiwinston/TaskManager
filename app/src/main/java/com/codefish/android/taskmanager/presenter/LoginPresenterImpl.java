package com.codefish.android.taskmanager.presenter;

import com.codefish.android.taskmanager.activity.ILoginView;
import com.codefish.android.taskmanager.interactor.ILoginInteraction;

/**
 * Created by abedch on 2/9/2016.
 */
public class LoginPresenterImpl implements ILoginPresenter {


    ILoginInteraction loginInteraction;

    private ILoginView loginView;

    public LoginPresenterImpl(ILoginInteraction loginInteraction) {
        this.loginInteraction = loginInteraction;
    }

    @Override
    public void validateCredentials(String username, String password) {
        loginView.showProgressBar();

        if (username == null || username.length() == 0
                || password == null || password.length() == 0) {
            loginView.showErrorMessage("Wrong Credentials!!");
            return;
        }

        loginInteraction.getUser(username, password, this);
    }

    @Override
    public void setLoginView(ILoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void showErrorMsg(String msg) {
        loginView.showErrorMessage(msg);
        loginView.hideProgressBar();
    }

    @Override
    public void onLoginSuccess() {
        loginView.navigateToTasksView();
        loginView.hideProgressBar();
    }

}
