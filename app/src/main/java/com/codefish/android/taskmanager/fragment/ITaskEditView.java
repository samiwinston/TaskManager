package com.codefish.android.taskmanager.fragment;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by abedch on 5/16/2016.
 */
public interface ITaskEditView {


    void updateDueDateCallBack(Date date);
    void updatePossibleAssignees(List<HashMap<String, Object>> result);
    void showErrorMsg(String msg);
    void unassignTaskCallBack();
    void deleteTaskCallBack();
    void moveToProjectCallBack();
    void updateMyProjects(List<HashMap<String, Object>> result);
    void updateTags(List<HashMap<String, Object>> body);
}
