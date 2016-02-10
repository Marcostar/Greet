package com.sagycorp.greet;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParsePush;

/**
 * Created by Dzeko on 11/5/2015.
 */
public class PushStart extends Application {

    private Tracker mTracker;

    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.global_tracker);
        }
        return mTracker;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        Parse.initialize(this, "bLwuq7MBXbyuBSrzmuw7Kkl2vUGb6rpszbEsanIO", "LAGZVb7E473d56HjqkczculReTkBZyUcwkcU3JTs");
        ParsePush.unsubscribeInBackground("v2");
        ParsePush.subscribeInBackground("v3");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
