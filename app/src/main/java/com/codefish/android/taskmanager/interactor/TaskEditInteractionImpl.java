package com.codefish.android.taskmanager.interactor;

import com.codefish.android.taskmanager.model.LoginModel;
import com.codefish.android.taskmanager.model.ServiceModel;
import com.codefish.android.taskmanager.presenter.ITaskEditPresenter;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by abedch on 5/16/2016.
 */
public class TaskEditInteractionImpl implements ITaskEditInteraction {

    @Override
    public void reassignTask(Integer idTask, Integer idAppUser, final Integer reassignTo, final ITaskEditPresenter taskEditPresenter) {

        ServiceModel.getInstance().taskService.reassignTask(idTask, idAppUser, reassignTo).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    taskEditPresenter.showErrorMsg("Can not reach Codefish");
                    return;
                }
                if (reassignTo == 0) {
                    taskEditPresenter.unassignTaskCallBack();
                }
                else
                {
                    taskEditPresenter.reassignTaskCallBack();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                taskEditPresenter.showErrorMsg("Can not reach Codefish");
                t.printStackTrace();
            }
        });

    }

    @Override
    public void deleteTask(Integer idAppUser, int idTask, final ITaskEditPresenter taskEditPresenter) {
        ServiceModel.getInstance().taskService.deleteTask(idAppUser,idTask).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful())
                {
                    taskEditPresenter.deleteTaskCallBack();
                }
                else
                {
                    taskEditPresenter.showErrorMsg("Can not reach Codefish");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                taskEditPresenter.showErrorMsg("Can not reach Codefish");
            }
        });
    }

    @Override
    public void moveToProject(int idWorkflowInstance, int idProject, int idAppUser, final ITaskEditPresenter taskEditPresenter) {
        ServiceModel.getInstance().taskService.moveToProject(idWorkflowInstance,idProject,idAppUser).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful())
                {
                    taskEditPresenter.moveToProjectCallBack();
                }
                else
                {
                    taskEditPresenter.showErrorMsg("Can not reach Codefish");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                taskEditPresenter.showErrorMsg("Can not reach Codefish");
            }
        });
    }

    @Override
    public void updateDueDate(Integer idTask, final Date date, Integer idAppUser, final ITaskEditPresenter taskEditPresenter) {

        ServiceModel.getInstance().taskService.updateDueDate(idTask, idAppUser, date).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    taskEditPresenter.updateDueCallBack(date);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    @Override
    public void getTaskPossibleAssigness(Integer idTask, final ITaskEditPresenter taskEditPresenter) {


        ServiceModel.getInstance().taskService.getTaskPossibleAssignees(idTask).enqueue(new Callback<List<HashMap<String, Object>>>() {
            @Override
            public void onResponse(Call<List<HashMap<String, Object>>> call, Response<List<HashMap<String, Object>>> response) {
                if (response.isSuccessful()) {
                    taskEditPresenter.updatePossibleAssignees(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<HashMap<String, Object>>> call, Throwable t) {
                t.printStackTrace();
            }
        });


    }

    @Override
    public void getMyProjects(Integer idAppUser,boolean getStats,boolean closedProjects,final ITaskEditPresenter taskEditPresenter) {


        ServiceModel.getInstance().taskService.getMyProjects(LoginModel.getInstance().getUserBean().getId(), false, false).enqueue(new Callback<List<HashMap<String, Object>>>() {
            @Override
            public void onResponse(Call<List<HashMap<String, Object>>> call, Response<List<HashMap<String, Object>>> response) {
                if (response.isSuccessful()) {
                    taskEditPresenter.updateMyProjects(response.body());
                } else {
                    taskEditPresenter.showErrorMsg("Can not reach Codefish");
                }

            }

            @Override
            public void onFailure(Call<List<HashMap<String, Object>>> call, Throwable t) {
                taskEditPresenter.showErrorMsg("Can not reach Codefish");
            }
        });


    }




    @Override
    public void updateTaskField(Integer idWorkflowInstance, String path, Object value, boolean isEntity, final ITaskEditPresenter taskEditPresenter) {

        ServiceModel.getInstance().taskService.updateTaskField(idWorkflowInstance,path,value,isEntity).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful())
                {

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }


}
