package com.codefish.android.taskmanager.component.tasksRecyclerView;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by abedch on 2/20/2016.
 */
public class TaskListLayoutManager extends LinearLayoutManager{


    public TaskListLayoutManager(Context context) {
        super(context);
        this.setOrientation(LinearLayoutManager.VERTICAL);

    }
}
