package com.codefish.android.taskmanager.activity;

import com.codefish.android.taskmanager.model.UserTaskBean;
import com.codefish.android.taskmanager.model.hr.MobLeaveRequestFormBean;

/**
 * Created by abedch on 2/5/2016.
 */
public interface ILeaveWorkflowFormView {


    void showProgressBar();
    void hideProgressBar();
    void showErrorMessage(String msg);

    void getLeaveBeanCBH(MobLeaveRequestFormBean bean);

    void submitLeaveCBH(UserTaskBean userTaskBean);
}
