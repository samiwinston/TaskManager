package com.codefish.android.taskmanager.activity;

import android.widget.ProgressBar;

/**
 * Created by abedch on 2/5/2016.
 */
public interface ILoginView {


    public void toggleProgressBar(int visibility);
    public void navigateToTasksView();
    public void showErrorMessage(String msg);
}
