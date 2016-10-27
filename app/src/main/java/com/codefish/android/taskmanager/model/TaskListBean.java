package com.codefish.android.taskmanager.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskListBean implements Serializable{


    public TaskListBean(){
        followers = new ArrayList<>();
        tasks = new ArrayList<>();
    }

    public List<TaskListSubTaskBean> tasks;
    public List<FollowerBean> followers;
    public Integer idGroup;
    public Integer idOwner;
    public Integer idSubmittedBy;
    public String description;
    public Date dueDate;
    public String title;
    public String ownerName;


}
