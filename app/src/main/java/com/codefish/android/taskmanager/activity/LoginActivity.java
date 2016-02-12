package com.codefish.android.taskmanager.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.codefish.android.taskmanager.MyApplication;
import com.codefish.android.taskmanager.R;
import com.codefish.android.taskmanager.model.AppUser;
import com.codefish.android.taskmanager.presenter.LoginPresenterImpl;
import com.codefish.android.taskmanager.utils.IUser;


import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class LoginActivity extends AppCompatActivity implements ILoginView {


    @Bind(R.id.userEdtView)
    EditText userEdtView;
    @Bind(R.id.passEdtView)
    EditText passEdtView;
    @Bind(R.id.loginProgressBar)
    ProgressBar loginProgressBar;

    @Inject
    LoginPresenterImpl loginPresenter;


    private Boolean mockValidation = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        MyApplication.inject(this);


        String response = loginPresenter != null ? "Not Null" : "Is Null";
        Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
    }


    public void loginHandler(View view) {

        loginPresenter.validateCredentials(userEdtView.getText().toString(),passEdtView.getText().toString());
    }


    private void requestData() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.baseUrl))
                .build();

        IUser user = retrofit.create(IUser.class);
        user.getUser(new Callback<AppUser>() {
            @Override
            public void onResponse(Response<AppUser> response) {
                AppUser user = response.body();
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }


    @Override
    public void toggleProgressBar(int visibilityType) {
        loginProgressBar.setVisibility(visibilityType);
    }

    @Override
    public void navigateToTasksView() {

    }

    @Override
    public void showErrorMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


}
