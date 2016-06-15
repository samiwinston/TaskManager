package com.codefish.android.taskmanager.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.codefish.android.taskmanager.MyApplication;
import com.codefish.android.taskmanager.R;
import com.codefish.android.taskmanager.activity.TaskNewActivity;
import com.codefish.android.taskmanager.component.MyProjectsEditText;
import com.codefish.android.taskmanager.component.SearchUserEditText;
import com.codefish.android.taskmanager.model.LoginModel;
import com.codefish.android.taskmanager.model.ServiceModel;
import com.codefish.android.taskmanager.model.TaskListBean;
import com.codefish.android.taskmanager.model.UserTaskBean;
import com.codefish.android.taskmanager.utils.SmartDateFormatter;

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
public class ProjectsNavDrawerFragment extends Fragment {


    @Bind(R.id.project_navigation_layout_projects)
    ListView projectsListView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.project_navigation_drawer, container, false);
        ButterKnife.bind(this, view);
        //MyApplication.getAppComponent().inject(this);



        return view;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }




}
