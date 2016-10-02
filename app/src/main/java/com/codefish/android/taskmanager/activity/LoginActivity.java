package com.codefish.android.taskmanager.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.codefish.android.taskmanager.fragment.LoginFragment;
import com.codefish.android.taskmanager.fragment.SingleFragmentActivity;
import com.codefish.android.taskmanager.fragment.TasksListFragment;
import com.codefish.android.taskmanager.model.LoginModel;
import com.codefish.android.taskmanager.model.MobAppUserBean;
import com.codefish.android.taskmanager.model.ServiceModel;
import com.codefish.android.taskmanager.model.WidgetActionItemBean;
import com.crashlytics.android.Crashlytics;
import com.google.common.primitives.Booleans;
import com.google.gson.Gson;


import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends SingleFragmentActivity {


    private LoginFragment loginFragment;
    private boolean mock = false;

    public static final String MyPREFERENCES = "MyPrefs" ;

    @Override
    protected Fragment createFragment() {
        loginFragment = new LoginFragment();
        return loginFragment;
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            //Restore the fragment's instance
            loginFragment = (LoginFragment) getSupportFragmentManager().getFragment(savedInstanceState, "loginFragment");
        }

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {
            //Restore the fragment's instance
            loginFragment = (LoginFragment) getSupportFragmentManager().getFragment(savedInstanceState, "loginFragment");

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

            //Save the fragment's instance
            getSupportFragmentManager().putFragment(outState, "loginFragment", loginFragment);

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (mock)
        {
           // LoginModel.getInstance().setUserBean(new MobAppUserBean(1164,"Abed Chmaytilli"));
            navigateToTasksView(null);
        }
    }

    public void navigateToTasksView(WidgetActionItemBean leaveActionItemBean) {
        Intent intent = TasksListActivity.newInstance(this);
        intent.putExtra("leaveActionItemBean",leaveActionItemBean);
        startActivity(intent);
    }


    public void validateCredentials(String username, String password) {
        loginFragment.showProgressBar();


        if (username == null || username.length() == 0
                || password == null || password.length() == 0) {
            loginFragment.showToast("Please check your username or password");
            loginFragment.hideProgressBar();
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
                        WidgetActionItemBean leaveActionBean = null;

                        if(user.getActionItems()!=null && user.getActionItems().length>0)
                        {
                            for (WidgetActionItemBean actionBean:user.getActionItems()) {
                                if(actionBean.workflowName!=null && actionBean.workflowName.equals("hrLeaveRequestWorkflow"))
                                {
                                    leaveActionBean = actionBean;
                                    break;
                                }
                            }
                        }


                        navigateToTasksView(leaveActionBean);
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
                        loginFragment.showToast("Can not login, please check your username or password");
                    }


                } else {
                    try {
                        if(response.code()==500 && response.errorBody().contentLength()<500)
                        {
                            loginFragment.showToast(response.errorBody().string());
                        }
                        else
                        {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        loginFragment.showToast("Illegal error "+response.code()+", please contact the admin");
                    }

                }

                loginFragment.hideProgressBar();

            }

            @Override
            public void onFailure(Call<MobAppUserBean> call, Throwable t) {
                loginFragment.showToast("Can not reach CodeFish");
                loginFragment.hideProgressBar();
                t.printStackTrace();

                SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();
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
                loginFragment.userEdtView.setText(userName);
                loginFragment.passEdtView.setText(password);
                loginFragment.showProgressBar();
                getUser(userName,password);

            }
        }
    }
}
