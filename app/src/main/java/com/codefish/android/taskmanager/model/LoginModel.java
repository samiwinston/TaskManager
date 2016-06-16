package com.codefish.android.taskmanager.model;

/**
 * Created by abedch on 5/1/2016.
 */
public class LoginModel {


    private MobAppUserBean userBean ;

    private static LoginModel instance = new LoginModel();

    private LoginModel() {
        // private constructor for singletons
    }

    public static LoginModel getInstance()
    {
        return instance;
    }

    public MobAppUserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(MobAppUserBean userBean) {
        this.userBean = userBean;
    }
}
