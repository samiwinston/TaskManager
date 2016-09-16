package com.codefish.android.taskmanager.model;

/**
 * Created by abedch on 9/15/2016.
 */
public class ApiError {

    private int statusCode;
    private String message;

    public ApiError() {
    }

    public int status() {
        return statusCode;
    }

    public String message() {
        return message;
    }
}