package com.codefish.android.taskmanager.component.projectsNavList;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ListView;

import java.util.Date;
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

    public void refreshProjects()
    {
        listAdapter.getMyProjects(true);
    }

    public void updateDueDate(Integer idProject, Date dueDate)
    {
        for (HashMap<String,Object> project:listAdapter.mResultList)
        {
            Double id = (Double) project.get("id");

            if(id!=null && id == idProject.doubleValue())
            {
                project.put("dueDate",dueDate!=null?((Long)dueDate.getTime()).doubleValue():null);
                break;
            }
        }
    }

    public List<HashMap<String, Object>> getList()
    {
        return listAdapter.mResultList;
    }


}
