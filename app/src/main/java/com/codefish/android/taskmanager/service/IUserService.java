package com.codefish.android.taskmanager.service;

import com.codefish.android.taskmanager.model.AppUserBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by abedch on 2/2/2016.
 */
public interface IUserService {

    @GET("checkUser")
    Call<AppUserBean> getUser(@Query("username") String username, @Query("password") String password);

    @GET("testMvc")
    Call<String> testMvc();
}
