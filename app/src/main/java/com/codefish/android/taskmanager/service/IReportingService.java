package com.codefish.android.taskmanager.service;


import java.util.Collection;
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
public interface IReportingService {

    @GET("executeBeanReport")
    Call<List<HashMap<String,Object>>> executeBeanReport(@Query("beanPath") String beanPath
            , @Query("searchText") String searchText, @Query("idAppUser") Integer idAppUser);

}
