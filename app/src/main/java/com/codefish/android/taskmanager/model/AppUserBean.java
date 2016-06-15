package com.codefish.android.taskmanager.model;

import java.util.Collection;
import java.util.Date;


public class AppUserBean {

    private int id;
    private AppConfig appConfig;
    private Integer idCompany;
    private String groupRole;
    private Integer gender;
    private Integer idGroupRole;
    private Integer security;
    private String language;
    private String frameworkVersion;
    private String firstModule;
    private Boolean forcePasswordChange;
    private String name;
    private String openTabs;
    private String email;
    private String timeZone;
    private Date passwordChangeDate;
    private String username;
    private String documentsLocation;

    public AppUserBean(int id,String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupRole() {
        return groupRole;
    }

    public void setGroupRole(String groupRole) {
        this.groupRole = groupRole;
    }

    public Integer getSecurity() {
        return security;
    }

    public void setSecurity(Integer security) {
        this.security = security;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Boolean getForcePasswordChange() {
        return forcePasswordChange;
    }

    public void setForcePasswordChange(Boolean forcePasswordChange) {
        this.forcePasswordChange = forcePasswordChange;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getPasswordChangeDate() {
        return passwordChangeDate;
    }

    public void setPasswordChangeDate(Date passwordChangeDate) {
        this.passwordChangeDate = passwordChangeDate;
    }

    public Integer getIdGroupRole() {
        return idGroupRole;
    }

    public void setIdGroupRole(Integer idGroupRole) {
        this.idGroupRole = idGroupRole;
    }

//	public UserDashboardItem[] getDashboardItems() {
//		return dashboardItems;
//	}
//
//	public void setDashboardItems(UserDashboardItem[] dashboardItems) {
//		this.dashboardItems = dashboardItems;
//	}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getOpenTabs() {
        return openTabs;
    }

    public void setOpenTabs(String openTabs) {
        this.openTabs = openTabs;
    }

    public String getDocumentsLocation() {
        return documentsLocation;
    }

    public void setDocumentsLocation(String documentsLocation) {
        this.documentsLocation = documentsLocation;
    }

    public String getFrameworkVersion() {
        return frameworkVersion;
    }

    public void setFrameworkVersion(String frameworkVersion) {
        this.frameworkVersion = frameworkVersion;
    }


    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }


    public String getFirstModule() {
        return firstModule;
    }

    public void setFirstModule(String firstModule) {
        this.firstModule = firstModule;
    }

    public AppConfig getAppConfig() {
        return appConfig;
    }

    public void setAppConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public Integer getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(Integer idCompany) {
        this.idCompany = idCompany;
    }


    public String getInitials(){
        if(name!=null)
        {
            return this.name.charAt(0)+""+this.name.charAt(this.name.lastIndexOf(' ')+1);
        }
        return "";
    }
}
