package com.codefish.android.taskmanager.interactor;

import android.widget.Toast;

import com.codefish.android.taskmanager.model.LoginModel;
import com.codefish.android.taskmanager.model.ResponseBean;
import com.codefish.android.taskmanager.model.ServiceModel;
import com.codefish.android.taskmanager.presenter.ITaskEditPresenter;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by abedch on 5/16/2016.
 */
public class TaskEditInteractionImpl implements ITaskEditInteraction {

    @Override
    public void reassignTask(Integer idTask, Integer idAppUser, final Integer reassignTo, final ITaskEditPresenter taskEditPresenter) {

        ServiceModel.getInstance().taskService.reassignTask(idTask, idAppUser, reassignTo).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (reassignTo == 0) {
                        taskEditPresenter.unassignTaskCallBack();
                    } else {
                        taskEditPresenter.reassignTaskCallBack();
                    }
                } else {
                    try {
                        String errorB = response.errorBody().string();
                        Gson gson = new Gson();
                        ResponseBean responseBean = gson.fromJson(errorB, ResponseBean.class);
                        taskEditPresenter.showErrorMsg(responseBean.description);
                    } catch (Exception e) {
                        e.printStackTrace();
                        taskEditPresenter.showErrorMsg("Illegal error, "+response.code() +" please contact the admin");
                    }
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                taskEditPresenter.showErrorMsg("Can not reach Codefish");
                t.printStackTrace();
            }
        });

    }

    @Override
    public void deleteTask(Integer idAppUser, int idTask, final ITaskEditPresenter taskEditPresenter) {
        ServiceModel.getInstance().taskService.deleteTask(idAppUser, idTask).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    taskEditPresenter.deleteTaskCallBack();
                } else {
                    try {
                        String errorB = response.errorBody().string();
                        Gson gson = new Gson();
                        ResponseBean responseBean = gson.fromJson(errorB, ResponseBean.class);
                        taskEditPresenter.showErrorMsg(responseBean.description);
                    } catch (Exception e) {
                        e.printStackTrace();
                        taskEditPresenter.showErrorMsg("Illegal error, "+response.code() +" please contact the admin");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                taskEditPresenter.showErrorMsg("Can not reach Codefish");
            }
        });
    }

    @Override
    public void moveToProject(int idWorkflowInstance, int idProject, int idAppUser, final ITaskEditPresenter taskEditPresenter) {
        ServiceModel.getInstance().taskService.moveToProject(idWorkflowInstance, idProject, idAppUser).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    taskEditPresenter.moveToProjectCallBack();
                } else {
                    try {
                        String errorB = response.errorBody().string();
                        Gson gson = new Gson();
                        ResponseBean responseBean = gson.fromJson(errorB, ResponseBean.class);
                        taskEditPresenter.showErrorMsg(responseBean.description);
                    } catch (Exception e) {
                        e.printStackTrace();
                        taskEditPresenter.showErrorMsg("Illegal error, "+response.code() +" please contact the admin");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                taskEditPresenter.showErrorMsg("Can not reach Codefish");
            }
        });
    }

    @Override
    public void updateDueDate(Integer idTask, final Date date, Integer idAppUser, final ITaskEditPresenter taskEditPresenter) {


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String dueDateStr = dateFormat.format(date);
        ServiceModel.getInstance().taskService.updateDueDate(idTask, idAppUser, dueDateStr).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    taskEditPresenter.updateDueCallBack(date);
                } else {
                    try {
                        String errorB = response.errorBody().string();
                        Gson gson = new Gson();
                        ResponseBean responseBean = gson.fromJson(errorB, ResponseBean.class);
                        taskEditPresenter.showErrorMsg(responseBean.description);
                    } catch (Exception e) {
                        e.printStackTrace();
                        taskEditPresenter.showErrorMsg("Illegal error, "+response.code() +" please contact the admin");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();

                taskEditPresenter.showErrorMsg("Can not reach CodeFish");
            }
        });

    }

    @Override
    public void removeDueDate(Integer idTask, Integer idAppUser, final ITaskEditPresenter taskEditPresenter) {

        ServiceModel.getInstance().taskService.removeDueDate(idTask, idAppUser).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    taskEditPresenter.removeDueDateCallBack();
                } else {
                    try {
                        String errorB = response.errorBody().string();
                        Gson gson = new Gson();
                        ResponseBean responseBean = gson.fromJson(errorB, ResponseBean.class);
                        taskEditPresenter.showErrorMsg(responseBean.description);
                    } catch (Exception e) {
                        e.printStackTrace();
                        taskEditPresenter.showErrorMsg("Illegal error, "+response.code() +" please contact the admin");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                taskEditPresenter.showErrorMsg("Can not reach CodeFish");
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
                } else {
                    try {
                        String errorB = response.errorBody().string();
                        Gson gson = new Gson();
                        ResponseBean responseBean = gson.fromJson(errorB, ResponseBean.class);
                        taskEditPresenter.showErrorMsg(responseBean.description);
                    } catch (Exception e) {
                        e.printStackTrace();
                        taskEditPresenter.showErrorMsg("Illegal error, "+response.code() +" please contact the admin");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<HashMap<String, Object>>> call, Throwable t) {
                t.printStackTrace();
                taskEditPresenter.showErrorMsg("Can not reach CodeFish");
            }
        });


    }

    @Override
    public void getMyProjects(Integer idAppUser, boolean getStats, boolean closedProjects, final ITaskEditPresenter taskEditPresenter) {


        ServiceModel.getInstance().taskService.getMyProjects(idAppUser, false, false).enqueue(new Callback<List<HashMap<String, Object>>>() {
            @Override
            public void onResponse(Call<List<HashMap<String, Object>>> call, Response<List<HashMap<String, Object>>> response) {
                if (response.isSuccessful()) {
                    taskEditPresenter.updateMyProjects(response.body());
                } else {
                    try {
                        String errorB = response.errorBody().string();
                        Gson gson = new Gson();
                        ResponseBean responseBean = gson.fromJson(errorB, ResponseBean.class);
                        taskEditPresenter.showErrorMsg(responseBean.description);
                    } catch (Exception e) {
                        e.printStackTrace();
                        taskEditPresenter.showErrorMsg("Can not reach Codefish");
                    }
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

        ServiceModel.getInstance().taskService.updateTaskField(idWorkflowInstance, path, value, isEntity).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                } else {
                    try {
                        String errorB = response.errorBody().string();
                        Gson gson = new Gson();
                        ResponseBean responseBean = gson.fromJson(errorB, ResponseBean.class);
                        taskEditPresenter.showErrorMsg(responseBean.description);
                    } catch (Exception e) {
                        e.printStackTrace();
                        taskEditPresenter.showErrorMsg("Illegal error, "+response.code() +" please contact the admin");
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }


}
