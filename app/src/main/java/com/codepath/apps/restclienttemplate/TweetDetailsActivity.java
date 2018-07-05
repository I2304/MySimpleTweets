package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

public class TweetDetailsActivity extends AppCompatActivity {

    TwitterClient client;
    Context context;
    Tweet tweet;
    public static final int REQUEST_CODE = 1;
    ImageView ivProfileImage;
    TextView tvUserName;
    TextView tvBody;
    Boolean reply = Boolean.TRUE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_details);
        context = this;
        client = TwitterApplication.getRestClient(context);
        tweet = (Tweet) getIntent().getSerializableExtra("tweet");
        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvBody = (TextView) findViewById(R.id.tvBody);

        tvUserName.setText(tweet.user.name);
        tvBody.setText(tweet.body);

        Glide.with(context).load(tweet.user.profileImageUrl).into(ivProfileImage);
    }

    public void onReply(View view) {
        Intent i = new Intent(TweetDetailsActivity.this, ComposeActivity.class);
        i.putExtra("reply", reply);
        i.putExtra("name", tvUserName.getText());
        startActivityForResult(i, REQUEST_CODE);
    }
}
