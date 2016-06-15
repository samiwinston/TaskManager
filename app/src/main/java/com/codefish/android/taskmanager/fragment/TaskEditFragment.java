package com.codefish.android.taskmanager.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.codefish.android.taskmanager.MyApplication;
import com.codefish.android.taskmanager.R;
import com.codefish.android.taskmanager.activity.TaskDetailsActivity;
import com.codefish.android.taskmanager.component.smartDateView.SmartDateTextView;
import com.codefish.android.taskmanager.model.FollowerBean;
import com.codefish.android.taskmanager.model.UserTaskBean;
import com.codefish.android.taskmanager.presenter.ITaskEditPresenter;
import com.codefish.android.taskmanager.utils.SmartDateFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by abedch on 5/2/2016.
 */
public class TaskEditFragment extends Fragment implements ITaskEditView {

    @Bind(R.id.my_toolbar)
    Toolbar toolbar;
    @Bind(R.id.task_edit_layout_due_date)
    SmartDateTextView dueDateTextView;
    @Bind(R.id.task_edit_layout_remove_assignee)
    AppCompatImageButton removeAssigneeBtn;
    @Bind(R.id.task_edit_layout_add_assignee)
    TextView addAssigneeView;
    @Bind(R.id.task_edit_layout_assignee)
    TextView assigneeView;
    @Bind(R.id.task_edit_layout_task_title)
    TextView taskTitleView;
    @Bind(R.id.task_edit_layout_delete)
    TextView deleteTextView;
    @Bind(R.id.task_edit_layout_description)
    TextView taskDescView;
    @Bind(R.id.task_edit_layout_followers)
    TextView followersTextView;
    @Bind(R.id.task_edit_layout_assignee_initials)
    TextView assigneeInitials;
    @Bind(R.id.task_edit_layout_assignee_switcher)
    ViewSwitcher viewSwitcher;
    @Inject
    ITaskEditPresenter taskEditPresenter;


    private TaskDetailsActivity taskDetailsActivity;
    private UserTaskBean userTaskBean;

    TaskDetailsFragment targetFragment;

    public static TaskEditFragment newInstance(TaskDetailsFragment targetFragment) {
        TaskEditFragment fragment = new TaskEditFragment();
        fragment.setTargetFragment(targetFragment, TaskDetailsFragment.REQUEST_TASK_BEAN);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        targetFragment = (TaskDetailsFragment) getTargetFragment();
        MyApplication.getAppComponent().inject(this);
        taskEditPresenter.setTaskEditView(this);
        taskEditPresenter.getTaskPossibleAssignees(taskDetailsActivity.selectedTask.idWorkflowInstance);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_edit_layout, container, false);
        ButterKnife.bind(this, view);

        dueDateTextView.setOnClickListener(onDateClick());
        removeAssigneeBtn.setOnClickListener(onRemoveAssigneeClick());
        addAssigneeView.setOnClickListener(onAddAssigneeClick());
        followersTextView.setOnClickListener(onAddFollowerClick());
        taskTitleView.setOnClickListener(onTitleClick());
        taskDescView.setOnClickListener(onDescClick());
        deleteTextView.setOnClickListener(onDeleteClick());

        if (taskDetailsActivity.selectedTask != null) {
            userTaskBean = taskDetailsActivity.selectedTask;

            if (userTaskBean.ownerName != null) {
                assigneeView.setText(userTaskBean.ownerName);
                assigneeInitials.setText(userTaskBean.getOwnerInitals());
                viewSwitcher.showNext();
            }

            if (userTaskBean.followers != null && userTaskBean.followers.size() > 0) {
                int numOfFollowers = userTaskBean.followers.size();
                followersTextView.setText(numOfFollowers + " " + (numOfFollowers > 1 ? "Followers" : "Follower"));
            }

            taskTitleView.setText(userTaskBean.title);
            taskDescView.setText(userTaskBean.description);
        }


