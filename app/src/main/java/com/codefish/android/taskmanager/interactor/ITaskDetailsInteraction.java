package com.codefish.android.taskmanager.interactor;

import com.codefish.android.taskmanager.model.GetTaskParameter;
import com.codefish.android.taskmanager.model.SubmitActionParam;
import com.codefish.android.taskmanager.presenter.ITaskDetailsPresenter;
import com.codefish.android.taskmanager.presenter.ITaskPresenter;
import com.codefish.android.taskmanager.presenter.TaskDetailsPresenterImpl;

import java.util.Date;

/**
 * Created by abedch on 5/25/2016.
 */
public interface ITaskDetailsInteraction {
    void deleteTask(Integer idAppUser, int idTask, ITaskDetailsPresenter taskDetailsPresenter);

    void updateDueDate(Integer idTask, Date date, Integer idAppUser, ITaskDetailsPresenter taskDetailsPresenter);

    void removeDueDate(Integer idTask, Integer idAppUser, ITaskDetailsPresenter taskDetailsPresenter);

    void getTask(GetTaskParameter param, ITaskDetailsPresenter taskDetailsPresenter);
    void changeState(SubmitActionParam submitParams, ITaskDetailsPresenter taskDetailsPresenter);
    void updateImportance(Integer idAppUser, Integer idUserTask, Integer importance, ITaskDetailsPresenter taskDetailsPresenter);
    void getWorkflowForm(Integer idAppUser, Integer idWorkflowInstance,ITaskDetailsPresenter taskDetailsPresenter);
}

