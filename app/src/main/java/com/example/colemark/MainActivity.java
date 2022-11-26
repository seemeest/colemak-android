package com.example.colemark;

import com.loopj.android.http.*;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.colemark.ui.main.MainFragment;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    static EditText email;
    static EditText password;
    static ImageButton loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email=findViewById(R.id.loginEmail);
        password=findViewById(R.id.loginPassword);
        loginButton=findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=MainActivity.email.getText().toString().trim();
                String password=MainActivity.password.getText().toString().trim();
                if (email.trim()=="" || password==""){
                    // here error
                    return;
                }
                RequestParams params = new RequestParams();
                params.put("email",email);
                params.put("password",password);

                HttpUtils.post("users/login",params,new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        // checks

                        System.out.println((new String(responseBody)).toString());
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        // print error with network
                        // System.out.println(error.toString()+"\n"+(new String(responseBody)).toString());
                    }

//
                });

                System.out.println("TEST");
            }
        });
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }

    }
}