package com.codefish.android.taskmanager.model;

import java.util.Map;

public class AppConfig {

    public AppConfig(){

    }

    public Boolean allowRememberMe;
    public Integer sessionTimeOut;
    public Boolean helpVisible;

    public Boolean contactUsVisible;
    public String userGuideUrl;

    public String catchAllDomain;
    public String applicationTitle;
    public Boolean messagingOpen;
    public Boolean singleChatOnly;

    public Boolean notificationsDisabled;
    public Boolean disableConnectionPopup;
    public Boolean chatEnabled;
    public Boolean projectsEnabled;
    public Boolean documentsEnabled;
    public Boolean calendarEnabled;
    public Boolean groupsEnabled;
    public Boolean dashboardEnabled;
    public Boolean customizeThemeEnabled;
    public Boolean multilingual;
    public Boolean homeDisabled;

    public String serverPath;
    public String guiPath;

    public String dateFormat;
    public String dateTimeFormat;


    //	public ApplicationTheme applicationTheme;
    public Boolean reportsEnabled;
    public Boolean formsEnabled;
    public String landingModule;
    public Boolean teamDisabled;
    public String applicationVersion;
    public String systemVersion;

    public Map<String, String> currencyFormatter;

}
