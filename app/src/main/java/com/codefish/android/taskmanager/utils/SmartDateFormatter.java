package com.codefish.android.taskmanager.utils;

import com.codefish.android.taskmanager.R;
import com.codefish.android.taskmanager.component.smartDateView.SmartDate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by abedch on 6/4/2016.
 */
public class SmartDateFormatter {

    SimpleDateFormat dayOfTheWeekFormatter;
    SimpleDateFormat dayMonthFormatter;
    SimpleDateFormat dayMonthYearFormatter;
    Calendar today;
    Calendar date;


    public SmartDateFormatter() {
        dayOfTheWeekFormatter = new SimpleDateFormat("EEEE");
        dayMonthFormatter = new SimpleDateFormat("MMM dd");
        dayMonthYearFormatter = new SimpleDateFormat("MMM dd, yyyy");
        today = Calendar.getInstance();
        date = Calendar.getInstance();
    }

    public SmartDate getSmartDate(Date dateValue) {
        String formattedDate;
        Integer colorResource = 0;

        date.setTime(dateValue);


        if (date.get(Calendar.YEAR) == today.get(Calendar.YEAR)) {
            Integer dayDiff = date.get(Calendar.DAY_OF_YEAR) - today.get(Calendar.DAY_OF_YEAR);
            if (dayDiff == 1) {
                formattedDate = "Tomorrow";
                colorResource = R.color.colorDGreen;
            } else if (dayDiff == -1) {
                formattedDate = "Yesterday";
                colorResource = R.color.colorRed;
            } else if (dayDiff == 0) {
                formattedDate = "Today";
                colorResource = R.color.colorDGreen;
            } else if (dayDiff > 1 && dayDiff < 8) {
                formattedDate = dayOfTheWeekFormatter.format(dateValue);
            } else
                formattedDate = dayMonthFormatter.format(dateValue);
        } else {
            formattedDate = dayMonthYearFormatter.format(dateValue);
        }

        return new SmartDate(formattedDate, colorResource);

    }

}
