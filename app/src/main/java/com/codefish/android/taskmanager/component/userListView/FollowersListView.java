package com.codefish.android.taskmanager.component.userListView;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.widget.ListView;
import android.widget.Toast;

import com.codefish.android.taskmanager.component.IGenericCallBack;
import com.codefish.android.taskmanager.model.FollowerBean;
import com.codefish.android.taskmanager.model.LoginModel;
import com.codefish.android.taskmanager.model.ServiceModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by abedch on 5/26/2016.
 */
public class FollowersListView extends ListView implements IFollowerCallBack {

    private FollowerListAdapter listAdapter;
    private Integer idUserTask;

    public FollowersListView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }


    public void init(Integer idUserTask, List<FollowerBean> followers) {
        listAdapter = new FollowerListAdapter(getContext());
        setAdapter(listAdapter);
        listAdapter.mAllResults = followers;
        listAdapter.notifyDataSetChanged();
        listAdapter.followerCallBack = this;
        this.idUserTask = idUserTask;
    }

    public List<FollowerBean> getAdapterList() {
        return listAdapter.mAllResults;
    }

    public void addFollower(FollowerBean bean ) {
        listAdapter.mAllResults.add(0,bean);
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void itemClicked(FollowerBean followerBean) {
        ServiceModel.getInstance()
                .taskService
                .removeFollower(idUserTask, LoginModel.getInstance().getUserBean().getId(), followerBean.getIdAppUser())
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {

                        } else {
                            Toast.makeText(getContext(), "Can not reach CodeFish", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(getContext(), "Can not reach CodeFish", Toast.LENGTH_LONG).show();
                    }
                });



    }

    @Override
    public void followerRemoved(FollowerBean followerBean) {

    }


}
