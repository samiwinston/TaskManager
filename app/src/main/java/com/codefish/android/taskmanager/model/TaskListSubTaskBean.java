package com.codefish.android.taskmanager.model;

import java.io.Serializable;
import java.util.Date;

public class TaskListSubTaskBean implements Serializable{
	
	public TaskListSubTaskBean(){
		
	}
	
	public String ownerName;
	public Integer idOwner;
	public Integer idAppUser;
	public Date dueDate;
	public String name;

}
