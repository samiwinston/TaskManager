package com.codefish.android.taskmanager.interactor;

import com.codefish.android.taskmanager.MyApplication;
import com.codefish.android.taskmanager.model.MobAppUserBean;
import com.codefish.android.taskmanager.model.MobUserTaskBean;
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

        userService.getUser(username, password).enqueue(new Callback<MobAppUserBean>() {
            @Override
            public void onResponse(Call<MobAppUserBean> call, Response<MobAppUserBean> response) {
                if(response.isSuccessful())
                {
                    MobAppUserBean user = response.body();
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
            public void onFailure(Call<MobAppUserBean> call, Throwable t) {
                loginPresenter.showErrorMsg("Failure "+t.getMessage());
            }
        });

    }


}
