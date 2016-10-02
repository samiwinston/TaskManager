package com.codefish.android.taskmanager.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.codefish.android.taskmanager.R;
import com.codefish.android.taskmanager.activity.LeaveWorkflowFormActivity;
import com.codefish.android.taskmanager.activity.TaskDetailsActivity;
import com.codefish.android.taskmanager.model.ServiceModel;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by abedch on 5/2/2016.
 */
public class LeaveSummaryStrFieldFragment extends Fragment {

    public static final String ARGS_ITEM = "argItem";
    @Bind(R.id.my_toolbar)
    Toolbar toolbar;
    @Bind(R.id.task_edit_title_layout_title)
    EditText editField;

    private Menu menu;
    private LeaveWorkflowFormActivity leaveWorkflowFormActivity;
    public final static String ARGS_VALUES = "argsvalues";
    private String path;
    private String title;
    private Integer requestCode;
    private boolean isSaveVisible;

    public static LeaveSummaryStrFieldFragment newInstance(Fragment targetFragment, Integer requestCode, String title, String path, String value) {

        LeaveSummaryStrFieldFragment fragment = new LeaveSummaryStrFieldFragment();
        fragment.setTargetFragment(targetFragment, requestCode);
        Bundle args = new Bundle();
        args.putString(ARGS_VALUES, value);
        fragment.setArguments(args);
        fragment.path = path;
        fragment.title = title;
        fragment.requestCode = requestCode;

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_edit_string_layout, container, false);
        ButterKnife.bind(this, view);
        initToolBar();


        editField.setText((String) getArguments().get(ARGS_VALUES));
        editField.addTextChangedListener(onTextChangeListener());


        return view;
    }

    private TextWatcher onTextChangeListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isSaveVisible = s.length() > 0;
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        leaveWorkflowFormActivity = (LeaveWorkflowFormActivity) context;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }


    private void initToolBar() {
        toolbar.setTitle(title);
        leaveWorkflowFormActivity.setSupportActionBar(toolbar);

        ActionBar supportActionBar = leaveWorkflowFormActivity.getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setDisplayShowHomeEnabled(true);
        supportActionBar.setDisplayShowTitleEnabled(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if(isSaveVisible)
        {
            showSaveOption();
        }
        else
        {
            hideSaveOption();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.task_edit_title, menu);
        this.menu = menu;
    }


    private void hideSaveOption() {
        MenuItem item = menu.findItem(R.id.menu_item_save);
        item.setVisible(false);
    }

    private void showSaveOption() {
        MenuItem item = menu.findItem(R.id.menu_item_save);
        item.setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    getFragmentManager().popBackStack();
                }
                return true;
            case R.id.menu_item_save:
                updateTaskField();
                item.setEnabled(false);
                View view = getActivity().getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateTaskField() {

        // return to fragment and update related field

    }


}
