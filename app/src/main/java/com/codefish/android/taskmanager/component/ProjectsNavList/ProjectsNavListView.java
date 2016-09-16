package com.codefish.android.taskmanager.component.projectsNavList;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ListView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by abedch on 6/6/2016.
 */
public class ProjectsNavListView extends ListView  {


    ProjectsNavListAdapter listAdapter;


    public ProjectsNavListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(!isInEditMode()){
            if(context==null)
            Log.v("LogListAdapter","context is null");
            else
                Log.v("LogListAdapter","context is not null");
            listAdapter = new ProjectsNavListAdapter(context,"title");
            setAdapter(listAdapter);
        }

    }

    public List<HashMap<String, Object>> getList()
    {
        return listAdapter.mResultList;
    }


}
