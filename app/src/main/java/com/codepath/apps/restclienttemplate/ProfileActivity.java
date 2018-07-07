package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.User;

public class ProfileActivity extends AppCompatActivity {

    public User user;
    public TextView tvUserName;
    public TextView tvScreenName;
    public ImageView ivProfileImage;
    public TextView tvFollowing;
    public TextView tvFollowers;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvScreenName = (TextView) findViewById(R.id.tvScreenName);
        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        tvFollowing = (TextView) findViewById(R.id.tvFollowing);
        tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        user = (User) getIntent().getSerializableExtra("user");

        tvUserName.setText(user.name);
        tvScreenName.setText("@" + user.screenName);
        tvFollowers.setText(user.followersCount + " followers!");
        tvFollowing.setText("Follows " + user.followingCount + "!");
        Glide.with(context).load(user.profileImageUrl).into(ivProfileImage);
    }

}
