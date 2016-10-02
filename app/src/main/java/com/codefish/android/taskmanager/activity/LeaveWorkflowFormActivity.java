package com.codefish.android.taskmanager.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.codefish.android.taskmanager.fragment.LeaveWorkflowFormFragment;
import com.codefish.android.taskmanager.fragment.SingleFragmentActivity;
import com.codefish.android.taskmanager.fragment.TaskDetailsFragment;
import com.codefish.android.taskmanager.model.UserTaskBean;
import com.codefish.android.taskmanager.model.WidgetActionItemBean;
import com.codefish.android.taskmanager.model.hr.MobLeaveRequestFormBean;

/**
 * Created by abedch on 9/30/2016.
 */
public class LeaveWorkflowFormActivity extends SingleFragmentActivity {



    LeaveWorkflowFormFragment leaveWorkflowFormFragment;
    public MobLeaveRequestFormBean mobLeaveRequestFormBean;
    public WidgetActionItemBean leaveActionItemBean;

    public static Intent newInstance(Context context, WidgetActionItemBean leaveActionItemBean) {

        Intent intent = new Intent(context, LeaveWorkflowFormActivity.class);
        intent.putExtra("leaveActionItemBean",leaveActionItemBean);
        //intent.putExtra("mobLeaveRequestFormBean",mobLeaveRequestFormBean.getBundle());
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        leaveWorkflowFormFragment = new LeaveWorkflowFormFragment();
        return leaveWorkflowFormFragment;
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
          //  taskDetailsFragment.goBackToList();
            //additional code
        }
        else
        {
            leaveWorkflowFormFragment.show();
            super.onBackPressed();
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mobLeaveRequestFormBean = new MobLeaveRequestFormBean(getIntent().getExtras().getBundle("mobLeaveRequestFormBean"));
        leaveActionItemBean = getIntent().getExtras().getParcelable("leaveActionItemBean");










        if (savedInstanceState != null) {
            //Restore the fragment's instance
            leaveWorkflowFormFragment = (LeaveWorkflowFormFragment) getSupportFragmentManager().getFragment(savedInstanceState, "LeaveWorkflowFormFragment");
            mobLeaveRequestFormBean = new MobLeaveRequestFormBean(savedInstanceState.getBundle("mobLeaveRequestFormBean"));
        }

    }

   public void getLeaveWorkflowByAction(){


   }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Save the fragment's instance
        getSupportFragmentManager().putFragment(outState, "LeaveWorkflowFormFragment", leaveWorkflowFormFragment);
        outState.putBundle("mobLeaveRequestFormBean",mobLeaveRequestFormBean.getBundle());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {
            //Restore the fragment's instance
            leaveWorkflowFormFragment = (LeaveWorkflowFormFragment) getSupportFragmentManager().getFragment(savedInstanceState, "LeaveWorkflowFormFragment");
            mobLeaveRequestFormBean = new MobLeaveRequestFormBean(savedInstanceState.getBundle("mobLeaveRequestFormBean"));
        }
    }

}
