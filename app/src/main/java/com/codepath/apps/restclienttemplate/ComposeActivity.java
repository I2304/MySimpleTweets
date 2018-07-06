package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {

    private TwitterClient client;
    Context context;
    String message;
    TextView tvCount;
    private ProgressBar progressBar;
    Boolean reply;
    long uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        progressBar = (ProgressBar) findViewById(R.id.pbLoading);
        context = this;
        client = TwitterApplication.getRestClient(context);

        final EditText simpleEditText = (EditText) findViewById(R.id.et_simple);
        tvCount = (TextView) findViewById(R.id.tvCount);
        reply = getIntent().getBooleanExtra("reply", false);
        message = simpleEditText.getText().toString();

        if (reply) {
            uid = getIntent().getLongExtra("inReplyTo", 0);
            simpleEditText.setSelection(simpleEditText.getText().length());
        }

        simpleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = simpleEditText.length();
                int remaining = 140 - length;
                tvCount.setText(String.format("%d out of 140", remaining));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void onSubmit(View v) {
        showProgressBar();
        EditText simpleEditText = (EditText) findViewById(R.id.et_simple);
        message = simpleEditText.getText().toString();
        if (!reply) {
            client.sendTweet(message, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        Tweet tweet = Tweet.fromJSON(response);
                        Intent data = new Intent();
                        data.putExtra("tweet", Parcels.wrap(tweet));
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

        else {
            client.sendTweet(message, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        Tweet tweet = Tweet.fromJSON(response);
                        Intent data = new Intent();
                        data.putExtra("tweet", Parcels.wrap(tweet));
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

            }, uid);
        }
    }


    public void showProgressBar() {
        // Show progress item
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        // Hide progress item
        progressBar.setVisibility(View.INVISIBLE);
    }
}