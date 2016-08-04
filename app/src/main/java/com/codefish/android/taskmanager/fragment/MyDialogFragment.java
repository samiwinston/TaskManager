package com.codefish.android.taskmanager.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.codefish.android.taskmanager.R;
import com.codefish.android.taskmanager.model.TasksModel;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by abedch on 5/2/2016.
 */
public class MyDialogFragment extends DialogFragment {


    public int requestCode;

    public static MyDialogFragment newInstance() {
        return new MyDialogFragment();
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setMessage("Are you sure you want to delete this task?")
                .setCancelable(false)
                // Positive button
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_OK);
                    }
                })
                // Negative Button
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something else
                    }
                }).create();
    }


    private void sendResult(int resultOk) {
        if (getTargetFragment() == null)
            return;
        getTargetFragment().onActivityResult(TasksModel.REQUEST_REMOVE_TASK, resultOk, null);
    }
}



