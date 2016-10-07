package com.codefish.android.taskmanager.component.tasksRecyclerView;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

/**
 * Created by abedch on 2/20/2016.
 */
public class TaskListLayoutManager extends LinearLayoutManager{


    public TaskListLayoutManager(Context context) {
        super(context);
        this.setOrientation(LinearLayoutManager.VERTICAL);
    }

    public TaskListLayoutManager(Context context, AttributeSet attrs, int defStyleAttr,int defStyleRes){

        super(context, attrs, defStyleAttr, defStyleRes);
        this.setOrientation(LinearLayoutManager.VERTICAL);

    }

    //(android.content.Context, android.util.AttributeSet, int, int).


}
