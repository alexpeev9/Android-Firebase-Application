package com.example.simplefirebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.edt_email) EditText edtEmail;

    @BindView(R.id.edt_password)  EditText edtPassword;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();

    }

    @OnClick(R.id.btn_login)
    public void onLoginClicked(){
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Authentication successful!.",
                                    Toast.LENGTH_SHORT).show();
                            redirectToMainScreen();
                        } else {
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                }
    @OnClick(R.id.btn_register)
    public void onRegisterClicked(){
        if(edtEmail.getText() != null && edtPassword.getText() != null) {
            String email = edtEmail.getText().toString();
            String password = edtPassword.getText().toString();
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Authentication successful!.",
                                        Toast.LENGTH_SHORT).show();
                                redirectToMainScreen();
                            } else {
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else{
            Toast.makeText(LoginActivity.this, "Name and Password cannot be null!",
                    Toast.LENGTH_SHORT).show();
        }
    }
    @OnClick(R.id.btn_exit)
    public void onExitClicked() {
        System.exit(0);
    }
    public void redirectToMainScreen(){
        startActivity(new Intent(this,MainActivity.class));
        finish(); // we don't want to go back to login while logged
    }
    @OnClick(R.id.btn_delete)
    public void onDeleteClicked() {
        CollectionReference db = FirebaseFirestore.getInstance().collection("tweets");
        Task<QuerySnapshot> tweetsCollection = db
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                    myListOfDocuments.remove(myListOfDocuments.size()-1);
                    for(DocumentSnapshot curr : myListOfDocuments)
                    {
                        db.document(curr.getId())
                                .delete();
                    }
            }});
        Toast.makeText(LoginActivity.this, "Successful operation!", Toast.LENGTH_SHORT).show();
    }
}
