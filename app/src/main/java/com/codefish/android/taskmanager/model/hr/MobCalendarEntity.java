package com.codefish.android.taskmanager.model.hr;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MobCalendarEntity implements Serializable {
	
	public MobCalendarEntity(){
	}

	
	private List<?> entityItems;

	public Date date;
	
	private Integer id; 

	public List<?> getEntityItems() {
		return entityItems;
	}

	public void setEntityItems(List<?> entityItems) {
		this.entityItems = entityItems;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}



}
