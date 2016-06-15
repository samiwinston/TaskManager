package com.codefish.android.taskmanager.service;


import com.codefish.android.taskmanager.model.GetTaskParameter;
import com.codefish.android.taskmanager.model.GetUserTasksParameter;
import com.codefish.android.taskmanager.model.MobUserTaskBean;
import com.codefish.android.taskmanager.model.SaveEntityAction;
import com.codefish.android.taskmanager.model.SubmitActionParam;
import com.codefish.android.taskmanager.model.TaskListBean;
import com.codefish.android.taskmanager.model.UserTaskBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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

    @POST("changeState")
    Call<String> changeState(@Body SubmitActionParam params);

    @GET("updateTaskField")
    Call<String> updateTaskField(@Query("idUserTask") Integer idUserTask,
                                 @Query("path") String path,
                                 @Query("value") String value);

    @GET("updateImportance")
    Call<String> updateImportance(@Query("idAppUser") Integer idAppUser,
                                 @Query("idUserTask") Integer idUserTask,
                                 @Query("importance") Integer importance);

    @GET("updateDueDate")
    Call<String> updateDueDate(@Query("idUserTask") Integer idUserTask,
                               @Query("idAppUser") Integer idAppUser,@Query("dueDate") Date dueDate);
    @GET("reassignTask")
    Call<String> reassignTask(@Query("idUserTask") Integer idUserTask,
                               @Query("idAppUser") Integer idAppUser,@Query("reassignTo") Integer reassignTo);

    @GET("deleteTask")
    Call<String> deleteTask(@Query("idAppUser") Integer idAppUser,
                              @Query("idUserTask") Integer idUserTask);

    @GET("addFollower")
    Call<String> addFollower(@Query("topicName")  String topicName,@Query("idUserTask")  Integer idUserTask,
                                @Query("idAppUser")Integer  idAppUser,
                                @Query("idFollower")Integer  idFollower,
                                @Query("idSup")Boolean isSup,@Query("sendNotification")Boolean sendNotification);

    @GET("removeFollower")
    Call<String> removeFollower(@Query("idUserTask") Integer idUserTask,
                              @Query("idAppUser") Integer idAppUser,@Query("followerIdAppUser") Integer followerIdAppUser);

}
