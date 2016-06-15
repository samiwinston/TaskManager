package com.codefish.android.taskmanager.component;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.codefish.android.taskmanager.component.userListView.UserAdapter;
import com.codefish.android.taskmanager.model.ServiceModel;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by abedch on 4/8/2016.
 */
public class AssigneesSearchText extends TextView {


    private ListView mListView;
    private CharSequence mLabelField;
    private UserAdapter mSimpleAdapter;
    private Context context;
    private HashMap<String, Object> mSelectedItem;
    public Integer idTask;
    public IGenericCallBack genericCallBack;

    public AssigneesSearchText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initExtraAttributes(context, attrs);
        addTextChangedListener(textChangeListener());
    }

    private void initExtraAttributes(Context context, AttributeSet attrs) {
        mLabelField = "name";
    }

    public void initListView(ListView value, List<HashMap<String, Object>> values) {
        mListView = value;
        mSimpleAdapter = new UserAdapter(context, mLabelField);
        mSimpleAdapter.mAllResults = mSimpleAdapter.mResultList = values;
        // the drop down list is a list view
        //listView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite));
        mListView.setAdapter(mSimpleAdapter);
        // set on item selected
        mListView.setOnItemClickListener(onItemClickListener());
    }


    private AdapterView.OnItemClickListener onItemClickListener() {
        return new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                mSelectedItem = mSimpleAdapter.getItem(position);
                setText(mSelectedItem.get(mLabelField).toString());
                if (genericCallBack != null) {
                    genericCallBack.itemClicked(mSelectedItem);
                }
            }
        };
    }


    private TextWatcher textChangeListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 3) {
                    mSimpleAdapter.getFilter().filter(s);
                    //refreshList(s);
                    // filter data and then open
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
    }


    private void refreshList() {
        ServiceModel.getInstance().taskService.getTaskPossibleAssignees(idTask).enqueue(callBack());
    }

    private Callback<List<HashMap<String, Object>>> callBack() {

        return new Callback<List<HashMap<String, Object>>>() {
            @Override
            public void onResponse(Call<List<HashMap<String, Object>>> call, Response<List<HashMap<String, Object>>> response) {
                if (response.isSuccessful()) {
                    mSimpleAdapter.mAllResults = response.body();
                }
            }

            @Override
            public void onFailure(Call<List<HashMap<String, Object>>> call, Throwable t) {
                t.printStackTrace();
            }
        };


    }


    public void showList() {
        if (mListView.getVisibility() != VISIBLE)
            mListView.setVisibility(VISIBLE);
    }

    public void hideList() {
        if (mListView.getVisibility() != GONE)
            mListView.setVisibility(GONE);
    }


    public HashMap<String, Object> getmSelectedItem() {
        return mSelectedItem;
    }

    public void setmSelectedItem(HashMap<String, Object> mSelectedItem) {
        this.mSelectedItem = mSelectedItem;
    }

    public Integer getIdSelectedItem() {
        if (mSelectedItem != null && mSelectedItem.get("id") != null) {
            return ((Number) mSelectedItem.get("id")).intValue();
        }
        return null;
    }


}



