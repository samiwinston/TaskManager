package com.codefish.android.taskmanager.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.codefish.android.taskmanager.R;
import com.codefish.android.taskmanager.activity.TaskDetailsActivity;
import com.codefish.android.taskmanager.component.WorkflowActionButton;
import com.codefish.android.taskmanager.model.LoginModel;
import com.codefish.android.taskmanager.model.MobWorkflowForm;
import com.codefish.android.taskmanager.model.ServiceModel;
import com.codefish.android.taskmanager.model.SubmitActionParam;
import com.codefish.android.taskmanager.model.TasksModel;
import com.codefish.android.taskmanager.model.UserTaskBean;
import com.codefish.android.taskmanager.model.WorkflowActionBean;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by abedch on 5/2/2016.
 */
public class WorkflowFormFragment extends Fragment {

    @Bind(R.id.workflow_form_layout_web_view)
    WebView webView;
    @Bind(R.id.workflow_form_layout_action_btn_group)
    LinearLayout actionBtnGroup;
    private TaskDetailsActivity taskDetailsActivity;
    private MobWorkflowForm mobWorkflowForm;
    private Integer idWorkflowInstance;

    public static WorkflowFormFragment newInstance(Fragment targetFragment, Integer requestCode,MobWorkflowForm mobWorkflowForm,Integer idWorkflowInstance) {

        WorkflowFormFragment fragment = new WorkflowFormFragment();
        fragment.mobWorkflowForm = mobWorkflowForm;
        fragment.idWorkflowInstance = idWorkflowInstance;
        fragment.setTargetFragment(targetFragment,requestCode);
       /* fragment.setTargetFragment(targetFragment, requestCode);
        fragment.htmlData = htmlData;
        fragment.requestCode = requestCode;*/

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
        View view = inflater.inflate(R.layout.workflow_form_layout, container, false);
        ButterKnife.bind(this, view);

        if (mobWorkflowForm.htmlForm!=null && mobWorkflowForm.htmlForm.length() >0)
        {
            webView.loadData(mobWorkflowForm.htmlForm, "text/html; charset=UTF-8", null);

            for (WorkflowActionBean actionBean : mobWorkflowForm.actionBeans) {
                WorkflowActionButton button = new WorkflowActionButton(getContext(), null);
                button.setWorkflowActionBean(actionBean);
                button.setOnClickListener(onActionClick());
                actionBtnGroup.addView(button);
            }
        }
        else Toast.makeText(getContext(), "Can not load form, please contact admin!!", Toast.LENGTH_SHORT).show();



        return view;
    }

    private View.OnClickListener onActionClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        ServiceModel.getInstance().taskService.submitWorkflowAction(LoginModel.getInstance().getUserBean().getId(),
                idWorkflowInstance,bean.getAction()).enqueue(new Callback<UserTaskBean>() {
            @Override
            public void onResponse(Call<UserTaskBean> call, Response<UserTaskBean> response) {
                if(response.isSuccessful())
                {
                    UserTaskBean bean = response.body();
                    Bundle bundle = bean.getBundle();
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    getTargetFragment().onActivityResult(TasksModel.REQUEST_TASK_UPDATE,Activity.RESULT_OK,intent);

                }
            }

            @Override
            public void onFailure(Call<UserTaskBean> call, Throwable t) {

            }
        });

    }

}
