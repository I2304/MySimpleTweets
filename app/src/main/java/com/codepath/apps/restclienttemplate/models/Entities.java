package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Entities implements Serializable{
    public List<Media> media = new ArrayList<>();

    public static Entities fromJSON(JSONObject jsonObject) {
        Entities entities = new Entities();
        JSONArray media = jsonObject.optJSONArray("media");
        if (media != null) {
            entities.media.addAll(Media.fromJSONArray(media));
        }
        return entities;
    }
}
