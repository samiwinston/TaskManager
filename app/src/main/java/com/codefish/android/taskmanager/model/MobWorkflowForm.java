package com.codefish.android.taskmanager.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by abedch on 7/28/2016.
 */
public class MobWorkflowForm implements Parcelable {

    public String htmlForm;
    public List<WorkflowActionBean> actionBeans;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.htmlForm);
        dest.writeTypedList(this.actionBeans);
    }


    protected MobWorkflowForm(Parcel in) {
        this.htmlForm = in.readString();
        this.actionBeans = in.createTypedArrayList(WorkflowActionBean.CREATOR);
    }

    public static final Parcelable.Creator<MobWorkflowForm> CREATOR = new Parcelable.Creator<MobWorkflowForm>() {
        @Override
        public MobWorkflowForm createFromParcel(Parcel source) {
            return new MobWorkflowForm(source);
        }

        @Override
        public MobWorkflowForm[] newArray(int size) {
            return new MobWorkflowForm[size];
        }
    };
}
