package com.codefish.android.taskmanager.interactor;

import com.codefish.android.taskmanager.presenter.ITaskEditPresenter;
import com.codefish.android.taskmanager.presenter.TaskEditPresenterImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by abedch on 5/16/2016.
 */
public interface ITaskEditInteraction {


    void updateDueDate(Integer idTask,Date date,Integer idAppUser, final ITaskEditPresenter taskEditPresenter);
    void getTaskPossibleAssigness(Integer idTask, ITaskEditPresenter taskEditPresenter);
    void reassignTask(Integer idTask, Integer idAppUser, Integer reassignTo, ITaskEditPresenter taskEditPresenter);
    void deleteTask(Integer idAppUser, int idTask, final ITaskEditPresenter taskEditPresenter);
}
