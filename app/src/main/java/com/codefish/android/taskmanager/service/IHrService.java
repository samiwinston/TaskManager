package com.codefish.android.taskmanager.service;


import com.codefish.android.taskmanager.model.SubmitActionParam;
import com.codefish.android.taskmanager.model.UserTaskBean;
import com.codefish.android.taskmanager.model.hr.MobLeaveRequestFormBean;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by abedch on 2/2/2016.
 */
public interface IHrService {

    @POST("getMobLeaveRequestBean")
    Call<MobLeaveRequestFormBean> getMobLeaveRequestBean(@Body SubmitActionParam submitParams);


    @POST("submitLeaveRequest")
    Call<ResponseBody> submitLeaveRequest(@Body MobLeaveRequestFormBean mLeaveBean);
}
