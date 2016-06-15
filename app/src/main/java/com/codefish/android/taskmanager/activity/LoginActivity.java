package com.codefish.android.taskmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.codefish.android.taskmanager.fragment.LoginFragment;
import com.codefish.android.taskmanager.fragment.SingleFragmentActivity;
import com.codefish.android.taskmanager.model.LoginModel;
import com.codefish.android.taskmanager.model.ServiceModel;
import com.codefish.android.taskmanager.model.AppUserBean;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends SingleFragmentActivity {


    private LoginFragment fragment;
    private boolean mock = false;

    @Override
    protected Fragment createFragment() {
        fragment = new LoginFragment();
        return fragment;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (mock) navigateToTasksView();
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


        ServiceModel.getInstance().userService.getUser(username, password).enqueue(new Callback<AppUserBean>() {
            @Override
            public void onResponse(Call<AppUserBean> call, Response<AppUserBean> response) {
                if (response.isSuccessful()) {
                    AppUserBean user = response.body();
                    if (user.getId() > 0) {
                        LoginModel.getInstance().setUserBean(user);
                        navigateToTasksView();
                        finish();
                    } else {
                        fragment.showToast("Can not login, please check your username or password");
                    }


                } else {
                    fragment.showToast("Can not reach CodeFish: response is not successful");
                }

                fragment.hideProgressBar();

            }

            @Override
            public void onFailure(Call<AppUserBean> call, Throwable t) {
                fragment.showToast("Can not reach CodeFish: error "+t.getMessage());
                fragment.hideProgressBar();
                t.printStackTrace();
            }
        });

    }

}
