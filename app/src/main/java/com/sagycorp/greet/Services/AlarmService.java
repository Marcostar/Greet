package com.sagycorp.greet.Services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.sagycorp.greet.MainActivity;
import com.sagycorp.greet.R;

import java.util.Calendar;
import java.util.Random;

/**
 * Created by Dzeko on 2/3/2016.
 */
public class AlarmService extends IntentService {

    private Integer randomNumber;
    private NotificationManager nm;
    private Uri uri;
    private String notification[] = {"Read today's astonishing story.",
            "Read surprising fact from Black Box.",
            "Your horoscope is quite interesting today.",
            "Get inspired today with a great thought.",
            "Visit this scenic place inside."};
    private Random random = new Random();

    public AlarmService() {
        super(AlarmService.class.getSimpleName());
    }

    public static Intent startNotificationServices(Context context)
    {
        Intent intent = new Intent(context, AlarmService.class);
        return intent;
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);

        if((currentHour >= 9)&&(currentHour < 10))
        {
            randomNumber = random.nextInt(5);
            nm = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

            uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Intent notificationIntent = new Intent(this, MainActivity.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(this.getApplicationContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Greet")
                    .setContentText(notification[randomNumber])
                    .setContentIntent(pendingIntent)
                    .setVibrate(new long[]{1000, 1000, 1000})
                    .setLights(Color.BLUE, 3000, 3000)
                    .setSound(uri)
                    .setAutoCancel(true);

            nm.notify(24392, mBuilder.build());

        }



        WakefulBroadcastReceiver.completeWakefulIntent(intent);
    }

}
