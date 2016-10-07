package com.codefish.android.taskmanager.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.codefish.android.taskmanager.MyApplication;
import com.codefish.android.taskmanager.R;
import com.codefish.android.taskmanager.activity.TaskDetailsActivity;
import com.codefish.android.taskmanager.component.smartDateView.SmartDateButton;
import com.codefish.android.taskmanager.model.MobWorkflowForm;
import com.codefish.android.taskmanager.model.TasksModel;
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

    @Bind(R.id.task_details_layout_toolbar)
    Toolbar toolbar;
    @Bind(R.id.task_details_due_date)
    SmartDateButton dueDateBtn;
    @Bind(R.id.task_details_layout_task_title)
    TextView taskTitleView;
    @Bind(R.id.task_details_layout_assignee_initials)
    TextView initialsView;
    @Bind(R.id.task_details_layout_assignee)
    TextView assigneeView;
    @Bind(R.id.task_details_layout_action_button)
    Button actionButton;
    @Bind(R.id.task_details_description)
    TextView taskDescriptionView;
    TaskDetailsActivity taskDetailsActivity;
    @Inject
    ITaskDetailsPresenter taskDetailsPresenter;
    @Inject
    SmartDateFormatter smartDateFormatter;

    TaskEditFragment taskEditFragment;
    private MobWorkflowForm loadedWorkflowForm = null;
    private boolean workflowButtonIsLoaded = false;
    Menu menu;

  /*  public static TaskDetailsFragment newInstance() {
        TaskDetailsFragment taskDetailsFragment = new TaskDetailsFragment();
        return taskDetailsFragment;
    }*/


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            loadedWorkflowForm = savedInstanceState.getParcelable("loadedWorkflowForm");
            workflowButtonIsLoaded = savedInstanceState.getBoolean("workflowButtonIsLoaded");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (loadedWorkflowForm != null) {
            outState.putParcelable("loadedWorkflowForm", loadedWorkflowForm);
            outState.putBoolean("workflowButtonIsLoaded", workflowButtonIsLoaded);
        }


    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getAppComponent().inject(this);
        setHasOptionsMenu(true);
        taskDetailsPresenter.setTaskDetailsView(this);
        if (taskDetailsActivity.selectedTask != null)
            taskDetailsPresenter.getTask(PreferenceManager.getDefaultSharedPreferences(getContext()).getInt("userId", 0),
                    taskDetailsActivity.selectedTask.idWorkflowInstance);
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
            if (workflowButtonIsLoaded) {
                loadUserTaskBean(bean);
            }
        }
        actionButton.setOnClickListener(onTakeActionClick());
        dueDateBtn.setOnClickListener(onDateClick());

        initToolBar();
        initBean();


        return view;
    }

    private View.OnClickListener onTakeActionClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadedWorkflowForm != null) {
                    loadWorkflowForm(loadedWorkflowForm);
                } else {
                    taskDetailsPresenter.getWorkflowForm(PreferenceManager.getDefaultSharedPreferences(getContext()).getInt("userId", 0),
                            taskDetailsActivity.selectedTask.idWorkflowInstance);
                }
            }
        };
    }

    private View.OnClickListener onDateClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(taskDetailsActivity.selectedTask.dueDate);
                datePickerFragment.setTargetFragment(TaskDetailsFragment.this, TasksModel.REQUEST_DATE);
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
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (menu != null)
            this.menu = menu;


        if (taskDetailsActivity.selectedTask.importance == 1) {
            MenuItem menuItem = menu.findItem(R.id.menu_item_important);
            menuItem.setIcon(R.drawable.icon_task_details_topbar_heart_filled);
        }


        if (taskDetailsActivity.selectedTask.hasForm) {
            MenuItem menuItemComplete = menu.findItem(R.id.menu_item_complete);
            menuItemComplete.setVisible(false);
            MenuItem menuDeleteItem = menu.findItem(R.id.menu_item_delete);
            menuDeleteItem.setVisible(false);
            MenuItem menuItemEdit = menu.findItem(R.id.menu_item_edit);
            menuItemEdit.setVisible(false);
            MenuItem menuItemImportant = menu.findItem(R.id.menu_item_important);
            menuItemImportant.setVisible(false);

        } else if (!taskDetailsActivity.selectedTask.isOpen) {
            MenuItem menuItem = menu.findItem(R.id.menu_item_complete);
            menuItem.setIcon(R.drawable.icon_task_details_topbar_task_complete);
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.task_details_menu, menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                goBackToList();
                return true;
            case R.id.menu_item_edit:
                taskEditFragment = TaskEditFragment.newInstance(TaskDetailsFragment.this);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, taskEditFragment)
                        .addToBackStack("Back To Parent").commit();
                return true;
            case R.id.menu_item_complete:
                item.setEnabled(false);
                if (taskDetailsActivity.selectedTask.isOpen) {
                    taskDetailsActivity.selectedTask.isOpen = false;
                    item.setIcon(R.drawable.icon_task_details_topbar_task_complete);
                } else {
                    taskDetailsActivity.selectedTask.isOpen = true;
                    item.setIcon(R.drawable.icon_task_details_topbar_task);
                }
                taskDetailsPresenter.changeState(PreferenceManager.getDefaultSharedPreferences(getContext()).getInt("userId", 0),
                        taskDetailsActivity.selectedTask);
                return true;
            case R.id.menu_item_important:
                if (taskDetailsActivity.selectedTask.importance == 0) {
                    taskDetailsActivity.selectedTask.importance = 1;
                    item.setIcon(R.drawable.icon_task_details_topbar_heart_filled);
                } else {
                    taskDetailsActivity.selectedTask.importance = 0;
                    item.setIcon(R.drawable.icon_task_details_topbar_heart);
                }
                taskDetailsPresenter.updateImportance(PreferenceManager.getDefaultSharedPreferences(getContext()).getInt("userId", 0), taskDetailsActivity.selectedTask.idTask, taskDetailsActivity.selectedTask.importance);
                return true;
            case R.id.menu_item_delete:
                onDeleteClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void onDeleteClick() {

        MyDialogFragment dialogFragment = MyDialogFragment.newInstance();
        dialogFragment.setTargetFragment(TaskDetailsFragment.this, TasksModel.REQUEST_REMOVE_TASK);
        if (!dialogFragment.isAdded()) {
            dialogFragment.show(getFragmentManager(), "dialog date");
        }

    }

    public void goBackToList() {
        Intent intent = new Intent();
        intent.putExtras(taskDetailsActivity.selectedTask.getBundle());
        intent.putExtra("hasNoFollowers", taskDetailsActivity.selectedTask.hasNoFollowers());
        if ((!taskDetailsActivity.selectedTask.isOpen && (taskDetailsActivity.selectedTask.requiresReview == null || !taskDetailsActivity.selectedTask.requiresReview)) ||
                (taskDetailsActivity.idSelectedProject != 0 &&
                        (!taskDetailsActivity.selectedTask.idGroup.equals(taskDetailsActivity.idSelectedProject)))) {

            intent.putExtra("deleteThisTask", true);
        }
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }

