package com.codefish.android.taskmanager.component;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.codefish.android.taskmanager.model.WorkflowActionBean;

/**
 * Created by abedch on 7/28/2016.
 */
public class WorkflowActionButton extends Button {

    private WorkflowActionBean workflowActionBean;

    public WorkflowActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WorkflowActionBean getWorkflowActionBean() {
        return workflowActionBean;
    }

    public void setWorkflowActionBean(WorkflowActionBean workflowActionBean) {
        this.setText(workflowActionBean.getTitle());
        this.workflowActionBean = workflowActionBean;
    }
}
