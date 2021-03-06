package com.codefish.android.taskmanager.interactor;

import android.widget.Toast;

import com.codefish.android.taskmanager.model.GenericCommentBean;
import com.codefish.android.taskmanager.model.GetTaskParameter;
import com.codefish.android.taskmanager.model.MobWorkflowForm;
import com.codefish.android.taskmanager.model.PostCommentParam;
import com.codefish.android.taskmanager.model.ResponseBean;
import com.codefish.android.taskmanager.model.ServiceModel;
import com.codefish.android.taskmanager.model.SubmitActionParam;
import com.codefish.android.taskmanager.model.UserTaskBean;
import com.codefish.android.taskmanager.presenter.ITaskDetailsPresenter;
import com.codefish.android.taskmanager.presenter.TaskDetailsPresenterImpl;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by abedch on 5/25/2016.
 */
public class TaskDetailsInteractionImpl implements ITaskDetailsInteraction {


    @Override
    public void deleteTask(Integer idAppUser, int idTask, final ITaskDetailsPresenter taskDetailsPresenter) {
        ServiceModel.getInstance().taskService.deleteTask(idAppUser, idTask).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    taskDetailsPresenter.deleteTaskCallBack();
                } else {
                    try {
                        String errorB = response.errorBody().string();
                        Gson gson = new Gson();
                        ResponseBean responseBean = gson.fromJson(errorB, ResponseBean.class);
                        taskDetailsPresenter.showErrorMsg(responseBean.description);
                    } catch (Exception e) {
                        e.printStackTrace();
                        taskDetailsPresenter.showErrorMsg("Illegal error, " + response.code() + " please contact the admin");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                taskDetailsPresenter.showErrorMsg("Can not reach Codefish");
            }
        });
    }

    @Override
    public void postTaskComment(PostCommentParam postCommentParam, final ITaskDetailsPresenter taskDetailsPresenter) {

        ServiceModel.getInstance().taskService.postTaskComment(postCommentParam).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    /*GenericCommentBean genericCommentBean = response.body();
                    taskDetailsPresenter.publishTaskComment(genericCommentBean);*/

                } else {
                    try {
                        String errorB = response.errorBody().string();
                        Gson gson = new Gson();
                        ResponseBean responseBean = gson.fromJson(errorB, ResponseBean.class);
                        taskDetailsPresenter.showErrorMsg(responseBean.description);
                    } catch (Exception e) {
                        e.printStackTrace();
                        taskDetailsPresenter.showErrorMsg("Illegal error, " + response.code() + " please contact the admin");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                taskDetailsPresenter.showErrorMsg("Can not reach CodeFish");
            }
        });
    }

    @Override
    public void updateDueDate(Integer idTask, final Date date, Integer idAppUser, final ITaskDetailsPresenter taskDetailsPresenter) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String dueDateStr = dateFormat.format(date);
        ServiceModel.getInstance().taskService.updateDueDate(idTask, idAppUser, dueDateStr).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    taskDetailsPresenter.updateDueCallBack(date);
                } else {
                    try {
                        String errorB = response.errorBody().string();
                        Gson gson = new Gson();
                        ResponseBean responseBean = gson.fromJson(errorB, ResponseBean.class);
                        taskDetailsPresenter.showErrorMsg(responseBean.description);
                    } catch (Exception e) {
                        e.printStackTrace();
                        taskDetailsPresenter.showErrorMsg("Illegal error, " + response.code() + " please contact the admin");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                taskDetailsPresenter.showErrorMsg("Can not reach CodeFish");
            }
        });

    }

    @Override
    public void removeDueDate(Integer idTask, Integer idAppUser, final ITaskDetailsPresenter taskDetailsPresenter) {

        ServiceModel.getInstance().taskService.removeDueDate(idTask, idAppUser).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    taskDetailsPresenter.removeDueDateCallBack();
                } else {
                    try {
                        String errorB = response.errorBody().string();
                        Gson gson = new Gson();
                        ResponseBean responseBean = gson.fromJson(errorB, ResponseBean.class);
                        taskDetailsPresenter.showErrorMsg(responseBean.description);
                    } catch (Exception e) {
                        e.printStackTrace();
                        taskDetailsPresenter.showErrorMsg("Illegal error, " + response.code() + " please contact the admin");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                taskDetailsPresenter.showErrorMsg("Can not reach CodeFish");
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
                } else {
                    try {
                        String errorB = response.errorBody().string();
                        Gson gson = new Gson();
                        ResponseBean responseBean = gson.fromJson(errorB, ResponseBean.class);
                        taskDetailsPresenter.showErrorMsg(responseBean.description);
                    } catch (Exception e) {
                        e.printStackTrace();
                        taskDetailsPresenter.showErrorMsg("Illegal error, " + response.code() + " please contact the admin");
                    }
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
                    taskDetailsPresenter.changeStateCBH(response.body());
                } else {
                    try {
                        String errorB = response.errorBody().string();
                        Gson gson = new Gson();
                        ResponseBean responseBean = gson.fromJson(errorB, ResponseBean.class);
                        taskDetailsPresenter.showErrorMsg(responseBean.description);
                    } catch (Exception e) {
                        e.printStackTrace();
                        taskDetailsPresenter.showErrorMsg("Can not reach Codefish, please contact the admin");
                    }
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

        ServiceModel.getInstance().taskService.updateImportance(idAppUser, idUserTask, importance).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    taskDetailsPresenter.updateImportanceCBH();
                } else {
                    try {
                        String errorB = response.errorBody().string();
                        Gson gson = new Gson();
                        ResponseBean responseBean = gson.fromJson(errorB, ResponseBean.class);
                        taskDetailsPresenter.showErrorMsg(responseBean.description);
                    } catch (Exception e) {
                        e.printStackTrace();
                        taskDetailsPresenter.showErrorMsg("Illegal error, " + response.code() + " please contact the admin");

                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                taskDetailsPresenter.showErrorMsg("Can not reach CodeFish");

            }
        });
    }

    @Override
    public void getWorkflowForm(Integer idAppUser, Integer idWorkflowInstance, final ITaskDetailsPresenter taskDetailsPresenter) {

        ServiceModel.getInstance().taskService.getWorkflowForm(idAppUser, idWorkflowInstance).enqueue(new Callback<MobWorkflowForm>() {
            @Override
            public void onResponse(Call<MobWorkflowForm> call, Response<MobWorkflowForm> response) {
                if (response.isSuccessful()) {
                    taskDetailsPresenter.loadWorkflowForm(response.body());
                } else {
                    try {
                        String errorB = response.errorBody().string();
                        Gson gson = new Gson();
                        ResponseBean responseBean = gson.fromJson(errorB, ResponseBean.class);
                        taskDetailsPresenter.showErrorMsg(responseBean.description);
                    } catch (Exception e) {
                        e.printStackTrace();
                        taskDetailsPresenter.showErrorMsg("Illegal error " + response.code() + ", please contact the admin");
                    }
                }
            }

            @Override
            public void onFailure(Call<MobWorkflowForm> call, Throwable t) {
                taskDetailsPresenter.showErrorMsg("Can not reach CodeFish");
            }
        });

    }

    @Override
    public void publishTaskComment(GenericCommentBean genericCommentBean, TaskDetailsPresenterImpl taskDetailsPresenter) {



    }

}
