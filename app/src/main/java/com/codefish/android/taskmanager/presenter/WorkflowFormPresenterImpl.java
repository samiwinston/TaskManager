package com.codefish.android.taskmanager.presenter;

import com.codefish.android.taskmanager.activity.ILeaveFormSummaryView;
import com.codefish.android.taskmanager.activity.ILeaveWorkflowFormView;
import com.codefish.android.taskmanager.activity.ILoginView;
import com.codefish.android.taskmanager.interactor.ILoginInteraction;
import com.codefish.android.taskmanager.interactor.IWorkflowFormInteraction;
import com.codefish.android.taskmanager.model.SubmitActionParam;
import com.codefish.android.taskmanager.model.UserTaskBean;
import com.codefish.android.taskmanager.model.WidgetActionItemBean;
import com.codefish.android.taskmanager.model.hr.MobLeaveRequestFormBean;

/**
 * Created by abedch on 2/9/2016.
 */
public class WorkflowFormPresenterImpl implements IWorkflowFormPresenter {


    IWorkflowFormInteraction workflowFormInteraction;

    ILeaveWorkflowFormView leaveWorkflowFormView;
    ILeaveFormSummaryView leaveFormSummaryView;

    public WorkflowFormPresenterImpl(IWorkflowFormInteraction workflowFormInteraction) {
        this.workflowFormInteraction = workflowFormInteraction;
    }


    @Override
    public void getWorkflowBeanByAction(Integer idAppUser, WidgetActionItemBean widgetActionItemBean) {

        SubmitActionParam submitActionParam = new SubmitActionParam();
        submitActionParam.workflowName = widgetActionItemBean.workflowName;
        submitActionParam.idAppUser = idAppUser;
        submitActionParam.parentWorkflow = null;
        submitActionParam.idBaseEntity = 0;

        //taskInteraction.getWorkflowBeanFromAction(submitActionParam, this);
        workflowFormInteraction.getLeaveBean(submitActionParam, this);
    }

    @Override
    public void submitLeave(MobLeaveRequestFormBean mobLeaveRequestFormBean) {
        workflowFormInteraction.submitLeave(mobLeaveRequestFormBean,this);
    }

    @Override
    public void showErrorMsg(String msg) {
        leaveWorkflowFormView.showErrorMsg(msg);
    }

    @Override
    public void showErrorMsgInSummary(String msg) {
        leaveFormSummaryView.showErrorMsg(msg);
    }

    @Override
    public void getLeaveBeanCBH(MobLeaveRequestFormBean bean) {
        leaveWorkflowFormView.getLeaveBeanCBH(bean);
    }

    @Override
    public void submitLeaveCBH(UserTaskBean userTaskBean) {
        leaveFormSummaryView.submitLeaveCBH(userTaskBean);
    }

    @Override
    public void setLeaveWorkflowFormView(ILeaveWorkflowFormView leaveWorkflowFormView) {
        this.leaveWorkflowFormView = leaveWorkflowFormView;
    }

    @Override
    public void setLeaveFormSummaryView(ILeaveFormSummaryView leaveFormSummaryView) {
        this.leaveFormSummaryView = leaveFormSummaryView;
    }
}
