package com.codefish.android.taskmanager.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.codefish.android.taskmanager.MyApplication;
import com.codefish.android.taskmanager.R;
import com.codefish.android.taskmanager.activity.ITasksView;
import com.codefish.android.taskmanager.component.projectsNavList.ProjectsNavListView;
import com.codefish.android.taskmanager.component.SimpleDividerItemDecoration;
import com.codefish.android.taskmanager.component.tasksRecyclerView.TaskListAdapter;
import com.codefish.android.taskmanager.component.tasksRecyclerView.TaskListLayoutManager;
import com.codefish.android.taskmanager.model.LoginModel;
import com.codefish.android.taskmanager.model.UserTaskBean;
import com.codefish.android.taskmanager.presenter.ITaskPresenter;
import com.codefish.android.taskmanager.utils.SmartDateFormatter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TasksListFragment extends Fragment implements ITasksView, View.OnClickListener {

    public static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
    @Bind(R.id.tasks_list_layout_drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.tasks_list_layout_recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.tasks_list_layout_user_widget)
    TextView userWidget;
    @Bind(R.id.tasks_list_layout_open_profile)
    TextView openProfileView;
    @Bind(R.id.tasks_list_layout_project_title)
    TextView projectTitleView;
    @Bind(R.id.tasks_list_view_projects)
    AppCompatImageButton projectsBtn;
    @Bind(R.id.tasks_list_layout_mic)
    AppCompatImageButton micBtn;
    @Bind(R.id.tasks_list_layout_coordinator_layout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.tasks_list_layout_nav_drawer_projects)
    ProjectsNavListView projectsNavListView;
    @Inject
    ITaskPresenter taskPresenter;
    @Inject
    TaskListAdapter taskListAdapter;
    @Inject
    SmartDateFormatter smartDateFormatter;
    @Bind(R.id.tasks_list_layout_add_new_task)
    FloatingActionButton addNewTaskBtn;
    @Bind(R.id.tasks_list_layout_swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    public Integer idSelectedProject;

    private Callbacks activity;

    private TaskListLayoutManager mLayoutManager;
    boolean isMockData = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tasks_list_layout, container, false);
        ButterKnife.bind(this, view);
        MyApplication.getAppComponent().inject(this);
        taskPresenter.setLoginView(this);
        userWidget.setOnClickListener(onWidgetCick());
        openProfileView.setOnClickListener(onWidgetCick());
        micBtn.setOnClickListener(onMicClick());
        projectsBtn.setOnClickListener(onProjectsClick());
        addNewTaskBtn.setOnClickListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        userWidget.setText(LoginModel.getInstance().getUserBean().getInitials());
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshUserTasks();
            }


        });
        projectsNavListView.setOnItemClickListener(onProjNavClick());


        initListData();
        return view;
    }

    private AdapterView.OnItemClickListener onProjNavClick() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> project = (HashMap<String, Object>) parent.getAdapter().getItem(position);
                if (project != null && project.get("title") != null) {
                    idSelectedProject = (project != null && project.get("id") != null) ? ((Double) project.get("id")).intValue() : null;
                    projectTitleView.setText(project.get("title").toString());
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                    initListData();
                } else {
                    showErrorMsg("Can not init project, please contact your admin");
                }

            }

        };
    }

    private View.OnClickListener onProjectsClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.RIGHT);
            }
        };
    }

    private View.OnClickListener onWidgetCick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserProfileDialog userProfileDialog = UserProfileDialog.newInstance(TasksListFragment.this);
                userProfileDialog.show(getFragmentManager(), "User Profile Dialog");
            }
        };
    }

    public void refreshUserTasks() {
        taskPresenter.getUserTasks(idSelectedProject);
    }

    private View.OnClickListener onMicClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                        "Whats your next task?");
                getActivity().startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);

            }
        };
    }


    private void initListData() {
        showProgressBar();
        if (isMockData) {
            ArrayList<UserTaskBean> dataSet = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                dataSet.add(new UserTaskBean("My Title " + i, i % 2 == 0 ? new Date() : null));
            }

            taskListAdapter.setDataSet(dataSet);
            refreshList();
        } else {
            refreshUserTasks();
        }
    }


    @Override
    public void refreshList() {
        if (mLayoutManager == null) {
            initList();
        } else {
            taskListAdapter.notifyDataSetChanged();
        }
        hideProgressBar();


    }

    @Override
    public void initList() {
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new TaskListLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        taskListAdapter.setTaskPresenter(taskPresenter,smartDateFormatter);
        recyclerView.setAdapter(taskListAdapter);
    }

    @Override
    public void selectTask(UserTaskBean bean, int position) {
        activity.onItemSelected(bean);
    }

    @Override
    public void showErrorMsg(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }


    public void addItem(final UserTaskBean taskBean) {
        if (idSelectedProject == null || taskBean.idGroup.equals(idSelectedProject)) {
            taskListAdapter.addItem(taskBean);
            mLayoutManager.scrollToPosition(0);
        }

    }

    public void showTaskViewSnackBar(final UserTaskBean taskBean) {
        Snackbar.make(coordinatorLayout, "Task Created", Snackbar.LENGTH_LONG)
                .setActionTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary))
                .setAction("VIEW", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectTask(taskBean, 0);
                    }
                }).show();

    }


    public void updateItem(UserTaskBean taskBean) {
        taskListAdapter.updateSelectedItem(taskBean);
    }

    public void removeSelectedItem() {
        taskListAdapter.removeSelectedItem();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void showProgressBar() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });


    }


    @Override
    public void hideProgressBar() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }

    @Override
    public void onClick(View v) {
        activity.onNewItemSelect();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (Callbacks) activity;
    }


    public interface Callbacks {
        void onNewItemSelect();

        void onItemSelected(UserTaskBean bean);
    }

}
