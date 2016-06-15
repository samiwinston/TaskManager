package com.codefish.android.taskmanager.component.userListView;

import android.content.Context;
import android.support.v7.widget.AppCompatImageButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.codefish.android.taskmanager.R;
import com.codefish.android.taskmanager.model.FollowerBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abedch on 5/26/2016.
 */
public class FollowerListAdapter extends BaseAdapter {

    private Context mContext;
    public List<FollowerBean> mAllResults = new ArrayList<>();
    IFollowerCallBack followerCallBack;
    public FollowerListAdapter(Context context) {
        mContext = context;
    }


    @Override
    public int getCount() {
        if(mAllResults!=null)
        return mAllResults.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return mAllResults.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.follower_item_layout, parent, false);

            holder = new ViewHolder();

            holder.nameTextView = (TextView) convertView.findViewById(R.id.follower_item_layout_name);
            holder.emailTextView = (TextView) convertView.findViewById(R.id.follower_item_layout_email);
            holder.initalsTextView = (TextView) convertView.findViewById(R.id.follower_item_layout_initials);
            holder.removeItemBtn = (AppCompatImageButton) convertView.findViewById(R.id.follower_item_layout_remove);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        FollowerBean bean = mAllResults.get(position);

        holder.nameTextView.setText(bean.getName());
        holder.emailTextView.setText(bean.getEmail());
        holder.initalsTextView.setText(bean.getInitials());
        if(!bean.getIsAssignee())
        {
            holder.removeItemBtn.setOnClickListener(onRemoveFollower());
        }
        else
        {
            holder.removeItemBtn.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }


    private View.OnClickListener onRemoveFollower() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View parentRow = (View) v.getParent();
                ListView listView = (ListView) parentRow.getParent();
                final int position = listView.getPositionForView(parentRow);
                FollowerBean bean = mAllResults.get(position);
              if(!bean.getIsAssignee())
              {
                  followerCallBack.itemClicked(bean);
                  mAllResults.remove(position);
                  notifyDataSetChanged();
              }
            }
        };
    }

    static class ViewHolder {
        TextView initalsTextView;
        TextView emailTextView;
        TextView nameTextView;
        AppCompatImageButton removeItemBtn;


    }
}
