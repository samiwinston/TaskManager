package com.codefish.android.taskmanager.presenter;

import com.codefish.android.taskmanager.activity.ITasksView;
import com.codefish.android.taskmanager.interactor.ITaskInteraction;
import com.codefish.android.taskmanager.model.GetTaskParameter;
import com.codefish.android.taskmanager.model.GetUserTasksParameter;
import com.codefish.android.taskmanager.model.LoginModel;
import com.codefish.android.taskmanager.model.SubmitActionParam;
import com.codefish.android.taskmanager.model.UserTaskBean;

/**
 * Created by abedch on 2/9/2016.
 */
public class TaskPresenterImpl implements ITaskPresenter {


    ITaskInteraction taskInteraction;

    ITasksView taskView;


    public TaskPresenterImpl(ITaskInteraction tasksInteraction) {
        this.taskInteraction = tasksInteraction;
    }





    @Override
    public void getUserTasks(Integer idSelectedProject) {

        Integer idAppUser = LoginModel.getInstance().getUserBean().getId();

        if (idSelectedProject == null) {
            GetUserTasksParameter params = new GetUserTasksParameter();
            params.idAppUser = idAppUser;
            params.getArchived = false;
            params.sortType = 3;
            params.showMyRequests = true;
            params.showOversight = true;
            params.showFollowing = true;
            params.filterTitle = null;
            params.openTasksOnly = true;

            taskInteraction.getUserTasks(params, this);
        } else {
            GetTaskParameter param = new GetTaskParameter();
            param.idAppUser = LoginModel.getInstance().getUserBean().getId();
            param.idWorkflowInstance = idSelectedProject;
            param.showCompleted = false;
            param.getSubtasks = true;
            param.getProjectPhases = true;

            taskInteraction.getProjectTasks(param, this);
        }


    }


    @Override
    public void refreshList() {
        taskView.refreshList();
        taskView.hideProgressBar();
    }

    @Override
    public void showErrorMsg(String msg) {
        taskView.showErrorMsg(msg);
        taskView.hideProgressBar();
    }

    @Override
    public void setLoginView(ITasksView taskView) {
        this.taskView = taskView;
    }

    @Override
    public void selectTask(UserTaskBean bean, int position) {
        taskView.selectTask(bean, position);
    }
}
