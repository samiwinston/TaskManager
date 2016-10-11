package com.codefish.android.taskmanager.service;


import com.codefish.android.taskmanager.model.SubmitActionParam;
import com.codefish.android.taskmanager.model.UserTaskBean;
import com.codefish.android.taskmanager.model.hr.MobLeaveRequestFormBean;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by abedch on 2/2/2016.
 */
public interface IHrService {

    @POST("getMobLeaveRequestBean")
    Call<MobLeaveRequestFormBean> getMobLeaveRequestBean(@Body SubmitActionParam submitParams);


    @POST("submitLeaveRequest")
    Call<ResponseBody> submitLeaveRequest(@Body MobLeaveRequestFormBean mLeaveBean);

    @GET("getEmployeesOnLeaveDuringDates")
    Call<List<HashMap<String,Object>>> getEmployeesOnLeaveDuringDates(@Query("idAppUser") Integer idAppUser,
                                                                      @Query("startDate") String startDate,
                                                                      @Query("endDate") String endDate);
}
