package com.codefish.android.taskmanager.interactor;

import com.codefish.android.taskmanager.presenter.ILoginPresenter;

/**
 * Created by abedch on 2/15/2016.
 */
public interface ILoginInteraction {


    void getUser(String username,String password,ILoginPresenter loginPresenter);

}
