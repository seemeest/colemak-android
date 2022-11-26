package com.example.colemark;

import com.loopj.android.http.*;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.colemark.ui.main.MainFragment;

import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.atomic.AtomicReference;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    @SuppressLint("StaticFieldLeak")
    static EditText email;
    @SuppressLint("StaticFieldLeak")
    static EditText password;
    @SuppressLint("StaticFieldLeak")
    static Button loginButton;
    @SuppressLint("StaticFieldLeak")
    static Button registerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email=findViewById(R.id.loginLoginEmail);
        password=findViewById(R.id.loginLoginPassword);
        loginButton=findViewById(R.id.loginLoginButton);
        registerButton=findViewById(R.id.registerLoginButton);
        SharedPreferences mPrefs = getSharedPreferences("settings", 0);
        AtomicReference<String> mString = new AtomicReference<>(mPrefs.getString("token", ""));
        if (!mString.equals("")){
            // menu
            // setContentView(R.layout.activity_menu);
        }

        registerButton.setOnClickListener(v -> {
//          example of getting token
//            mString.set(mPrefs.getString("token", ""));
//            System.out.println(mString.get());
            Intent intentMain = new Intent(MainActivity.this ,
                    RegisterActivity.class);

            MainActivity.this.startActivity(intentMain);
             //setContentView(R.layout.activity_register);
        });

        loginButton.setOnClickListener(v -> {
            String email=MainActivity.email.getText().toString().trim();
            String password=MainActivity.password.getText().toString().trim();
            if (email.equals("") || password.equals("")){
                // here error
                MainActivity.registerButton.setVisibility(View.VISIBLE);
            }
            RequestParams params = new RequestParams();
            params.put("email",email);
            params.put("password",password);
//                JSONObject jsonParams = new JSONObject();
//                StringEntity entity = new StringEntity(jsonParams.toString());
            HttpUtils.post("users/login",params,new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    // checks
                    JSONObject response=new JSONObject();
                    if (statusCode==200){
                        try {
                            response=new JSONObject((new String(responseBody)));
                        } catch (JSONException ignored) {

                        }
                        String token= "";
                        try {
                            token=response.get("token").toString();

                        } catch (Exception ignored){

                        }
                        SharedPreferences mPrefs = getSharedPreferences("settings", 0);
                        SharedPreferences.Editor mEditor = mPrefs.edit();
                        mEditor.putString("token", token).apply();

                    } else {
                        MainActivity.registerButton.setVisibility(View.VISIBLE);
                    }



                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    MainActivity.registerButton.setVisibility(View.VISIBLE);
                }

//
            });
        });
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }

    }
}