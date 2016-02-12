package com.codefish.android.taskmanager.utils;

import com.codefish.android.taskmanager.model.AppUser;

import retrofit2.Callback;

/**
 * Created by abedch on 2/2/2016.
 */
public interface ITask {

    public void getTaskById();
    public void getOwnedTasks();
    public void getTasksByProject();

}
