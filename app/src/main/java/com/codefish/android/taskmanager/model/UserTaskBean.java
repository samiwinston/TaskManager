package com.codefish.android.taskmanager.model;


import android.os.Bundle;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class UserTaskBean {

    public static final int TYPE_APPROVAL = 3;

    public UserTaskBean(int idWorkflowInstance) {
        this.idWorkflowInstance = idWorkflowInstance;
    }

    public UserTaskBean(String title, Date dueDate) {
        this.dueDate = dueDate;
        this.title = title;
    }

    public int idWorkflowInstance;
    public int idBaseEntity;

    public int idTask;
    public Date dueDate;
    public String description;
    public String ownerName;
    public Integer idOwner;
    public Integer idGroup;
    public Boolean isOpen = false;
    public int importance = 0;
    public Boolean requiresReview;
    public Boolean isReviewed;
    public Boolean hasForm;
    public Boolean isAssignee;
    public Boolean isOwner;
    public String groupName;
    public String categoryName;
    public Integer idCategory;
    public Integer taskType;
    public String currentState;


    public Boolean canReassign;
    public List<HashMap<String, Object>> assignees;
    public List<FollowerBean> followers;
    public ArrayList<UserTaskBean> children;


    public UserTaskBean(Bundle bundle) {
        this.title = (String) bundle.get("title");
        this.idTask = (Integer) bundle.get("idTask");
        this.idWorkflowInstance = (Integer) bundle.get("idWorkflowInstance");
        this.description = (String) bundle.get("description");
        this.dueDate = (Date) bundle.get("dueDate");
        this.ownerName = (String) bundle.get("ownerName");
        this.groupName = (String) bundle.get("groupName");
        this.categoryName = (String) bundle.get("categoryName");
        this.currentState = (String) bundle.get("currentState");
        this.idOwner = (Integer) bundle.get("idOwner");
        this.idGroup = (Integer) bundle.get("idGroup");
        this.taskType = (Integer) bundle.get("taskType");
        this.idCategory = (Integer) bundle.get("idCategory");
        this.importance = (Integer) bundle.get("importance");
        this.canReassign = (Boolean) bundle.get("canReassign");
        this.hasForm = (Boolean) bundle.get("hasForm");
        this.isOpen = (Boolean) bundle.get("isOpen");
        this.isOwner = (Boolean) bundle.get("isOwner");
        this.isAssignee = (Boolean) bundle.get("isAssignee");
        this.isReviewed = (Boolean) bundle.get("isReviewed");
        this.requiresReview = (Boolean) bundle.get("requiresReview");
        //this.followers = (List<FollowerBean>) bundle.getSerializable("followers");
    }

    public Bundle getBundle() {

        Bundle b = new Bundle();
        b.putString("title", this.title);
        b.putInt("idTask", idTask);
        b.putInt("idWorkflowInstance", idWorkflowInstance);
        b.putInt("importance", importance);
        b.putSerializable("dueDate", dueDate);
        b.putString("description", description);
        b.putString("ownerName", ownerName);
        b.putString("groupName", groupName);
        b.putString("currentState", currentState);
        b.putString("categoryName", categoryName);
        b.putInt("idCategory", idCategory == null ? 0 : idCategory);
        b.putInt("idOwner", idOwner == null ? 0 : idOwner);
        b.putInt("taskType", taskType == null ? 0 : taskType);
        b.putInt("idGroup", idGroup == null ? 0 : idGroup);
        b.putBoolean("canReassign", canReassign);
        b.putBoolean("hasForm", hasForm);
        b.putBoolean("isOpen", isOpen);
        b.putBoolean("isAssignee", isAssignee);
        b.putBoolean("isOwner", isOwner);
        b.putBoolean("requiresReview", requiresReview);
        b.putBoolean("isReviewed", isReviewed);

        return b;
    }


    public String title;

    public String getOwnerInitals() {
        if (ownerName != null) {
            return this.ownerName.charAt(0) + "" + this.ownerName.charAt(this.ownerName.lastIndexOf(' ') + 1);
        }
        return "";
    }


    public boolean hasNoFollowers() {

        return followers == null || followers.size() == 0;

    }

    public String getCurrentStateLbl(){

        if(requiresReview)
        return currentState+" (Pend. Approval)";

        return currentState;
    }

    public boolean canTakeAction()
    {
        return  (isOwner || (isAssignee && idOwner==null)) && isOpen;
    }
}