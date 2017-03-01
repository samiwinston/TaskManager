package com.codefish.android.taskmanager.component.widgetAction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.codefish.android.taskmanager.R;
import com.codefish.android.taskmanager.model.WidgetActionItemBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by abedch on 4/13/2016.
 */
public class WidgetActionTypeAdapter extends BaseAdapter {

    private Context mContext;
    public List<WidgetActionItemBean> mResultList = new ArrayList<>();
    public List<WidgetActionItemBean> mAllResults = new ArrayList<>();
    private Boolean mListLoadOnInit = false;

    public WidgetActionTypeAdapter(Context context) {
        mContext = context;
    }


    @Override
    public int getCount() {
        return mResultList.size();
    }

    @Override
    public WidgetActionItemBean getItem(int position) {
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
            convertView = inflater.inflate(R.layout.widget_action_item_layout, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.descView = (TextView) convertView.findViewById(R.id.widget_action_description);


            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder)convertView.getTag();
        }


        String description = getItem(position).description!=null?getItem(position).description.toString():"";

        viewHolder.descView.setText(description);

        return convertView;
    }



    public void refresh() {
        notifyDataSetChanged();
    }

   private static class ViewHolder{
        TextView descView;


    }

}

