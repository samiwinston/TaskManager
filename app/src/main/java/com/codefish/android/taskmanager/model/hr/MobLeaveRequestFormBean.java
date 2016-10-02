package com.codefish.android.taskmanager.model.hr;


import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.codefish.android.taskmanager.model.SubmitActionParam;
import com.codefish.android.taskmanager.model.WorkflowActionBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MobLeaveRequestFormBean implements Parcelable {

	public MobLeaveRequestFormBean() {

	}

	public List<HashMap<String, Object>> leaveList;

	public SubmitActionParam submitParams;

	public List<WorkflowActionBean> actions;

	public List<Map<String, Object>> onBehalfList;

	public MobSimpleLeaveCalendarBean calendarBean;

	public LeaveInfoBean leaveInfoBean;

	public List<MobSimpleLeaveCalendarEntity> requestedLeaves;

	public String leaveTypeName;
	public Integer leaveTypeId;

	public Boolean includeInLeave;
	public Boolean showOthersOnLeave;
	public Boolean showAddressBox;
	public Boolean showDoctorsNote;

	public String fullName;
	public Integer idEmployee;

	public Integer minRequiredTime;

	public Date requestStartDate;
	public Date requestEndDate;

	public Integer totalDays = 0;

	public String warningMessage;

	public Boolean isOneDay;
	public Boolean isNew;

	public Boolean canUseTickets = true;
	public Boolean personalTicketRequested = true;
	public Boolean familyTicketsRequested;
	public Integer maxAllowedFamilyTickets;
	public Integer maxAllowedTickets;
	public String lastAddressOnLeave;
	public int lastCountryOnLeave;
	public String lastPhoneOnLeave;
	public String allowedDays;

	public MobLeaveRequestFormBean(Bundle b) {
		this.leaveList = (List<HashMap<String, Object>>) b.getSerializable("leaveList");
		this.leaveInfoBean = (LeaveInfoBean) b.getSerializable("leaveInfoBean");
		this.calendarBean = (MobSimpleLeaveCalendarBean)b.getSerializable("calendarBean");
		this.requestedLeaves = (List<MobSimpleLeaveCalendarEntity>) b.getSerializable("requestedLeaves");
		this.leaveTypeId = b.getInt("leaveTypeId");
		this.leaveTypeName = b.getString("leaveTypeName");
		this.lastPhoneOnLeave = b.getString("lastPhoneOnLeave");
		this.lastAddressOnLeave = b.getString("lastAddressOnLeave");
		this.lastCountryOnLeave = b.getInt("lastCountryOnLeave");
        this.actions = b.getParcelableArrayList("actions");
        this.requestStartDate = (Date) b.getSerializable("requestStartDate");
		this.requestEndDate = (Date) b.getSerializable("requestedEndDate");
		this.idEmployee = b.getInt("idEmployee");
		this.submitParams = (SubmitActionParam) b.getSerializable("submitParams");
        this.warningMessage = b.getString("warningMessage");
		this.isOneDay = b.getBoolean("isOneDay");
		this.showAddressBox = b.getBoolean("showAddressBox");
		this.includeInLeave = b.getBoolean("includeInLeave");
		this.totalDays = b.getInt("totalDays");
	}



	public Bundle getBundle() {

		Bundle b = new Bundle();
		b.putSerializable("leaveList", (Serializable) leaveList);
		b.putSerializable("leaveInfoBean", leaveInfoBean);
		b.putSerializable("calendarBean",calendarBean);
		b.putSerializable("requestedLeaves", (Serializable) requestedLeaves);
		b.putInt("leaveTypeId", leaveTypeId == null ? 0 : leaveTypeId);
		b.putInt("idEmployee", idEmployee == null ? 0 : idEmployee);
		b.putInt("totalDays", totalDays == null ? 0 : totalDays);
		b.putString("leaveTypeName",leaveTypeName);
		b.putString("lastPhoneOnLeave",lastPhoneOnLeave);
		b.putString("lastAddressOnLeave",lastAddressOnLeave);
		b.putInt("lastCountryOnLeave", lastCountryOnLeave);
        b.putParcelableArrayList("actions", (ArrayList<? extends Parcelable>) actions);
        b.putSerializable("requestStartDate",requestStartDate);
		b.putSerializable("requestEndDate",requestEndDate);
        b.putSerializable("submitParams", submitParams);
		b.putString("warningMessage",warningMessage);
		b.putBoolean("isOneDay",isOneDay);
		b.putBoolean("showAddressBox",showAddressBox==null ? false:showAddressBox);
		b.putBoolean("includeInLeave",includeInLeave== null ?false:includeInLeave);
		return b;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	public void updateActionName(){
		if(submitParams !=null && actions!=null && actions.size()==0)
		submitParams.actionName = actions.get(0).getAction();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

		dest.writeSerializable((Serializable) this.leaveList);
		//dest.writeParcelable(this.calendarBean, flags);
		//dest.writeParcelable(this.leaveInfoBean, flags);
		/*dest.writeList(this.requestedLeaves);
		dest.writeString(this.leaveTypeName);
		dest.writeValue(this.leaveTypeId);
		dest.writeValue(this.includeInLeave);
		dest.writeValue(this.showOthersOnLeave);
		dest.writeValue(this.showAddressBox);
		dest.writeValue(this.showDoctorsNote);*/
		dest.writeString(this.fullName);
		/*dest.writeValue(this.idEmployee);*/
		//dest.writeValue(this.minRequiredTime);
/*		dest.writeLong(this.requestStartDate != null ? this.requestStartDate.getTime() : -1);
		dest.writeLong(this.requestEndDate != null ? this.requestEndDate.getTime() : -1);
		dest.writeValue(this.totalDays);
		dest.writeString(this.warningMessage);
		dest.writeValue(this.isOneDay);
		dest.writeValue(this.isNew);
		dest.writeValue(this.canUseTickets);
		dest.writeValue(this.personalTicketRequested);*/
		//dest.writeValue(this.familyTicketsRequested);
/*		dest.writeValue(this.maxAllowedFamilyTickets);
		dest.writeValue(this.maxAllowedTickets);
		dest.writeString(this.lastAddressOnLeave);
		dest.writeInt(this.lastCountryOnLeave);
		dest.writeString(this.lastPhoneOnLeave);
		dest.writeString(this.allowedDays);*/
	}

	protected MobLeaveRequestFormBean(Parcel in) {
		this.leaveList = new ArrayList<>();
		in.readList(this.leaveList,HashMap.class.getClassLoader());
		//this.leaveList = new ArrayList<HashMap<String, Object>>();
		//in.readList(this.leaveList, HashMap.class.getClassLoader());
		//this.onBehalfList = new ArrayList<Map<String, Object>>();
		//in.readList(this.onBehalfList, Map<String, Object>.class.getClassLoader());
		//this.calendarBean = in.readParcelable(MobSimpleLeaveCalendarBean.class.getClassLoader());
		//this.leaveInfoBean = in.readParcelable(LeaveInfoBean.class.getClassLoader());
		//this.requestedLeaves = new ArrayList<MobSimpleLeaveCalendarEntity>();
		//in.readList(this.requestedLeaves, MobSimpleLeaveCalendarEntity.class.getClassLoader());
		/*this.leaveTypeName = in.readString();
		this.leaveTypeId = (Integer) in.readValue(Integer.class.getClassLoader());
		this.includeInLeave = (Boolean) in.readValue(Boolean.class.getClassLoader());
		this.showOthersOnLeave = (Boolean) in.readValue(Boolean.class.getClassLoader());
		this.showAddressBox = (Boolean) in.readValue(Boolean.class.getClassLoader());
		this.showDoctorsNote = (Boolean) in.readValue(Boolean.class.getClassLoader());*/
		this.fullName = in.readString();
		//this.idEmployee = (Integer) in.readValue(Integer.class.getClassLoader());
		//this.minRequiredTime = (Integer) in.readValue(Integer.class.getClassLoader());
		/*long tmpRequestStartDate = in.readLong();
		this.requestStartDate = tmpRequestStartDate == -1 ? null : new Date(tmpRequestStartDate);
		long tmpRequestEndDate = in.readLong();
		this.requestEndDate = tmpRequestEndDate == -1 ? null : new Date(tmpRequestEndDate);
		this.totalDays = (Integer) in.readValue(Integer.class.getClassLoader());
		this.warningMessage = in.readString();
		this.isOneDay = (Boolean) in.readValue(Boolean.class.getClassLoader());
		this.isNew = (Boolean) in.readValue(Boolean.class.getClassLoader());
		this.canUseTickets = (Boolean) in.readValue(Boolean.class.getClassLoader());
		this.personalTicketRequested = (Boolean) in.readValue(Boolean.class.getClassLoader());
		//this.familyTicketsRequested = (Boolean) in.readValue(Boolean.class.getClassLoader());
		this.maxAllowedFamilyTickets = (Integer) in.readValue(Integer.class.getClassLoader());
		this.maxAllowedTickets = (Integer) in.readValue(Integer.class.getClassLoader());
		this.lastAddressOnLeave = in.readString();
		this.lastCountryOnLeave = in.readInt();
		this.lastPhoneOnLeave = in.readString();
		this.allowedDays = in.readString();*/
	}

	public static final Parcelable.Creator<MobLeaveRequestFormBean> CREATOR = new Parcelable.Creator<MobLeaveRequestFormBean>() {
		@Override
		public MobLeaveRequestFormBean createFromParcel(Parcel source) {
			return new MobLeaveRequestFormBean(source);
		}

		@Override
		public MobLeaveRequestFormBean[] newArray(int size) {
			return new MobLeaveRequestFormBean[size];
		}
	};
}