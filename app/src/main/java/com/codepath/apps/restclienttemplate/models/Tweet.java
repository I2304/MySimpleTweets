package com.codepath.apps.restclienttemplate.models;

import android.text.format.DateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Parcel
public class Tweet implements Serializable{

    // list of the attributes
    public String body;
    public long uid;
    public User user;
    public String createdAt;
    public Entities entities;
    public String relativeTime;
    public boolean favorite;

    // deserialize the JSON
    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();

        // extract the values from JSON
        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        JSONObject entities = jsonObject.optJSONObject("entities");
        tweet.entities = entities == null ? null : Entities.fromJSON(entities);
        tweet.relativeTime = getRelativeTimeAgo(tweet.createdAt);
        tweet.favorite = false;
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

    public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }
}
