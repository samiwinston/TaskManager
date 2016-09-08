package com.codefish.android.taskmanager.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.codefish.android.taskmanager.fragment.LoginFragment;
import com.codefish.android.taskmanager.fragment.SingleFragmentActivity;
import com.codefish.android.taskmanager.model.LoginModel;
import com.codefish.android.taskmanager.model.MobAppUserBean;
import com.codefish.android.taskmanager.model.ServiceModel;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends SingleFragmentActivity {


    private LoginFragment fragment;
    private boolean mock = false;

    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    @Override
    protected Fragment createFragment() {
        fragment = new LoginFragment();
        return fragment;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (mock)
        {
            LoginModel.getInstance().setUserBean(new MobAppUserBean(1164,"Abed Chmaytilli"));
            navigateToTasksView();
        }
    }

    public void navigateToTasksView() {
        Intent intent = TasksListActivity.newInstance(this);
        startActivity(intent);
    }


    public void validateCredentials(String username, String password) {
        fragment.showProgressBar();


        if (username == null || username.length() == 0
                || password == null || password.length() == 0) {
            fragment.showToast("Please check your username or password");
            fragment.hideProgressBar();
            return;
        }
        getUser(username, password);
    }

    private void getUser(final String username, final String password) {


        ServiceModel.getInstance().userService.getUser(username, password).enqueue(new Callback<MobAppUserBean>() {
            @Override
            public void onResponse(Call<MobAppUserBean> call, Response<MobAppUserBean> response) {
                if (response.isSuccessful()) {
                    MobAppUserBean user = response.body();
                    if (user.getId() > 0) {
                        LoginModel.getInstance().setUserBean(user);
                        navigateToTasksView();
                        finish();

                        SharedPreferences.Editor editor = sharedpreferences.edit();

                        editor.putString("userName", user.getUsername());
                        editor.putString("password", password);
                        editor.commit();
                    } else {
                        fragment.showToast("Can not login, please check your username or password");
                    }


                } else {
                    fragment.showToast("Can not reach CodeFish: response is not successful");
                }

                fragment.hideProgressBar();

            }

            @Override
            public void onFailure(Call<MobAppUserBean> call, Throwable t) {
                fragment.showToast("Can not reach CodeFish: error "+t.getMessage());
                fragment.hideProgressBar();
                t.printStackTrace();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        String userName = sharedpreferences.getString("userName","");
        String password = sharedpreferences.getString("password","");

        if(userName !=null && userName.length()>0 && password !=null && password.length()>0)
        {
            getUser(userName,password);
        }

    }
}
