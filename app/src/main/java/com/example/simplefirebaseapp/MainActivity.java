package com.example.simplefirebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private CollectionReference tweetsCollection;
    @BindView(R.id.txt_tweets) TextView txtTweets;
    @BindView(R.id.edt_tweet) EditText edtTweet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getDataOneTime();
    }

    private void getDataOneTime(){
        tweetsCollection = FirebaseFirestore.getInstance().collection("tweets");
        tweetsCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String allText = "";
                            List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                            for(DocumentSnapshot curr : myListOfDocuments)
                            {
                                allText += curr.getData().values()+ "\n";
                            }
                            txtTweets.setText(allText);
                        }
                    }
                });
    }

    @OnClick(R.id.btn_tweet)
    public void onTweetClicked(){

        String user = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String tweetText = edtTweet.getText().toString();

        Map<String, Object> tweet = new HashMap<>();
        tweet.put("content", tweetText);
        tweet.put("type", "text");
        tweet.put("user", user);

        tweetsCollection.add(tweet)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(MainActivity.this,"Send",Toast.LENGTH_SHORT).show();
                    }
                });
        getDataOneTime();
    }
}