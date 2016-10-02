package com.codefish.android.taskmanager.model;

import com.codefish.android.taskmanager.model.hr.ICustomFormBean;

import java.util.Date;
import java.util.List;
import java.util.Map;


public class MobWorkflowBean {

	private List<WorkflowActionBean> actions;
	private boolean canTakeAction;
	private List<FollowerBean> currentApprovers;
	private String currentStatus;
	private Date dueDate;
	private ICustomFormBean form;
	private Integer idOwnerAsFollwer;
	private Integer idBaseEntity;
	private Integer idWorkflowForm;
	private Integer idGroup;
	private Integer idParentWorkflow;
	private Integer idWorkflowInstance;
	private boolean groupEditable;
	private boolean isOpen;
	private Boolean isOwner;
	private Boolean isSupervisor;
	private boolean newWorkflow;
	private boolean hideActionButtons;
	private boolean cancelable;
	private boolean saveable;
	private boolean allowBack;
	private Boolean reassignable;
	private int reassignmentType;
	private Boolean canModifyDescription;
	private Boolean canModifyTitle;
	private Boolean canModifyDueDate;
	private boolean viewOnly;
	private Integer idOwner;
	private Map<String,Object> workGroup;
	private String owner;
	private String submittedBy;
	private String groupName;
	private String workflowCurrentAssignee;
	private Date submittedOn;
	private List<FollowerBean> followers;
	private List<MobWorkflowBean> children;
	private String workflowStateName;
	private String workflowName;
	private String description;
	private String title;
	private Integer numOpenForms;
	private Boolean canUnfollow;
	private Boolean notificationsEnabled;
	private Boolean hasForm;
	private Boolean canEditState;
	private Boolean isAssignee;
	private Boolean canEditGroup;
	private Boolean showActionBar = true;
	
	public MobWorkflowBean() {
		
	}
	
/*	public MobWorkflowBean(IWorkflowForm form) {

		currentApprovers = new ArrayList<FollowerBean>();
		actions = new ArrayList<WorkflowActionBean>();
		
		this.setForm(form);
		
		if (form!=null){
			hasForm = true;
		}
		else{
			hasForm = false;
		}
	}*/

	public List<WorkflowActionBean> getActions() {
		return actions;
	}

