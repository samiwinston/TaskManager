package com.codefish.android.taskmanager.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.codefish.android.taskmanager.R;
import com.codefish.android.taskmanager.activity.LoginActivity;

import butterknife.Bind;
import butterknife.ButterKnife;


public class LoginSplashFragment extends Fragment{



    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    LoginActivity loginActivity;


    private Boolean mockValidation = false;

   @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            loginActivity = (LoginActivity) context;
        } catch (ClassCastException castException) {
            /** The activity does not implement the listener. */
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_splash_layout,container,false);
        ButterKnife.bind(this,view);
        return view;
    }








    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }



    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }


    public void navigateToTasksView() {

    }

    public void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }


}