package com.codefish.android.taskmanager.activity;

import com.codefish.android.taskmanager.model.UserTaskBean;
import com.codefish.android.taskmanager.model.hr.MobLeaveRequestFormBean;

import java.util.HashMap;
import java.util.List;

/**
 * Created by abedch on 2/5/2016.
 */
public interface ILeaveFormSummaryView {



    void submitLeaveCBH(UserTaskBean userTaskBean);

    void showErrorMsg(String msg);

    void getPeersOnLeaveCBH(List<HashMap<String, Object>> result);
}
