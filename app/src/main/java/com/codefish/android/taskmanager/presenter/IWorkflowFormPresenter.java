package com.codefish.android.taskmanager.presenter;


import com.codefish.android.taskmanager.activity.ILeaveFormSummaryView;
import com.codefish.android.taskmanager.activity.ILeaveWorkflowFormView;
import com.codefish.android.taskmanager.model.UserTaskBean;
import com.codefish.android.taskmanager.model.WidgetActionItemBean;
import com.codefish.android.taskmanager.model.hr.MobLeaveRequestFormBean;

/**
 * Created by abedch on 2/2/2016.
 */
public interface IWorkflowFormPresenter {


    void getWorkflowBeanByAction(Integer idAppUser, WidgetActionItemBean widgetActionItemBean);

    void submitLeave(MobLeaveRequestFormBean mobLeaveRequestFormBean);

    void showErrorMsg(String msg);

    void getLeaveBeanCBH(MobLeaveRequestFormBean bean);


    void submitLeaveCBH(UserTaskBean userTaskBean);

    void setLeaveWorkflowFormView(ILeaveWorkflowFormView leaveWorkflowFormView);

    void setLeaveFormSummaryView(ILeaveFormSummaryView leaveFormSummaryView);
}
