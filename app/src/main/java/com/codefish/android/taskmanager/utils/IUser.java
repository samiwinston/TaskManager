package com.codefish.android.taskmanager.utils;

import com.codefish.android.taskmanager.model.AppUser;
import retrofit2.http.GET;
import retrofit2.Callback;
/**
 * Created by abedch on 2/2/2016.
 */
public interface IUser {

    @GET("/checkUser")
    public void getUser(Callback<AppUser> response);

}
