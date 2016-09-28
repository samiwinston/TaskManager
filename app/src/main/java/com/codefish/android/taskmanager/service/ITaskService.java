package com.codefish.android.taskmanager.service;


import com.codefish.android.taskmanager.model.GetTaskParameter;
import com.codefish.android.taskmanager.model.GetUserTasksParameter;
import com.codefish.android.taskmanager.model.MobUserTaskBean;
import com.codefish.android.taskmanager.model.MobWorkflowForm;
import com.codefish.android.taskmanager.model.SaveEntityAction;
import com.codefish.android.taskmanager.model.SubmitActionParam;
import com.codefish.android.taskmanager.model.TaskListBean;
import com.codefish.android.taskmanager.model.UserTaskBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by abedch on 2/2/2016.
 */
public interface ITaskService {

    @POST("getUserTasks")
    Call<ArrayList<UserTaskBean>> getUserTasks(@Body GetUserTasksParameter params);

    @GET("getMyProjects")
    Call<List<HashMap<String,Object>>> getMyProjects(@Query("idAppUser") Integer idAppUser,@Query("getStats") Boolean getStats
                                                     ,@Query("closedProjects") Boolean closedProjects);

    @GET("getTags")
    Call<List<HashMap<String,Object>>> getTags(@Query("searchText") String searchText);

    @POST("getTask")
    Call<UserTaskBean> getTask(@Body GetTaskParameter params);

    @POST("createTask")
    Call<UserTaskBean> createTask(@Body TaskListBean params);

    @GET("getTaskPossibleAssignees")
    Call<List<HashMap<String,Object>>> getTaskPossibleAssignees(@Query("idTask") Integer idTask);

    @POST("updateTask")
    Call<UserTaskBean> updateTask(@Body SaveEntityAction params);

    @GET("completeteTask")
    Call<String> completeteTask(@Query("idAppUser") Integer idAppUser,
                                 @Query("idTask") Integer idTask,
                                 @Query("score") Integer score);

    @GET("getWorkflowForm")
    Call<MobWorkflowForm> getWorkflowForm(@Query("idAppUser") Integer idAppUser,
                                          @Query("idWorkflowInstance") Integer idWorkflowInstance);

    @GET("submitWorkflowAction")
    Call<UserTaskBean> submitWorkflowAction(@Query("idAppUser") Integer idAppUser,
                                            @Query("idWorkflowInstance") Integer idWorkflowInstance,
                                            @Query("action") String action);

    @POST("changeState")
    Call<ResponseBody> changeState(@Body SubmitActionParam params);

    @GET("updateTaskField")
    Call<ResponseBody> updateTaskField(@Query("idWorkflowInstance") Integer idWorkflowInstance,
                                 @Query("path") String path,
                                 @Query("value") Object value,
                                 @Query("isEntity") boolean isEntity);

    @GET("updateImportance")
    Call<ResponseBody> updateImportance(@Query("idAppUser") Integer idAppUser,
                                 @Query("idUserTask") Integer idUserTask,
                                 @Query("importance") Integer importance);

    @GET("updateDueDate")
    Call<ResponseBody> updateDueDate(@Query("idUserTask") Integer idUserTask,
                               @Query("idAppUser") Integer idAppUser,@Query("dueDate") Date dueDate);
    @GET("removeDueDate")
    Call<ResponseBody> removeDueDate(@Query("idUserTask") Integer idUserTask,
                                     @Query("idAppUser") Integer idAppUser);
    @GET("reassignTask")
    Call<ResponseBody> reassignTask(@Query("idUserTask") Integer idUserTask,
                               @Query("idAppUser") Integer idAppUser,@Query("reassignTo") Integer reassignTo);

    @GET("deleteTask")
    Call<ResponseBody> deleteTask(@Query("idAppUser") Integer idAppUser,
                              @Query("idUserTask") Integer idUserTask);

    @GET("moveToProject")
    Call<ResponseBody> moveToProject(@Query("idWorkflowInstance") Integer idWorkflowInstance,
                                   @Query("idProject") Integer idProject,
                                   @Query("idAppUser") Integer idAppUser);

    @GET("addFollower")
    Call<ResponseBody> addFollower(@Query("topicName")  String topicName,
                             @Query("subTopic")  Integer subTopic,
                                @Query("idAppUser")Integer  idAppUser,
                                @Query("idFollower")Integer  idFollower,
                                @Query("idSup")Boolean isSup,
                             @Query("sendNotification")Boolean sendNotification);

    @GET("removeFollower")
    Call<ResponseBody> removeFollower(@Query("idAppUser") Integer idAppUser,
                                @Query("idWorkflowInstance") Integer idWorkflowInstance,@Query("followerIdAppUser") Integer followerIdAppUser);

    @GET("unfollowTask")
    Call<String> unfollowTask(@Query("topicName") String topicName,
                                @Query("subTopic") Integer subTopic,@Query("idAppUser") Integer idAppUser);

}
