package com.example.colemark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class RegisterActivity extends AppCompatActivity {
    static EditText firstName;
    static EditText surName;
    static EditText email;
    static EditText password1;
    static EditText password2;
    static Button register;
    static Button backLogin;
    static TextView wrongData;

    static protected void eraseInputs(){
        RegisterActivity.firstName.getText().clear();
        RegisterActivity.surName.getText().clear();
        RegisterActivity.email.getText().clear();
        RegisterActivity.password2.getText().clear();
        RegisterActivity.password1.getText().clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firstName=findViewById(R.id.registerFirstName);
        surName=findViewById(R.id.registerSurName);
        email=findViewById(R.id.RegisterEmail);
        password1=findViewById(R.id.registerRegisterPassword1);
        password2=findViewById(R.id.registerRegisterPassword2);
        register=findViewById(R.id.registerRegisterButton);
        backLogin=findViewById(R.id.registerLoginButton);
        wrongData=findViewById(R.id.registerWrongData);
        register.setOnClickListener(v -> {
            String firstName=RegisterActivity.firstName.getText().toString().trim();
            System.out.println(firstName);
            String surName=RegisterActivity.surName.getText().toString().trim();
            String email=RegisterActivity.email.getText().toString().trim();
            String password1=RegisterActivity.password1.getText().toString().trim();
            String password2=RegisterActivity.password2.getText().toString().trim();
            if (!password1.equals(password2) || firstName.equals("") || surName.equals("") || email.equals("")){
                RegisterActivity.eraseInputs();
                RegisterActivity.wrongData.setVisibility(View.VISIBLE);

            } else {
                RequestParams params = new RequestParams();
                params.put("name",firstName);
                params.put("surname",surName);
                params.put("email",email);
                params.put("password",password1);
                params.put("repeatPassword",password2);

                HttpUtils.post("users/registration",params,new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        // checks

                        JSONObject response=new JSONObject();
                        if (statusCode==200){
                            RegisterActivity.wrongData.setVisibility(View.INVISIBLE);
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
                            Intent intentMain = new Intent(RegisterActivity.this ,
                                    ProfileActivity.class);

                            RegisterActivity.this.startActivity(intentMain);


                        } else {
                            RegisterActivity.wrongData.setVisibility(View.VISIBLE);
                            RegisterActivity.eraseInputs();
                        }



                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        RegisterActivity.wrongData.setVisibility(View.VISIBLE);
                    }

//
                });
            }

            // System.out.println(1);
        });

        backLogin.setOnClickListener(v -> {
            Intent intentMain = new Intent(RegisterActivity.this ,
                    MainActivity.class);

            RegisterActivity.this.startActivity(intentMain);
        });
    }
}