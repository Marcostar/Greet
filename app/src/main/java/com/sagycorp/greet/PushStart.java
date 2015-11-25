package com.sagycorp.greet;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParsePush;

/**
 * Created by Dzeko on 11/5/2015.
 */
public class PushStart extends Application {

    @Override
    public void onCreate()
    {
        super.onCreate();
        Parse.initialize(this, "bLwuq7MBXbyuBSrzmuw7Kkl2vUGb6rpszbEsanIO", "LAGZVb7E473d56HjqkczculReTkBZyUcwkcU3JTs");
        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParsePush.subscribeInBackground("Quotes");
    }
}
