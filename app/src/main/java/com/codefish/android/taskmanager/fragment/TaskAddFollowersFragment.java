package com.codefish.android.taskmanager.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codefish.android.taskmanager.R;
import com.codefish.android.taskmanager.activity.TaskDetailsActivity;
import com.codefish.android.taskmanager.component.IGenericCallBack;
import com.codefish.android.taskmanager.component.SearchUserEditText;
import com.codefish.android.taskmanager.model.FollowerBean;
import com.codefish.android.taskmanager.model.TasksModel;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by abedch on 5/2/2016.
 */
public class TaskAddFollowersFragment extends Fragment implements IGenericCallBack {

    @Bind(R.id.my_toolbar)
    Toolbar toolbar;
    private TaskDetailsActivity taskDetailsActivity;
    @Bind(R.id.task_add_followers_list)
    ListView usersListView;
    @Bind(R.id.task_add_followers_search_text)
    SearchUserEditText searchUsersInput;

    public final static String ARGS_VALUES = "argsvalues";

    public static TaskAddFollowersFragment newInstance(Fragment targetFragment)  {

        TaskAddFollowersFragment fragment = new TaskAddFollowersFragment();
        fragment.setTargetFragment(targetFragment, TasksModel.REQUEST_FOLLOWER);


        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_add_followers_layout, container, false);
        ButterKnife.bind(this, view);
        initToolBar();

        searchUsersInput.initListView(usersListView, null);
        searchUsersInput.genericCallBack = this;

        return view;

    }


 /*   @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        taskDetailsActivity = (TaskDetailsActivity) activity;
    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        taskDetailsActivity = (TaskDetailsActivity) context;
    }

    private void initToolBar() {
        taskDetailsActivity.setSupportActionBar(toolbar);

        ActionBar supportActionBar = taskDetailsActivity.getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setDisplayShowHomeEnabled(true);
        supportActionBar.setDisplayShowTitleEnabled(false);
        supportActionBar.setDisplayShowTitleEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    getFragmentManager().popBackStack();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void itemClicked(HashMap<String, Object> item) {

        if (getFragmentManager().getBackStackEntryCount() > 0) {
            // call add follower
            FollowerBean followerBean = new FollowerBean();
            Intent intent = new Intent();
            intent.putExtras(followerBean.getBundle(item));
            getTargetFragment().onActivityResult(TasksModel.REQUEST_FOLLOWER,Activity.RESULT_OK,intent);
            //getFragmentManager().beginTransaction().show(getTargetFragment()).commit();
            getFragmentManager().popBackStack();
        }
    }
}
