package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {

    private TwitterClient client;
    Context context;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        context = this;
        client = TwitterApplication.getRestClient(context);

        EditText simpleEditText = (EditText) findViewById(R.id.et_simple);
        message = simpleEditText.getText().toString();
        // set the title bar to reflect the purpose of the view
        getSupportActionBar().setTitle("Compose Tweet");

    }

    public void onSubmit(View v) {
        Toast.makeText(this, "Hello There", Toast.LENGTH_LONG).show();
        EditText simpleEditText = (EditText) findViewById(R.id.et_simple);
        message = simpleEditText.getText().toString();
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
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Twitter Client", responseString);
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("Twitter Client", errorResponse.toString());
                throwable.printStackTrace();
            }

        });
    }
}
