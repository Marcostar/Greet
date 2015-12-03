package com.sagycorp.greet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dzeko on 11/5/2015.
 */
public class NotificationReceiver extends ParsePushBroadcastReceiver {

    /*public static final String TAG = NotificationReceiver.class.getSimpleName();*/
    SharedPreferences sharedPreferences ;
    SharedPreferences.Editor editor;

    public NotificationReceiver() {
        super();
    }

    @Override
    protected void onPushReceive(Context context, Intent intent)
    {
        sharedPreferences = context.getSharedPreferences(Startup.PreferenceSETTINGS,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        super.onPushReceive(context, intent);

        if(intent == null)
        {
            return;
        }

        try {
            JSONObject notification = new JSONObject(intent.getExtras().getString("com.parse.Data"));
            /*String notificationReceived = notification.getString("alert");*/
            /*Log.d(TAG, notificationReceived);*/
            /*String channelReceived = notification.getString("channel");*/
            String Facts = notification.getString("Facts");
            String Quotes = notification.getString("Quotes");
            editor.putString(Startup.DidYouKnow,Facts);
            editor.putString(Startup.Quotes, Quotes);
            editor.apply();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPushDismiss(Context context, Intent intent) {
        super.onPushDismiss(context, intent);
    }

    @Override
    protected void onPushOpen(Context context, Intent intent) {
        super.onPushOpen(context, intent);
    }

}
