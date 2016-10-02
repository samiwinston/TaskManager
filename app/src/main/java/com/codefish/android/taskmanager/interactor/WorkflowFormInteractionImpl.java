package com.codefish.android.taskmanager.interactor;

import com.codefish.android.taskmanager.MyApplication;
import com.codefish.android.taskmanager.model.LoginModel;
import com.codefish.android.taskmanager.model.MobAppUserBean;
import com.codefish.android.taskmanager.model.ServiceModel;
import com.codefish.android.taskmanager.model.SubmitActionParam;
import com.codefish.android.taskmanager.model.UserTaskBean;
import com.codefish.android.taskmanager.model.WidgetActionItemBean;
import com.codefish.android.taskmanager.model.hr.MobLeaveRequestFormBean;
import com.codefish.android.taskmanager.presenter.ILoginPresenter;
import com.codefish.android.taskmanager.presenter.ITaskPresenter;
import com.codefish.android.taskmanager.presenter.IWorkflowFormPresenter;
import com.codefish.android.taskmanager.service.IHrService;
import com.codefish.android.taskmanager.service.IUserService;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by abedch on 2/15/2016.
 */
public class WorkflowFormInteractionImpl implements IWorkflowFormInteraction {


    @Inject
    IHrService hrService;

    //@Inject
    //ILoginPresenter loginPresenter;

    public WorkflowFormInteractionImpl() {
        MyApplication.getAppComponent().inject(this);
    }

    @Override
    public void submitLeave(MobLeaveRequestFormBean mobLeaveRequestFormBean, IWorkflowFormPresenter workflowFormPresenter) {

        ServiceModel.getInstance().hrService.submitLeaveRequest(mobLeaveRequestFormBean).enqueue(new Callback<UserTaskBean>() {
            @Override
            public void onResponse(Call<UserTaskBean> call, Response<UserTaskBean> response) {
                if (response.isSuccessful()) {

                } else {

                }
            }

            @Override
            public void onFailure(Call<UserTaskBean> call, Throwable t) {

            }
        });

    }


    @Override
    public void getLeaveBean(final SubmitActionParam submitActionParam, final IWorkflowFormPresenter workflowFormPresenter) {

        hrService.getMobLeaveRequestBean(submitActionParam).enqueue(new Callback<MobLeaveRequestFormBean>() {
            @Override
            public void onResponse(Call<MobLeaveRequestFormBean> call, Response<MobLeaveRequestFormBean> response) {
                if(response.isSuccessful())
                {
                    MobLeaveRequestFormBean bean = response.body();
                    bean.submitParams = submitActionParam;
                    bean.idEmployee = submitActionParam.idAppUser;
                    workflowFormPresenter.getLeaveBeanCBH(bean);
                }
                else {
                    try {
                        if (response.code() == 500 && response.errorBody().contentLength()<500) {
                            workflowFormPresenter.showErrorMsg(response.errorBody().string());
                        } else {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        workflowFormPresenter.showErrorMsg("Illegal error, "+response.code() +" please contact the admin");
                    }
                }
            }

            @Override
            public void onFailure(Call<MobLeaveRequestFormBean> call, Throwable t) {
                workflowFormPresenter.showErrorMsg("Can no reach CodeFish");
            }
        });


    }
}
