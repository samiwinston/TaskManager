package com.codefish.android.taskmanager.model;

import com.codefish.android.taskmanager.MyApplication;
import com.codefish.android.taskmanager.service.IHrService;
import com.codefish.android.taskmanager.service.IProjectService;
import com.codefish.android.taskmanager.service.IReportingService;
import com.codefish.android.taskmanager.service.ITaskService;
import com.codefish.android.taskmanager.service.IUserService;

import javax.inject.Inject;

/**
 * Created by abedch on 4/13/2016.
 */
public class ServiceModel {

    private static ServiceModel instance = new ServiceModel();

    @Inject
    public IReportingService reportingService;
    @Inject
    public IProjectService projectService;
    @Inject
    public IUserService userService;
    @Inject
    public ITaskService taskService;
    @Inject
    public IHrService hrService;

    private ServiceModel()
    {
        MyApplication.getAppComponent().inject(this);
    }

    public static ServiceModel getInstance(){

        return instance;
    }

}
