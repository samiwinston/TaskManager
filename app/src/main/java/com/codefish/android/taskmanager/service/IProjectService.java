package com.codefish.android.taskmanager.service;


import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by abedch on 2/2/2016.
 */
public interface IProjectService {

    @GET("getMyProjects")
    Call<List<HashMap<String,Object>>> getMyProjects(@Query("idAppUser") Integer idAppUser,@Query("getStats") Boolean getStats,
                                                     @Query("closedProjects") Boolean closedProjects);

}
