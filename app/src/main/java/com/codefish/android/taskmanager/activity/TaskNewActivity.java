package com.codefish.android.taskmanager.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.codefish.android.taskmanager.fragment.SingleFragmentActivity;
import com.codefish.android.taskmanager.fragment.TaskNewFragment;
import com.codefish.android.taskmanager.model.UserTaskBean;

/**
 * Created by abedch on 2/27/2016.
 */
public class TaskNewActivity extends SingleFragmentActivity {

    public static Intent newInstance(Context context) {
        Intent intent = new Intent(context,TaskNewActivity.class);
        return intent;
    }


    @Override
    protected Fragment createFragment() {
        return new TaskNewFragment();
    }

    public void submitNewTask(UserTaskBean taskBean) {
        Intent intent = new Intent();
        intent.putExtras(taskBean.getBundle());
        setResult(1001, intent);
        finish();
    }


}
