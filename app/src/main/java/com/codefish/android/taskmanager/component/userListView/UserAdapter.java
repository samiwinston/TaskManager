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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by abedch on 4/13/2016.
 */
public class UserAdapter extends BaseAdapter implements Filterable {

    private Context mContext;
    private CharSequence mLabelField;
    public List<HashMap<String, Object>> mResultList = new ArrayList<>();
    public List<HashMap<String, Object>> mAllResults = new ArrayList<>();

    public UserAdapter(Context context, CharSequence labelField) {
        mContext = context;
        mLabelField = labelField;
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
            convertView = inflater.inflate(R.layout.user_item_layout, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.user_item_layout_name);
            viewHolder.emailTextView = (TextView) convertView.findViewById(R.id.user_item_layout_email);
            viewHolder.initalsTextView = (TextView) convertView.findViewById(R.id.user_item_layout_initials);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        HashMap<String,Object> object = mResultList.get(position);
        String name = object.get("name").toString();
        viewHolder.nameTextView.setText(name);
        viewHolder.emailTextView.setText(object.get("email")!=null?object.get("email").toString():"");
        viewHolder.initalsTextView.setText(name.charAt(0)+""+name.charAt(name.lastIndexOf(' ')+1));


        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    List<HashMap<String, Object>> tempResults = new ArrayList<>();

                    for (HashMap<String, Object> item : mAllResults) {
                        if (item.get(mLabelField).toString().toLowerCase().contains(constraint.toString())) {
                            tempResults.add(item);
                        }


                    }


                    // Assign the data to the FilterResults
                    filterResults.values = tempResults;
                    filterResults.count = tempResults.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null) {
                    mResultList = (List<HashMap<String, Object>>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }

    static class ViewHolder {
        TextView initalsTextView;
        TextView emailTextView;
        TextView nameTextView;


    }

    public void refresh() {
        notifyDataSetChanged();
    }

}

