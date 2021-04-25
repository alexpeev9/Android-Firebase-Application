package com.example.simplefirebaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import butterknife.OnClick;

public class PersonalChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_chat);
    }
    @OnClick(R.id.btn_logout)
    public void onLogoutClicked() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(PersonalChatActivity.this, LoginActivity.class));
        finish();
    }
    @OnClick(R.id.btn_personal)
    public void onPersonalClicked(){
        startActivity(new Intent(PersonalChatActivity.this, MainActivity.class));
    }
}