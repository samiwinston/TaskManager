package com.codefish.android.taskmanager.daggerConfig.component;

import com.codefish.android.taskmanager.activity.LoginActivity;
import com.codefish.android.taskmanager.component.smartDateView.SmartDateButton;
import com.codefish.android.taskmanager.component.smartDateView.SmartDateTextView;
import com.codefish.android.taskmanager.component.tasksRecyclerView.TaskListAdapter;
import com.codefish.android.taskmanager.fragment.TaskDetailsFragment;
import com.codefish.android.taskmanager.fragment.TaskEditFragment;
import com.codefish.android.taskmanager.fragment.TaskNewFragment;
import com.codefish.android.taskmanager.fragment.TasksListFragment;
import com.codefish.android.taskmanager.interactor.LoginInteractionImpl;
import com.codefish.android.taskmanager.daggerConfig.module.AppModule;
import com.codefish.android.taskmanager.daggerConfig.module.NetModule;
import com.codefish.android.taskmanager.interactor.TaskInteractionImpl;
import com.codefish.android.taskmanager.model.ServiceModel;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by abedch on 2/11/2016.
 */
@Singleton
@Component(modules = {AppModule.class,NetModule.class})
public interface AppComponent {


    void inject(LoginActivity loginActivity);
    void inject(TasksListFragment tasksListFragment);
    void inject(LoginInteractionImpl loginInteraction);
    void inject(TaskInteractionImpl loginInteraction);
    void inject(TaskListAdapter taskListAdapter);
    void inject(ServiceModel serviceModel);
    void inject(TaskEditFragment taskEditFragment);
    void inject(TaskDetailsFragment taskEditFragment);
    void inject(TaskNewFragment taskNewFragment);
    void inject(SmartDateTextView smartDateTextView);
    void inject(SmartDateButton smartDateButton);
}
