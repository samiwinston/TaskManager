package com.codefish.android.taskmanager.presenter;

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
    public void updateDueDate(Integer idTask, Date date) {
        taskEditInteraction.updateDueDate(idTask, date, LoginModel.getInstance().getUserBean().getId(), this);
    }

    @Override
    public void unassignTask(Integer idTask) {
        taskEditInteraction.reassignTask(idTask, LoginModel.getInstance().getUserBean().getId(), 0, this);
    }

    @Override
    public void reassignTask(Integer idTask, Integer reassignTo) {
        taskEditInteraction.reassignTask(idTask, LoginModel.getInstance().getUserBean().getId(), reassignTo, this);
    }

    @Override
    public void updateAssignee(Integer idTask, Integer idAssignee) {

    }

    @Override
    public void getTaskPossibleAssignees(Integer idTask) {
        taskEditInteraction.getTaskPossibleAssigness(idTask, this);
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
    public void deleteTask(int idTask) {
        taskEditInteraction.deleteTask(LoginModel.getInstance().getUserBean().getId(), idTask, this);
    }

    @Override
    public void deleteTaskCallBack() {
        taskEditView.deleteTaskCallBack();
    }

    @Override
    public void moveToProject(int idWorkflowInstance, int idProject) {
        taskEditInteraction.moveToProject(idWorkflowInstance, idProject, LoginModel.getInstance().getUserBean().getId(), this);
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
    public void getMyProjects() {
        taskEditInteraction.getMyProjects(LoginModel.getInstance().getUserBean().getId(), false, false, this);
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
