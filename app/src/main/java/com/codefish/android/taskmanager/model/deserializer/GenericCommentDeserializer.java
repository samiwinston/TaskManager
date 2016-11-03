package com.codefish.android.taskmanager.model.deserializer;

import com.codefish.android.taskmanager.model.GenericCommentBean;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Created by abedch on 9/29/2016.
 */

public class GenericCommentDeserializer implements JsonDeserializer<GenericCommentBean> {

    @Override
    public GenericCommentBean deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
            throws JsonParseException {

        //The deserialisation code is missing

        final JsonObject jsonObject = json.getAsJsonObject();

        final JsonElement jsonDatePosted = jsonObject.get("datePosted");
        Long dateInMilli = jsonDatePosted.getAsLong();

        Date datePosted = new Date(dateInMilli);

        final String postedBy = jsonObject.get("postedBy").getAsString();
        final String text = jsonObject.get("text").getAsString();
        final Integer commentType = jsonObject.get("commentType").getAsInt();

        final GenericCommentBean commentBean = new GenericCommentBean();
        commentBean.setPostedBy(postedBy);
        commentBean.setText(text);
        commentBean.setDatePosted(datePosted);
        commentBean.setCommentType(commentType);
        return commentBean;
    }
}

