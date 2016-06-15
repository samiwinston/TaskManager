package com.codefish.android.taskmanager.model;

/**
 * Created by abedch on 2/22/2016.
 */
public class UserTask {

    private String name;
    private String phone;

    public UserTask(String name,String phone) {
        this.name = name;
        this.setPhone(phone);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
