package com.codefish.android.taskmanager.model.deserializer;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by abedch on 9/29/2016.
 */

public class MyDeserializer<MobLeaveRequestFormBean> implements JsonDeserializer<MobLeaveRequestFormBean> {


    @Override
    public MobLeaveRequestFormBean deserialize(JsonElement je, Type type, JsonDeserializationContext jdc)
            throws JsonParseException {
        // Get the "content" element from the parsed JSON
        JsonElement content = je.getAsJsonObject().get("fullName");
        content.getAsString();
        // Deserialize it. You use a new instance of Gson to avoid infinite recursion
        // to this deserializer
        return new Gson().fromJson(content, type);

    }

}

