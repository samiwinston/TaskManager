package com.codefish.android.taskmanager.interactor;

import com.codefish.android.taskmanager.model.GetTaskParameter;
import com.codefish.android.taskmanager.model.MobWorkflowForm;
import com.codefish.android.taskmanager.model.ServiceModel;
import com.codefish.android.taskmanager.model.SubmitActionParam;
import com.codefish.android.taskmanager.model.UserTaskBean;
import com.codefish.android.taskmanager.presenter.ITaskDetailsPresenter;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by abedch on 5/25/2016.
 */
public class TaskDetailsInteractionImpl implements ITaskDetailsInteraction {


    @Override
    public void updateDueDate(Integer idTask, final Date date, Integer idAppUser, final ITaskDetailsPresenter taskDetailsPresenter) {

        ServiceModel.getInstance().taskService.updateDueDate(idTask, idAppUser, date).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    taskDetailsPresenter.updateDueCallBack(date);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    @Override
    public void getTask(GetTaskParameter params, final ITaskDetailsPresenter taskDetailsPresenter) {

        //params.idAppUser = 1;

        // ITaskService taskService = retrofit.create(ITaskService.class);
        ServiceModel.getInstance().taskService.getTask(params).enqueue(new Callback<UserTaskBean>() {
            @Override
            public void onResponse(Call<UserTaskBean> call, Response<UserTaskBean> response) {
                if (response.isSuccessful()) {
                    UserTaskBean bean = response.body();
                    taskDetailsPresenter.loadUserTaskBean(bean);
                }
            }

            @Override
            public void onFailure(Call<UserTaskBean> call, Throwable t) {
                t.printStackTrace();
                taskDetailsPresenter.showErrorMsg("Could not reach Codefish");
            }
        });

    }

    @Override
    public void changeState(SubmitActionParam submitParams, final ITaskDetailsPresenter taskDetailsPresenter) {

        ServiceModel.getInstance().taskService.changeState(submitParams).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    taskDetailsPresenter.changeStateCBH();
                } else {
                    taskDetailsPresenter.showErrorMsg("Can not reach CodeFish");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                taskDetailsPresenter.showErrorMsg("Can not reach CodeFish");
            }
        });

    }

    @Override
    public void updateImportance(Integer idAppUser, Integer idUserTask, Integer importance, final ITaskDetailsPresenter taskDetailsPresenter) {

        ServiceModel.getInstance().taskService.updateImportance(idAppUser, idUserTask, importance).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    taskDetailsPresenter.updateImportanceCBH();
                } else {
                    taskDetailsPresenter.showErrorMsg("Can not reach CodeFish");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                taskDetailsPresenter.showErrorMsg("Can not reach CodeFish");

            }
        });
    }

    @Override
    public void getWorkflowForm(Integer idAppUser,Integer idWorkflowInstance,final ITaskDetailsPresenter taskDetailsPresenter) {

        ServiceModel.getInstance().taskService.getWorkflowForm(idAppUser,idWorkflowInstance).enqueue(new Callback<MobWorkflowForm>() {
            @Override
            public void onResponse(Call<MobWorkflowForm> call, Response<MobWorkflowForm> response) {
                if (response.isSuccessful()) {
                    taskDetailsPresenter.loadWorkflowForm(response.body());
                } else {
                    taskDetailsPresenter.showErrorMsg("Can not reach CodeFish");
                }
            }

            @Override
            public void onFailure(Call<MobWorkflowForm> call, Throwable t) {
                taskDetailsPresenter.showErrorMsg("Can not reach CodeFish");
            }
        });

    }

}