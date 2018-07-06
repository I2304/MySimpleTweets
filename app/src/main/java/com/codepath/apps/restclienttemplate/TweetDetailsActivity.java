package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Entities;
import com.codepath.apps.restclienttemplate.models.Media;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class TweetDetailsActivity extends AppCompatActivity {

    TwitterClient client;
    Context context;
    Tweet tweet;
    public static final int REQUEST_CODE = 1;
    ImageView ivProfileImage;
    TextView tvUserName;
    TextView tvBody;
    ImageView ivMedia;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_details);
        context = this;
        client = TwitterApplication.getRestClient(context);
        tweet = (Tweet) getIntent().getSerializableExtra("tweet");
        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        ivMedia = (ImageView) findViewById(R.id.ivMedia);
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvBody = (TextView) findViewById(R.id.tvBody);
        progressBar = (ProgressBar) findViewById(R.id.pbLoading);
        tvUserName.setText(tweet.user.name);
        tvBody.setText(tweet.body);
        Entities entities = tweet.entities;
        String mediaUrl = null;
        if (entities != null) {
            List<Media> media = entities.media;
            if (!media.isEmpty()) {
                mediaUrl = media.get(0).mediaUrl;
            }
        }
        if (mediaUrl != null) {
            Glide.with(context).load(mediaUrl).into(ivMedia);
            ivMedia.setVisibility(View.VISIBLE);
        }
        else {
            ivMedia.setVisibility(View.GONE);
        }
        Glide.with(context).load(tweet.user.profileImageUrl).into(ivProfileImage);
    }

    public void onReply(View view) {

        Intent i = new Intent(TweetDetailsActivity.this, ComposeActivity.class);
        i.putExtra("reply", Boolean.TRUE);
        i.putExtra("inReplyTo", tweet.uid);
        startActivityForResult(i, REQUEST_CODE);
    }

    public void onRetweet(View v) {
        showProgressBar();
        Toast.makeText(this, "Retweeting", Toast.LENGTH_LONG).show();
        String message = "Retweeting " + tweet.user.name + "'s post:\n" + tweet.body;
        client = TwitterApplication.getRestClient(context);
        client.sendTweet(message, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Tweet tweet = Tweet.fromJSON(response);
                    Intent data = new Intent();
                    data.putExtra("tweet", tweet);
                    setResult(RESULT_OK, data);
                    finish();
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                hideProgressBar();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString,
                                  Throwable throwable) {
                Log.d("Twitter Client", responseString);
                throwable.printStackTrace();
                hideProgressBar();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable,
                                  JSONArray errorResponse) {
                Log.d("Twitter Client", errorResponse.toString());
                throwable.printStackTrace();
                hideProgressBar();
            }

        });
    }

    public void showProgressBar() {
        // Show progress item
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        // Hide progress item
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void onFavorite(View view) {
        ImageButton aButton = (ImageButton)view;
        if (!tweet.favorite) {
            aButton.setImageResource(R.drawable.ic_vector_heart);
        }
        else {
            aButton.setImageResource(R.drawable.ic_vector_heart_stroke);
        }
        tweet.favorite = !tweet.favorite;
    }

    public void onPerson(View view) {
        Intent intent = new Intent(TweetDetailsActivity.this, ProfileActivity.class);
        intent.putExtra("user", tweet.user);
        startActivity(intent);
    }
}
