package com.codefish.android.taskmanager.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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
public class DatePickerFragment extends DialogFragment {

    public static final String ARG_DATE = "date";
    public static final String EXTRA_DATE= "com.codefish.android.taskmanager.date";

    @Bind(R.id.dialog_date_date_picker)
    DatePicker mDatePicker;

    public int requestCode;

    public static DatePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date,null);
        ButterKnife.bind(this,view);

        Date date = null;
        if(getArguments()!=null)
          date = (Date)getArguments().getSerializable(ARG_DATE);

        if(date!=null)
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get( Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            mDatePicker.init(year,month,day,null);
        }

        Dialog dialog =   new AlertDialog.Builder(getActivity())
                .setTitle(R.string.date_picker_title)
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int year = mDatePicker.getYear();
                        int month = mDatePicker.getMonth();
                        int day = mDatePicker.getDayOfMonth();
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year,month,day);
                        //calender.setTime(mDatePicker.getCalendarView().getDate());
                        sendResult(Activity.RESULT_OK, calendar.getTime());

                    }
                })
                .setNegativeButton(date!=null?"Remove":"Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_OK, null);
                    }
                })
                .create();



        return dialog;
    }

    private void sendResult(int resultOk, Date date) {
        if(getTargetFragment()==null)
            return;

        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);

        getTargetFragment().onActivityResult(TasksModel.REQUEST_DATE,resultOk,intent);
    }
}
