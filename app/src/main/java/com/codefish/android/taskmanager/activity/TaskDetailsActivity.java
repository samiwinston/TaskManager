package com.codefish.android.taskmanager.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;

import com.codefish.android.taskmanager.fragment.SingleFragmentActivity;
import com.codefish.android.taskmanager.fragment.TaskDetailsFragment;
import com.codefish.android.taskmanager.fragment.TaskNewFragment;
import com.codefish.android.taskmanager.fragment.TasksListFragment;
import com.codefish.android.taskmanager.model.UserTaskBean;

/**
 * Created by abedch on 2/27/2016.
 */
public class TaskDetailsActivity extends SingleFragmentActivity implements ITaskDetailsActivity {

    public static final String USER_TASK_BEAN = "UserTaskBean";
    public static final String SELECTED_PROJECT = "SelectedProject";
    public UserTaskBean selectedTask;
    public Integer idSelectedProject;
    TaskDetailsFragment taskDetailsFragment;

    public static Intent newInstance(Context context, UserTaskBean userTaskBean,Integer idSelectedProject) {

        Intent intent = new Intent(context, TaskDetailsActivity.class);
        intent.putExtra(SELECTED_PROJECT,idSelectedProject);
        intent.putExtra(USER_TASK_BEAN, userTaskBean.getBundle());
        return intent;

    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            taskDetailsFragment.goBackToList();
            //additional code
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedTask = new UserTaskBean(getIntent().getExtras().getBundle(TaskDetailsActivity.USER_TASK_BEAN));
        idSelectedProject = getIntent().getIntExtra(SELECTED_PROJECT,0);
    }


    @Override
    protected Fragment createFragment() {
        taskDetailsFragment = new TaskDetailsFragment();
        return taskDetailsFragment;
    }


    @Override
    public void updateTaskBeanAndFinish(UserTaskBean usertaskBean) {
        finish();
    }
}
