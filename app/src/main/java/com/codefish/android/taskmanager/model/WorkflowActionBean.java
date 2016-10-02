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
        dest.writeString(this.action);
        dest.writeString(this.title);
        dest.writeValue(this.allowApprovalSelection);
        dest.writeValue(this.allowDueDateSelection);
        dest.writeValue(this.requiresConfirmation);
        dest.writeValue(this.visible);
        dest.writeValue(this.idWorkflowInstance);
        dest.writeValue(this.idWorkflowForm);
        dest.writeString(this.actionType);
        dest.writeString(this.actionClass);
    }

    protected WorkflowActionBean(Parcel in) {
        this.action = in.readString();
        this.title = in.readString();
        this.allowApprovalSelection = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.allowDueDateSelection = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.requiresConfirmation = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.visible = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.idWorkflowInstance = (Integer) in.readValue(Integer.class.getClassLoader());
        this.idWorkflowForm = (Integer) in.readValue(Integer.class.getClassLoader());
        this.actionType = in.readString();
        this.actionClass = in.readString();
    }

    public static final Parcelable.Creator<WorkflowActionBean> CREATOR = new Parcelable.Creator<WorkflowActionBean>() {
        @Override
        public WorkflowActionBean createFromParcel(Parcel source) {
            return new WorkflowActionBean(source);
        }

        @Override
        public WorkflowActionBean[] newArray(int size) {
            return new WorkflowActionBean[size];
        }
    };
}