        initToolBar();
        initForm();
        return view;

    }

    private View.OnClickListener onDeleteClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialogFragment dialogFragment = MyDialogFragment.newInstance();
                dialogFragment.setTargetFragment(TaskEditFragment.this, getResources().getInteger(R.integer.REQUEST_REMOVE_TASK));
                if (!dialogFragment.isAdded()) {
                    dialogFragment.show(getFragmentManager(), "dialog date");
                }
            }
        };
    }

    private View.OnClickListener onDescClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskEditStrFieldFragment taskEditTitleFragment = TaskEditStrFieldFragment.newInstance(TaskEditFragment.this,
                        getResources().getInteger(R.integer.REQUEST_CHANGE_DESC), "Edit Description", "description", userTaskBean.description);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, taskEditTitleFragment)
                        .addToBackStack("Back To Parent").commit();
            }
        };
    }

    private View.OnClickListener onTitleClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskEditStrFieldFragment taskEditTitleFragment = TaskEditStrFieldFragment.newInstance(TaskEditFragment.this,
                        getResources().getInteger(R.integer.REQUEST_CHANGE_TITLE), "Edit Title", "title", userTaskBean.title);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, taskEditTitleFragment)
                        .addToBackStack("Back To Parent").commit();
            }
        };
    }

    private View.OnClickListener onAddFollowerClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskFollowersFragment taskFollowersFragment = TaskFollowersFragment.newInstance(TaskEditFragment.this, userTaskBean.idWorkflowInstance, userTaskBean.followers);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, taskFollowersFragment)
                        .addToBackStack("Back To Parent").commit();
            }
        };
    }


    private void initForm() {
        if (userTaskBean.dueDate != null) {
            dueDateTextView.setDate(userTaskBean.dueDate);
        }

    }


    @Override
    public void updatePossibleAssignees(List<HashMap<String, Object>> result) {
        taskDetailsActivity.selectedTask.assignees = result;
    }

    @Override
    public void showErrorMsg(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }


    @Override
    public void unassignTaskCallBack() {
        hideAssigneeView();
        taskDetailsActivity.selectedTask.ownerName = null;
        taskDetailsActivity.selectedTask.idOwner = 0;
    }

    @Override
    public void deleteTaskCallBack() {
        Intent intent = new Intent();
        intent.putExtra("deleteThisTask", true);
        getActivity().setResult(getResources().getInteger(R.integer.REQUEST_REMOVE_TASK), intent);
        getActivity().finish();
    }

    public View.OnClickListener onRemoveAssigneeClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskEditPresenter.unassignTask(userTaskBean.idTask);
            }
        };
    }

    private void hideAssigneeView() {
        viewSwitcher.showPrevious();
    }


    public View.OnClickListener onDateClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(taskDetailsActivity.selectedTask.dueDate);
                datePickerFragment.setTargetFragment(TaskEditFragment.this, getResources().getInteger(R.integer.REQUEST_DATE));

                if (datePickerFragment.isAdded()) {
                    return;
                }
                datePickerFragment.show(getFragmentManager(), "dialog date");
            }
        };
    }

    public View.OnClickListener onAddAssigneeClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskAddAssigneeFragment taskEditAssigneeFragment = TaskAddAssigneeFragment.newInstance(TaskEditFragment.this, taskDetailsActivity.selectedTask.assignees);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, taskEditAssigneeFragment)
                        .addToBackStack("Back To Parent").commit();
            }
        };
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
                    getFragmentManager().popBackStack();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        taskDetailsActivity = (TaskDetailsActivity) activity;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == getResources().getInteger(R.integer.REQUEST_DATE)) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            if (date != null) {
                userTaskBean.dueDate = taskDetailsActivity.selectedTask.dueDate = date;
                dueDateTextView.setDate(date);
                taskEditPresenter.updateDueDate(taskDetailsActivity.selectedTask.idTask, date);
            }
        } else if (requestCode == TaskAddAssigneeFragment.REQUEST_ASSIGNEE) {
            HashMap<String, Object> item = (HashMap<String, Object>) data.getSerializableExtra(TaskAddAssigneeFragment.ARGS_ITEM);
            Double idReassignTo = (Double) item.get("id");
            String assigneeName = item.get("name").toString();
            taskDetailsActivity.selectedTask.ownerName = assigneeName;
            taskEditPresenter.reassignTask(taskDetailsActivity.selectedTask.idTask, idReassignTo.intValue());
        } else if (requestCode == TaskFollowersFragment.UPDATE_FOLLOWERS) {
            List<FollowerBean> followers = (List<FollowerBean>) data.getSerializableExtra(TaskAddFollowersFragment.ARGS_VALUES);
            taskDetailsActivity.selectedTask.followers = followers;
        } else if (requestCode == getResources().getInteger(R.integer.REQUEST_CHANGE_TITLE)) {
            taskDetailsActivity.selectedTask.title = data.getStringExtra(TaskEditStrFieldFragment.ARGS_ITEM);
        } else if (requestCode == getResources().getInteger(R.integer.REQUEST_CHANGE_DESC)) {
            taskDetailsActivity.selectedTask.description = data.getStringExtra(TaskEditStrFieldFragment.ARGS_ITEM);
        } else if (requestCode == getResources().getInteger(R.integer.REQUEST_REMOVE_TASK)) {
            taskEditPresenter.deleteTask(userTaskBean.idWorkflowInstance);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void updateDueDateCallBack(Date date) {

    }
}
