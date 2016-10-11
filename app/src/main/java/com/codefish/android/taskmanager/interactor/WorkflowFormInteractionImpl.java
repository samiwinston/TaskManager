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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import okhttp3.ResponseBody;
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
    public void getPeersOnLeave(Integer idAppUser, Date startDate, Date endDate, final IWorkflowFormPresenter workflowFormPresenter) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String startDateStr = dateFormat.format(startDate);
        String endDateStr = dateFormat.format(endDate);

        ServiceModel.getInstance().hrService.getEmployeesOnLeaveDuringDates(idAppUser, startDateStr, endDateStr).enqueue(new Callback<List<HashMap<String,Object>>>() {
            @Override
            public void onResponse(Call<List<HashMap<String,Object>>> call, Response<List<HashMap<String,Object>>> response) {
                if (response.isSuccessful()) {
                    workflowFormPresenter.getPeersOnLeaveCBH(response.body());
                } else {
                    try {
                        if (response.code() == 500 && response.errorBody().contentLength() < 500) {
                            workflowFormPresenter.showErrorMsgInSummary(response.errorBody().string());
                        } else {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        workflowFormPresenter.showErrorMsgInSummary("Illegal error, " + response.code() + " please contact the admin");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<HashMap<String,Object>>> call, Throwable t) {
                workflowFormPresenter.showErrorMsgInSummary("Can no reach CodeFish");
            }
        });
    }

    @Override
    public void submitLeave(MobLeaveRequestFormBean mobLeaveRequestFormBean,final IWorkflowFormPresenter workflowFormPresenter) {

        ServiceModel.getInstance().hrService.submitLeaveRequest(mobLeaveRequestFormBean).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    workflowFormPresenter.submitLeaveCBH(null);
                }  else {
                    try {
                        if (response.code() == 500 && response.errorBody().contentLength()<500) {
                            workflowFormPresenter.showErrorMsgInSummary(response.errorBody().string());
                        } else {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        workflowFormPresenter.showErrorMsgInSummary("Illegal error, "+response.code() +" please contact the admin");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                workflowFormPresenter.showErrorMsgInSummary("Can no reach CodeFish");
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
