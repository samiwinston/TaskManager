package com.codefish.android.taskmanager.model.hr;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class SimpleLeaveRequestFormBean implements ICustomFormBean  {

	public SimpleLeaveRequestFormBean() {

	}


	public List<HashMap<String, Object>> leaveList;

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




}
