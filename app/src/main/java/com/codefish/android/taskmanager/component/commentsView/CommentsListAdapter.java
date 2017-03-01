package com.codefish.android.taskmanager.component.commentsView;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.codefish.android.taskmanager.R;
import com.codefish.android.taskmanager.model.GenericCommentBean;
import com.codefish.android.taskmanager.model.ResponseBean;
import com.codefish.android.taskmanager.model.ServiceModel;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by abedch on 4/13/2016.
 */
public class CommentsListAdapter extends BaseAdapter {

    private Context mContext;
    private CharSequence mLabelField;
    public List<GenericCommentBean> mResultList = new ArrayList<>();
    public Integer idWorkflowInstance;
    private SimpleDateFormat dateFormat;

    public CommentsListAdapter(Context context, CharSequence labelField) {
        mContext = context;
        mLabelField = labelField;
        dateFormat = new SimpleDateFormat("MMM dd, yyyy 'at' HH:mm");
    }


    @Override
    public int getCount() {
        return mResultList.size();
    }

    @Override
    public GenericCommentBean getItem(int position) {
        return mResultList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addItem(GenericCommentBean bean)
    {
        mResultList.add(bean);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.comment_item_layout, parent, false);

            holder = new ViewHolder();

            holder.postedByTextView = (TextView) convertView.findViewById(R.id.comment_item_layout_posted_by);
            holder.commentTextView = (TextView) convertView.findViewById(R.id.comment_item_layout_comment);
            holder.dateTextView = (TextView) convertView.findViewById(R.id.comment_item_layout_date_posted);


            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Integer commentType = getItem(position).getCommentType();

        String postedBy = getItem(position).getPostedBy();
        String comment = getItem(position).getText();
        Date datePosted = getItem(position).getDatePosted();


        if(commentType == GenericCommentBean.USER_COMMENT)
        {
            holder.commentTextView.setText(comment);
            holder.commentTextView.setVisibility(View.VISIBLE);
            holder.postedByTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        }
        else
        {
            holder.postedByTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            holder.commentTextView.setVisibility(View.GONE);
            postedBy+=" "+comment;
        }

        //holder.commentTextView.setText(comment);
        holder.postedByTextView.setText(postedBy);
        holder.dateTextView.setText(dateFormat.format(datePosted));


        return convertView;
    }


    public void getComments() {

        List<GenericCommentBean> mockComments = new ArrayList<>();

       /* for (int i = 0; i < 50; i++) {
            mockComments.add(new GenericCommentBean("Test "+i,"posted by "+i,new Date()));
        }

        mResultList.addAll(mockComments);
        notifyDataSetChanged();*/

        ServiceModel.getInstance().taskService.getTaskGenericComments(idWorkflowInstance, 0).enqueue(new Callback<List<GenericCommentBean>>() {
            @Override
            public void onResponse(Call<List<GenericCommentBean>> call, Response<List<GenericCommentBean>> response) {
                if (response.isSuccessful()) {
                        mResultList = response.body();
                        notifyDataSetChanged();
                } else {
                    try {
                        String errorB = response.errorBody().string();
                        Gson gson = new Gson();
                        ResponseBean responseBean = gson.fromJson(errorB, ResponseBean.class);
                        Toast.makeText(mContext, responseBean.description, Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(mContext, mContext.getString(R.string.illegal_error_msg), Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<List<GenericCommentBean>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(mContext, mContext.getString(R.string.illegal_error_msg), Toast.LENGTH_LONG).show();
            }
        });
    }


    static class ViewHolder {
        TextView postedByTextView;
        TextView commentTextView;
        TextView dateTextView;
    }

}

