package com.codefish.android.taskmanager.fragment;

import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by abedch on 1/31/2017.
 */

public class WebAppInterface {
    WorkflowFormSubmitFragment fragment;
    /** Instantiate the interface and set the context */
    WebAppInterface(WorkflowFormSubmitFragment fragment) {
        this.fragment = fragment;
    }


    @JavascriptInterface
    public void submitSuccess(String toast) {
        Toast.makeText(fragment.getContext(), "Your form has been successfully submitted", Toast.LENGTH_SHORT).show();
        fragment.getActivity().finish();
    }

    @JavascriptInterface
    public void submitError(String toast) {
        Toast.makeText(fragment.getContext(), toast, Toast.LENGTH_SHORT).show();
    }

}