package com.example.colemark;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.concurrent.atomic.AtomicReference;

import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity {
    String token;
    @SuppressLint("StaticFieldLeak")
    static TextView name;
    @SuppressLint("StaticFieldLeak")
    static TextView surName;


    static ListView[] events=new ListView[3];
    static ListView[] gifts=new ListView[3];
    static ListView[] groups=new ListView[3];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences mPrefs = getSharedPreferences("settings", 0);
        AtomicReference<String> mString = new AtomicReference<>(mPrefs.getString("token", ""));
        if (mString.get().equals("")){
            Intent intentMain = new Intent(ProfileActivity.this ,
                    MainActivity.class);

            ProfileActivity.this.startActivity(intentMain);
            return;
        } else {
            token= mString.get();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pro_file);
        name=findViewById(R.id.profileProfileName);
        surName=findViewById(R.id.profileProfileSurname);

        events[0]=findViewById(R.id.profile1List1);
        events[1]=findViewById(R.id.profile1List2);
        events[2]=findViewById(R.id.profile1List3);

        gifts[0]=findViewById(R.id.profile2List1);
        gifts[1]=findViewById(R.id.profile2List2);
        gifts[2]=findViewById(R.id.profile2List3);

        groups[0]=findViewById(R.id.profile3List1);
        groups[1]=findViewById(R.id.profile3List2);
        groups[2]=findViewById(R.id.profile3List3);

        // users
        HttpUtils.client.addHeader("Authorization","Bearer "+token);

        HttpUtils.get("users", new RequestParams(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                JSONObject response=new JSONObject();
                if (statusCode==200){
                    try {
                        response=new JSONObject((new String(responseBody)));
                    } catch (JSONException ignored) {

                    }

                    try {
                        ProfileActivity.name.setText(response.getString("name"));
                        ProfileActivity.surName.setText(response.getString("surname"));
                    } catch (Exception ignored){

                    }

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("ERROR");
                if (statusCode==404){
                    SharedPreferences mPrefs = getSharedPreferences("settings", 0);
                    SharedPreferences.Editor mEditor = mPrefs.edit();
                    mEditor.putString("token", "").apply();
                    Intent intentMain = new Intent(ProfileActivity.this ,
                            MainActivity.class);

                    ProfileActivity.this.startActivity(intentMain);
                }
            }
        });


        HttpUtils.get("events", new RequestParams(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                JSONObject response=new JSONObject();
                if (statusCode==200){
                    try {
                        response=new JSONObject((new String(responseBody)));
                    } catch (JSONException ignored) {

                    }

                    try {
                        ProfileActivity.name.setText(response.getString("name"));
                        ProfileActivity.surName.setText(response.getString("surname"));
                    } catch (Exception ignored){

                    }

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("ERROR");
                if (statusCode==404){
                    SharedPreferences mPrefs = getSharedPreferences("settings", 0);
                    SharedPreferences.Editor mEditor = mPrefs.edit();
                    mEditor.putString("token", "").apply();
                    Intent intentMain = new Intent(ProfileActivity.this ,
                            MainActivity.class);

                    ProfileActivity.this.startActivity(intentMain);
                }
            }
        });
    }
}