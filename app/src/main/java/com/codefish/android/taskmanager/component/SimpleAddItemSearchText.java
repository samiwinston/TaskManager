package com.codefish.android.taskmanager.component;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.codefish.android.taskmanager.R;
import com.codefish.android.taskmanager.component.userListView.SearchableListAdapter;
import com.codefish.android.taskmanager.component.userListView.UserListAdapter;

import java.util.HashMap;
import java.util.List;


/**
 * Created by abedch on 4/8/2016.
 */
public class SimpleAddItemSearchText extends EditText {


    private ListView mListView;
    private CharSequence mLabelField = "name";
    private SearchableListAdapter listAdapter;
    private HashMap<String, Object> mSelectedItem;
    public Integer idTask;
    public IGenericCallBack genericCallBack;
    private Context mContext;

    public SimpleAddItemSearchText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        addTextChangedListener(textChangeListener());
    }

  /*  private void initExtraAttributes(AttributeSet attrs) {
    }*/

    public void initListView(ListView value, List<HashMap<String, Object>> values) {
        mListView = value;
        listAdapter = new SearchableListAdapter(mContext,mLabelField,R.layout.simple_item_layout);
        listAdapter.mAllResults = listAdapter.mResultList = values;
        // the drop down list is a list view
        //listView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite));
        mListView.setAdapter(listAdapter);
        // set on item selected
        mListView.setOnItemClickListener(onItemClickListener());
    }


    private AdapterView.OnItemClickListener onItemClickListener() {
        return new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                mSelectedItem = listAdapter.getItem(position);
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
                if (s.length() > 2) {
                    listAdapter.getFilter().filter(s);
                }
                else if(s.length()==0)
                {
                    listAdapter.mResultList = listAdapter.mAllResults;
                    listAdapter.refresh();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
    }

}



