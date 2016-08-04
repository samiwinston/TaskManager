package com.codefish.android.taskmanager.presenter;


import com.codefish.android.taskmanager.fragment.ITaskDetailsView;
import com.codefish.android.taskmanager.fragment.TaskDetailsFragment;
import com.codefish.android.taskmanager.model.MobWorkflowForm;
import com.codefish.android.taskmanager.model.UserTaskBean;

import java.util.Date;

/**
 * Created by abedch on 2/2/2016.
 */
public interface ITaskDetailsPresenter {

    void changeState(UserTaskBean userTaskBean);
    void updateDueDate(Integer idTask,Date date);
    void setTaskDetailsView(ITaskDetailsView taskDetailsView);

    void updateImportance(Integer idUserTask, Integer importance);

    void updateImportanceCBH();

    void updateDueCallBack(Date date);
    void getTask(Integer idTask);
    void showErrorMsg(String s);
    void loadUserTaskBean(UserTaskBean bean);
    void changeStateCBH();
    void getWorkflowForm(Integer idWorkflowInstance);
    void loadWorkflowForm(MobWorkflowForm mobWorkflowForm);
}
