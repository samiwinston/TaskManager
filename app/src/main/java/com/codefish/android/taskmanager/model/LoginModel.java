package com.codefish.android.taskmanager.model;

/**
 * Created by abedch on 5/1/2016.
 */
public class LoginModel {


    private AppUserBean userBean = new AppUserBean(1,"Abed Chmaytilli");

    private static LoginModel instance = new LoginModel();

    private LoginModel() {
        // private constructor for singletons
    }

    public static LoginModel getInstance()
    {
        return instance;
    }

    public AppUserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(AppUserBean userBean) {
        this.userBean = userBean;
    }
}
