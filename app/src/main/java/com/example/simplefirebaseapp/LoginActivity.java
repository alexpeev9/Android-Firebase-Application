package com.example.simplefirebaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.edt_email) EditText editEmail;

    @BindView(R.id.edt_password)  EditText editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @OnClick(R.id.btn_login)
    public void onLoginClicked(){

    }
    @OnClick(R.id.btn_register)
    public void onRegisterClicked(){

    }
}