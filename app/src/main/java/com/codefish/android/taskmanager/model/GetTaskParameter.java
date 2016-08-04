package com.codefish.android.taskmanager.model;

public class GetTaskParameter {
	public Integer idAppUser;
	public Integer idWorkflowInstance;
	public Integer taskFilter;
	public Integer idMileStone;
	public Boolean showCompleted;
	public boolean getSubtasks;

	public boolean getProjectPhases;

	public Integer[] tagFilters;
	public Integer[] categoryFilters;
	
	public GetTaskParameter(){
		
	}
	

}