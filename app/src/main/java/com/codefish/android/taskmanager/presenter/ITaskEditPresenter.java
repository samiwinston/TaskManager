package com.codefish.android.taskmanager.presenter;

import com.codefish.android.taskmanager.fragment.ITaskEditView;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by abedch on 5/16/2016.
 */
public interface ITaskEditPresenter {

    void updateDueDate(Integer idTask,Date date);
    void reassignTask(Integer idTask, Integer reassignTo);
    void unassignTask(Integer idTask);
    void updateAssignee(Integer idTask, Integer idAssignee);
    void getTaskPossibleAssignees(Integer itTask);
    void setTaskEditView(ITaskEditView taskEditView);
    void updateDueCallBack(Date date);
    void updatePossibleAssignees(List<HashMap<String,Object>> result);
    void reassignTaskCallBack();
    void showErrorMsg(String msg);
    void unassignTaskCallBack();
    void deleteTask(int idTask);
    void deleteTaskCallBack();
}
