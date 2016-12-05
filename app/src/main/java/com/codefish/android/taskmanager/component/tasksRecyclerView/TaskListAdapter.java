package com.codefish.android.taskmanager.component.tasksRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codefish.android.taskmanager.R;
import com.codefish.android.taskmanager.component.smartDateView.SmartDateTextView;
import com.codefish.android.taskmanager.model.UserTaskBean;
import com.codefish.android.taskmanager.presenter.ITaskPresenter;
import com.codefish.android.taskmanager.utils.SmartDateFormatter;

import java.util.ArrayList;

/**
 * Created by abedch on 2/20/2016.
 */
public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {


    private ITaskPresenter taskPresenter;
    SmartDateFormatter smartDateFormatter;

    public void setTaskPresenter(ITaskPresenter taskPresenter, SmartDateFormatter smartDateFormatter) {
        this.taskPresenter = taskPresenter;
        this.smartDateFormatter = smartDateFormatter;

    }

    public int currentlySelectedPosition = 0;
    public int previouslySelectedPosition = 0;
    private ArrayList<UserTaskBean> dataSet = new ArrayList<>();

    public TaskListAdapter(ArrayList<UserTaskBean> userTasks) {
        this.dataSet = userTasks;
    }

    private final View.OnClickListener mOnClickListener = new MyOnClickListener();




    // Provide a suitable constructor (depends on the kind of dataset)

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public UserTaskBean bean;
        public TextView taskTitleView;
        public TextView currentStateView;
        public SmartDateTextView dueDateView;
        private ItemClickListener clickListener;

        public ViewHolder(View v) {
            super(v);
            taskTitleView = (TextView) v.findViewById(R.id.task_item_layout_task_title);
            currentStateView = (TextView) v.findViewById(R.id.task_item_layout_current_state);
            dueDateView = (SmartDateTextView) v.findViewById(R.id.task_item_layout_task_due_date);
            itemView.setOnClickListener(this);


        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            // call function with the position
            this.clickListener.onClick(v, getAdapterPosition());
        }
    }


    public void addItem(UserTaskBean taskBean) {
        if (dataSet != null && dataSet.size() >= 0) {
            dataSet.add(0, taskBean);
            this.notifyItemInserted(0);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View container = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item_layout, parent, false);


        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(container);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserTaskBean bean = dataSet.get(position);
        holder.bean = bean;
        holder.taskTitleView.setText(bean.title);
        if (bean.currentState != null && !bean.currentState.equals("Open")) {
            holder.currentStateView.setText(bean.getCurrentStateLbl());
            holder.currentStateView.setVisibility(View.VISIBLE);
        } else {
            holder.currentStateView.setVisibility(View.GONE);

        }

        if (bean.dueDate != null && (!bean.hasForm)) {
            holder.dueDateView.setDate(bean.dueDate);
            holder.dueDateView.setVisibility(View.VISIBLE);
        } else {
            holder.dueDateView.setVisibility(View.GONE);
        }

        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                UserTaskBean bean = dataSet.get(position);
                taskPresenter.selectTask(bean, position);
                previouslySelectedPosition = currentlySelectedPosition;
                currentlySelectedPosition = position;
            }
        });

    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (dataSet != null)
            return dataSet.size();
        return 0;
    }


    public void updateSelectedItem(UserTaskBean userTaskBean) {
        try {
            dataSet.set(currentlySelectedPosition, userTaskBean);
            notifyItemChanged(currentlySelectedPosition);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void removeSelectedItem() {
        try{
            dataSet.remove(currentlySelectedPosition);
            notifyItemRemoved(currentlySelectedPosition);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public ArrayList<UserTaskBean> getDataSet() {
        return dataSet;
    }

    public void setDataSet(ArrayList<UserTaskBean> dataSet) {
        this.dataSet = dataSet;
    }
}
