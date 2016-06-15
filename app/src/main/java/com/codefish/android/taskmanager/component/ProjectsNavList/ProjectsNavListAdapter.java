package com.codefish.android.taskmanager.component.projectsNavList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.codefish.android.taskmanager.R;
import com.codefish.android.taskmanager.model.LoginModel;
import com.codefish.android.taskmanager.model.ServiceModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by abedch on 4/13/2016.
 */
public class ProjectsNavListAdapter extends BaseAdapter {

    private Context mContext;
    private CharSequence mLabelField;
    public List<HashMap<String, Object>> mResultList = new ArrayList<>();

    public ProjectsNavListAdapter(Context context, CharSequence labelField) {
        mContext = context;
        mLabelField = labelField;
        getMyProjects();
    }


    @Override
    public int getCount() {
        return mResultList.size();
    }

    @Override
    public HashMap<String, Object> getItem(int position) {
        return mResultList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.project_nav_item_layout, parent, false);
        }
        ((TextView) convertView.findViewById(R.id.text1)).setText(getItem(position).get(mLabelField).toString());
        return convertView;
    }


    private void getMyProjects() {

        HashMap<String, Object> myListObj = new HashMap<>();
        myListObj.put("title", "My Tasks");
        mResultList.add(myListObj);

        ServiceModel.getInstance().taskService.getMyProjects(LoginModel.getInstance().getUserBean().getId(), false, false).enqueue(new Callback<List<HashMap<String, Object>>>() {
            @Override
            public void onResponse(Call<List<HashMap<String, Object>>> call, Response<List<HashMap<String, Object>>> response) {
                if (response.isSuccessful()) {
                    mResultList.addAll(response.body());
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(mContext, "Can not reach CodeFish", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<HashMap<String, Object>>> call, Throwable t) {
                Toast.makeText(mContext, "Can not reach CodeFish", Toast.LENGTH_LONG).show();
            }
        });
    }

}

