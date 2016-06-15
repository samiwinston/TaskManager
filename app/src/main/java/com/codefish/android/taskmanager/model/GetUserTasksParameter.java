package com.codefish.android.taskmanager.model;

public class GetUserTasksParameter {

    public Integer idAppUser;
	public Boolean getArchived;
	public Integer sortType;
	public Boolean showMyRequests;
	public Boolean showOversight;
	public Boolean showFollowing;
	public String filterTitle;
	public Boolean openTasksOnly;

    public static final Integer SORT_DUE_DATE = 1;
    public static final Integer SORT_IMPORTANCE = 2;

	public GetUserTasksParameter(){
		
	}

	public GetUserTasksParameter(Integer idAppUser, Boolean getArchived, int sortType, boolean showMyRequests, boolean showOversight,
			boolean showFollowing, String filterTitle, boolean openTasksOnly) {
		this.idAppUser = idAppUser;
		this.getArchived = getArchived;
		this.sortType = sortType;
		this.showMyRequests = showMyRequests;
		this.showOversight = showOversight;
		this.showFollowing = showFollowing;
		this.filterTitle = filterTitle;
		this.openTasksOnly = openTasksOnly;
	}
}