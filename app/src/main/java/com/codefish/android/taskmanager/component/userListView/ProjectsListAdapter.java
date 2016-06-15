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
public class ProjectsListAdapter extends BaseAdapter implements Filterable  {

    private Context mContext;
    private CharSequence mLabelField;
    public List<HashMap<String, Object>> mResultList = new ArrayList<>();
    public List<HashMap<String, Object>> mAllResults = new ArrayList<>();

    public ProjectsListAdapter(Context context, CharSequence labelField) {
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
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.simple_item_layout, parent, false);
        }
        ((TextView) convertView.findViewById(R.id.text1)).setText(getItem(position).get(mLabelField).toString());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    List<HashMap<String,Object>> tempResults = new ArrayList<>();

                    for (HashMap<String,Object> item:mAllResults) {
                        if (item.get(mLabelField).toString().toLowerCase().contains(constraint.toString()))
                        {
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
                if (results != null ) {
                    mResultList = (List<HashMap<String,Object>>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }};
        return filter;
    }


/*    class LabelFieldPredicate implements Predicate<HashMap<String,Object>> {

        @Override
        public boolean apply(@Nullable HashMap<String,Object> file) {
            return Files.getFileExtension(file.getName()).equalsIgnoreCase("mkv");
        }
    }*/

}

