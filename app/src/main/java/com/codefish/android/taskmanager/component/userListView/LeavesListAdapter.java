package com.codefish.android.taskmanager.component.userListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.codefish.android.taskmanager.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by abedch on 4/13/2016.
 */
public class LeavesListAdapter extends BaseAdapter {

    private Context mContext;
    private CharSequence mLabelField;
    public List<HashMap<String, Object>> mResultList = new ArrayList<>();
    public List<HashMap<String, Object>> mAllResults = new ArrayList<>();
    private int mIdLayout;
    private Boolean mListLoadOnInit = false;

    public LeavesListAdapter(Context context, CharSequence labelField,int idLayout) {
        mContext = context;
        mLabelField = labelField;
        this.mIdLayout = idLayout;
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
            convertView = inflater.inflate(R.layout.leave_type_list, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.typeView = (TextView) convertView.findViewById(R.id.leave_type_list_type);
            viewHolder.daysTakenView = (TextView) convertView.findViewById(R.id.leave_type_daysTaken);
            viewHolder.descView = (TextView) convertView.findViewById(R.id.leave_type_description);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder)convertView.getTag();
        }


        String type = getItem(position).get("leaveTypeName")!=null?getItem(position).get("leaveTypeName").toString():"";
        String daysTaken = getItem(position).get("totalDays")!=null?getItem(position).get("totalDays").toString():"";
        String desc = getItem(position).get("description")!=null?getItem(position).get("description").toString():"";

        viewHolder.typeView.setText(type);
        viewHolder.daysTakenView.setText(daysTaken);
        viewHolder.descView.setText(desc);

        return convertView;
    }



    public void refresh() {
        notifyDataSetChanged();
    }

    static class ViewHolder{
        TextView typeView;
        TextView daysTakenView;
        TextView descView;

    }

}

