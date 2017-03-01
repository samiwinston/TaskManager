package com.codefish.android.taskmanager.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.codefish.android.taskmanager.fragment.LoginFragment;
import com.codefish.android.taskmanager.fragment.SingleFragmentActivity;
import com.codefish.android.taskmanager.fragment.TasksListFragment;
import com.codefish.android.taskmanager.model.LoginModel;
import com.codefish.android.taskmanager.model.MobAppUserBean;
import com.codefish.android.taskmanager.model.ServiceModel;
import com.codefish.android.taskmanager.model.WidgetActionItemBean;
import com.google.common.primitives.Booleans;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;


import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends SingleFragmentActivity {


    private LoginFragment loginFragment;
    //private boolean mock = false;

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


        //String IID_TOKEN = FirebaseInstanceId.getInstance().getToken();
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
//        if (mock)
//        {
//           // LoginModel.getInstance().setUserBean(new MobAppUserBean(1164,"Abed Chmaytilli"));
//            navigateToTasksView(null,null);
//        }
    }

    public void navigateToTasksView(WidgetActionItemBean leaveActionItemBean, WidgetActionItemBean[]  widgetActionItems) {
        Intent intent = TasksListActivity.newInstance(this);
        intent.putParcelableArrayListExtra("widgetActionItems", new ArrayList<>(Arrays.asList(widgetActionItems)));
        intent.putExtra("leaveActionItemBean",leaveActionItemBean);
        startActivity(intent);
    }




    /*@Override
    protected void onResume() {
        super.onResume();*/

      /*  SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        if(sharedpreferences!=null)
        {
            String userName = sharedpreferences.getString("username","");
            String password = sharedpreferences.getString("password","");

            if(userName !=null && userName.length()>0 && password !=null && password.length()>0)
            {

                loginFragment.hideControls();
                loginFragment.showProgressBar();
                loginFragment.getUser(userName,password);

            }
        }*/
    //}




}
