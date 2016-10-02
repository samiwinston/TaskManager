package com.codefish.android.taskmanager.model.hr;

import java.util.Map;


public interface ICalendarBean {
	
	public Map<String,ICalendarEntity> getEtityItems();
	
	public Integer[] getWeekends();

}
