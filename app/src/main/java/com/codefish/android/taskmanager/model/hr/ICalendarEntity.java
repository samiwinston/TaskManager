package com.codefish.android.taskmanager.model.hr;

import java.util.Date;
import java.util.List;


public interface ICalendarEntity {

	public Date getDate();

	public List<?> getEntityItems();

	public Integer getId();

	public void setId(Integer id);

}