package com.codefish.android.taskmanager.interactor;

import android.widget.Toast;

import com.codefish.android.taskmanager.model.LoginModel;
import com.codefish.android.taskmanager.model.ServiceModel;
import com.codefish.android.taskmanager.presenter.ITaskEditPresenter;

import java.io.IOException;
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
                        if (response.code() == 500 && response.errorBody().contentLength()<200) {
                            taskEditPresenter.showErrorMsg(response.errorBody().string());
                        } else {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        taskEditPresenter.showErrorMsg("Illegal error, please contact the admin");
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
                        if (response.code() == 500 && response.errorBody().contentLength()<200) {
                            taskEditPresenter.showErrorMsg(response.errorBody().string());
                        } else {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        taskEditPresenter.showErrorMsg("Illegal error, please contact the admin");
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
                        if (response.code() == 500 && response.errorBody().contentLength()<200) {
                            taskEditPresenter.showErrorMsg(response.errorBody().string());
                        } else {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        taskEditPresenter.showErrorMsg("Illegal error, please contact the admin");
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

        ServiceModel.getInstance().taskService.updateDueDate(idTask, idAppUser, date).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    taskEditPresenter.updateDueCallBack(date);
                } else {
                    try {
                        if (response.code() == 500 && response.errorBody().contentLength()<200) {
                            taskEditPresenter.showErrorMsg(response.errorBody().string());
                        } else {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        taskEditPresenter.showErrorMsg("Illegal error, please contact the admin");
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
                        if (response.code() == 500 && response.errorBody().contentLength()<200) {
                            taskEditPresenter.showErrorMsg(response.errorBody().string());
                        } else {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        taskEditPresenter.showErrorMsg("Illegal error, please contact the admin");
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
                        if (response.code() == 500 && response.errorBody().contentLength()<200) {
                            taskEditPresenter.showErrorMsg(response.errorBody().string());
                        } else {
                            throw new Exception();
                        }
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
                        if (response.code() == 500 && response.errorBody().contentLength()<200) {
                            taskEditPresenter.showErrorMsg(response.errorBody().string());
                        } else {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        taskEditPresenter.showErrorMsg("Illegal error, please contact the admin!!");
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }


}
