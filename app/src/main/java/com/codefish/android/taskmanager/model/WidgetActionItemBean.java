package com.codefish.android.taskmanager.model;


import android.os.Parcel;
import android.os.Parcelable;

public class WidgetActionItemBean implements Parcelable {
	
	public WidgetActionItemBean(){}

	public Integer id;

	public String formName;
	public String name;
	public String description;
	
	public String customViewName;
	public String widgetName;
	public String idEntity;
	public String groupName;
	public Integer security;
	public Integer sequence = 0;
	public Boolean isUserForm;
	public Boolean showInNewTask;
	public Boolean showInQuickActions;
	public String actionColor;
	public byte[] icon;
	
	public String workflowName;
	public String parentWorkflow;
	public String parentWorkflowEntityPath;
	public Object[] securityTags;

	public WidgetActionItemBean(Parcel in) {
		id = in.readInt();
		formName = in.readString();
		name = in.readString();
		customViewName = in.readString();
		widgetName = in.readString();
		idEntity = in.readString();
		groupName = in.readString();
		workflowName =in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

		dest.writeInt(id);
		dest.writeString(formName);
		dest.writeString(name);
		dest.writeString(customViewName);
		dest.writeString(widgetName);
		dest.writeString(idEntity);
		dest.writeString(groupName);
		dest.writeString(workflowName);
	}


	public static final Parcelable.Creator<WidgetActionItemBean> CREATOR = new Parcelable.Creator<WidgetActionItemBean>() {
		public WidgetActionItemBean createFromParcel(Parcel in) {
			return new WidgetActionItemBean(in);
		}

		public WidgetActionItemBean[] newArray(int size) {
			return new WidgetActionItemBean[size];
		}
	};

}
