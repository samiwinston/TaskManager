package com.codefish.android.taskmanager.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.codefish.android.taskmanager.R;
import com.codefish.android.taskmanager.activity.LeaveWorkflowFormActivity;
import com.codefish.android.taskmanager.activity.WidgetActionItemsActivity;
import com.codefish.android.taskmanager.activity.WorkflowFormSubmitActivity;
import com.codefish.android.taskmanager.component.widgetAction.WidgetActionTypeAdapter;
import com.codefish.android.taskmanager.model.TasksModel;
import com.codefish.android.taskmanager.model.WidgetActionItemBean;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by abedch on 5/2/2016.
 */
public class WidgetActionItemsFragment extends Fragment {

    //    @Inject
//    IWorkflowFormPresenter workflowFormPresenter;
    @Bind(R.id.widget_action_list_layout_toolbar)
    Toolbar toolbar;
    @Bind(R.id.widget_action_items_list_layout_list)
    ListView widgetActionItemsList;
    private WidgetActionTypeAdapter widgetActionTypeAdapter;

    private WidgetActionItemsActivity widgetActionItemsActivity;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (savedInstanceState != null) {
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.widget_action_list_layout, container, false);
        ButterKnife.bind(this, view);
        //MyApplication.getAppComponent().inject(this);
        //workflowFormPresenter.setLeaveWorkflowFormView(this);
        initListView();
        initToolBar();

        return view;
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
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void initToolBar() {
        widgetActionItemsActivity.setSupportActionBar(toolbar);
        ActionBar supportActionBar = widgetActionItemsActivity.getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setDisplayShowHomeEnabled(true);
        supportActionBar.setDisplayShowTitleEnabled(true);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        widgetActionItemsActivity = (WidgetActionItemsActivity) context;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    public void initListView() {
        widgetActionTypeAdapter = new WidgetActionTypeAdapter(getContext());


        ArrayList<WidgetActionItemBean> filteredActions = new ArrayList<>();

        String procReqForm = getResources().getString(R.string.procurement_request_form);

        for (WidgetActionItemBean actionItem:widgetActionItemsActivity.widgetActionItems) {

            if(actionItem.formIdentifier().equals(procReqForm) || actionItem.formIdentifier().equals("hrLeaveRequestWorkflow"))
            {
                filteredActions.add(actionItem);
            }

        }

        widgetActionTypeAdapter.mAllResults = widgetActionTypeAdapter.mResultList = filteredActions;
        // the drop down list is a list view
        //listView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite));
        widgetActionItemsList.setAdapter(widgetActionTypeAdapter);
        // set on item selected
        widgetActionItemsList.setOnItemClickListener(onItemClickListener());
    }

    private AdapterView.OnItemClickListener onItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                WidgetActionItemBean widgetActionItemBean = widgetActionTypeAdapter.getItem(position);


                if (widgetActionItemBean.workflowName == null && widgetActionItemBean.formName == null) {
                    showErrorMsg("The selected action doesn't have an identifier, please contact the admin!!");
                    return;
                }
                Intent navToDetails ;
                if (widgetActionItemBean.isWorkflowForm() &&  widgetActionItemBean.workflowName.equals("hrLeaveRequestWorkflow")) {
                     navToDetails = LeaveWorkflowFormActivity.newInstance(getContext(), widgetActionItemBean);
                } else {
                     navToDetails = WorkflowFormSubmitActivity.newInstance(getContext(), widgetActionItemBean);
                }
                startActivityForResult(navToDetails, TasksModel.REQUEST_WORKFLOW_TASK_UPDATE);
                getActivity().finish();
            }
        };
    }


    public void showErrorMsg(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    public void show() {
        getFragmentManager().beginTransaction().show(WidgetActionItemsFragment.this).commit();
    }

}