package com.codefish.android.taskmanager.model;

public class ReportSaveAction  {

	private String baseEntityName;
	private int idBaseEntity;

	public ReportSaveAction() {

	}

	public ReportSaveAction(String baseEntityName2, int idBaseEntity2) {
		this.idBaseEntity = idBaseEntity2;
		this.baseEntityName = baseEntityName2;
	}

	public String getBaseEntityName() {
		return baseEntityName;
	}

	public int getIdBaseEntity() {
		return idBaseEntity;
	}

	public void setBaseEntityName(String baseEntityName) {
		this.baseEntityName = baseEntityName;
	}

	public void setIdBaseEntity(int idBaseEntity) {
		this.idBaseEntity = idBaseEntity;
	}


}
