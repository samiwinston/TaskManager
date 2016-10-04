package com.codefish.android.taskmanager.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageButton;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codefish.android.taskmanager.R;
import com.codefish.android.taskmanager.activity.LoginActivity;
import com.codefish.android.taskmanager.model.MobAppUserBean;
import com.codefish.android.taskmanager.model.ServiceModel;
import com.codefish.android.taskmanager.model.TasksModel;
import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by abedch on 6/1/2016.
 */
public class ProjectNewDialog extends DialogFragment {


    @Bind(R.id.project_new_layout_projectName)
    EditText projectEditText;
    @Bind(R.id.project_new_layout_inputBtn)
    Button inputBtn;


    public static ProjectNewDialog newInstance(Fragment targetFragment,int requestCode) {
        ProjectNewDialog userProfileDialog = new ProjectNewDialog();
        userProfileDialog.setTargetFragment(targetFragment, requestCode);
        return userProfileDialog;


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.project_new_dialog_layout, container, false);
        ButterKnife.bind(this, view);



       /* int width = getResources().getDimensionPixelSize(R.dimen.popup_width);
        int height = getResources().getDimensionPixelSize(R.dimen.popup_height);*/
        //getDialog().getWindow().setLayout(width, height);


        inputBtn.setOnClickListener(createProject());
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        //getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getDialog().getWindow().setLayout(10000, 10000);
        getDialog().getWindow().setGravity(Gravity.CENTER_VERTICAL);

        return view;


    }

    private View.OnClickListener createProject() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (projectEditText.getText().length() > 0)
                {
                    inputBtn.setEnabled(false);
                    ServiceModel.getInstance().taskService.createProject(PreferenceManager.getDefaultSharedPreferences(getContext()).getInt("userId",0),projectEditText.getText().toString()).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if(response.isSuccessful())
                            {
                                Intent intent = new Intent();
                                ((TasksListFragment)getTargetFragment()).refreshProjects();
                                dismiss();
                            }
                            else {
                                try {
                                    if(response.code()==500 && response.errorBody().contentLength()<500)
                                    {
                                        Toast.makeText(getContext(), response.errorBody().string(),Toast.LENGTH_LONG).show();
                                    }
                                    else
                                    {
                                        throw new Exception();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(getContext(),"Illegal Error",Toast.LENGTH_LONG).show();
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getContext(),"Can not reach codefish",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        };
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }
}
