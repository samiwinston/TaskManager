package com.codefish.android.taskmanager.model.hr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by abedch on 9/29/2016.
 */

public class CalendarEntity {


    public int id;
    public List<MobCalendarEntityItem> entityItemsArray;
    public String color;
    public Date date;

    public  CalendarEntity()
    {
        entityItemsArray = new ArrayList<>();
    }

    public void addAll(CalendarEntity entity){
        if (entity.entityItemsArray!=null){
            for (MobCalendarEntityItem item:entity.entityItemsArray){
                entityItemsArray.add(item);
            }
        }

    }


}
