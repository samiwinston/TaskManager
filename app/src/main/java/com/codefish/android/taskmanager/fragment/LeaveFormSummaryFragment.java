package com.codefish.android.taskmanager.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.codefish.android.taskmanager.MyApplication;
import com.codefish.android.taskmanager.R;
import com.codefish.android.taskmanager.activity.ILeaveFormSummaryView;
import com.codefish.android.taskmanager.activity.LeaveWorkflowFormActivity;
import com.codefish.android.taskmanager.model.TasksModel;
import com.codefish.android.taskmanager.model.UserTaskBean;
import com.codefish.android.taskmanager.model.hr.MobLeaveRequestFormBean;
import com.codefish.android.taskmanager.presenter.IWorkflowFormPresenter;

import java.util.HashMap;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by abedch on 5/2/2016.
 */
public class LeaveFormSummaryFragment extends Fragment implements ILeaveFormSummaryView {


    @Bind(R.id.leave_request_summary_form_toolbar)
    Toolbar toolbar;
    @Inject
    IWorkflowFormPresenter workflowFormPresenter;

    private LeaveWorkflowFormActivity leaveWorkflowFormActivity;

    /*@Bind(R.id.leave_request_summary_form_submit)
    TextView submitBtn;*/
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
    @Bind(R.id.leave_request_summary_form_address_on_leave)
    TextView addressOnLeave;
    @Bind(R.id.leave_request_summary_form_days_requested_startDate)
    TextView requestedStartDate;
  /*  @Bind(R.id.leave_request_summary_form_days_requested_endDate)
    TextView requestedEndDate;*/
    @Bind(R.id.leave_request_summary_form_country)
    TextView countryOnLeaveView;
   /* @Bind(R.id.leave_request_summary_form_days_requested_endDateGrp)
    LinearLayout requestedEndDateGrp;*/
    @Bind(R.id.leave_request_summary_form_days_allowed_grp)
    LinearLayout daysAllowedGrp;
    @Bind(R.id.leave_request_summary_form_days_remaining_grp)
    LinearLayout daysRemainingGrp;
    @Bind(R.id.leave_request_summary_form_days_country_grp)
    LinearLayout countryGrp;
    @Bind(R.id.leave_request_summary_form_progress_bar)
    ProgressBar progressBar;

    private Menu menu;
    private boolean isSaveVisible = false;
    private String enteredPhoneNumber = null;
    private String enteredAddressOnLeave = null;
    private Integer idSelectedCountry = 0;
    private String selectedCountryName = "";

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean("isSaveVisible", isSaveVisible);
        outState.putString("enteredPhoneNumber", enteredPhoneNumber);
        outState.putString("enteredAddressOnLeave", enteredAddressOnLeave);
        outState.putInt("idSelectedCountry", idSelectedCountry);
        outState.putString("selectedCountryName", selectedCountryName);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            this.isSaveVisible = savedInstanceState.getBoolean("isSaveVisible");
            this.enteredPhoneNumber = savedInstanceState.getString("enteredPhoneNumber");
            this.enteredAddressOnLeave = savedInstanceState.getString("enteredAddressOnLeave");
            this.selectedCountryName = savedInstanceState.getString("selectedCountryName");
            this.idSelectedCountry = savedInstanceState.getInt("idSelectedCountry");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        isSaveVisible = (!leaveWorkflowFormActivity.mobLeaveRequestFormBean.showAddressBox);
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (menu != null)
            this.menu = menu;

