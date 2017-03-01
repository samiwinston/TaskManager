package com.codefish.android.taskmanager.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.codefish.android.taskmanager.fragment.LeaveWorkflowFormFragment;
import com.codefish.android.taskmanager.fragment.SingleFragmentActivity;
import com.codefish.android.taskmanager.fragment.WidgetActionItemsFragment;
import com.codefish.android.taskmanager.model.WidgetActionItemBean;
import com.codefish.android.taskmanager.model.hr.MobLeaveRequestFormBean;

import java.util.ArrayList;

/**
 * Created by abedch on 9/30/2016.
 */
public class WidgetActionItemsActivity extends SingleFragmentActivity {



    WidgetActionItemsFragment widgetActionItemsFragment;
    public ArrayList<WidgetActionItemBean> widgetActionItems;

    public static Intent newInstance(Context context, ArrayList<WidgetActionItemBean> widgetActionItems) {

        Intent intent = new Intent(context, WidgetActionItemsActivity.class);
        intent.putParcelableArrayListExtra("widgetActionItems", widgetActionItems);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        widgetActionItemsFragment = new WidgetActionItemsFragment();
        return widgetActionItemsFragment;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        widgetActionItems = getIntent().getExtras().getParcelableArrayList("widgetActionItems");

        if (savedInstanceState != null) {
            //Restore the fragment's instance

        }

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Save the fragment's instance
        getSupportFragmentManager().putFragment(outState, "WidgetActionItemsFragment", widgetActionItemsFragment);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            //Restore the fragment's instance
            widgetActionItemsFragment = (WidgetActionItemsFragment) getSupportFragmentManager().getFragment(savedInstanceState, "WidgetActionItemsFragment");
        }
    }
}