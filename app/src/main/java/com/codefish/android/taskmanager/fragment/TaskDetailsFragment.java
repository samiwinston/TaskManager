package com.codefish.android.taskmanager.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.codefish.android.taskmanager.MyApplication;
import com.codefish.android.taskmanager.R;
import com.codefish.android.taskmanager.activity.TaskDetailsActivity;
import com.codefish.android.taskmanager.component.smartDateView.SmartDateButton;
import com.codefish.android.taskmanager.model.UserTaskBean;
import com.codefish.android.taskmanager.presenter.ITaskDetailsPresenter;
import com.codefish.android.taskmanager.utils.SmartDateFormatter;

import java.util.Date;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by abedch on 5/4/2016.
 */
public class TaskDetailsFragment extends Fragment implements ITaskDetailsView {

    public static final int REQUEST_TASK_BEAN = 1010;

    @Bind(R.id.my_toolbar)
    Toolbar toolbar;
    @Bind(R.id.task_details_due_date)
    SmartDateButton dueDateBtn;
    @Bind(R.id.task_details_layout_task_title)
    TextView taskTitleView;
    @Bind(R.id.task_details_layout_assignee_initials)
    TextView initialsView;
    @Bind(R.id.task_details_layout_assignee)
    TextView assigneeView;
    @Bind(R.id.task_details_description)
    TextView taskDescriptionView;
    TaskDetailsActivity taskDetailsActivity;
    @Inject
    ITaskDetailsPresenter taskDetailsPresenter;
    @Inject
    SmartDateFormatter smartDateFormatter;

    TaskEditFragment taskEditFragment;

    public static TaskDetailsFragment newInstance() {
        TaskDetailsFragment taskDetailsFragment = new TaskDetailsFragment();
        return taskDetailsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getAppComponent().inject(this);
        setHasOptionsMenu(true);
        taskDetailsPresenter.setTaskDetailsView(this);
        taskEditFragment = TaskEditFragment.newInstance(TaskDetailsFragment.this);
        if (taskDetailsActivity.selectedTask != null)
            taskDetailsPresenter.getTask(taskDetailsActivity.selectedTask.idWorkflowInstance);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_details_layout, container, false);
        ButterKnife.bind(this, view);

        if (taskDetailsActivity.selectedTask != null) {
            UserTaskBean bean = taskDetailsActivity.selectedTask;
            taskTitleView.setText(bean.title);
            taskDescriptionView.setText(bean.description);
            if (bean.dueDate != null)
                dueDateBtn.setDate(bean.dueDate);
            if (bean.ownerName != null) {
                initialsView.setText(bean.getOwnerInitals());
                assigneeView.setText(bean.ownerName);
            }

        }
        dueDateBtn.setOnClickListener(onDateClick());

        initToolBar();
        initBean();

        return view;
    }

    private View.OnClickListener onDateClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(taskDetailsActivity.selectedTask.dueDate);
                datePickerFragment.setTargetFragment(TaskDetailsFragment.this, getResources().getInteger(R.integer.REQUEST_DATE));
                if (!datePickerFragment.isAdded()) {
                    datePickerFragment.show(getFragmentManager(), "dialog date");
                }
            }
        };
    }

    private void initBean() {


    }


    private void initToolBar() {
        toolbar.setOverflowIcon(ContextCompat.getDrawable(getContext(), R.drawable.icon_task_details_topbar_overflow));
        taskDetailsActivity.setSupportActionBar(toolbar);
        ActionBar supportActionBar = taskDetailsActivity.getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setDisplayShowHomeEnabled(true);
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayShowTitleEnabled(false);
        supportActionBar.setHomeAsUpIndicator(R.drawable.icon_close_dark);

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.task_details_menu, menu);

        if (taskDetailsActivity.selectedTask.importance == 1) {
            MenuItem menuItem = menu.findItem(R.id.menu_item_important);
            menuItem.setIcon(R.drawable.icon_task_details_topbar_heart_filled);
        }
     /*   if (!taskDetailsActivity.selectedTask.isOpen) {
            MenuItem menuItem = menu.findItem(R.id.menu_item_complete);
            menuItem.setIcon(R.drawable.icon_task_details_topbar_task_complete);
        }*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                goBackToList();
                return true;
            case R.id.menu_item_edit:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, taskEditFragment)
                        .addToBackStack("Back To Parent").commit();
                return true;
            case R.id.menu_item_complete:
                if (taskDetailsActivity.selectedTask.isOpen) {
                    taskDetailsActivity.selectedTask.isOpen = false;
                    item.setIcon(R.drawable.icon_task_details_topbar_task_complete);
                } else {
                    taskDetailsActivity.selectedTask.isOpen = true;
                    item.setIcon(R.drawable.icon_task_details_topbar_task);
                }
                //taskDetailsPresenter.changeState(taskDetailsActivity.selectedTask);
                return true;
            case R.id.menu_item_important:
                if (taskDetailsActivity.selectedTask.importance == 0) {
                    taskDetailsActivity.selectedTask.importance = 1;
                    item.setIcon(R.drawable.icon_task_details_topbar_heart_filled);
                } else {
                    taskDetailsActivity.selectedTask.importance = 0;
                    item.setIcon(R.drawable.icon_task_details_topbar_heart);
                }
                taskDetailsPresenter.updateImportance(taskDetailsActivity.selectedTask.idTask, taskDetailsActivity.selectedTask.importance);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void goBackToList() {
        Intent intent = new Intent();
        intent.putExtras(taskDetailsActivity.selectedTask.getBundle());
        intent.putExtra("hasNoFollowers", taskDetailsActivity.selectedTask.hasNoFollowers());
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        taskDetailsActivity = (TaskDetailsActivity) activity;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == getResources().getInteger(R.integer.REQUEST_DATE)) {
            final Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            taskDetailsActivity.selectedTask.dueDate = date;
            dueDateBtn.setDate(date);

            taskDetailsPresenter.updateDueDate(taskDetailsActivity.selectedTask.idTask, date);
        }
    }


    @Override
    public void updateDueCallBack(Date date) {
        /*taskDetailsActivity.selectedTask.dueDate = date;
        dueDateBtn.setDate(date);*/
    }

    @Override
    public void showErrorMsg(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadUserTaskBean(UserTaskBean userTaskBean) {
        this.taskDetailsActivity.selectedTask = userTaskBean;
    }

    @Override
    public void changeStateCBH() {

    }

    @Override
    public void updateImportanceCBH() {

    }
}
