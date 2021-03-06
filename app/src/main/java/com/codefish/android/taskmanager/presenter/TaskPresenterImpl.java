package com.codefish.android.taskmanager.presenter;

import android.preference.PreferenceManager;

import com.codefish.android.taskmanager.activity.ITasksView;
import com.codefish.android.taskmanager.interactor.ITaskInteraction;
import com.codefish.android.taskmanager.model.GetTaskParameter;
import com.codefish.android.taskmanager.model.GetUserTasksParameter;
import com.codefish.android.taskmanager.model.LoginModel;
import com.codefish.android.taskmanager.model.SubmitActionParam;
import com.codefish.android.taskmanager.model.UserTaskBean;
import com.codefish.android.taskmanager.model.WidgetActionItemBean;
import com.codefish.android.taskmanager.model.hr.MobLeaveRequestFormBean;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by abedch on 2/9/2016.
 */
public class TaskPresenterImpl implements ITaskPresenter {


    ITaskInteraction taskInteraction;

    ITasksView taskView;

    private ArrayList<WidgetActionItemBean>  widgetActionItems;

    public TaskPresenterImpl(ITaskInteraction tasksInteraction) {
        this.taskInteraction = tasksInteraction;
    }


    @Override
    public void setWidgetActionItems(ArrayList<WidgetActionItemBean> widgetActionItems) {
        this.widgetActionItems = widgetActionItems;
    }

    @Override
    public ArrayList<WidgetActionItemBean> getWidgetActionItems() {
        return this.widgetActionItems;
    }

    @Override
    public void updateProjectDueDate(Integer idAppUser,Integer idTask, Date date) {
        
        if(date==null)
            taskInteraction.removeProjectDueDate(idTask, idAppUser, this);
            else
        {
            taskInteraction.updateProjectDueDate(idTask, date, idAppUser, this);
        }

    }

    @Override
    public void updateDueCallBack(Date date) {

    }


    @Override
    public void getUserTasks(Integer idAppUser, Integer idSelectedProject) {

        //Integer idAppUser = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt("userId",0)

        if (idSelectedProject == 0) {
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
            param.idAppUser = idAppUser;
            param.idWorkflowInstance = idSelectedProject;
            param.showCompleted = false;
            param.getSubtasks = true;
            param.getProjectPhases = false;

            taskInteraction.getProjectTasks(param, this);
        }


    }


    @Override
    public void getProjectTasksCBH(UserTaskBean selectedProject) {
        taskView.refreshSelectedProject(selectedProject);
        refreshList();
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

    @Override
    public void removeProjectDueDateCallBack() {

    }

}
