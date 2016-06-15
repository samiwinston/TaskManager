package com.codefish.android.taskmanager.component.smartDateView;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.TextView;

import com.codefish.android.taskmanager.MyApplication;
import com.codefish.android.taskmanager.utils.SmartDateFormatter;

import java.util.Date;

import javax.inject.Inject;

/**
 * Created by abedch on 6/9/2016.
 */
public class SmartDateTextView extends TextView {

    @Inject
    SmartDateFormatter smartDateFormatter;

    public SmartDateTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode())
            MyApplication.getAppComponent().inject(this);
    }


    public void setDate(Date date) {
        SmartDate smartDate = smartDateFormatter.getSmartDate(date);
        if (smartDate.getColorResource() > 0)
            setTextColor(ContextCompat.getColor(getContext(), smartDate.getColorResource()));
        else
        {
            setTextColor(Color.BLACK);
        }
        setText(smartDate.getFormattedDate());
    }


}
