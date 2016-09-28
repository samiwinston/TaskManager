package com.codefish.android.taskmanager.model;


import android.os.Parcel;
import android.os.Parcelable;

public class WorkflowActionBean implements Parcelable {

    private String action;
    private String title;
    private Boolean allowApprovalSelection;
    private Boolean allowDueDateSelection;
    private Boolean requiresConfirmation;
    private Boolean visible;
    private Integer idWorkflowInstance;
    private Integer idWorkflowForm;
    private String actionType;
    private String actionClass;

    public WorkflowActionBean() {

    }

    private WorkflowActionBean(Parcel in) {
        action = in.readString();
        title = in.readString();
        allowApprovalSelection  = in.readByte() != 0;
        allowDueDateSelection = in.readByte() != 0;
        requiresConfirmation = in.readByte() != 0;
        visible = in.readByte() != 0;
        idWorkflowInstance = in.readInt();
        idWorkflowForm =in.readInt();
        actionType = in.readString();
        actionClass = in.readString();
    }

    public static final Parcelable.Creator<WorkflowActionBean> CREATOR = new Parcelable.Creator<WorkflowActionBean>() {
        public WorkflowActionBean createFromParcel(Parcel in) {
            return new WorkflowActionBean(in);
        }

        public WorkflowActionBean[] newArray(int size) {
            return new WorkflowActionBean[size];
        }
    };


    public String getAction() {
        return action;
    }

    public Boolean getAllowApprovalSelection() {
        return allowApprovalSelection;
    }

    public Boolean getAllowDueDateSelection() {
        return allowDueDateSelection;
    }
    public void setAction(String action) {
        this.action = action;
    }

    public void setAllowApprovalSelection(Boolean allowApprovalSelection) {
        this.allowApprovalSelection = allowApprovalSelection;
    }

    public void setAllowDueDateSelection(Boolean allowDueDateSelection) {
        this.allowDueDateSelection = allowDueDateSelection;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public Boolean getRequiresConfirmation() {
        return requiresConfirmation;
    }

    public void setRequiresConfirmation(Boolean requiresConfirmation) {
        this.requiresConfirmation = requiresConfirmation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActionClass() {
        return actionClass;
    }

    public void setActionClass(String actionClass) {
        this.actionClass = actionClass;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Integer getIdWorkflowInstance() {
        return idWorkflowInstance;
    }

    public void setIdWorkflowInstance(Integer idWorkflowInstance) {
        this.idWorkflowInstance = idWorkflowInstance;
    }

    public Integer getIdWorkflowForm() {
        return idWorkflowForm;
    }

    public void setIdWorkflowForm(Integer idWorkflowForm) {
        this.idWorkflowForm = idWorkflowForm;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }




}
