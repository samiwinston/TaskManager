package com.codefish.android.taskmanager.daggerConfig.module;


import com.codefish.android.taskmanager.service.IProjectService;
import com.codefish.android.taskmanager.service.IReportingService;
import com.codefish.android.taskmanager.service.ITaskService;
import com.codefish.android.taskmanager.service.IUserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by abedch on 2/15/2016.
 */
@Module
public class NetModule {

    private final String baseUrl;
    private final OkHttpClient okHttpClient;
    private final Gson gson;

    public NetModule(String baseUrl, OkHttpClient okHttpClient) {
        this.baseUrl = baseUrl;
        this.okHttpClient = okHttpClient;
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
                    @Override
                    public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
                        Integer value = (int)Math.round(src);
                        return new JsonPrimitive(value);
                    }
                })
                .setDateFormat("yyyy-MM-dd")
                .setLenient()
                .create();
    }


    @Provides
    @Singleton
    ITaskService providesTaskService() {

        ITaskService taskService = new Retrofit.
                Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson)).build().create(ITaskService.class);
        return taskService;
    }

    @Provides
    @Singleton
    IUserService providesUserService() {
        IUserService userService = new Retrofit.
                Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create(gson)).build().create(IUserService.class);
        return userService;
    }

    @Provides
    @Singleton
    IReportingService providesReportingService() {
        IReportingService reportingService = new Retrofit.
                Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create(gson)).build().create(IReportingService.class);
        return reportingService;
    }

    @Provides
    @Singleton
    IProjectService providesProjectService() {
        IProjectService projectService = new Retrofit.
                Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create(gson)).build().create(IProjectService.class);
        return projectService;
    }

}
