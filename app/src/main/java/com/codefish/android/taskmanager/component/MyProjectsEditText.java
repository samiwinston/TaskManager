package com.codefish.android.taskmanager.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.codefish.android.taskmanager.R;
import com.codefish.android.taskmanager.component.userListView.SearchableListAdapter;
import com.codefish.android.taskmanager.model.LoginModel;
import com.codefish.android.taskmanager.model.ServiceModel;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by abedch on 4/8/2016.
 */
public class MyProjectsEditText extends EditText {


    private ListView mListView;
    private CharSequence mLabelField;
    private SearchableListAdapter mSimpleAdapter;
    private Context context;
    private Integer mIdAppUser;
    private HashMap<String, Object> mSelectedItem;

    public MyProjectsEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        if(!isInEditMode())
        {
            mIdAppUser = PreferenceManager.getDefaultSharedPreferences(context).getInt("userId",0);
            initExtraAttributes(context, attrs);
            addTextChangedListener(textChangeListener());
            setOnFocusChangeListener(onFocusChangeListener());
            refreshList();
        }

    }

    private void initExtraAttributes(Context context, AttributeSet attrs) {
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.SearchUserEditText);
        mLabelField = "title";
    }

    public void initListView(ListView value,List<HashMap<String,Object>> values) {
        mListView = value;
        mSimpleAdapter = new SearchableListAdapter(context,mLabelField,R.layout.project_item_layout);
        mSimpleAdapter.mAllResults = values;
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
                hideList();
            }
        };
    }

    private OnFocusChangeListener onFocusChangeListener() {
        return new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus ) {
                    hideList();
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
                if (s.length() > 2) {
                    mSimpleAdapter.getFilter().filter(s);
                    showList();
                    //refreshList(s);
                    // filter data and then open
                } else if (mListView.getVisibility() == VISIBLE) {
                    hideList();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
    }



    private void refreshList() {
       ServiceModel.getInstance().projectService.getMyProjects(mIdAppUser,false,false).enqueue(callBack());
    }

    private Callback<List<HashMap<String, Object>>> callBack(){

        return new Callback<List<HashMap<String, Object>>>() {
            @Override
            public void onResponse(Call<List<HashMap<String, Object>>> call, Response<List<HashMap<String, Object>>> response) {
                if(response.isSuccessful())
                {
                    mSimpleAdapter.mAllResults = response.body();
                }
                else
                {
                    try {
                        if(response.code()==500 && response.errorBody().contentLength()<200)
                        {
                            Toast.makeText(getContext(),  response.errorBody().string(), Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Illegal error, please contact the admin", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<HashMap<String, Object>>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext(), "Can not reach Codefish", Toast.LENGTH_LONG).show();
            }
        };


    }


    public void showList() {
        if(mListView.getVisibility()!=VISIBLE)
        mListView.setVisibility(VISIBLE);
    }

    public void hideList() {
        if(mListView.getVisibility()!=GONE)
            mListView.setVisibility(GONE);
    }


    public HashMap<String, Object> getmSelectedItem() {
        return mSelectedItem;
    }

    public void setmSelectedItem(HashMap<String, Object> mSelectedItem) {
        this.mSelectedItem = mSelectedItem;
    }

    public Integer getIdSelectedItem(){
        if(mSelectedItem!=null &&mSelectedItem.get("id")!=null)
        {
            return ((Number) mSelectedItem.get("id")).intValue();
        }
        return null;
    }


}



