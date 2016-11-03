package com.codefish.android.taskmanager.model;

import java.util.Date;

import retrofit2.Converter;

public class GenericCommentBean extends Converter.Factory {


    public static final int USER_COMMENT = 1;
    public static final int USER_STORY = 2;


    private Integer id;
    private Integer idPostedBy;
    private Integer commentType;
    private Date datePosted;
    private String postedBy;
    private String text;
    private String topic;
    private String subtopic;
    private String documentPath;
    private String documentName;
    private String documentExtension;

    public GenericCommentBean() {

    }

    public GenericCommentBean(String text, String postedBy, Date datePosted) {
        this.text = text;
        this.postedBy = postedBy;
/*
        this.datePosted = datePosted;
*/
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdPostedBy() {
        return idPostedBy;
    }

    public void setIdPostedBy(Integer idPostedBy) {
        this.idPostedBy = idPostedBy;
    }

    public Integer getCommentType() {
        return commentType;
    }

    public void setCommentType(Integer commentType) {
        this.commentType = commentType;
    }

    public Date getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSubtopic() {
        return subtopic;
    }

    public void setSubtopic(String subtopic) {
        this.subtopic = subtopic;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentExtension() {
        return documentExtension;
    }

    public void setDocumentExtension(String documentExtension) {
        this.documentExtension = documentExtension;
    }


}
