package com.codefish.android.taskmanager.model;

import java.util.Date;

/**
 * Created by abedch on 11/2/2016.
 */
public class PostCommentParam {


    public String topic;
    public String subtopic;
    public Integer idAppUser;
    public String comment;
    public Boolean sendMail = false;
    public Date datePosted;
    public Integer[] notifiers;
    //public DocumentBean documentBean;
    public String title;
    public String description;

    public PostCommentParam() {
    }

}