        if (isSaveVisible) {
            showSaveOption();
        } else {
            hideSaveOption();
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.leave_summary_menu, menu);
        this.menu = menu;
    }

    private void hideSaveOption() {
        MenuItem item = menu.findItem(R.id.leave_summary_menu_submit);
        item.setVisible(false);
    }

    private void showSaveOption() {
        MenuItem item = menu.findItem(R.id.leave_summary_menu_submit);
        item.setVisible(true);
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

        //submitBtn.setOnClickListener(onSubmitForm());
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimary),
                android.graphics.PorterDuff.Mode.MULTIPLY);

        initToolBar();

        MobLeaveRequestFormBean bean = leaveWorkflowFormActivity.mobLeaveRequestFormBean;
        leaveType.setText(bean.leaveTypeName + " leave was requested");
        daysRequested.setText(bean.totalDays + (bean.isOneDay ? " day" : " days") + " requested");
        String requestedStartDateMsg = "From ";
        String requestedEndDateMsg = "";

        if (bean.isOneDay) {
            requestedStartDateMsg = "On ";
        } else {
            requestedEndDateMsg =" To "+bean.getRequestedEndDateLbl();
        }

        requestedStartDate.setText(requestedStartDateMsg + "" + bean.getRequestedStartDateLbl()+requestedEndDateMsg);

        if (bean.includeInLeave) {
            daysAllowed.setText(bean.leaveInfoBean.getDaysAllowed() + " allowed as of today");
            daysAllowedGrp.setVisibility(View.VISIBLE);
            daysRemaining.setText((bean.leaveInfoBean.getDoubleTotalDaysLeft() - bean.totalDays) + " remaining for this year");
            daysRemainingGrp.setVisibility(View.VISIBLE);
        } else {
            daysAllowedGrp.setVisibility(View.GONE);
            daysRemainingGrp.setVisibility(View.GONE);
        }


        if (bean.warningMessage != null) {
            warningView.setText(bean.warningMessage);
            warningGrp.setVisibility(View.VISIBLE);
        } else {
            warningGrp.setVisibility(View.GONE);
        }

        if (bean.showAddressBox) {
            phoneOnLeaveGrp.setVisibility(View.VISIBLE);
            addressOnLeaveGrp.setVisibility(View.VISIBLE);
            countryGrp.setVisibility(View.VISIBLE);
            countryOnLeaveView.setOnClickListener(onAddCountryClick());
            phoneOnLeave.setTextLocale(Locale.getDefault());
            phoneOnLeave.addTextChangedListener(onTextChangeListener());
            addressOnLeave.setOnClickListener(addAddressOnLeave());
            if (enteredAddressOnLeave != null)
                addressOnLeave.setText(enteredAddressOnLeave);
            if (selectedCountryName != null)
                countryOnLeaveView.setText(selectedCountryName);
        }


        return view;
    }

    private View.OnClickListener onAddCountryClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LeaveSummaryAddCountryFragment leaveSummaryAddCountryFragment = LeaveSummaryAddCountryFragment.
                        newInstance(LeaveFormSummaryFragment.this);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, leaveSummaryAddCountryFragment)
                        .addToBackStack("Back To Parent").commit();
            }
        };
    }

    private View.OnClickListener addAddressOnLeave() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LeaveSummaryStrFieldFragment leaveSummaryStrFieldFragment = LeaveSummaryStrFieldFragment.newInstance(LeaveFormSummaryFragment.this,
                        TasksModel.REQUEST_UPDATE_FIELD, "Enter address on leave", addressOnLeave.getText().toString());
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, leaveSummaryStrFieldFragment)
                        .addToBackStack("Back To Parent").commit();
            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case TasksModel.REQUEST_UPDATE_FIELD:
                enteredAddressOnLeave = data.getStringExtra(LeaveSummaryStrFieldFragment.ARGS_ITEM);
                updateSubmitVisibility();
                break;
            case TasksModel.REQUEST_COUNTRY:
                HashMap<String, Object> item = (HashMap<String, Object>) data.getSerializableExtra(LeaveSummaryAddCountryFragment.ARGS_ITEM);
                idSelectedCountry = ((Double) item.get("id")).intValue();
                selectedCountryName = (String) item.get("name");
                updateSubmitVisibility();
                break;
            default:
        }
    }


    private TextWatcher onTextChangeListener() {
        return new PhoneNumberFormattingTextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                super.beforeTextChanged(s, start, count, after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
            }

            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                if (s.length() > 3) {
                    enteredPhoneNumber = s.toString();

                } else {
                    enteredPhoneNumber = null;
                }
                updateSubmitVisibility();
            }
        };
    }

    private void updateSubmitVisibility() {

        if (enteredPhoneNumber != null && enteredAddressOnLeave != null && idSelectedCountry > 0) {
            isSaveVisible = true;
        } else {
            isSaveVisible = false;
        }


        getActivity().invalidateOptionsMenu();

    }


    private void onSubmitForm() {
        progressBar.setVisibility(View.VISIBLE);
        MobLeaveRequestFormBean bean = leaveWorkflowFormActivity.mobLeaveRequestFormBean;
        bean.lastPhoneOnLeave = enteredPhoneNumber;
        bean.lastCountryOnLeave = idSelectedCountry;
        bean.lastAddressOnLeave = enteredAddressOnLeave;
        bean.updateActionName();
        workflowFormPresenter.submitLeave(bean);
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
                getActivity().finish();
                return true;
            case R.id.leave_summary_menu_submit:
                item.setEnabled(false);
                View view = getActivity().getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                onSubmitForm();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.leaveWorkflowFormActivity = (LeaveWorkflowFormActivity) context;
    }

    @Override
    public void submitLeaveCBH(UserTaskBean userTaskBean) {
        Toast.makeText(getContext(),"Request is sent, please refresh list",Toast.LENGTH_LONG).show();
          getActivity().finish();
    }

    @Override
    public void showErrorMsg(String msg) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show();
    }
}
