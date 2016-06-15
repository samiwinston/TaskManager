package com.codefish.android.taskmanager.presenter;

import com.codefish.android.taskmanager.activity.ITasksView;
import com.codefish.android.taskmanager.model.UserTaskBean;

/**
 * Created by abedch on 2/2/2016.
 */
public interface ITaskPresenter {


    void getUserTasks(Integer idSelectedProject);

    void refreshList();

    void showErrorMsg(String msg);

    void setLoginView(ITasksView taskView);

    void selectTask(UserTaskBean bean, int position);
}
