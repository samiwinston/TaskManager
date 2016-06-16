package com.codefish.android.taskmanager.model;


public class MobAppUserBean {

    private int id;
    private String name;
    private String email;
    private String username;

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



    public String getInitials(){
        if(name!=null)
        {
            return this.name.charAt(0)+""+this.name.charAt(this.name.lastIndexOf(' ')+1);
        }
        return "";
    }
}
