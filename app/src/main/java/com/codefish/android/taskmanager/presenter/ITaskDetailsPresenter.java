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

    void changeState(Integer idAppUser,UserTaskBean userTaskBean);
    void updateDueDate(Integer idAppUser,Integer idTask,Date date);
    void setTaskDetailsView(ITaskDetailsView taskDetailsView);

    void updateImportance(Integer userId, Integer idUserTask, Integer importance);

    void removeDueDate(Integer idAppUser, Integer idTask);

    void removeDueDateCallBack();

    void updateImportanceCBH();

    void updateDueCallBack(Date date);
    void getTask(Integer idAppUser, Integer idWorkflowInstance);

    void showErrorMsg(String s);
    void loadUserTaskBean(UserTaskBean bean);
    void changeStateCBH(String updatedState);
    void getWorkflowForm(Integer idAppUser,Integer idWorkflowInstance);
    void loadWorkflowForm(MobWorkflowForm mobWorkflowForm);

    void deleteTask(int userId, int idWorkflowInstance);

    void deleteTaskCallBack();
}
