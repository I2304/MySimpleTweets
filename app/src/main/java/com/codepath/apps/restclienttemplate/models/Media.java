package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Media implements Serializable{
    public long id;
    public String mediaUrl;
    public String url;

    public static Media fromJSON(JSONObject jsonObject) {
        Media media = null;
        try {
            media = new Media();
            media.id = jsonObject.getLong("id");
            media.mediaUrl = jsonObject.getString("media_url");
            media.url = jsonObject.getString("url");
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return media;
    }

    public static List<Media> fromJSONArray(JSONArray jsonArray) {
        List<Media> medias = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                Media media = fromJSON(jsonArray.getJSONObject(i));
                if (media != null)
                {
                    medias.add(media);
                }
            }
            catch (JSONException e){
                e.printStackTrace();
            }
        }
        return medias;
    }
}
