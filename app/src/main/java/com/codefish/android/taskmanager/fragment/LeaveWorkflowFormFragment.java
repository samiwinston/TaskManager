package com.codefish.android.taskmanager.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.codefish.android.taskmanager.MyApplication;
import com.codefish.android.taskmanager.R;
import com.codefish.android.taskmanager.activity.ILeaveWorkflowFormView;
import com.codefish.android.taskmanager.activity.LeaveWorkflowFormActivity;
import com.codefish.android.taskmanager.component.WorkflowActionButton;
import com.codefish.android.taskmanager.component.userListView.LeavesListAdapter;
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
import java.util.Map;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by abedch on 5/2/2016.
 */
public class LeaveWorkflowFormFragment extends Fragment implements ILeaveWorkflowFormView {

    @Inject
    IWorkflowFormPresenter workflowFormPresenter;

    @Bind(R.id.leave_request_form_layout_toolbar)
    Toolbar toolbar;
    @Bind(R.id.leave_request_form_layout_leaves_List)
    ListView mListView;
    @Bind(R.id.leave_request_form_progress_bar)
    ProgressBar progressBar;
    @Bind(R.id.leave_request_form_view_switcher)
    ViewSwitcher viewSwitcher;
    @Bind(R.id.leave_request_form_layout_calendar_view)
    MaterialCalendarView calendarView;
    @Bind(R.id.leave_request_form_layout_action_grp)
    RelativeLayout actionGrp;
    /* @Bind(R.id.leave_request_form_layout_phoneOnLeave)
     EditText phoneOnLeave;
     @Bind(R.id.leave_request_form_layout_addressOnLeave)
     EditText addressOnLeave;*/
    @Bind(R.id.leave_request_form_layout_previous)
    Button previousBtn;
    @Bind(R.id.leave_request_form_layout_next)
    Button nextBtn;
    private LeaveWorkflowFormActivity leaveWorkflowFormActivity;
    private LeavesListAdapter listAdapter;
    private HashMap<String, Object> selectedLeaveType;
    private LeaveFormSummaryFragment leaveFormSummaryFragment;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.leave_request_form_layout, container, false);
        ButterKnife.bind(this, view);
        MyApplication.getAppComponent().inject(this);
        workflowFormPresenter.setLeaveWorkflowFormView(this);
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimary),
                android.graphics.PorterDuff.Mode.MULTIPLY);


        if (leaveWorkflowFormActivity.mobLeaveRequestFormBean == null) {
            workflowFormPresenter.getWorkflowBeanByAction(PreferenceManager.
                    getDefaultSharedPreferences(getContext()).getInt("userId", 0), leaveWorkflowFormActivity.leaveActionItemBean);
        } else {
            prepareView();
        }


        return view;
    }

    private void prepareView() {

        final Map<String, MobSimpleLeaveCalendarEntity> entityItems = leaveWorkflowFormActivity.mobLeaveRequestFormBean.calendarBean.getEntityItems();
        //final List<WorkflowActionBean> actions = leaveWorkflowFormActivity.mobLeaveRequestFormBean.actions;

        previousBtn.setOnClickListener(onPreviousClick());
        nextBtn.setOnClickListener(onNextClick());
        /*for (WorkflowActionBean actionBean : actions) {
            WorkflowActionButton button = new WorkflowActionButton(getContext(), null);
            button.setWorkflowActionBean(actionBean);
            button.setOnClickListener(onActionClick());
            actionGrp.addView(button);
        }*/

        calendarView.setOnDateChangedListener(onDateChangeClick());


        DisabledDatesDecorator decorator = new DisabledDatesDecorator();
        decorator.items = entityItems;

        for (Integer weekDay : leaveWorkflowFormActivity.mobLeaveRequestFormBean.calendarBean.getWeekends()) {
            decorator.disabledWDays[weekDay] = true;
        }
        calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);
        calendarView.addDecorator(decorator);

        Calendar firstDayOfYear = Calendar.getInstance();
        //Calendar maximumDate = Calendar.getInstance();
        firstDayOfYear.set(Calendar.DAY_OF_YEAR, 1);
        //maximumDate.add(Calendar.YEAR,1);
        //maximumDate.set(Calendar.);
        calendarView.state().edit()
                .setMinimumDate(firstDayOfYear)
                .commit();


        initListView();
        initToolBar();

    }

    private View.OnClickListener onNextClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // make sure that selected date is > 0
                if (calendarView.getSelectedDates().size() == 0) {
                    Toast.makeText(getContext(), "Please select  leave dates", Toast.LENGTH_LONG).show();
                } else {
                    prepareLeave();
                    leaveFormSummaryFragment = LeaveFormSummaryFragment.newInstance(LeaveWorkflowFormFragment.this);
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, leaveFormSummaryFragment)
                            .addToBackStack("Back To Parent").commit();
                }

            }
        };
    }

    private OnDateSelectedListener onDateChangeClick() {
        return new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                if (!selected) {
                    leaveWorkflowFormActivity.mobLeaveRequestFormBean.calendarBean.removeEntityItem(date.getDate());
                }

            }
        };

    }

  /*  private View.OnClickListener onActionClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calendarView.getSelectedDates().size() == 0) {
                    Toast.makeText(getContext(), "Some fields are missing", Toast.LENGTH_SHORT).show();
                } else {
                    WorkflowActionBean bean = ((WorkflowActionButton) v).getWorkflowActionBean();
                    leaveWorkflowFormActivity.mobLeaveRequestFormBean.submitParams.actionName = bean.getAction();
                    prepareLeave();
                    //Toast.makeText(getContext(), "Ready " + calendarView.getSelectedDates().size(), Toast.LENGTH_SHORT).show();
                }
            }
        };
    }*/

    private void prepareLeave() {


        MobLeaveRequestFormBean mobLeaveRequestFormBean = leaveWorkflowFormActivity.mobLeaveRequestFormBean;

        List<MobSimpleLeaveCalendarEntity> requestedLeaves = new ArrayList<>();

        for (CalendarDay item : calendarView.getSelectedDates()) {

            MobSimpleLeaveCalendarEntity dateEntity = new MobSimpleLeaveCalendarEntity();
            dateEntity.status = MobSimpleLeaveCalendarEntity.NEW;
            dateEntity.daySelectionType = 1;
            dateEntity.allowLeaveSelection = true;
            dateEntity.date = item.getDate();
            dateEntity.idType = mobLeaveRequestFormBean.leaveTypeId;
            dateEntity.idCategory = ((Double) selectedLeaveType.get("categoryId")).intValue();

            mobLeaveRequestFormBean.calendarBean.addEntityItem(dateEntity);

        }

        for (Map.Entry<String, MobSimpleLeaveCalendarEntity> entry : mobLeaveRequestFormBean.calendarBean.getEntityItems().entrySet()) {
            if (entry.getValue().status == MobSimpleLeaveCalendarEntity.NEW)
                requestedLeaves.add(entry.getValue());
        }

        Collections.sort(requestedLeaves, new Comparator<MobSimpleLeaveCalendarEntity>() {
            @Override
            public int compare(MobSimpleLeaveCalendarEntity lhs, MobSimpleLeaveCalendarEntity rhs) {
                return lhs.getDate().compareTo(rhs.getDate());
            }
        });


        mobLeaveRequestFormBean.requestedLeaves = requestedLeaves;
        mobLeaveRequestFormBean.requestStartDate = requestedLeaves.get(0).getDate();
        mobLeaveRequestFormBean.requestEndDate = requestedLeaves.get(requestedLeaves.size() - 1).getDate();
        mobLeaveRequestFormBean.totalDays = requestedLeaves.size();


        mobLeaveRequestFormBean.isOneDay = (requestedLeaves.size() == 1);
        mobLeaveRequestFormBean.warningMessage = null;

        if (mobLeaveRequestFormBean.requestStartDate != null && mobLeaveRequestFormBean.includeInLeave) {
            Calendar today = Calendar.getInstance();
            if (mobLeaveRequestFormBean.requestStartDate.before(today.getTime())) {
                mobLeaveRequestFormBean.warningMessage = "Requested leave is in the past !";
            } else {
                today.add(Calendar.MONTH, 1);
                if (mobLeaveRequestFormBean.requestStartDate.before(today.getTime())) {
                    mobLeaveRequestFormBean.warningMessage = "Requested leave is in less than a month !";
                }
            }
        }


        leaveWorkflowFormActivity.mobLeaveRequestFormBean = mobLeaveRequestFormBean;


    }


    private View.OnClickListener onPreviousClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewSwitcher.showPrevious();
            }
        };
    }


    public void initListView() {
        listAdapter = new LeavesListAdapter(getContext(), "name", R.layout.project_item_layout);
        listAdapter.mAllResults = listAdapter.mResultList = leaveWorkflowFormActivity.mobLeaveRequestFormBean.leaveList;
        // the drop down list is a list view
        //listView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite));
        mListView.setAdapter(listAdapter);
        // set on item selected
        mListView.setOnItemClickListener(onItemClickListener());
    }

    private AdapterView.OnItemClickListener onItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedLeaveType = listAdapter.getItem(position);
                leaveWorkflowFormActivity.mobLeaveRequestFormBean.leaveTypeId = ((Double) selectedLeaveType.get("leaveTypeId")).intValue();
                leaveWorkflowFormActivity.mobLeaveRequestFormBean.leaveTypeName = (String) selectedLeaveType.get("leaveTypeName");
                leaveWorkflowFormActivity.mobLeaveRequestFormBean.showAddressBox = (Boolean) selectedLeaveType.get("showAddressBox");
                leaveWorkflowFormActivity.mobLeaveRequestFormBean.includeInLeave = (Boolean) selectedLeaveType.get("includeInLeave");


                viewSwitcher.showNext();
            }
        };
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //inflater.inflate(R.menu.workflow_form_menu, menu);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            selectedLeaveType = (HashMap<String, Object>) savedInstanceState.getSerializable("selectedLeaveType");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("selectedLeaveType", selectedLeaveType);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (savedInstanceState != null) {
            selectedLeaveType = (HashMap<String, Object>) savedInstanceState.getSerializable("selectedLeaveType");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                if (viewSwitcher.getCurrentView().getId() == R.id.leave_request_form_layout_calendar_grp) {
                    viewSwitcher.showPrevious();
                } else {
                    getActivity().finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void initToolBar() {
        leaveWorkflowFormActivity.setSupportActionBar(toolbar);
        ActionBar supportActionBar = leaveWorkflowFormActivity.getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setDisplayShowHomeEnabled(true);
        supportActionBar.setDisplayShowTitleEnabled(true);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        leaveWorkflowFormActivity = (LeaveWorkflowFormActivity) context;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }



    @Override
    public void getLeaveBeanCBH(MobLeaveRequestFormBean bean) {
        leaveWorkflowFormActivity.mobLeaveRequestFormBean = bean;
        prepareView();
        progressBar.setVisibility(View.GONE);
        mListView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorMsg(String msg) {
        Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show();
    }

    public void show() {
        getFragmentManager().beginTransaction().show(LeaveWorkflowFormFragment.this).commit();
    }


    private static class DisabledDatesDecorator implements DayViewDecorator {


        public List<Integer> values;

        public Map<String, MobSimpleLeaveCalendarEntity> items;

        public boolean[] disabledWDays = {
                false,  // 0?
                false, // 1
                false, // 2
                false, // 3
                false, // 4
                false, // 5
                false // 6


        };

        private SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy");

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            // Log.v("DECO-TAG","day is "+day.getDay()+"");


            // Log.v("DECO-TAG","item is "+item+"");
            return (items.get(formatter.format(day.getDate())) != null)
                    || disabledWDays[day.getCalendar().get(Calendar.DAY_OF_WEEK) - 1];

        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setDaysDisabled(true);
        }
    }


}