/*    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        taskDetailsActivity = (TaskDetailsActivity) activity;
    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        taskDetailsActivity = (TaskDetailsActivity) context;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case TasksModel.REQUEST_DATE:
                final Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
                taskDetailsActivity.selectedTask.dueDate = date;
                dueDateBtn.setDate(date);
                taskDetailsPresenter.updateDueDate(PreferenceManager.getDefaultSharedPreferences(getContext()).getInt("userId", 0),
                        taskDetailsActivity.selectedTask.idTask, date);
                break;
            case TasksModel.REQUEST_TASK_UPDATE:
                taskDetailsActivity.selectedTask = new UserTaskBean(data.getExtras());
                goBackToList();
                break;
            case TasksModel.REQUEST_REMOVE_TASK:
                taskDetailsPresenter.deleteTask(PreferenceManager.getDefaultSharedPreferences(getContext()).getInt("userId", 0), taskDetailsActivity.selectedTask.idWorkflowInstance);
                break;

            default:
        }


    }

    @Override
    public void deleteTaskCallBack() {
        Intent intent = new Intent();
        intent.putExtra("deleteThisTask", true);
        getActivity().setResult(TasksModel.REQUEST_REMOVE_TASK, intent);
        getActivity().finish();
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

        if (taskDetailsActivity.selectedTask.hasForm) {
            actionButton.setText(taskDetailsActivity.selectedTask.canTakeAction() ? "Take Action" : "View Details");
            actionButton.setVisibility(View.VISIBLE);
            workflowButtonIsLoaded = true;
        }
    }

    @Override
    public void changeStateCBH(String updatedState) {
        if (menu != null) {
            MenuItem menuItem = menu.findItem(R.id.menu_item_complete);
            menuItem.setEnabled(true);
            taskDetailsActivity.selectedTask.currentState = updatedState;
        }
    }

    @Override
    public void updateImportanceCBH() {

    }

    @Override
    public void loadWorkflowForm(MobWorkflowForm mobWorkflowForm) {
        WorkflowFormFragment fragment = WorkflowFormFragment.newInstance(TaskDetailsFragment.this, TasksModel.REQUEST_TASK_UPDATE, mobWorkflowForm, taskDetailsActivity.selectedTask.idWorkflowInstance);
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment)
                .addToBackStack("Back To Parent").commit();
        loadedWorkflowForm = mobWorkflowForm;
    }
}
