package com.codefish.android.taskmanager.model.hr;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



public class LeaveInfoBean implements Serializable {

	private Double doubleDaysAllowed;
	private Double doubleTotalDaysLeft;
	private Double doubleDaysTaken;
	private Double doubleDaysAllocated;
	private String daysAllowed;
	private String daysAccumulated;
	private String daysTaken;
	private String currentYear;
	
	private String carriedOver;
	private String daysAllocated;
	private String daysScheduled;
	private String additionalDays;
	private String leaveType;
	private String totalLeaveDays;
	private String totalDaysLeft;
	private String allTakenDays;
	
	private String cycleEndDate;
	private String cycleStartDate;
	
	private Integer idCurrentLeaveRequest;
	private Boolean canTakeLeave=false;
	
	private Integer idEmployeeCurrentCycle;
	private Integer idEmployee;
	
	public List<LeaveInfoBean> specialLeaveBalance;
	public boolean hasCycle = true;

	public String warningText;

	public Integer leaveTypeId;

	public Integer leaveRatio = 0;
	public String scheduledLeave;
	
	public LeaveInfoBean(){}

	public Double getDoubleDaysAllowed() {
		return doubleDaysAllowed;
	}

	public void setDoubleDaysAllowed(Double doubleDaysAllowed) {
		this.doubleDaysAllowed = doubleDaysAllowed;
	}

	public Double getDoubleTotalDaysLeft() {
		return doubleTotalDaysLeft;
	}

	public void setDoubleTotalDaysLeft(Double doubleTotalDaysLeft) {
		this.doubleTotalDaysLeft = doubleTotalDaysLeft;
	}

	public Double getDoubleDaysTaken() {
		return doubleDaysTaken;
	}

	public void setDoubleDaysTaken(Double doubleDaysTaken) {
		this.doubleDaysTaken = doubleDaysTaken;
	}

	public Double getDoubleDaysAllocated() {
		return doubleDaysAllocated;
	}

	public void setDoubleDaysAllocated(Double doubleDaysAllocated) {
		this.doubleDaysAllocated = doubleDaysAllocated;
	}

	public String getDaysAllowed() {
		return daysAllowed;
	}

	public void setDaysAllowed(String daysAllowed) {
		this.daysAllowed = daysAllowed;
	}

	public String getDaysAccumulated() {
		return daysAccumulated;
	}

	public void setDaysAccumulated(String daysAccumulated) {
		this.daysAccumulated = daysAccumulated;
	}

	public String getDaysTaken() {
		return daysTaken;
	}

	public void setDaysTaken(String daysTaken) {
		this.daysTaken = daysTaken;
	}

	public String getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(String currentYear) {
		this.currentYear = currentYear;
	}

	public String getCarriedOver() {
		return carriedOver;
	}

	public void setCarriedOver(String carriedOver) {
		this.carriedOver = carriedOver;
	}

	public String getDaysAllocated() {
		return daysAllocated;
	}

	public void setDaysAllocated(String daysAllocated) {
		this.daysAllocated = daysAllocated;
	}

	public String getDaysScheduled() {
		return daysScheduled;
	}

	public void setDaysScheduled(String daysScheduled) {
		this.daysScheduled = daysScheduled;
	}

	public String getAdditionalDays() {
		return additionalDays;
	}

	public void setAdditionalDays(String additionalDays) {
		this.additionalDays = additionalDays;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public String getTotalLeaveDays() {
		return totalLeaveDays;
	}

	public void setTotalLeaveDays(String totalLeaveDays) {
		this.totalLeaveDays = totalLeaveDays;
	}

	public String getTotalDaysLeft() {
		return totalDaysLeft;
	}

	public void setTotalDaysLeft(String totalDaysLeft) {
		this.totalDaysLeft = totalDaysLeft;
	}

	public String getAllTakenDays() {
		return allTakenDays;
	}

	public void setAllTakenDays(String allTakenDays) {
		this.allTakenDays = allTakenDays;
	}

	public String getCycleEndDate() {
		return cycleEndDate;
	}

	public void setCycleEndDate(String cycleEndDate) {
		this.cycleEndDate = cycleEndDate;
	}

	public String getCycleStartDate() {
		return cycleStartDate;
	}

	public void setCycleStartDate(String cycleStartDate) {
		this.cycleStartDate = cycleStartDate;
	}

	public Integer getIdCurrentLeaveRequest() {
		return idCurrentLeaveRequest;
	}

	public void setIdCurrentLeaveRequest(Integer idCurrentLeaveRequest) {
		this.idCurrentLeaveRequest = idCurrentLeaveRequest;
	}

	public Boolean getCanTakeLeave() {
		return canTakeLeave;
	}

	public void setCanTakeLeave(Boolean canTakeLeave) {
		this.canTakeLeave = canTakeLeave;
	}

	public Integer getIdEmployeeCurrentCycle() {
		return idEmployeeCurrentCycle;
	}

	public void setIdEmployeeCurrentCycle(Integer idEmployeeCurrentCycle) {
		this.idEmployeeCurrentCycle = idEmployeeCurrentCycle;
	}

	public Integer getIdEmployee() {
		return idEmployee;
	}

	public void setIdEmployee(Integer idEmployee) {
		this.idEmployee = idEmployee;
	}

	public List<LeaveInfoBean> getSpecialLeaveBalance() {
		return specialLeaveBalance;
	}

	public void setSpecialLeaveBalance(List<LeaveInfoBean> specialLeaveBalance) {
		this.specialLeaveBalance = specialLeaveBalance;
	}

	public boolean isHasCycle() {
		return hasCycle;
	}

	public void setHasCycle(boolean hasCycle) {
		this.hasCycle = hasCycle;
	}

	public String getWarningText() {
		return warningText;
	}

	public void setWarningText(String warningText) {
		this.warningText = warningText;
	}

	public Integer getLeaveTypeId() {
		return leaveTypeId;
	}

	public void setLeaveTypeId(Integer leaveTypeId) {
		this.leaveTypeId = leaveTypeId;
	}

	public Integer getLeaveRatio() {
		return leaveRatio;
	}

	public void setLeaveRatio(Integer leaveRatio) {
		this.leaveRatio = leaveRatio;
	}

	public String getScheduledLeave() {
		return scheduledLeave;
	}

	public void setScheduledLeave(String scheduledLeave) {
		this.scheduledLeave = scheduledLeave;
	}
}
