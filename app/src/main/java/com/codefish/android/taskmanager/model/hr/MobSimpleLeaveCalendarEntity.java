package com.codefish.android.taskmanager.model.hr;



import java.io.Serializable;

public class MobSimpleLeaveCalendarEntity extends MobCalendarEntity implements Serializable  {

	public static int APPROVED = 1;
	public static int HOLIDAY = 2;
	public static int DISABLED_BY_MANAGEMENT = 3;
	public static int REQUESTED = 5;
	public static int NEW = 0;

	public static int FULLDAY = 1;
	public static int AM = 2;
	public static int PM = 3;


	public int idType;
	public int idCategory;
	public int status;
	public String description;
	public int daySelectionType = 0;
	public String title;
	public Boolean allowLeaveSelection =false;
	public String daySelectionTypeString;



}
