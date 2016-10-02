package com.codefish.android.taskmanager.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.codefish.android.taskmanager.R;
import com.codefish.android.taskmanager.fragment.LeaveWorkflowFormFragment;
import com.codefish.android.taskmanager.fragment.SingleFragmentActivity;
import com.codefish.android.taskmanager.fragment.TaskDetailsFragment;
import com.codefish.android.taskmanager.fragment.TaskNewFragment;
import com.codefish.android.taskmanager.fragment.TasksListFragment;
import com.codefish.android.taskmanager.model.LoginModel;
import com.codefish.android.taskmanager.model.ServiceModel;
import com.codefish.android.taskmanager.model.TaskListBean;
import com.codefish.android.taskmanager.model.TasksModel;
import com.codefish.android.taskmanager.model.UserTaskBean;
import com.codefish.android.taskmanager.model.WidgetActionItemBean;
import com.codefish.android.taskmanager.model.hr.MobLeaveRequestFormBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by abedch on 4/26/2016.
 */
public class TasksListActivity extends SingleFragmentActivity implements TasksListFragment.Callbacks {


    private TasksListFragment tasksListFragment;

    public static Intent newInstance(Context context) {
        Intent intent = new Intent(context, TasksListActivity.class);
        return intent;

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected Fragment createFragment() {
        tasksListFragment = new TasksListFragment();
        return tasksListFragment;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            //Restore the fragment's instance
            tasksListFragment = (TasksListFragment) getSupportFragmentManager().getFragment(savedInstanceState, "TasksListFragment");

        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {
            //Restore the fragment's instance
            tasksListFragment = (TasksListFragment) getSupportFragmentManager().getFragment(savedInstanceState, "TasksListFragment");

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Save the fragment's instance
        getSupportFragmentManager().putFragment(outState, "TasksListFragment", tasksListFragment);

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    @Override
    public void onNewItemSelect() {
        Intent navToNewTask = TaskNewActivity.newInstance(this);
        startActivityForResult(navToNewTask, TasksModel.REQUEST_ADD_NEW_ITEM);
    }


    @Override
    public void onOpenLeaveForm(WidgetActionItemBean leaveActionItemBean) {
        Intent navToDetails = LeaveWorkflowFormActivity.newInstance(this,leaveActionItemBean);
        startActivityForResult(navToDetails, TasksModel.REQUEST_WORKFLOW_TASK_UPDATE);
    }

    @Override
    public void onItemSelected(UserTaskBean bean) {
        // open second view
        if (bean != null) {
            Intent navToDetails = TaskDetailsActivity.newInstance(this, bean,tasksListFragment.idSelectedProject);
            startActivityForResult(navToDetails, TaskDetailsFragment.REQUEST_TASK_BEAN);
        }

    }





    @Override
    public void onBackPressed() {

        moveTaskToBack(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UserTaskBean taskBean;
        if (tasksListFragment != null && data != null) {
            if (requestCode == TasksModel.REQUEST_ADD_NEW_ITEM) {
                taskBean = new UserTaskBean(data.getExtras());
                tasksListFragment.addItem(taskBean);
                tasksListFragment.showTaskViewSnackBar(taskBean);
            } else if (requestCode == TaskDetailsFragment.REQUEST_TASK_BEAN) {
                if (data.getExtras().getBoolean("deleteThisTask")) {
                    tasksListFragment.removeSelectedItem();
                } else {
                    UserTaskBean bean = new UserTaskBean(data.getExtras());
                    tasksListFragment.updateItem(bean);
                }
            } else if (requestCode == TasksListFragment.VOICE_RECOGNITION_REQUEST_CODE) {
                ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if (matches.get(0) != null && matches.get(0).length() > 0) {
                    submitTaskByVoice(matches.get(0));
                }
            }
        }
    }


    public void submitTaskByVoice(String taskTitle) {
        if (taskTitle != null && taskTitle.length() > 0) {
            final TaskListBean taskListBean = new TaskListBean();

            taskListBean.name = taskTitle;
            taskListBean.idSubmittedBy = PreferenceManager.getDefaultSharedPreferences(getBaseContext()).getInt("userId",0);
            if (tasksListFragment.idSelectedProject != null && tasksListFragment.idSelectedProject >0)
            {
                taskListBean.idGroup = tasksListFragment.idSelectedProject;
            }

            ServiceModel.getInstance().taskService.createTask(taskListBean).enqueue(new Callback<UserTaskBean>() {
                @Override
                public void onResponse(Call<UserTaskBean> call, Response<UserTaskBean> response) {
                    if (response.isSuccessful()) {
                        UserTaskBean newTaskBean = response.body();
                        if (newTaskBean != null && tasksListFragment != null) {
                            tasksListFragment.addItem(newTaskBean);
                            tasksListFragment.showTaskViewSnackBar(newTaskBean);
                            tasksListFragment.refreshUserTasks();
                        } else {
                            Toast.makeText(getBaseContext(),getBaseContext().getString(R.string.illegal_error_msg), Toast.LENGTH_LONG);
                        }
                    }
                    else
                    {
                        try {
                            if(response.code()==500 && response.errorBody().contentLength()<500)
                            {
                                Toast.makeText(getBaseContext(), response.errorBody().string(), Toast.LENGTH_LONG);
                            }
                            else
                            {
                                throw new Exception();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getBaseContext(), getBaseContext().getString(R.string.illegal_error_msg), Toast.LENGTH_LONG);
                        }
                    }

                }

                @Override
                public void onFailure(Call<UserTaskBean> call, Throwable t) {

                }
            });

        }

    }

}

