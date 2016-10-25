package com.codefish.android.taskmanager.presenter;

import com.codefish.android.taskmanager.activity.ITasksView;
import com.codefish.android.taskmanager.model.UserTaskBean;
import com.codefish.android.taskmanager.model.WidgetActionItemBean;
import com.codefish.android.taskmanager.model.hr.MobLeaveRequestFormBean;

import java.util.Date;

/**
 * Created by abedch on 2/2/2016.
 */
public interface ITaskPresenter {


    void updateProjectDueDate(Integer idAppUser, Integer idTask, Date date);

    void updateDueCallBack(Date date);

    void getUserTasks(Integer idAppUser, Integer idSelectedProject);

    void getProjectTasksCBH(UserTaskBean selectedProject);

    void refreshList();

    void showErrorMsg(String msg);

    void setLoginView(ITasksView taskView);

    void selectTask(UserTaskBean bean, int position);

    void removeProjectDueDateCallBack();
}
