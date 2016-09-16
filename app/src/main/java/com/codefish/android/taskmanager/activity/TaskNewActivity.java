package com.codefish.android.taskmanager.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.codefish.android.taskmanager.fragment.SingleFragmentActivity;
import com.codefish.android.taskmanager.fragment.TaskNewFragment;
import com.codefish.android.taskmanager.fragment.TasksListFragment;
import com.codefish.android.taskmanager.model.UserTaskBean;

/**
 * Created by abedch on 2/27/2016.
 */
public class TaskNewActivity extends SingleFragmentActivity {

    private TaskNewFragment taskNewFragment;

    public static Intent newInstance(Context context) {
        Intent intent = new Intent(context,TaskNewActivity.class);
        return intent;
    }


    @Override
    protected Fragment createFragment() {
        taskNewFragment = new TaskNewFragment();
        return taskNewFragment;
    }

    public void submitNewTask(UserTaskBean taskBean) {
        Intent intent = new Intent();
        intent.putExtras(taskBean.getBundle());
        setResult(1001, intent);
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            //Restore the fragment's instance
            taskNewFragment = (TaskNewFragment) getSupportFragmentManager().getFragment(savedInstanceState, "TaskNewFragment");

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Save the fragment's instance
        getSupportFragmentManager().putFragment(outState, "TaskNewFragment", taskNewFragment);

    }


}
