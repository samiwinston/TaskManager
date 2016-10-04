package com.codefish.android.taskmanager.model.hr;


import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MobSimpleLeaveCalendarBean implements Serializable {

	public MobSimpleLeaveCalendarBean(){
	}
	public static Format formatter = new SimpleDateFormat("dd-MMM-yy");
	
	private Integer[] weekends;
	private Map<String, MobSimpleLeaveCalendarEntity> entityItems;

	public Map<String, MobSimpleLeaveCalendarEntity> getEtityItems() {
		return getEntityItems();
	}

	public Integer[] getWeekends() {
		return weekends;
	}

	public void setWeekends(Integer[] weekends) {
		this.weekends = weekends;
	}

	public Map<String, MobSimpleLeaveCalendarEntity> getEntityItems() {
		return entityItems;
	}

	public void setEntityItems(Map<String, MobSimpleLeaveCalendarEntity> entityItems) {
		this.entityItems = entityItems;
	}

	public void addEntityItem(MobSimpleLeaveCalendarEntity item) {
		if (entityItems == null){
			entityItems = new HashMap<String, MobSimpleLeaveCalendarEntity>();
		}
		entityItems.put(formatter.format(item.getDate()), item);
	}

	public void removeEntityItem(Date entityDate){
		if (entityItems==null){
			return;
		}

		String formatted= formatter.format(entityDate);
		entityItems.remove(formatted);
	}





}