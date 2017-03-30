package com.codefish.android.taskmanager.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.codefish.android.taskmanager.R;
import com.codefish.android.taskmanager.activity.TaskDetailsActivity;
import com.codefish.android.taskmanager.activity.WorkflowFormSubmitActivity;
import com.codefish.android.taskmanager.model.MobWorkflowForm;
import com.codefish.android.taskmanager.model.ResponseBean;
import com.codefish.android.taskmanager.model.ServiceModel;
import com.codefish.android.taskmanager.model.WidgetActionItemBean;
import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;
import im.delight.android.webview.AdvancedWebView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by abedch on 5/2/2016.
 */
public class WorkflowFormSubmitFragment extends Fragment implements AdvancedWebView.Listener {

    @Bind(R.id.workflow_form_submit_layout_toolbar)
    Toolbar toolbar;
    //    @Bind(R.id.workflow_form_layout_progress_bar)
//    ProgressBar progressBar;
    @Bind(R.id.workflow_form_submit_layout_web_view)
    AdvancedWebView mWebView;
    @Bind(R.id.workflow_form_submit_layout_progress_bar)
    ProgressBar progressBar;
    private WorkflowFormSubmitActivity workflowFormSubmitActivity;

//    public static WorkflowFormSubmitFragment newInstance(Fragment targetFragment, Integer requestCode,WidgetActionItemBean widgetActionItemBean) {
//
//        WorkflowFormSubmitFragment fragment = new WorkflowFormSubmitFragment();
//        fragment.widgetActionItemBean = widgetActionItemBean;
//        fragment.setTargetFragment(targetFragment, requestCode);
//
//
//        return fragment;
//    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //inflater.inflate(R.menu.workflow_form_menu, menu);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            // widgetActionItemBean = savedInstanceState.getParcelable("widgetActionItemBean");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //outState.putParcelable("widgetActionItemBean", widgetActionItemBean);
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
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.workflow_form_submit_layout, container, false);
        ButterKnife.bind(this, view);

        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimary),
                android.graphics.PorterDuff.Mode.MULTIPLY);

        mWebView.setListener(getActivity(), this);


        WidgetActionItemBean widgetActionItemBean = workflowFormSubmitActivity.widgetActionItemBean;
        String formTitle = (widgetActionItemBean != null ? widgetActionItemBean.description : "");
        //WebView.setWebContentsDebuggingEnabled(true);
        mWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        mWebView.addJavascriptInterface(new WebAppInterface(this, formTitle), "Android");
        //mWebView.getSettings().setJavaScriptEnabled(true);

        initToolBar();
        submitWorkflowAction();
        //mWebView.loadUrl("http://10.0.0.2:8080/CodefishTestServer/mvc/taskManager/submitNewWorkflowAction?workflowName=hrEmergencyLeaveWorkflow&idAppUser=1151");
        return view;
    }


    private void initToolBar() {
        workflowFormSubmitActivity.setSupportActionBar(toolbar);
        ActionBar supportActionBar = workflowFormSubmitActivity.getSupportActionBar();

        WidgetActionItemBean widgetActionItemBean = workflowFormSubmitActivity.widgetActionItemBean;
        if (widgetActionItemBean != null) {
            supportActionBar.setTitle(widgetActionItemBean.description);
        }


        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setDisplayShowHomeEnabled(true);
        supportActionBar.setDisplayShowTitleEnabled(true);

    }

    private View.OnClickListener onActionClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        workflowFormSubmitActivity = (WorkflowFormSubmitActivity) context;

    }


    private void submitWorkflowAction() {

        Integer idAppUser = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt("userId", 0);
        WidgetActionItemBean widgetActionItemBean = workflowFormSubmitActivity.widgetActionItemBean;

        ServiceModel.getInstance().taskService.submitNewWorkflowAction(idAppUser, widgetActionItemBean.formIdentifier(), widgetActionItemBean.isWorkflowForm())
                .enqueue(new Callback<MobWorkflowForm>() {
                    @Override
                    public void onResponse(Call<MobWorkflowForm> call, Response<MobWorkflowForm> response) {

                        MobWorkflowForm workflowForm = response.body();
                        if (response.isSuccessful()) {
                            if (workflowForm.htmlForm != null && workflowForm.htmlForm.length() > 0) {
                                mWebView.loadData(workflowForm.htmlForm, "text/html", "UTF-8");
                            } else
                                Toast.makeText(getContext(), "Can not load form, please contact the admin!!", Toast.LENGTH_SHORT).show();
                        } else {

                            try {
                                String errorB = response.errorBody().string();
                                Gson gson = new Gson();
                                ResponseBean responseBean = gson.fromJson(errorB, ResponseBean.class);
                                Toast.makeText(getContext(), responseBean.description, Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(getContext(), "Illegal error " + response.code() + ", please contact the admin", Toast.LENGTH_LONG).show();
                            }


                        }

                    }

                    @Override
                    public void onFailure(Call<MobWorkflowForm> call, Throwable t) {
                        Toast.makeText(getContext(), "Can not reach Codefish", Toast.LENGTH_LONG).show();
                    }
                });

    }

    public void showSubmitProgress(){
        mWebView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideSubmitProgress(){
        progressBar.setVisibility(View.GONE);
        mWebView.setVisibility(View.VISIBLE);
    }

    @SuppressLint("NewApi")
    @Override
    public void onResume() {
        super.onResume();
        mWebView.onResume();
        // ...
    }

    @SuppressLint("NewApi")
    @Override
    public void onPause() {
        mWebView.onPause();
        // ...
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mWebView.onDestroy();
        // ...
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        mWebView.onActivityResult(requestCode, resultCode, intent);
        // ...
    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) {
    }

    @Override
    public void onPageFinished(String url) {
        hideSubmitProgress();
    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {
    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {
    }

    @Override
    public void onExternalPageRequest(String url) {
    }
}
