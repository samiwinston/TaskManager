package com.codefish.android.taskmanager.presenter;

import android.content.Context;

import com.codefish.android.taskmanager.fragment.ITaskEditView;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by abedch on 5/16/2016.
 */
public interface ITaskEditPresenter {

    void updateDueDate(Integer idAppUser,Integer idTask,Date date);
    void reassignTask(Integer idAppUser,Integer idTask, Integer reassignTo);

    void removeDueDate(Integer idAppUser, Integer idTask);

    void unassignTask(Integer idAppUser, Integer idTask);
    void updateAssignee(Integer idTask, Integer idAssignee);
    void getTaskPossibleAssignees(Integer itTask);
    void setTaskEditView(ITaskEditView taskEditView);

    void removeDueDateCallBack();


    void updateDueCallBack(Date date);
    void updatePossibleAssignees(List<HashMap<String,Object>> result);
    void reassignTaskCallBack();
    void showErrorMsg(String msg);
    void unassignTaskCallBack();
    void deleteTask(Integer idAppUser,int idTask);
    void deleteTaskCallBack();
    void moveToProject(Integer idAppUser,int idWorkflowInstance, int idProject);
    void moveToProjectCallBack();
    void getMyProjects(Integer idAppUser);
    void getTags();
    void updateMyProjects(List<HashMap<String, Object>> body);
    void updateTaskField(Integer idWorkflowInstance, String path, Object value, boolean isEntity);
}
