package com.codefish.android.taskmanager.interactor;

import com.codefish.android.taskmanager.model.GetTaskParameter;
import com.codefish.android.taskmanager.model.GetUserTasksParameter;
import com.codefish.android.taskmanager.model.SubmitActionParam;
import com.codefish.android.taskmanager.model.UserTask;
import com.codefish.android.taskmanager.model.UserTaskBean;
import com.codefish.android.taskmanager.presenter.ITaskPresenter;
import com.codefish.android.taskmanager.presenter.TaskPresenterImpl;

import java.util.ArrayList;

/**
 * Created by abedch on 2/22/2016.
 */
public interface ITaskInteraction {
   void getUserTasks(GetUserTasksParameter getUserTasksParameter,ITaskPresenter taskPresenter);
   void getProjectTasks(GetTaskParameter param, ITaskPresenter taskPresenter);
}
