package com.codefish.android.taskmanager.model;


public interface IReportAction {

	public Integer execute() throws Exception;

	public void updateEntities(Integer idBaseEntity, String baseEntityName);

}
