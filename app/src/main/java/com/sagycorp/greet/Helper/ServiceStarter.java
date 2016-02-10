package com.sagycorp.greet.Helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Dzeko on 2/4/2016.
 */
public class ServiceStarter extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCreator.setAlarm(context);
    }
}
