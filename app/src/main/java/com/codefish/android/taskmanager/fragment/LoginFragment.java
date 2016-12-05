package com.codefish.android.taskmanager.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.codefish.android.taskmanager.R;
import com.codefish.android.taskmanager.activity.LoginActivity;
import com.codefish.android.taskmanager.model.LoginModel;
import com.codefish.android.taskmanager.model.MobAppUserBean;
import com.codefish.android.taskmanager.model.ServiceModel;
import com.codefish.android.taskmanager.model.WidgetActionItemBean;
import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;


import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends Fragment implements View.OnClickListener {


    @Bind(R.id.userEdtView)
    public EditText userEdtView;
    @Bind(R.id.passEdtView)
    public EditText passEdtView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.loginBtn)
    public Button loginBtn;

    LoginActivity loginActivity;

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
        View view = inflater.inflate(R.layout.login_layout, container, false);
        ButterKnife.bind(this, view);

        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimary),
                android.graphics.PorterDuff.Mode.MULTIPLY);

        loginBtn.setOnClickListener(this);
        return view;
    }


    public void validateCredentials(String username, String password) {


        showProgressBar();


        if (username == null || username.length() == 0
                || password == null || password.length() == 0) {
            showToast("Please check your username or password");
            hideProgressBar();
            showControls();
            return;
        }
        getUser(username, password);

    }

    public void getUser(final String username, final String password) {


        ServiceModel.getInstance().userService.getUser(username, password).enqueue(new Callback<MobAppUserBean>() {
            @Override
            public void onResponse(Call<MobAppUserBean> call, Response<MobAppUserBean> response) {
                if (response.isSuccessful()) {
                    MobAppUserBean user = response.body();
                    if (user.getId() > 0) {
                        LoginModel.getInstance().setUserBean(user);
                        WidgetActionItemBean leaveActionBean = null;

                        if (user.getActionItems() != null && user.getActionItems().length > 0) {
                            for (WidgetActionItemBean actionBean : user.getActionItems()) {
                                if (actionBean.workflowName != null && actionBean.workflowName.equals("hrLeaveRequestWorkflow")) {
                                    leaveActionBean = actionBean;
                                    break;
                                }
                            }
                        }


                        loginActivity.navigateToTasksView(leaveActionBean);
                        loginActivity.finish();


                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        Gson gson = new Gson();
                        String json = gson.toJson(user);
                        editor.putString("MobAppUserBean", json);
                        editor.putString("email", user.getEmail());
                        editor.putString("username", user.getUsername());
                        editor.putString("name", user.getName());
                        editor.putInt("userId", user.getId());
                        editor.putString("userInitials", user.getName().charAt(0) + "" + user.getName().charAt(user.getName().lastIndexOf(' ') + 1));
                        editor.putString("password", password);
                        editor.commit();


                        // TODO: Move this to where you establish a user session
                        logUser(user.getUsername(), "Email Here", user.getName());


                    } else {
                        showToast("Can not login, please check your username or password");
                        showControls();
                    }


                } else {
                    try {
                        if (response.code() == 500 && response.errorBody().contentLength() < 500) {
                            showToast(response.errorBody().string());
                        } else {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        showToast("Illegal error " + response.code() + ", please contact the admin");
                    }
                    showControls();
                }

                hideProgressBar();

            }

            @Override
            public void onFailure(Call<MobAppUserBean> call, Throwable t) {
                showToast("Can not reach CodeFish");
                hideProgressBar();
                showControls();
                t.printStackTrace();

                SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();
            }
        });

    }



    @Override
    public void onResume() {
        super.onResume();

        if (isAdded()) {


            SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

            if (sharedpreferences != null) {
                String userName = sharedpreferences.getString("username", "");
                String password = sharedpreferences.getString("password", "");

                if (!userName.equals("") && !password.equals("") ) {

                    hideControls();
                    showProgressBar();
                    getUser(userName, password);

                }
            }
        }

    }

    private void logUser(String userName, String userEmail, String name) {
        // TODO: Use the current user's information
        // You can call any combination of these three methods
        Crashlytics.setUserIdentifier(name);
        Crashlytics.setUserEmail(userEmail);
        Crashlytics.setUserName(userName);
    }


    public void showControls() {
        userEdtView.setVisibility(View.VISIBLE);
        passEdtView.setVisibility(View.VISIBLE);
        loginBtn.setVisibility(View.VISIBLE);
    }

    public void hideControls() {
        userEdtView.setVisibility(View.INVISIBLE);
        passEdtView.setVisibility(View.INVISIBLE);
        loginBtn.setVisibility(View.INVISIBLE);
    }


    public void loginHandler() {
        validateCredentials(userEdtView.getText().toString(), passEdtView.getText().toString());
    }


    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }


    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void showToast(String msg) {
        if (getContext() != null) {
            Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        loginHandler();
    }

}