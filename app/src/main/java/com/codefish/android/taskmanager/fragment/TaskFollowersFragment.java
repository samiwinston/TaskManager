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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codefish.android.taskmanager.R;
import com.codefish.android.taskmanager.activity.TaskDetailsActivity;
import com.codefish.android.taskmanager.component.IGenericCallBack;
import com.codefish.android.taskmanager.component.userListView.FollowersListView;
import com.codefish.android.taskmanager.component.userListView.IFollowerCallBack;
import com.codefish.android.taskmanager.model.FollowerBean;
import com.codefish.android.taskmanager.model.LoginModel;
import com.codefish.android.taskmanager.model.ServiceModel;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by abedch on 5/2/2016.
 */
public class TaskFollowersFragment extends Fragment {

    public static final int UPDATE_FOLLOWERS = 1007;
    @Bind(R.id.my_toolbar)
    Toolbar toolbar;
    @Bind(R.id.task_followers_layout_followers)
    FollowersListView followersListView;
    @Bind(R.id.task_followers_layout_add_follower_group)
    RelativeLayout addFollowerGroup;
    private TaskDetailsActivity taskDetailsActivity;
    public final static int REQUEST_FOLLOWER = 1006;
    public final static String ARGS_VALUES = "argsvalues";
    public final static String ARGS_ITEM = "argsitem";
    TaskAddFollowersFragment taskAddFollowersFragment;

    public static TaskFollowersFragment newInstance(Fragment targetFragment, Integer idUserTask, List<FollowerBean> values) {

        TaskFollowersFragment fragment = new TaskFollowersFragment();
        fragment.setTargetFragment(targetFragment, REQUEST_FOLLOWER);
        Bundle args = new Bundle();
        args.putSerializable(ARGS_VALUES, (Serializable) values);
        args.putInt("idUserTask", idUserTask);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        taskAddFollowersFragment = TaskAddFollowersFragment.newInstance(TaskFollowersFragment.this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_followers_layout, container, false);
        ButterKnife.bind(this, view);
        initToolBar();

        List<FollowerBean> values = (List<FollowerBean>) getArguments().getSerializable(ARGS_VALUES);
        Integer idUserTask = getArguments().getInt("idUserTask");
        followersListView.init(idUserTask, values);
        addFollowerGroup.setOnClickListener(onAddFollowerClick());
        return view;
    }

    private View.OnClickListener onAddFollowerClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, taskAddFollowersFragment)
                        .addToBackStack("Back To Parent").commit();
            }
        };
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        taskDetailsActivity = (TaskDetailsActivity) activity;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_FOLLOWER) {
            FollowerBean bean = new FollowerBean(data.getExtras());
            followersListView.addFollower(bean);

            ServiceModel.getInstance()
                    .taskService.addFollower("WorkflowInstance", taskDetailsActivity.selectedTask.idTask
                    , LoginModel.getInstance().getUserBean().getId(), bean.getIdAppUser(), false, true).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {

                    } else {
                        Toast.makeText(getContext(), "Can no reach CodeFish", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(getContext(), "Can no reach CodeFish", Toast.LENGTH_LONG).show();

                }
            });


        }
    }


    private void initToolBar() {
        taskDetailsActivity.setSupportActionBar(toolbar);

        ActionBar supportActionBar = taskDetailsActivity.getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setDisplayShowHomeEnabled(true);
        supportActionBar.setDisplayShowTitleEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    Intent intent = new Intent();
                    Bundle args = new Bundle();
                    args.putSerializable(ARGS_VALUES, (Serializable) followersListView.getAdapterList());
                    intent.putExtras(args);
                    getTargetFragment().onActivityResult(UPDATE_FOLLOWERS, Activity.RESULT_OK, intent);
                    getFragmentManager().popBackStack();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
