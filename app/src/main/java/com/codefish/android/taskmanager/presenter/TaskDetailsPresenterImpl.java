package com.codefish.android.taskmanager.presenter;

import android.util.Log;

import com.codefish.android.taskmanager.fragment.ITaskDetailsView;
import com.codefish.android.taskmanager.interactor.ITaskDetailsInteraction;
import com.codefish.android.taskmanager.model.GetTaskParameter;
import com.codefish.android.taskmanager.model.LoginModel;
import com.codefish.android.taskmanager.model.MobWorkflowForm;
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
    public void updateImportance(Integer idAppUser,Integer idUserTask, Integer importance) {
        taskDetailsInteraction.updateImportance(idAppUser,idUserTask,importance,this);
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
    public void changeState(Integer idAppUser,UserTaskBean userTaskBean) {
        SubmitActionParam submitParams= new SubmitActionParam();
        submitParams.idWorkflowInstance = userTaskBean.idWorkflowInstance;
        submitParams.actionName = "_action0";
        submitParams.idWorkflowForm = userTaskBean.idTask;
        submitParams.idAppUser = idAppUser;
        submitParams.returnWorkflowBean = false;

        taskDetailsInteraction.changeState(submitParams, this);

    }

    @Override
    public void updateDueDate(Integer idAppUser,Integer idTask, Date date) {
        taskDetailsInteraction.updateDueDate(idTask, date, idAppUser, this);
    }


    @Override
    public void getTask(Integer idAppUser,Integer idWorkflowInstance) {

        GetTaskParameter param = new GetTaskParameter();
        param.idAppUser = idAppUser;
        param.idWorkflowInstance = idWorkflowInstance;
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

    @Override
    public void getWorkflowForm(Integer idAppUser,Integer idWorkflowInstance) {
        taskDetailsInteraction.getWorkflowForm(idAppUser,idWorkflowInstance,this);
    }


    @Override
    public void loadWorkflowForm(MobWorkflowForm mobWorkflowForm) {
        taskDetailsView.loadWorkflowForm(mobWorkflowForm);
    }


}
