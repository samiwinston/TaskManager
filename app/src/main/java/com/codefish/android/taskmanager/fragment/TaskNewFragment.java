package com.codefish.android.taskmanager.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.codefish.android.taskmanager.MyApplication;
import com.codefish.android.taskmanager.R;
import com.codefish.android.taskmanager.activity.TaskNewActivity;
import com.codefish.android.taskmanager.component.MyProjectsEditText;
import com.codefish.android.taskmanager.component.SearchUserEditText;
import com.codefish.android.taskmanager.component.smartDateView.SmartDateButton;
import com.codefish.android.taskmanager.model.LoginModel;
import com.codefish.android.taskmanager.model.MobUserTaskBean;
import com.codefish.android.taskmanager.model.ResponseBean;
import com.codefish.android.taskmanager.model.ServiceModel;
import com.codefish.android.taskmanager.model.TaskListBean;
import com.codefish.android.taskmanager.model.TasksModel;
import com.codefish.android.taskmanager.model.UserTaskBean;
import com.codefish.android.taskmanager.utils.SmartDateFormatter;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by abedch on 2/27/2016.
 */
public class TaskNewFragment extends Fragment implements View.OnClickListener {


    @Bind(R.id.taskNameInput)
    EditText taskNameInput;
    @Bind(R.id.searchUsersInput)
    SearchUserEditText searchUsersInput;
    @Bind(R.id.searchProjectInput)
    MyProjectsEditText searchProjectsInput;
    @Bind(R.id.usersListView)
    ListView usersListView;
    @Bind(R.id.projectsListView)
    ListView projectsListView;
    @Bind(R.id.task_new_layout_date_button)
    SmartDateButton dateBtn;
    @Bind(R.id.createNewTask)
    Button createNewTask;
    @Bind(R.id.task_new_layout_description)
    EditText descriptionEditText;
    private TaskNewActivity taskNewActivity;

    private TaskListBean taskListBean = new TaskListBean();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.task_new_layout, container, false);
        ButterKnife.bind(this, view);
        MyApplication.getAppComponent().inject(this);

        taskNameInput.setFocusableInTouchMode(true);
        taskNameInput.requestFocus();


        List<HashMap<String, Object>> users = new ArrayList<>();
       /* HashMap<String, Object> obj = new HashMap<>();
        obj.put("name", "Abed Chmaytilli");
        for (int i = 0; i < 100; i++) {
            users.add(obj);
        }*/

        List<HashMap<String, Object>> projects = new ArrayList<>();
   /*     HashMap<String, Object> obj2 = new HashMap<>();
        obj2.put("name", "Mzrtch Chahine");
        for (int i = 0; i < 100; i++) {
            projects.add(obj2);
        }*/

        searchUsersInput.initListView(usersListView, users);
        searchProjectsInput.initListView(projectsListView, projects);
        createNewTask.setOnClickListener(this);
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setTargetFragment(TaskNewFragment.this, TasksModel.REQUEST_DATE);
        dateBtn.setOnClickListener(onDateClick(datePickerFragment));

        return view;

    }

/*
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        taskNewActivity = (TaskNewActivity) activity;
    }
*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        taskNewActivity = (TaskNewActivity) context;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TasksModel.REQUEST_DATE) {
            taskListBean.dueDate = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            updateDateLabel(taskListBean.dueDate);
        }
    }

    private void updateDateLabel(Date dueDate) {
        if (dueDate != null) {
            dateBtn.setDate(dueDate);
        }
    }

    public void submitNewTask() {
        if (taskNameInput.getText().length() > 0) {
            createNewTask.setEnabled(false);
            taskListBean.title = taskNameInput.getText().toString();
            taskListBean.idSubmittedBy = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt("userId", 0);
            taskListBean.idOwner = searchUsersInput.getIdSelectedItem();
            taskListBean.idGroup = searchProjectsInput.getIdSelectedItem();
            taskListBean.description = descriptionEditText.getText().toString();

            ServiceModel.getInstance().taskService.createTask(taskListBean).enqueue(new Callback<UserTaskBean>() {
                @Override
                public void onResponse(Call<UserTaskBean> call, Response<UserTaskBean> response) {
                    if (response.isSuccessful()) {
                        UserTaskBean newTaskBean = response.body();
                        if (newTaskBean != null) {
                            taskNewActivity.submitNewTask(newTaskBean);
                        } else {
                            Toast.makeText(getContext(), getContext().getString(R.string.illegal_error_msg), Toast.LENGTH_LONG);
                        }

                    } else {
                        try {
                            String errorB = response.errorBody().string();
                            Gson gson = new Gson();
                            ResponseBean responseBean = gson.fromJson(errorB, ResponseBean.class);
                            Toast.makeText(getContext(), responseBean.description, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), getContext().getString(R.string.illegal_error_msg), Toast.LENGTH_LONG).show();
                        }
                    }

                }

                @Override
                public void onFailure(Call<UserTaskBean> call, Throwable t) {

                }
            });

        }

    }


    @Override
    public void onClick(View v) {
        submitNewTask();
    }


    public View.OnClickListener onDateClick(final DatePickerFragment fragment) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.show(getFragmentManager(), DatePickerFragment.ARG_DATE);
            }
        };
    }
}
