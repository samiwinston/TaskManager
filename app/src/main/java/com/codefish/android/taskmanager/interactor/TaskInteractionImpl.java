package com.codefish.android.taskmanager.interactor;


import com.codefish.android.taskmanager.MyApplication;
import com.codefish.android.taskmanager.component.tasksRecyclerView.TaskListAdapter;
import com.codefish.android.taskmanager.model.GetTaskParameter;
import com.codefish.android.taskmanager.model.GetUserTasksParameter;
import com.codefish.android.taskmanager.model.MobWorkflowBean;
import com.codefish.android.taskmanager.model.ServiceModel;
import com.codefish.android.taskmanager.model.SubmitActionParam;
import com.codefish.android.taskmanager.model.UserTaskBean;
import com.codefish.android.taskmanager.model.hr.ICustomFormBean;
import com.codefish.android.taskmanager.model.hr.MobLeaveRequestFormBean;
import com.codefish.android.taskmanager.model.hr.SimpleLeaveRequestFormBean;
import com.codefish.android.taskmanager.presenter.ITaskPresenter;
import com.codefish.android.taskmanager.service.IHrService;
import com.codefish.android.taskmanager.service.ITaskService;

import java.util.ArrayList;


import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by abedch on 2/15/2016.
 */
public class TaskInteractionImpl implements ITaskInteraction {


    @Inject
    ITaskService taskService;

    @Inject
    IHrService hrService;

    @Inject
    TaskListAdapter taskListAdapter;

    /*@Inject
    ITaskPresenter taskPresenter;*/

    public TaskInteractionImpl() {
        MyApplication.getAppComponent().inject(this);
    }


    @Override
    public void getUserTasks(GetUserTasksParameter params, final ITaskPresenter taskPresenter) {

        taskService.getUserTasks(params).enqueue(new Callback<ArrayList<UserTaskBean>>() {
            @Override
            public void onResponse(Call<ArrayList<UserTaskBean>> call, Response<ArrayList<UserTaskBean>> response) {
                if (response.isSuccessful()) {
                    ArrayList<UserTaskBean> list = response.body();
                    taskListAdapter.setDataSet(list);
                    taskPresenter.refreshList();
                } else {
                    try {
                        if (response.code() == 500 && response.errorBody().contentLength()<500) {
                            taskPresenter.showErrorMsg(response.errorBody().string());
                        } else {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        taskPresenter.showErrorMsg("Illegal error, "+response.code() +" please contact the admin");
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<UserTaskBean>> call, Throwable t) {
                t.printStackTrace();
                taskPresenter.showErrorMsg("Could not reach Codefish");
            }
        });

    }

    @Override
    public void getProjectTasks(GetTaskParameter param, final ITaskPresenter taskPresenter) {

        ServiceModel.getInstance().taskService.getTask(param).enqueue(new Callback<UserTaskBean>() {
            @Override
            public void onResponse(Call<UserTaskBean> call, Response<UserTaskBean> response) {
                if (response.isSuccessful()) {
                    UserTaskBean project = response.body();
                    taskListAdapter.setDataSet(project.children);
                    taskPresenter.refreshList();
                } else {
                    try {
                        if (response.code() == 500 && response.errorBody().contentLength()<500) {
                            taskPresenter.showErrorMsg(response.errorBody().string());
                        } else {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        taskPresenter.showErrorMsg("Illegal error, "+response.code() +" please contact the admin");
                    }
                }
            }

            @Override
            public void onFailure(Call<UserTaskBean> call, Throwable t) {
                taskPresenter.showErrorMsg("Can not reach CodeFish");
            }
        });


    }


}
