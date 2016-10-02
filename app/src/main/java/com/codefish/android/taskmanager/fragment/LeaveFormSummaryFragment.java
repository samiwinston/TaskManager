package com.codefish.android.taskmanager.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.codefish.android.taskmanager.MyApplication;
import com.codefish.android.taskmanager.R;
import com.codefish.android.taskmanager.activity.ILeaveFormSummaryView;
import com.codefish.android.taskmanager.activity.ILeaveWorkflowFormView;
import com.codefish.android.taskmanager.activity.LeaveWorkflowFormActivity;
import com.codefish.android.taskmanager.component.WorkflowActionButton;
import com.codefish.android.taskmanager.component.userListView.LeavesListAdapter;
import com.codefish.android.taskmanager.model.ServiceModel;
import com.codefish.android.taskmanager.model.TasksModel;
import com.codefish.android.taskmanager.model.UserTaskBean;
import com.codefish.android.taskmanager.model.WorkflowActionBean;
import com.codefish.android.taskmanager.model.hr.MobLeaveRequestFormBean;
import com.codefish.android.taskmanager.model.hr.MobSimpleLeaveCalendarEntity;
import com.codefish.android.taskmanager.presenter.IWorkflowFormPresenter;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by abedch on 5/2/2016.
 */
public class LeaveFormSummaryFragment extends Fragment implements ILeaveFormSummaryView {


    @Bind(R.id.leave_request_summary_form_toolbar)
    Toolbar toolbar;
    @Inject
    IWorkflowFormPresenter workflowFormPresenter;

    private LeaveWorkflowFormActivity leaveWorkflowFormActivity;

    @Bind(R.id.leave_request_summary_form_submit)
    TextView submitBtn;
    @Bind(R.id.leave_request_summary_warning_msg)
    TextView warningView;
    @Bind(R.id.leave_request_form_warning_grp)
    LinearLayout warningGrp;
    @Bind(R.id.leave_request_summary_form_phone_on_leave)
    EditText phoneOnLeave;
    @Bind(R.id.leave_request_summary_form_days_phone_grp)
    LinearLayout phoneOnLeaveGrp;
    @Bind(R.id.leave_request_summary_form_days_address_grp)
    LinearLayout addressOnLeaveGrp;
    @Bind(R.id.leave_request_summary_form_days_allowed)
    TextView daysAllowed;
    @Bind(R.id.leave_request_summary_form_days_remaining)
    TextView daysRemaining;
    @Bind(R.id.leave_request_summary_form_days_requested)
    TextView daysRequested;
    @Bind(R.id.leave_request_summary_form_leave_type)
    TextView leaveType;
    @Bind(R.id.leave_request_summary_form_days_allowed_grp)
    LinearLayout daysAllowedGrp;
    @Bind(R.id.leave_request_summary_form_days_remaining_grp)
    LinearLayout daysRemainingGrp;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }


    
    public static LeaveFormSummaryFragment newInstance(LeaveWorkflowFormFragment targetFragment) {
        LeaveFormSummaryFragment fragment = new LeaveFormSummaryFragment();
        fragment.setTargetFragment(targetFragment, TasksModel.REQUEST_CLOSED_SUMMARY);
        return fragment;
    }
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.leave_request_summary_form_layout, container, false);
        ButterKnife.bind(this, view);
        MyApplication.getAppComponent().inject(this);
        workflowFormPresenter.setLeaveFormSummaryView(this);

        //workflowFormPresenter.submitLeave(leaveWorkflowFormActivity.mobLeaveRequestFormBean);

        //submitBtn.setOnClickListener(onSubmitClick());

        initToolBar();

        MobLeaveRequestFormBean bean = leaveWorkflowFormActivity.mobLeaveRequestFormBean;
        bean.updateActionName();
        leaveType.setText(bean.leaveTypeName+" leave was requested");
        daysRequested.setText(bean.totalDays +(bean.isOneDay?" day":" days")+ " requested");

        if(bean.includeInLeave)
        {
            daysAllowed.setText(bean.leaveInfoBean.getDaysAllowed()+" allowed as of today");
            daysAllowedGrp.setVisibility(View.VISIBLE);
            daysRemaining.setText((bean.leaveInfoBean.getDoubleTotalDaysLeft() - bean.totalDays)+" remaining for this year");
            daysRemainingGrp.setVisibility(View.VISIBLE);
        }

        if (bean.warningMessage!=null) {
            warningView.setText(bean.warningMessage);
            warningGrp.setVisibility(View.VISIBLE);
        }
        else
        {
            warningGrp.setVisibility(View.GONE);
        }

        if(bean.showAddressBox)
        {
            phoneOnLeaveGrp.setVisibility(View.VISIBLE);
            addressOnLeaveGrp.setVisibility(View.VISIBLE);
            phoneOnLeave.setTextLocale(Locale.US);
            phoneOnLeave.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        }




        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private View.OnClickListener onSubmitClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
    }

    private void initToolBar() {
        toolbar.setTitle("Leave Request Summary");
        leaveWorkflowFormActivity.setSupportActionBar(toolbar);

        ActionBar supportActionBar = leaveWorkflowFormActivity.getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setDisplayShowHomeEnabled(true);
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayShowTitleEnabled(false);
        supportActionBar.setHomeAsUpIndicator(R.drawable.icon_close_dark);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    getFragmentManager().popBackStack();
                    getFragmentManager().beginTransaction().show(getTargetFragment()).commit();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.leaveWorkflowFormActivity = (LeaveWorkflowFormActivity)context;
    }

    @Override
    public void submitLeaveCBH(UserTaskBean userTaskBean) {

    }
}
