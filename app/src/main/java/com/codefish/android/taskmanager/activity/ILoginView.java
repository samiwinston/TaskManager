package com.codefish.android.taskmanager.activity;

/**
 * Created by abedch on 2/5/2016.
 */
public interface ILoginView {


    void showProgressBar();
    void hideProgressBar();
    void navigateToTasksView();
    void showErrorMessage(String msg);
}
