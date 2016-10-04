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
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;

import com.codefish.android.taskmanager.R;
import com.codefish.android.taskmanager.activity.LeaveWorkflowFormActivity;
import com.codefish.android.taskmanager.component.IGenericCallBack;
import com.codefish.android.taskmanager.component.userListView.SearchCountryEditText;
import com.codefish.android.taskmanager.model.TasksModel;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by abedch on 5/2/2016.
 */
public class LeaveSummaryAddCountryFragment extends Fragment implements IGenericCallBack {

    @Bind(R.id.leave_summary_add_country_layout_toolbar)
    Toolbar toolbar;
    private LeaveWorkflowFormActivity leaveWorkflowFormActivity;
    @Bind(R.id.leave_summary_add_country_layout_countries)
    ListView mListView;
    @Bind(R.id.leave_summary_add_country_layout_search)
    SearchCountryEditText searchText;

    public final static String ARGS_ITEM = "argsitem";

    public static LeaveSummaryAddCountryFragment newInstance(Fragment targetFragment)  {

        LeaveSummaryAddCountryFragment fragment = new LeaveSummaryAddCountryFragment();
        fragment.setTargetFragment(targetFragment, TasksModel.REQUEST_COUNTRY);

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
        View view = inflater.inflate(R.layout.leave_summary_add_country_layout, container, false);
        ButterKnife.bind(this, view);
        initToolBar();

/*
        List<HashMap<String, Object>> values =(List<HashMap<String, Object>>) getArguments().getSerializable(ARGS_VALUES);
*/
        searchText.initListView(mListView,null);
        searchText.genericCallBack = this;

        return view;

    }


/*    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        leaveWorkflowFormActivity = (TaskDetailsActivity) activity;
    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        leaveWorkflowFormActivity = (LeaveWorkflowFormActivity) context;
    }

    private void initToolBar() {
        leaveWorkflowFormActivity.setSupportActionBar(toolbar);

        ActionBar supportActionBar = leaveWorkflowFormActivity.getSupportActionBar();
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
            getTargetFragment().onActivityResult(TasksModel.REQUEST_COUNTRY,Activity.RESULT_OK,intent);
            getFragmentManager().popBackStack();

            View view = getActivity().getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
}
