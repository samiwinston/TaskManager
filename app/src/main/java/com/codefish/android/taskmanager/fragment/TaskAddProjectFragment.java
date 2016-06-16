package com.codefish.android.taskmanager.fragment;

import android.app.Activity;
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
import com.codefish.android.taskmanager.component.AssigneesSearchText;
import com.codefish.android.taskmanager.component.IGenericCallBack;
import com.codefish.android.taskmanager.component.SimpleAddItemSearchText;
import com.codefish.android.taskmanager.component.userListView.ProjectsSearchText;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by abedch on 5/2/2016.
 */
public class TaskAddProjectFragment extends Fragment implements IGenericCallBack {

    @Bind(R.id.my_toolbar)
    Toolbar toolbar;
    private TaskDetailsActivity taskDetailsActivity;
    @Bind(R.id.task_add_project_list)
    ListView mListView;
    @Bind(R.id.task_add_project_search_text)
    ProjectsSearchText searchText;

    public final static int REQUEST_PROJECT = 1006;
    public final static String ARGS_VALUES = "argsvalues";
    public final static String ARGS_ITEM = "argsitem";

    public static TaskAddProjectFragment newInstance(Fragment targetFragment, List<HashMap<String, Object>> values)  {

        TaskAddProjectFragment fragment = new TaskAddProjectFragment();
        fragment.setTargetFragment(targetFragment,REQUEST_PROJECT);
        Bundle args = new Bundle();
        args.putSerializable(ARGS_VALUES, (Serializable) values);
        fragment.setArguments(args);

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
        View view = inflater.inflate(R.layout.task_add_project_layout, container, false);
        ButterKnife.bind(this, view);
        initToolBar();

        List<HashMap<String, Object>> values =(List<HashMap<String, Object>>) getArguments().getSerializable(ARGS_VALUES);
        searchText.initListView(mListView,values);
        searchText.genericCallBack = this;

        return view;

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        taskDetailsActivity = (TaskDetailsActivity) activity;
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
            Intent intent = new Intent();
            intent.putExtra(ARGS_ITEM, item);
            getTargetFragment().onActivityResult(REQUEST_PROJECT,Activity.RESULT_OK,intent);
            getFragmentManager().popBackStack();
        }
    }
}
