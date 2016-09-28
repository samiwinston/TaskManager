package com.codefish.android.taskmanager.presenter;

import android.content.Context;

import com.codefish.android.taskmanager.fragment.ITaskDetailsView;
import com.codefish.android.taskmanager.fragment.ITaskEditView;
import com.codefish.android.taskmanager.interactor.ITaskEditInteraction;
import com.codefish.android.taskmanager.model.LoginModel;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by abedch on 5/16/2016.
 */
public class TaskEditPresenterImpl implements ITaskEditPresenter {


    ITaskEditInteraction taskEditInteraction;
    ITaskEditView taskEditView;


    public TaskEditPresenterImpl(ITaskEditInteraction taskEditInteraction) {
        this.taskEditInteraction = taskEditInteraction;
    }

    @Override
    public void updateDueDate(Integer idAppUser,Integer idTask, Date date) {
        taskEditInteraction.updateDueDate(idTask, date, idAppUser, this);
    }

    @Override
    public void removeDueDate(Integer idAppUser,Integer idTask) {
        taskEditInteraction.removeDueDate(idTask, idAppUser, this);
    }

    @Override
    public void unassignTask(Integer idAppUser,Integer idTask) {
        taskEditInteraction.reassignTask(idTask, idAppUser, 0, this);
    }

    @Override
    public void reassignTask(Integer idAppUser,Integer idTask, Integer reassignTo) {
        taskEditInteraction.reassignTask(idTask, idAppUser, reassignTo, this);
    }

    @Override
    public void updateAssignee(Integer idTask, Integer idAssignee) {

    }

    @Override
    public void getTaskPossibleAssignees(Integer idTask) {
        taskEditInteraction.getTaskPossibleAssigness(idTask, this);
    }

    @Override
    public void removeDueDateCallBack() {
        taskEditView.updateDueDateCallBack(null);
    }

    @Override
    public void updateDueCallBack(Date date) {
        taskEditView.updateDueDateCallBack(date);
    }

    @Override
    public void updatePossibleAssignees(List<HashMap<String, Object>> result) {
        taskEditView.updatePossibleAssignees(result);
    }

    @Override
    public void reassignTaskCallBack() {

    }

    @Override
    public void showErrorMsg(String msg) {
        taskEditView.showErrorMsg(msg);
    }

    @Override
    public void unassignTaskCallBack() {
        taskEditView.unassignTaskCallBack();
    }

    @Override
    public void deleteTask(Integer idAppUser,int idTask) {
        taskEditInteraction.deleteTask(idAppUser, idTask, this);
    }

    @Override
    public void deleteTaskCallBack() {
        taskEditView.deleteTaskCallBack();
    }

    @Override
    public void moveToProject(Integer idAppUser,int idWorkflowInstance, int idProject) {
        taskEditInteraction.moveToProject(idWorkflowInstance, idProject, idAppUser, this);
    }

    @Override
    public void moveToProjectCallBack() {
        taskEditView.moveToProjectCallBack();
    }

    @Override
    public void setTaskEditView(ITaskEditView taskEditView) {
        this.taskEditView = taskEditView;
    }

    @Override
    public void getMyProjects(Integer idAppUser) {
        taskEditInteraction.getMyProjects(idAppUser, false, false, this);
    }

    @Override
    public void updateMyProjects(List<HashMap<String, Object>> result) {
        taskEditView.updateMyProjects(result);
    }

    @Override
    public void getTags() {

    }

    @Override
    public void updateTaskField(Integer idWorkflowInstance, String path, Object value, boolean isEntity) {
        taskEditInteraction.updateTaskField(idWorkflowInstance, path, value, isEntity,this);
    }

}
