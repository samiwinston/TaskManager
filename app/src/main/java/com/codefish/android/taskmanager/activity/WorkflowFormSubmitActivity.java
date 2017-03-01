package com.codefish.android.taskmanager.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.codefish.android.taskmanager.fragment.SingleFragmentActivity;
import com.codefish.android.taskmanager.fragment.WidgetActionItemsFragment;
import com.codefish.android.taskmanager.fragment.WorkflowFormSubmitFragment;
import com.codefish.android.taskmanager.model.WidgetActionItemBean;

import java.util.ArrayList;

/**
 * Created by abedch on 9/30/2016.
 */
public class WorkflowFormSubmitActivity extends SingleFragmentActivity {



    WorkflowFormSubmitFragment workflowFormSubmitFragment;
    public WidgetActionItemBean widgetActionItemBean;

    public static Intent newInstance(Context context, WidgetActionItemBean widgetActionItemBean) {

        Intent intent = new Intent(context, WorkflowFormSubmitActivity.class);
        intent.putExtra("widgetActionItemBean",widgetActionItemBean);

        return intent;
    }

    @Override
    protected Fragment createFragment() {
        workflowFormSubmitFragment = new WorkflowFormSubmitFragment();
        return workflowFormSubmitFragment;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (workflowFormSubmitFragment != null) {
            workflowFormSubmitFragment.onActivityResult(requestCode, resultCode, intent);
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        widgetActionItemBean = getIntent().getExtras().getParcelable("widgetActionItemBean");


        if (savedInstanceState != null) {
            //Restore the fragment's instance
            widgetActionItemBean = savedInstanceState.getParcelable("widgetActionItemBean");
        }

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Save the fragment's instance
        getSupportFragmentManager().putFragment(outState, "WorkflowFormSubmitFragment", workflowFormSubmitFragment);
        outState.putParcelable("widgetActionItemBean",widgetActionItemBean);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            //Restore the fragment's instance
            workflowFormSubmitFragment = (WorkflowFormSubmitFragment) getSupportFragmentManager().getFragment(savedInstanceState, "WorkflowFormSubmitFragment");
            widgetActionItemBean = savedInstanceState.getParcelable("widgetActionItemBean");
        }
    }
}