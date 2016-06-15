package com.codefish.android.taskmanager.daggerConfig.module;

import com.codefish.android.taskmanager.component.tasksRecyclerView.TaskListAdapter;
import com.codefish.android.taskmanager.interactor.ILoginInteraction;
import com.codefish.android.taskmanager.interactor.ITaskDetailsInteraction;
import com.codefish.android.taskmanager.interactor.ITaskEditInteraction;
import com.codefish.android.taskmanager.interactor.LoginInteractionImpl;
import com.codefish.android.taskmanager.interactor.TaskDetailsInteractionImpl;
import com.codefish.android.taskmanager.interactor.TaskEditInteractionImpl;
import com.codefish.android.taskmanager.interactor.TaskInteractionImpl;
import com.codefish.android.taskmanager.model.UserTaskBean;
import com.codefish.android.taskmanager.presenter.ILoginPresenter;
import com.codefish.android.taskmanager.presenter.ITaskDetailsPresenter;
import com.codefish.android.taskmanager.presenter.ITaskEditPresenter;
import com.codefish.android.taskmanager.presenter.ITaskPresenter;
import com.codefish.android.taskmanager.presenter.LoginPresenterImpl;
import com.codefish.android.taskmanager.presenter.TaskDetailsPresenterImpl;
import com.codefish.android.taskmanager.presenter.TaskEditPresenterImpl;
import com.codefish.android.taskmanager.presenter.TaskPresenterImpl;
import com.codefish.android.taskmanager.utils.SmartDateFormatter;

import java.util.ArrayList;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by abedch on 2/9/2016.
 */
@Module
public class AppModule {

    @Provides
    @Singleton
    SmartDateFormatter providesSmartDateFormatter(){
        return new SmartDateFormatter();
    }

    @Provides
    @Singleton
    TaskListAdapter providesTaskListAdapter(){
        return new TaskListAdapter(new ArrayList<UserTaskBean>());
    }


    @Provides
    @Singleton
    ILoginPresenter providesLoginPresenter(ILoginInteraction loginInteraction){
        return new LoginPresenterImpl(loginInteraction);
    }


    @Provides
    @Singleton
    ITaskEditPresenter providesTaskEditPresenter(ITaskEditInteraction taskEditInteraction){
        return new TaskEditPresenterImpl(taskEditInteraction);
    }

    @Provides
    @Singleton
    ITaskDetailsPresenter providesTaskDetailsPresenter(ITaskDetailsInteraction taskDetailsInteraction){
        return new TaskDetailsPresenterImpl(taskDetailsInteraction);
    }

    @Provides
    @Singleton
    ITaskDetailsInteraction providesTaskDetailsInteraction(){
        return new TaskDetailsInteractionImpl();
    }

    @Provides
    @Singleton
    ITaskEditInteraction providesTaskEditInteraction(){
        return new TaskEditInteractionImpl();
    }

    @Provides
    @Singleton
    ILoginInteraction providesLoginInteraction(){
        return new LoginInteractionImpl();
    }

    @Provides
    @Singleton
    ITaskPresenter providesTaskPresenter(){
        return new TaskPresenterImpl(new TaskInteractionImpl());
    }

   /* @Provides
    @Singleton
    ITaskInteraction providesTaskInteraction(){
        return new TaskInteractionImpl();
    }*/

}
