package com.example.simplefirebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonalChatActivity extends AppCompatActivity {

    private CollectionReference tweetsCollection;
    @BindView(R.id.txt_tweets) TextView txtTweets;
    @BindView(R.id.edt_sender) EditText edtSenderEmail;
    @BindView(R.id.edt_tweet) EditText edtTweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_chat);
        ButterKnife.bind(this);
        getDataOneTime();
    }
    private void getDataOneTime(){
        tweetsCollection = FirebaseFirestore.getInstance().collection("personalTweets");
        tweetsCollection.orderBy("timestamp", Query.Direction.ASCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    String allText = "";
                    List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                    for(DocumentSnapshot curr : myListOfDocuments)
                    {
                        Object textArray[] = curr.getData().values().toArray();
                       if(FirebaseAuth.getInstance().getCurrentUser().getEmail().equals(textArray[1])) {
                           allText += convertString(textArray);
                       }
                    }
                    txtTweets.setText(allText);
                }
            }
        });
    }
    private String convertString(Object[] textArray) {
        Object timeSent= textArray[0].toString().substring(11,19); /* data full */
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Date:");
        stringBuilder.append(timeSent);
        stringBuilder.append("\nSend From: ");
        stringBuilder.append(textArray[3]); /* send from*/
        stringBuilder.append("\nMessage:\n");
        stringBuilder.append(textArray[4]); /* Message */
        stringBuilder.append("\n\n");
        /*stringBuilder.append(textArray[1]);  send to */
        /*stringBuilder.append(textArray[5]); TimeStamp */
        return stringBuilder.toString();
    }

    @OnClick(R.id.btn_send)
    public void onTweetClicked(){

        String user = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String tweetText = edtTweet.getText().toString();
        String senderName = edtSenderEmail.getText().toString();
        Map<String, Object> tweet = new HashMap<>();
        tweet.put("sender",senderName);
        tweet.put("content", tweetText);
        tweet.put("type", "text");
        tweet.put("user", user);
        tweet.put("timestamp", System.currentTimeMillis());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tweet.put("date", ZonedDateTime.now().toString());
        }

        tweetsCollection.add(tweet)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(PersonalChatActivity.this,"Send",Toast.LENGTH_SHORT).show();
                    }
                });
        getDataOneTime();
        edtTweet.getText().clear();
        edtSenderEmail.getText().clear();
        Toast.makeText(PersonalChatActivity.this, "Message send successfully!",
                Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_al)
    public void onPersonalClicked(){
        startActivity(new Intent(PersonalChatActivity.this, MainActivity.class));
        finish();
    }
    @OnClick(R.id.btn_logout)
    public void onLogoutClicked() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(PersonalChatActivity.this, LoginActivity.class));
        finish();
    }
}