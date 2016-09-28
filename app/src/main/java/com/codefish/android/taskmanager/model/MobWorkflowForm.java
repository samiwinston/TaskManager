package com.codefish.android.taskmanager.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by abedch on 7/28/2016.
 */
public class MobWorkflowForm implements Parcelable{

    public String htmlForm;
    public List<WorkflowActionBean> actionBeans;

    private MobWorkflowForm(Parcel in) {
        htmlForm = in.readString();
        this.actionBeans = in.readArrayList(null);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(htmlForm);
        dest.writeList(actionBeans);
    }

    public static final Parcelable.Creator<MobWorkflowForm> CREATOR = new Parcelable.Creator<MobWorkflowForm>() {
        public MobWorkflowForm createFromParcel(Parcel in) {
            return new MobWorkflowForm(in);
        }

        public MobWorkflowForm[] newArray(int size) {
            return new MobWorkflowForm[size];
        }
    };


}
