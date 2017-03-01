package com.codefish.android.taskmanager.interactor;


import com.codefish.android.taskmanager.MyApplication;
import com.codefish.android.taskmanager.component.tasksRecyclerView.TaskListAdapter;
import com.codefish.android.taskmanager.model.GetTaskParameter;
import com.codefish.android.taskmanager.model.GetUserTasksParameter;
import com.codefish.android.taskmanager.model.MobWorkflowBean;
import com.codefish.android.taskmanager.model.ResponseBean;
import com.codefish.android.taskmanager.model.ServiceModel;
import com.codefish.android.taskmanager.model.SubmitActionParam;
import com.codefish.android.taskmanager.model.UserTaskBean;
import com.codefish.android.taskmanager.model.hr.ICustomFormBean;
import com.codefish.android.taskmanager.model.hr.MobLeaveRequestFormBean;
import com.codefish.android.taskmanager.model.hr.SimpleLeaveRequestFormBean;
import com.codefish.android.taskmanager.presenter.ITaskPresenter;
import com.codefish.android.taskmanager.service.IHrService;
import com.codefish.android.taskmanager.service.ITaskService;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import javax.inject.Inject;

import okhttp3.ResponseBody;
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
    public void updateProjectDueDate(Integer idWorkflowInstance, final Date date, Integer idAppUser, final ITaskPresenter taskPresenter) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String dueDateStr = dateFormat.format(date);
        ServiceModel.getInstance().taskService.updateProjectDueDate(idWorkflowInstance, idAppUser, dueDateStr).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    taskPresenter.updateDueCallBack(date);
                } else {
                    try {
                        String errorB = response.errorBody().string();
                        Gson gson = new Gson();
                        ResponseBean responseBean = gson.fromJson(errorB, ResponseBean.class);
                        taskPresenter.showErrorMsg(responseBean.description);
                    } catch (Exception e) {
                        e.printStackTrace();
                        taskPresenter.showErrorMsg("Illegal error, "+response.code() +" please contact the admin");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                taskPresenter.showErrorMsg("Can not reach CodeFish");
            }
        });

    }

    @Override
    public void removeProjectDueDate(Integer idTask, Integer idAppUser, final ITaskPresenter taskPresenter) {

        ServiceModel.getInstance().taskService.removeProjectDueDate(idTask, idAppUser).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    taskPresenter.removeProjectDueDateCallBack();
                } else {
                    try {
                        String errorB = response.errorBody().string();
                        Gson gson = new Gson();
                        ResponseBean responseBean = gson.fromJson(errorB, ResponseBean.class);
                        taskPresenter.showErrorMsg(responseBean.description);
                    } catch (Exception e) {
                        e.printStackTrace();
                        taskPresenter.showErrorMsg("Illegal error, "+response.code() +" please contact the admin");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                taskPresenter.showErrorMsg("Can not reach CodeFish");
            }
        });

    }

    @Override
    public void getUserTasks(GetUserTasksParameter params, final ITaskPresenter taskPresenter) {

        taskService.getUserTasks(params).enqueue(new Callback<ArrayList<UserTaskBean>>() {
            @Override
            public void onResponse(Call<ArrayList<UserTaskBean>> call, Response<ArrayList<UserTaskBean>> response) {
                if (response.isSuccessful()) {
                    ArrayList<UserTaskBean> list = response.body();
                    taskListAdapter.setDataSet(list==null?new ArrayList<UserTaskBean>():list);
                    taskPresenter.refreshList();
                } else {
                    try {
                        String errorB = response.errorBody().string();
                        Gson gson = new Gson();
                        ResponseBean responseBean = gson.fromJson(errorB, ResponseBean.class);
                        taskPresenter.showErrorMsg(responseBean.description);
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
                    taskListAdapter.setDataSet(project.children==null?new ArrayList<UserTaskBean>():project.children);
                    taskPresenter.getProjectTasksCBH(project);
                } else {
                    try {
                        String errorB = response.errorBody().string();
                        Gson gson = new Gson();
                        ResponseBean responseBean = gson.fromJson(errorB, ResponseBean.class);
                        taskPresenter.showErrorMsg(responseBean.description);
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
