package com.codefish.android.taskmanager.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.codefish.android.taskmanager.fragment.LoginFragment;
import com.codefish.android.taskmanager.fragment.SingleFragmentActivity;
import com.codefish.android.taskmanager.model.LoginModel;
import com.codefish.android.taskmanager.model.MobAppUserBean;
import com.codefish.android.taskmanager.model.ServiceModel;
import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;


import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends SingleFragmentActivity {


    private LoginFragment fragment;
    private boolean mock = false;

    public static final String MyPREFERENCES = "MyPrefs" ;

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
        if (mock)
        {
           // LoginModel.getInstance().setUserBean(new MobAppUserBean(1164,"Abed Chmaytilli"));
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


                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        Gson gson = new Gson();
                        String json = gson.toJson(user);
                        editor.putString("MobAppUserBean", json);
                        editor.putString("email",user.getEmail());
                        editor.putString("username",user.getUsername());
                        editor.putString("name", user.getName());
                        editor.putInt("userId", user.getId());
                        editor.putString("userInitials", user.getName().charAt(0)+""+user.getName().charAt(user.getName().lastIndexOf(' ')+1));
                        editor.putString("password", password);
                        editor.commit();


                        // TODO: Move this to where you establish a user session
                        logUser(user.getUsername(),"Email Here",user.getName());


                    } else {
                        fragment.showToast("Can not login, please check your username or password");
                    }


                } else {
                    try {
                        if(response.code()==404 && response.errorBody().contentLength()<200)
                        {
                            fragment.showToast(response.errorBody().string());
                        }
                        else
                        {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        fragment.showToast("Illegal error, please contact the admin");
                    }

                }

                fragment.hideProgressBar();

            }

            @Override
            public void onFailure(Call<MobAppUserBean> call, Throwable t) {
                fragment.showToast("Can not reach CodeFish");
                fragment.hideProgressBar();
                t.printStackTrace();
            }
        });

    }


    private void logUser(String userName,String userEmail, String name) {
        // TODO: Use the current user's information
        // You can call any combination of these three methods
        Crashlytics.setUserIdentifier(name);
        Crashlytics.setUserEmail(userEmail);
        Crashlytics.setUserName(userName);
    }


    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        if(sharedpreferences!=null)
        {
            String userName = sharedpreferences.getString("username","");
            String password = sharedpreferences.getString("password","");

            if(userName !=null && userName.length()>0 && password !=null && password.length()>0)
            {
                fragment.showProgressBar();
                getUser(userName,password);
            }
        }
    }
}
