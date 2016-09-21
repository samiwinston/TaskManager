package com.codefish.android.taskmanager.fragment;

import android.app.Dialog;
import android.content.Context;
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
import android.view.WindowManager;
import android.widget.TextView;

import com.codefish.android.taskmanager.R;
import com.codefish.android.taskmanager.activity.LoginActivity;
import com.codefish.android.taskmanager.model.LoginModel;
import com.codefish.android.taskmanager.model.MobAppUserBean;
import com.codefish.android.taskmanager.model.MobUserTaskBean;
import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by abedch on 6/1/2016.
 */
public class UserProfileDialog extends DialogFragment {


    @Bind(R.id.user_profile_layout_username)
    TextView userNameView;
    @Bind(R.id.user_profile_layout_initials)
    TextView userInitialsView;
    @Bind(R.id.user_profile_layout_logout_button)
    AppCompatImageButton logOutBtn;

    MobAppUserBean bean;


    public static UserProfileDialog newInstance(Fragment targetFragment){
        UserProfileDialog userProfileDialog = new UserProfileDialog();
        userProfileDialog.setTargetFragment(targetFragment,34);
        return userProfileDialog;


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Gson gson = new Gson();
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String json = mPrefs.getString("MobAppUserBean", "");
        MobAppUserBean obj = gson.fromJson(json, MobAppUserBean.class);
        bean = obj;


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_profile_dialog_layout, container, false);
        ButterKnife.bind(this, view);

        if (bean!=null) {
            userNameView.setText(bean.getName());
            userInitialsView.setText(bean.getInitials());
        }

       /* int width = getResources().getDimensionPixelSize(R.dimen.popup_width);
        int height = getResources().getDimensionPixelSize(R.dimen.popup_height);*/
        //getDialog().getWindow().setLayout(width, height);


        logOutBtn.setOnClickListener(onLogOutClick());
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        //getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getDialog().getWindow().setLayout(10000, 10000);
        getDialog().getWindow().setGravity(Gravity.TOP);

        return view;


    }

    private View.OnClickListener onLogOutClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toLogin = new Intent(getContext(), LoginActivity.class);
                toLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getActivity().startActivity(toLogin);
                getActivity().finish();

                SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();
            }
        };
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }
}
