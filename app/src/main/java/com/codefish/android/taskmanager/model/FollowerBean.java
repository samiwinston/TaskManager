package com.codefish.android.taskmanager.model;

import android.os.Bundle;

import java.util.HashMap;

public class FollowerBean {

    private String email;
    private Integer id;
    private Integer idWorkflowInstance;
    private Integer idAppUser;
    private Boolean isSupervisor;
    private Boolean isAssignee;
    private Boolean isRead;
    private Boolean isCreator;
    private Boolean isOwner;
    private Boolean isActive;
    private Boolean disableNotifications;
    private Boolean enableTimeTracking;
    private String name;

    public Bundle getBundle(){

        Bundle b = new Bundle();
        b.putString("name",this.name);
        b.putInt("id",id);
        b.putInt("idWorkflowInstance",idWorkflowInstance);
        b.putInt("idAppUser",idAppUser);
        b.putBoolean("isSupervisor",isSupervisor);
        b.putBoolean("isAssignee",isAssignee);
        b.putBoolean("isRead",isRead);
        b.putBoolean("isCreator",isCreator);
        b.putBoolean("isOwner",isOwner);
        b.putBoolean("isActive",isActive);
        b.putBoolean("disableNotifications",disableNotifications);
        b.putBoolean("enableTimeTracking",enableTimeTracking);



        return b;
    }

    public Bundle getBundle(HashMap<String,Object> item){

        Bundle b = new Bundle();
        b.putString("name",item.get("name").toString());
        Double intValue = (Double)item.get("id");
        b.putInt("idAppUser",intValue.intValue());
        b.putString("email",item.get("email").toString());


        return b;
    }

    public FollowerBean() {

    }

    public FollowerBean(HashMap<String, Object> item) {
        this.name = item.get("name").toString();
        this.email = item.get("email") != null ? item.get("email").toString() : "";
        this.idAppUser = item.get("idAppUser") != null ? (Integer) item.get("idAppUser") : null;
    }

    public FollowerBean(Bundle b) {
        this.name = b.getString("name");
        this.idAppUser = b.getInt("idAppUser");
        this.email = b.getString("email");
    }

/*	public FollowerBean(GenericFollower follower) {
		setIdAppUser(follower.getAppUser().getId());
		id = follower.getId();
		name = follower.getAppUser().getName();
		email = follower.getAppUser().getEmail();
		this.setDisableNotifications(follower.getDisableNotifications());
		this.isSupervisor = follower.getIsSupervisor();
		this.isAssignee = follower.getIsAssignee();
		this.isCreator = follower.getIsCreator();
		this.isOwner = follower.getIsOwner();
		this.isActive = follower.getIsActive();
		this.enableTimeTracking = follower.getEnableTimeTracking();
	}*/

    public String getEmail() {
        return email;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsSupervisor() {
        if (isSupervisor == null) return false;
        return isSupervisor;
    }

    public void setIsSupervisor(Boolean isSupervisor) {
        this.isSupervisor = isSupervisor;
    }

    public Integer getIdAppUser() {
        return idAppUser;
    }

    public void setIdAppUser(Integer idAppUser) {
        this.idAppUser = idAppUser;
    }

    public Integer getIdWorkflowInstance() {
        return idWorkflowInstance;
    }

    public void setIdWorkflowInstance(Integer idWorkflowInstance) {
        this.idWorkflowInstance = idWorkflowInstance;
    }

    public Boolean getIsAssignee() {
        if (isAssignee == null) return false;
        return isAssignee;
    }

    public void setIsAssignee(Boolean isAssignee) {
        this.isAssignee = isAssignee;
    }

    public Boolean getIsRead() {
        if (isRead == null) return false;
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public Boolean getIsCreator() {
        if (isCreator == null) return false;
        return isCreator;
    }

    public void setIsCreator(Boolean isCreator) {
        this.isCreator = isCreator;
    }

    public Boolean getIsOwner() {
        if (isOwner == null) return false;
        return isOwner;
    }

    public void setIsOwner(Boolean isOwner) {
        this.isOwner = isOwner;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getDisableNotifications() {
        return disableNotifications;
    }

    public void setDisableNotifications(Boolean disableNotifications) {
        this.disableNotifications = disableNotifications;
    }

    public Boolean getEnableTimeTracking() {
        if (enableTimeTracking == null)
            enableTimeTracking = false;

        return enableTimeTracking;
    }

    public void setEnableTimeTracking(Boolean enableTimeTracking) {
        this.enableTimeTracking = enableTimeTracking;
    }

    public String getInitials() {
        return this.name.charAt(0) + "" + this.name.charAt(this.name.lastIndexOf(' ') + 1);
    }


}
