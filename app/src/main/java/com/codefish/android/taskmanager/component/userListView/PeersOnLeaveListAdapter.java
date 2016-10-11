package com.codefish.android.taskmanager.component.userListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codefish.android.taskmanager.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by abedch on 4/13/2016.
 */
public class PeersOnLeaveListAdapter extends BaseAdapter {

    private Context mContext;
    public List<HashMap<String, Object>> mResultList = new ArrayList<>();
    public List<HashMap<String, Object>> mAllResults = new ArrayList<>();
    private Boolean mListLoadOnInit = false;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");

    public PeersOnLeaveListAdapter(Context context) {
        mContext = context;
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
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.peers_on_leave_list, parent, false);


            /*nameLabel.text=data.name;
            dateTaken.text="From " +ApplicationVariables.instance.dateFormatter.format(data.startDate)+" to " + ApplicationVariables.instance.dateFormatter.format( data.endDate);
            image.source = outerDocument.approved;
            image.visible = (data.status=="Approved")*/

            viewHolder = new ViewHolder();
            viewHolder.employeeView = (TextView) convertView.findViewById(R.id.peers_on_leave_employee);
            viewHolder.approvedView = (ImageView) convertView.findViewById(R.id.peers_on_leave_status);
            viewHolder.durationView = (TextView) convertView.findViewById(R.id.peers_on_leave_duration);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder)convertView.getTag();
        }


        String name = getItem(position).get("name")!=null?getItem(position).get("name").toString():"";
        Boolean isApproved = getItem(position).get("status")!=null?getItem(position).get("status").toString().equals("Approved"):false;
        String duration = getItem(position).get("durationLbl")!=null?getItem(position).get("durationLbl").toString():"";

        viewHolder.employeeView.setText(name);
        viewHolder.approvedView.setVisibility(isApproved?View.VISIBLE:View.INVISIBLE);
        viewHolder.durationView.setText(duration);

        return convertView;
    }



    public void refresh() {
        notifyDataSetChanged();
    }

    static class ViewHolder{
        TextView employeeView;
        ImageView approvedView;
        TextView durationView;

    }

}

