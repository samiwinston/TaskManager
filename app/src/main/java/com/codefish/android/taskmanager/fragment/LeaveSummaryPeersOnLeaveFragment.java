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
import com.codefish.android.taskmanager.component.userListView.LeavesListAdapter;
import com.codefish.android.taskmanager.component.userListView.PeersOnLeaveListAdapter;
import com.codefish.android.taskmanager.component.userListView.SearchCountryEditText;
import com.codefish.android.taskmanager.model.TasksModel;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by abedch on 5/2/2016.
 */
public class LeaveSummaryPeersOnLeaveFragment extends Fragment {

    @Bind(R.id.leave_form_peer_on_leave_toolbar)
    Toolbar toolbar;
    private LeaveWorkflowFormActivity leaveWorkflowFormActivity;
    @Bind(R.id.leave_form_peer_on_leave_list)
    ListView mListView;

    private PeersOnLeaveListAdapter listAdapter;

    public final static String ARGS_ITEM = "argsitem";
    private List<HashMap<String, Object>> data;

    public static LeaveSummaryPeersOnLeaveFragment newInstance(Fragment targetFragment,List<HashMap<String, Object>> data)  {

        LeaveSummaryPeersOnLeaveFragment fragment = new LeaveSummaryPeersOnLeaveFragment();
        fragment.setTargetFragment(targetFragment, TasksModel.REQUEST_COUNTRY);
        Bundle b = new Bundle();
        b.putSerializable(ARGS_ITEM, (Serializable) data);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState!=null)
        {
            data = (List<HashMap<String, Object>>) savedInstanceState.getSerializable(ARGS_ITEM);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.leave_form_peers_on_leave_layout, container, false);
        ButterKnife.bind(this, view);
        initToolBar();

        if(savedInstanceState!=null)
        {
            data = (List<HashMap<String, Object>>) savedInstanceState.getSerializable(ARGS_ITEM);
        }
        else
        {
            data = (List<HashMap<String, Object>>) getArguments().getSerializable(ARGS_ITEM);
        }


        initListView(data);
        initToolBar();
        return view;

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(ARGS_ITEM, (Serializable) data);
    }

    public void initListView(List<HashMap<String, Object>> data) {
        listAdapter = new PeersOnLeaveListAdapter(getContext());
        listAdapter.mAllResults = listAdapter.mResultList = data;
        // the drop down list is a list view
        //listView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite));
        mListView.setAdapter(listAdapter);
        // set on item selected
    }
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
}
