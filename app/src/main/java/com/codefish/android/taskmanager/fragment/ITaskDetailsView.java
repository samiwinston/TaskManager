package com.codefish.android.taskmanager.fragment;

import com.codefish.android.taskmanager.model.MobWorkflowForm;
import com.codefish.android.taskmanager.model.UserTaskBean;

import java.util.Date;

/**
 * Created by abedch on 5/25/2016.
 */
public interface ITaskDetailsView {
    void deleteTaskCallBack();

    void updateDueCallBack(Date date);
    void showErrorMsg(String s);
    void loadUserTaskBean(UserTaskBean userTaskBean);
    void changeStateCBH(String updatedState);
    void updateImportanceCBH();
    void loadWorkflowForm(MobWorkflowForm mobWorkflowForm);
}

