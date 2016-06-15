package com.codefish.android.taskmanager.interactor;

import com.codefish.android.taskmanager.MyApplication;
import com.codefish.android.taskmanager.model.AppUserBean;
import com.codefish.android.taskmanager.model.LoginModel;
import com.codefish.android.taskmanager.presenter.ILoginPresenter;
import com.codefish.android.taskmanager.service.IUserService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by abedch on 2/15/2016.
 */
public class LoginInteractionImpl implements ILoginInteraction {


    @Inject
    IUserService userService;

    //@Inject
    //ILoginPresenter loginPresenter;

    public LoginInteractionImpl() {
        MyApplication.getAppComponent().inject(this);
    }

    @Override
    public void getUser(final String username,final String password, final ILoginPresenter loginPresenter) {

        userService.getUser(username, password).enqueue(new Callback<AppUserBean>() {
            @Override
            public void onResponse(Call<AppUserBean> call, Response<AppUserBean> response) {
                if(response.isSuccessful())
                {
                    AppUserBean user = response.body();
                    if(user!=null)
                    {
                        LoginModel.getInstance().setUserBean(user);
                    }
                }
                else
                {
                    loginPresenter.showErrorMsg("Can not reach CodeFish");
                }

            }

            @Override
            public void onFailure(Call<AppUserBean> call, Throwable t) {
                loginPresenter.showErrorMsg("Failure "+t.getMessage());
            }
        });

    }


}
