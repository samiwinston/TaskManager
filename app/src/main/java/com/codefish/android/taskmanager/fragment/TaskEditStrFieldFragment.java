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
public class TaskEditStrFieldFragment extends Fragment {

    public static final String ARGS_ITEM = "argItem";
    @Bind(R.id.my_toolbar)
    Toolbar toolbar;
    @Bind(R.id.task_edit_title_layout_title)
    EditText editField;

    private Menu menu;
    private TaskDetailsActivity taskDetailsActivity;
    public final static String ARGS_VALUES = "argsvalues";
    private String path;
    private String title;
    private Integer requestCode;
    private boolean isSaveVisible;

    public static TaskEditStrFieldFragment newInstance(Fragment targetFragment, Integer requestCode, String title, String path, String value) {

        TaskEditStrFieldFragment fragment = new TaskEditStrFieldFragment();
        fragment.setTargetFragment(targetFragment, requestCode);
        Bundle args = new Bundle();
        args.putString(ARGS_VALUES, value);
        args.putString("title",title);
        args.putString("path",path);
        fragment.setArguments(args);
        fragment.requestCode = requestCode;

        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("path",getArguments().getString(path));
        outState.putString("title",getArguments().getString(title));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState!=null)
        {
            title = savedInstanceState.getString("title");
            path = savedInstanceState.getString("path");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_edit_string_layout, container, false);
        ButterKnife.bind(this, view);
        initToolBar();


        if(savedInstanceState!=null)
        {
            title = savedInstanceState.getString("title");
            path = savedInstanceState.getString("path");
        }
        else
        {
            editField.setText((String) getArguments().get(ARGS_VALUES));
            title = getArguments().getString("title");
            path = getArguments().getString("path");
        }




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


    /*@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        taskDetailsActivity = (TaskDetailsActivity) activity;
    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        taskDetailsActivity = (TaskDetailsActivity) context;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }


    private void initToolBar() {
        if(title!=null)
        toolbar.setTitle(title);
        else
        {
            toolbar.setTitle(getArguments().getString("title"));
        }
        taskDetailsActivity.setSupportActionBar(toolbar);

        ActionBar supportActionBar = taskDetailsActivity.getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setDisplayShowHomeEnabled(true);
        supportActionBar.setDisplayShowTitleEnabled(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if(menu!=null)
            this.menu = menu;

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
        inflater.inflate(R.menu.edit_field_menu, menu);
        this.menu = menu;
    }



    private void hideSaveOption() {
        MenuItem item = menu.findItem(R.id.edit_field_menu_item_save);
        item.setVisible(false);
    }

    private void showSaveOption() {
        MenuItem item = menu.findItem(R.id.edit_field_menu_item_save);
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
            case R.id.edit_field_menu_item_save:
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



        if (editField.getText().length() > 0) {
            ServiceModel.getInstance().taskService.updateTaskField(taskDetailsActivity.selectedTask.idWorkflowInstance, path, editField.getText().toString(), false).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        if (getFragmentManager()!=null && getFragmentManager().getBackStackEntryCount() > 0) {
                            Intent intent = new Intent();
                            intent.putExtra(ARGS_ITEM, editField.getText().toString());
                            getTargetFragment().onActivityResult(requestCode, Activity.RESULT_OK, intent);
                            getFragmentManager().popBackStack();
                        }
                    } else {
                        try {
                            if(response.code()==500 && response.errorBody().contentLength()<500)
                            {
                                Toast.makeText(getContext(),  response.errorBody().string(), Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                throw new Exception();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Illegal error "+response.code()+", please contact the admin", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(getContext(), "Can not reach CodeFish", Toast.LENGTH_LONG).show();
                }
            });

        }
    }


}
