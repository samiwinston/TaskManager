package com.codefish.android.taskmanager.component.smartDateView;

/**
 * Created by abedch on 6/9/2016.
 */
public class SmartDate {

    private String formattedDate;
    private Integer colorResource;

    public SmartDate(String formattedDate,Integer colorResource) {
        this.colorResource = colorResource;
        this.formattedDate = formattedDate;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }

    public Integer getColorResource() {
        return colorResource;
    }
}
