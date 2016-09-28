package com.codefish.android.taskmanager.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.codefish.android.taskmanager.R;
import com.codefish.android.taskmanager.activity.TaskDetailsActivity;
import com.codefish.android.taskmanager.component.WorkflowActionButton;
import com.codefish.android.taskmanager.model.MobWorkflowForm;
import com.codefish.android.taskmanager.model.ServiceModel;
import com.codefish.android.taskmanager.model.TasksModel;
import com.codefish.android.taskmanager.model.UserTaskBean;
import com.codefish.android.taskmanager.model.WorkflowActionBean;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by abedch on 5/2/2016.
 */
public class LeaveRequestWorkflowFormFragment extends Fragment {

    @Bind(R.id.workflow_form_layout_toolbar)
    Toolbar toolbar;
    @Bind(R.id.workflow_form_layout_progress_bar)
    ProgressBar progressBar;
    @Bind(R.id.workflow_form_layout_web_view)
    WebView webView;
    @Bind(R.id.workflow_form_layout_action_btn_group)
    LinearLayout actionBtnGroup;
    private TaskDetailsActivity taskDetailsActivity;
    private MobWorkflowForm mobWorkflowForm;
    private Integer idWorkflowInstance;

    public static LeaveRequestWorkflowFormFragment newInstance(Fragment targetFragment, Integer requestCode, MobWorkflowForm mobWorkflowForm, Integer idWorkflowInstance) {

        LeaveRequestWorkflowFormFragment fragment = new LeaveRequestWorkflowFormFragment();
        fragment.mobWorkflowForm = mobWorkflowForm;
        fragment.idWorkflowInstance = idWorkflowInstance;
        fragment.setTargetFragment(targetFragment, requestCode);
       /* fragment.setTargetFragment(targetFragment, requestCode);
        fragment.htmlData = htmlData;
        fragment.requestCode = requestCode;*/

        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //inflater.inflate(R.menu.workflow_form_menu, menu);


    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.workflow_form_layout, container, false);
        ButterKnife.bind(this, view);

        webView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
            }
        });

        if (mobWorkflowForm.htmlForm != null && mobWorkflowForm.htmlForm.length() > 0) {
            webView.loadData(mobWorkflowForm.htmlForm, "text/html; charset=UTF-8", null);

            for (WorkflowActionBean actionBean : mobWorkflowForm.actionBeans) {
                WorkflowActionButton button = new WorkflowActionButton(getContext(), null);
                button.setWorkflowActionBean(actionBean);
                button.setOnClickListener(onActionClick());
                actionBtnGroup.addView(button);
            }
        } else
            Toast.makeText(getContext(), "Can not load form, please contact admin!!", Toast.LENGTH_SHORT).show();

        initToolBar();

        return view;
    }



    private void initToolBar() {
        taskDetailsActivity.setSupportActionBar(toolbar);
        ActionBar supportActionBar = taskDetailsActivity.getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setDisplayShowHomeEnabled(true);
        supportActionBar.setDisplayShowTitleEnabled(true);

    }

    private View.OnClickListener onActionClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionBtnGroup.setEnabled(false);
                WorkflowActionBean bean = ((WorkflowActionButton) v).getWorkflowActionBean();
                submitWorkflowAction(bean);
            }
        };
    }


    @Override
    public void onAttach(Activity activity) {
        taskDetailsActivity = (TaskDetailsActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }


    private void submitWorkflowAction(WorkflowActionBean bean) {

        Integer idAppUser = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt("userId", 0);

        ServiceModel.getInstance().taskService.submitWorkflowAction(idAppUser,
                idWorkflowInstance, bean.getAction()).enqueue(new Callback<UserTaskBean>() {
            @Override
            public void onResponse(Call<UserTaskBean> call, Response<UserTaskBean> response) {
                if (response.isSuccessful()) {
                    UserTaskBean bean = response.body();
                    Bundle bundle = bean.getBundle();
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    getTargetFragment().onActivityResult(TasksModel.REQUEST_TASK_UPDATE, Activity.RESULT_OK, intent);

                } else {

                    try {
                        if (response.code() == 500 && response.errorBody().contentLength()<200) {
                            Toast.makeText(getContext(), response.errorBody().string(), Toast.LENGTH_LONG).show();
                        } else {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Illegal error "+response.code()+", please contact the admin", Toast.LENGTH_LONG).show();
                    }


                }
            }

            @Override
            public void onFailure(Call<UserTaskBean> call, Throwable t) {
                Toast.makeText(getContext(), "Can not reach Codefish", Toast.LENGTH_LONG).show();
            }
        });

    }

}
