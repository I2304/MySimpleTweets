package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Tweet implements Serializable{

    // list of the attributes
    public String body;
    public long uid;
    public User user;
    public String createdAt;

    // deserialize the JSON
    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();

        // extract the values from JSON
        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        return tweet;
    }

    public static List<Tweet> fromJSON(JSONArray tweetsJSON) {
        List<Tweet> tweets = new ArrayList<>(tweetsJSON.length());
        for (int i = 0; i < tweetsJSON.length(); i++) {
            try {
                JSONObject object = tweetsJSON.getJSONObject(i);
                Tweet tweet = Tweet.fromJSON(object);
                tweets.add(tweet);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return tweets;
    }
}
