package com.example.simplefirebaseapp;

public class Tweet {
    public String user;
    public String content;
    public String type;

    public Tweet(){
    }

    public Tweet(String user, String type, String tweetText) {
        this.user = user;
        this.type = type;
        this.content = tweetText;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(user);
        stringBuilder.append(": ");
        stringBuilder.append(content);
        stringBuilder.append("\n\n");

        return stringBuilder.toString();
    }
}
