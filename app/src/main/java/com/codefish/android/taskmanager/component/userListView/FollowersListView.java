package com.codefish.android.taskmanager.component.userListView;

import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.widget.ListView;
import android.widget.Toast;

import com.codefish.android.taskmanager.R;
import com.codefish.android.taskmanager.component.IGenericCallBack;
import com.codefish.android.taskmanager.model.FollowerBean;
import com.codefish.android.taskmanager.model.LoginModel;
import com.codefish.android.taskmanager.model.ResponseBean;
import com.codefish.android.taskmanager.model.ServiceModel;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by abedch on 5/26/2016.
 */
public class FollowersListView extends ListView implements IFollowerCallBack {

    private FollowerListAdapter listAdapter;
    private Integer idWorkflowInstance;

    public FollowersListView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }


    public void init(Integer idWorkflowInstance, List<FollowerBean> followers) {
        listAdapter = new FollowerListAdapter(getContext());
        setAdapter(listAdapter);
        listAdapter.mAllResults = followers;
        listAdapter.notifyDataSetChanged();
        listAdapter.followerCallBack = this;
        this.idWorkflowInstance = idWorkflowInstance;
    }

    public List<FollowerBean> getAdapterList() {
        return listAdapter.mAllResults;
    }

    public void addFollower(FollowerBean bean) {
        if(listAdapter.mAllResults!=null)
        {
            listAdapter.mAllResults.add(0, bean);
            listAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void itemClicked(FollowerBean followerBean) {

        Integer idAppUser = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt("userId", 0);


        ServiceModel.getInstance()
                .taskService
                .removeFollower(idAppUser, idWorkflowInstance, followerBean.getIdAppUser())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {

                        } else {
                            try {
                                String errorB = response.errorBody().string();
                                Gson gson = new Gson();
                                ResponseBean responseBean = gson.fromJson(errorB, ResponseBean.class);
                                Toast.makeText(getContext(), responseBean.description, Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(getContext(), getContext().getString(R.string.illegal_error_msg), Toast.LENGTH_LONG).show();

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getContext(), getContext().getString(R.string.network_error), Toast.LENGTH_LONG).show();
                    }
                });


    }

    @Override
    public void followerRemoved(FollowerBean followerBean) {

    }


}
