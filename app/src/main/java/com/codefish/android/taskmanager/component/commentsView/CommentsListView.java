package com.codefish.android.taskmanager.component.commentsView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ListView;

import com.codefish.android.taskmanager.model.GenericCommentBean;

import java.util.List;

/**
 * Created by abedch on 6/6/2016.
 */
public class CommentsListView extends ListView {


    CommentsListAdapter listAdapter;


    public CommentsListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            if (context == null)
                Log.v("LogListAdapter", "context is null");
            else
                Log.v("LogListAdapter", "context is not null");
            listAdapter = new CommentsListAdapter(context, "title");
            setAdapter(listAdapter);
        }

    }

    public List<GenericCommentBean> getList() {
        return listAdapter.mResultList;
    }

    public void addItem(GenericCommentBean bean) {
        listAdapter.addItem(bean);
        scrollMyListViewToBottom();
    }

    public void scrollMyListViewToBottom() {
        this.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                setSelection(getCount() - 1);
            }
        });
    }

    public void refreshComments() {
        listAdapter.getComments();
    }

    public void setIdWorkflowInstance(Integer idWorkflowInstance) {
        listAdapter.idWorkflowInstance = idWorkflowInstance;
    }

}
