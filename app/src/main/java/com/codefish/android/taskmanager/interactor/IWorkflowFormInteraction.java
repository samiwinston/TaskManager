package com.codefish.android.taskmanager.interactor;

import com.codefish.android.taskmanager.model.SubmitActionParam;
import com.codefish.android.taskmanager.model.WidgetActionItemBean;
import com.codefish.android.taskmanager.model.hr.MobLeaveRequestFormBean;
import com.codefish.android.taskmanager.presenter.ITaskPresenter;
import com.codefish.android.taskmanager.presenter.IWorkflowFormPresenter;

/**
 * Created by abedch on 2/15/2016.
 */
public interface IWorkflowFormInteraction {


    void submitLeave(MobLeaveRequestFormBean mobLeaveRequestFormBean, IWorkflowFormPresenter workflowFormPresenter);

    void getLeaveBean(SubmitActionParam submitActionParam, IWorkflowFormPresenter workflowFormPresenter);
}
