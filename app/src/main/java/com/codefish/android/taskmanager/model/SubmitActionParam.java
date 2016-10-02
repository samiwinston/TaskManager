package com.codefish.android.taskmanager.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class SubmitActionParam implements Serializable{


    public String comments;
    public List<Integer> customApprovers;
    public Date dueDate;
    public int idAppUser;

    public String workflowState;
    public int idWorkflowInstance;
    public int idWorkflowForm;
    public int idParentWorkflow;
    public String actionName;
    public String workflowName;
    public boolean returnWorkflowBean = true;
    public String parentWorkflow;
    public Integer idBaseEntity;

    public SubmitActionParam() {

    }



}