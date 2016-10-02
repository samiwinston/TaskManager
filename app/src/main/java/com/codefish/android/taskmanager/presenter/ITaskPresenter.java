package com.codefish.android.taskmanager.presenter;

import com.codefish.android.taskmanager.activity.ITasksView;
import com.codefish.android.taskmanager.model.UserTaskBean;
import com.codefish.android.taskmanager.model.WidgetActionItemBean;
import com.codefish.android.taskmanager.model.hr.MobLeaveRequestFormBean;

/**
 * Created by abedch on 2/2/2016.
 */
public interface ITaskPresenter {


    void getUserTasks(Integer idAppUser, Integer idSelectedProject);

    void refreshList();

    void showErrorMsg(String msg);

    void setLoginView(ITasksView taskView);

    void selectTask(UserTaskBean bean, int position);

}
