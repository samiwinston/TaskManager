package com.codefish.android.taskmanager.model;


import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.gson.Gson;

import java.util.Date;

public class MobAppUserBean {

    private int id;
    private String name;
    private String email;
    private String username;
    private WidgetActionItemBean[] actionItems;

    public MobAppUserBean(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Bundle getBundle() {

        Bundle b = new Bundle();
        b.putInt("id", id);
        b.putString("username", username);
        b.putString("name", name);
        b.putString("email", email);


        return b;
    }


    public MobAppUserBean(Bundle bundle) {
        this.id = (Integer) bundle.get("id");
        this.username = (String) bundle.get("username");
        this.name = (String) bundle.get("name");
        this.email = (String) bundle.get("email");
    }

    public MobAppUserBean(SharedPreferences preferences) {
        Gson gson = new Gson();
        String json = preferences.getString("MobAppUserBean", "");
        MobAppUserBean obj = gson.fromJson(json, MobAppUserBean.class);
        this.id = obj.getId();
        this.name = obj.getName();
        this.email = obj.getEmail();
        this.username = obj.getUsername();
    }


    public String getInitials(){
        if(name!=null)
        {
            return this.name.charAt(0)+""+this.name.charAt(this.name.lastIndexOf(' ')+1);
        }
        return "";
    }

    public WidgetActionItemBean[] getActionItems() {
        return actionItems;
    }

    public void setActionItems(WidgetActionItemBean[] actionItems) {
        this.actionItems = actionItems;
    }
}
