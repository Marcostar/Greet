package com.sagycorp.greet.Helper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.sagycorp.greet.Services.AlarmService;

import java.util.Calendar;

/**
 * Created by Dzeko on 1/26/2016.
 */
public class NotificationCreator extends WakefulBroadcastReceiver {


    public static void setAlarm(Context context)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 9);

        Intent intent = new Intent(context, NotificationCreator.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent startIntent = AlarmService.startNotificationServices(context);
        startWakefulService(context,startIntent);
    }
}
