package com.codefish.android.taskmanager.activity;

import com.codefish.android.taskmanager.model.hr.MobLeaveRequestFormBean;

/**
 * Created by abedch on 2/5/2016.
 */
public interface ILeaveWorkflowFormView {


    void getLeaveBeanCBH(MobLeaveRequestFormBean bean);
    void showErrorMsg(String msg);
}