	public boolean getCanTakeAction() {
		return canTakeAction;
	}
	public List<FollowerBean> getCurrentApprovers() {
		return currentApprovers;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public ICustomFormBean getForm() {
		return form;
	}

	public Integer getIdWorkflowForm() {
		return idWorkflowForm;
	}

	public Boolean getIsSupervisor() {
		if (isSupervisor == null) return false;
		return isSupervisor;
	}

	public Boolean getNewWorkflow() {
		return newWorkflow;
	}

	public String getOwner() {
		return owner;
	}

	public String getSubmittedBy() {
		return submittedBy;
	}

	public Date getSubmittedOn() {
		return submittedOn;
	}


	public String getWorkflowStateName() {
		return workflowStateName;
	}

	public void setActions(List<WorkflowActionBean> actions) {
		this.actions = actions;
	}

	public void setCanTakeAction(boolean isApprover) {
		this.canTakeAction = isApprover;
	}

	public void setCurrentApprovers(List<FollowerBean> currentApprovers) {
		this.currentApprovers = currentApprovers;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public void setForm(ICustomFormBean form) {
		this.form = form;
	}

	public void setIdWorkflowForm(Integer idWorkflowForm) {
		this.idWorkflowForm = idWorkflowForm;
	}

	public void setIsSupervisor(Boolean isSupervisor) {
		this.isSupervisor = isSupervisor;
	}

	public void setNewWorkflow(Boolean newWorkflow) {
		this.newWorkflow = newWorkflow;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public void setSubmittedBy(String submittedBy) {
		this.submittedBy = submittedBy;
	}

	public void setSubmittedOn(Date submittedOn) {
		this.submittedOn = submittedOn;
	}


	public void setWorkflowStateName(String workflowStateName) {
		this.workflowStateName = workflowStateName;
	}

	public Integer getNumOpenForms() {
		return numOpenForms;
	}

	public void setNumOpenForms(Integer numOpenForms) {
		this.numOpenForms = numOpenForms;
	}

	public List<MobWorkflowBean> getChildren() {
		return children;
	}

	public void setChildren(List<MobWorkflowBean> children) {
		this.children = children;
	}
	public Boolean getCancelable() {
		return cancelable;
	}

	public void setCancelable(Boolean cancelable) {
		if (cancelable == null){
			this.cancelable=false;
		}
		else
			this.cancelable = cancelable;
	}

	public Boolean getIsOwner() {
		if (isOwner == null) return false;
		return isOwner;
	}

	public void setIsOwner(Boolean isOwner) {
		this.isOwner = isOwner;
	}

	public String getWorkflowCurrentAssignee() {
		return workflowCurrentAssignee;
	}

	public void setWorkflowCurrentAssignee(String workflowCurrentAssignee) {
		this.workflowCurrentAssignee = workflowCurrentAssignee;
	}

	public Boolean getSaveable() {
		return saveable;
	}

	public void setSaveable(Boolean saveable) {
		this.saveable = saveable;
	}

	public Integer getIdWorkflowInstance() {
		return idWorkflowInstance;
	}

	public void setIdWorkflowInstance(Integer idWorkflowInstance) {
		this.idWorkflowInstance = idWorkflowInstance;
	}

	public Integer getIdBaseEntity() {
		return idBaseEntity;
	}

	public void setIdBaseEntity(Integer idBaseEntity) {
		this.idBaseEntity = idBaseEntity;
	}

	public Boolean getHideActionButtons() {
		return hideActionButtons;
	}

	public void setHideActionButtons(Boolean hideActionButtons) {
		this.hideActionButtons = hideActionButtons;
	}

	public String getWorkflowName() {
		return workflowName;
	}

	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}

	public boolean getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getIdParentWorkflow() {
		return idParentWorkflow;
	}

	public void setIdParentWorkflow(Integer idParentWorkflow) {
		this.idParentWorkflow = idParentWorkflow;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean getViewOnly() {
		return viewOnly;
	}

	public void setViewOnly(boolean viewOnly) {
		this.viewOnly = viewOnly;
	}
	
	public boolean isAllowBack() {
		return allowBack;
	}

	public void setAllowBack(boolean allowBack) {
		this.allowBack = allowBack;
	}

	public List<FollowerBean> getFollowers() {
		return followers;
	}

	public void setFollowers(List<FollowerBean> followers) {
		this.followers = followers;
	}

	public Integer getIdOwner() {
		return idOwner;
	}

	public void setIdOwner(Integer idOwner) {
		this.idOwner = idOwner;
	}

	public int getReassignmentType() {
		return reassignmentType;
	}

	public void setReassignmentType(int reassignmentType) {
		this.reassignmentType = reassignmentType;
	}

	public Boolean getCanModifyDueDate() {
		return canModifyDueDate;
	}

	public void setCanModifyDueDate(Boolean canModifyDueDate) {
		this.canModifyDueDate = canModifyDueDate;
	}

	public Boolean getReassignable() {
		return reassignable;
	}

	public void setReassignable(Boolean reassignable) {
		this.reassignable = reassignable;
	}

	public Boolean getCanUnfollow() {
		return canUnfollow;
	}

	public void setCanUnfollow(Boolean canUnfollow) {
		this.canUnfollow = canUnfollow;
	}

	public Boolean getCanModifyDescription() {
		return canModifyDescription;
	}

	public void setCanModifyDescription(Boolean canModifyDescription) {
		this.canModifyDescription = canModifyDescription;
	}

	public Boolean getCanModifyTitle() {
		return canModifyTitle;
	}

	public void setCanModifyTitle(Boolean canModifyTitle) {
		this.canModifyTitle = canModifyTitle;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Boolean getNotificationsEnabled() {
		return notificationsEnabled;
	}

	public void setNotificationsEnabled(Boolean notificationsEnabled) {
		this.notificationsEnabled = notificationsEnabled;
	}

	public Integer getIdOwnerAsFollwer() {
		return idOwnerAsFollwer;
	}

	public void setIdOwnerAsFollwer(Integer idOwnerAsFollwer) {
		this.idOwnerAsFollwer = idOwnerAsFollwer;
	}

	public Boolean getHasForm() {
		return hasForm;
	}

	public void setHasForm(Boolean hasForm) {
		this.hasForm = hasForm;
	}

	public Map<String,Object> getWorkGroup() {
		return workGroup;
	}

	public void setWorkGroup(Map<String,Object> workGroup) {
		this.workGroup = workGroup;
	}

	public boolean isGroupEditable() {
		return groupEditable;
	}

	public void setGroupEditable(boolean groupEditable) {
		this.groupEditable = groupEditable;
	}

	public Boolean getCanEditState() {
		return canEditState;
	}

	public void setCanEditState(Boolean canEditState) {
		this.canEditState = canEditState;
	}

	public Boolean getIsAssignee() {
		if (isAssignee == null) return false;
		return isAssignee;
	}

	public void setIsAssignee(Boolean isAssignee) {
		this.isAssignee = isAssignee;
	}

	public Boolean getCanEditGroup() {
		return canEditGroup;
	}

	public void setCanEditGroup(Boolean canEditGroup) {
		this.canEditGroup = canEditGroup;
	}

	public Integer getIdGroup() {
		return idGroup;
	}

	public void setIdGroup(Integer idGroup) {
		this.idGroup = idGroup;
	}

	public Boolean getShowActionBar() {
		return showActionBar;
	}

	public void setShowActionBar(Boolean showActionBar) {
		this.showActionBar = showActionBar;
	}

}
