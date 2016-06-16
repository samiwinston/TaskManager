package com.codefish.android.taskmanager.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.codefish.android.taskmanager.R;
import com.codefish.android.taskmanager.component.userListView.UserListAdapter;
import com.codefish.android.taskmanager.model.LoginModel;
import com.codefish.android.taskmanager.model.ServiceModel;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by abedch on 4/8/2016.
 */
public class SearchTagEditText extends EditText implements IGenericCallBack {


    private ListView mListView;
    private CharSequence mLabelField;
    private String mBeanPath;
    private UserListAdapter mSimpleAdapter;
    private Context context;
    private Integer mIdAppUser = LoginModel.getInstance().getUserBean().getId();
    private HashMap<String, Object> mSelectedItem;
    private boolean mIsProgramaticChange = false;
    public IGenericCallBack genericCallBack;


    public SearchTagEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initExtraAttributes(context, attrs);
        addTextChangedListener(textChangeListener());
        setOnFocusChangeListener(onFocusChangeListener());

    }

    private void initExtraAttributes(Context context, AttributeSet attrs) {
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.SearchUserEditText);
        mLabelField = arr.getString(R.styleable.SearchUserEditText_labelField);
        mBeanPath = arr.getString(R.styleable.SearchUserEditText_beanPath);
    }

    public void initListView(ListView value,List<HashMap<String,Object>> values) {
        mListView = value;



        mSimpleAdapter = new UserListAdapter(context);
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
                mIsProgramaticChange = true;
                mSelectedItem = mSimpleAdapter.getItem(position);
                setText(mSelectedItem.get(mLabelField).toString());
                hideList();

                if(genericCallBack!=null)
                {
                    genericCallBack.itemClicked(mSelectedItem);
                }
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
                if (s.length() > 3 && !mIsProgramaticChange) {
                    refreshList(s);
                } else if (mListView.getVisibility() == VISIBLE) {
                    hideList();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                mIsProgramaticChange = false;

            }
        };
    }

    private void refreshList(CharSequence searchText) {
        ServiceModel.getInstance().taskService.getTags(searchText.toString()).enqueue(callBack());
    }

    private Callback<List<HashMap<String, Object>>> callBack(){

        return new Callback<List<HashMap<String, Object>>>() {
            @Override
            public void onResponse(Call<List<HashMap<String, Object>>> call, Response<List<HashMap<String, Object>>> response) {
                if(response.isSuccessful())
                {
                    mSimpleAdapter.mResultList = response.body();
                    mSimpleAdapter.refresh();
                    showList();
                }
            }

            @Override
            public void onFailure(Call<List<HashMap<String, Object>>> call, Throwable t) {
                t.printStackTrace();
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
        if(mSelectedItem!=null && mSelectedItem.get("id")!=null)
        {
            return ((Number) mSelectedItem.get("id")).intValue();
        }
        return null;
    }

    @Override
    public void itemClicked(HashMap<String, Object> item) {

    }
}



