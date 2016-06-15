package com.codefish.android.taskmanager.model;

import java.util.HashMap;
import java.util.Map;

public class SaveEntityAction extends ReportSaveAction {

	private Map<String, Object> values;

	public SaveEntityAction(Integer idBaseEntity, String baseEntityName, HashMap<String,Object> values)
	{
		super(baseEntityName,idBaseEntity);
		this.values = values;
		if (this.values==null){
			this.values = new HashMap<>();
		}
	}


	public void addValue(String path,Object value){
		values.put(path,value);

	}

	public void addEntity(String path, Integer value){
		values.put(path,new ReferenceEntity(value));
	}

}
