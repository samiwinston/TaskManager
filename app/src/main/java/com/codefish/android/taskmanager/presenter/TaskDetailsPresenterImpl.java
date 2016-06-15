package com.codefish.android.taskmanager.presenter;

import android.util.Log;

import com.codefish.android.taskmanager.fragment.ITaskDetailsView;
import com.codefish.android.taskmanager.interactor.ITaskDetailsInteraction;
import com.codefish.android.taskmanager.model.GetTaskParameter;
import com.codefish.android.taskmanager.model.LoginModel;
import com.codefish.android.taskmanager.model.SubmitActionParam;
import com.codefish.android.taskmanager.model.UserTaskBean;

import java.util.Date;

/**
 * Created by abedch on 2/9/2016.
 */
public class TaskDetailsPresenterImpl implements ITaskDetailsPresenter {


    ITaskDetailsInteraction taskDetailsInteraction;
    ITaskDetailsView taskDetailsView;


    public TaskDetailsPresenterImpl(ITaskDetailsInteraction taskDetailsInteraction) {
        this.taskDetailsInteraction = taskDetailsInteraction;
    }

    @Override
    public void setTaskDetailsView(ITaskDetailsView taskDetailsView) {
        this.taskDetailsView = taskDetailsView;
    }

    @Override
    public void updateImportance(Integer idUserTask, Integer importance) {
        taskDetailsInteraction.updateImportance(LoginModel.getInstance().getUserBean().getId(),idUserTask,importance,this);
    }

    @Override
    public void updateImportanceCBH() {
        taskDetailsView.updateImportanceCBH();
    }

    @Override
    public void updateDueCallBack(Date date) {
        taskDetailsView.updateDueCallBack(date);
    }

    @Override
    public void changeState(UserTaskBean userTaskBean) {
        SubmitActionParam submitParams= new SubmitActionParam();
        submitParams.idWorkflowInstance = userTaskBean.idWorkflowInstance;
        submitParams.actionName = "_action0";
        submitParams.idWorkflowForm = userTaskBean.idWorkflowInstance;
        submitParams.idAppUser = LoginModel.getInstance().getUserBean().getId();
        submitParams.returnWorkflowBean = false;

        taskDetailsInteraction.changeState(submitParams, this);

    }

    @Override
    public void updateDueDate(Integer idTask, Date date) {
        taskDetailsInteraction.updateDueDate(idTask, date, LoginModel.getInstance().getUserBean().getId(), this);
    }


    @Override
    public void getTask(Integer idTask) {

        GetTaskParameter param = new GetTaskParameter();
        param.idAppUser = LoginModel.getInstance().getUserBean().getId();
        param.idWorkflowInstance = idTask;
        param.showCompleted = true;
        param.getSubtasks = true;

        param.getProjectPhases = false;

        taskDetailsInteraction.getTask(param,this);
    }

    @Override
    public void showErrorMsg(String s) {
        taskDetailsView.showErrorMsg(s);
    }

    @Override
    public void loadUserTaskBean(UserTaskBean bean) {
        taskDetailsView.loadUserTaskBean(bean);
    }

    @Override
    public void changeStateCBH() {
        taskDetailsView.changeStateCBH();
    }


}
