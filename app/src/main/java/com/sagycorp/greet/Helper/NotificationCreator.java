package com.sagycorp.greet.Helper;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.sagycorp.greet.MainActivity;
import com.sagycorp.greet.R;

import java.util.Random;

/**
 * Created by Dzeko on 1/26/2016.
 */
public class NotificationCreator extends BroadcastReceiver {

    NotificationManager nm;
    Uri uri;
    private String notification[] = {"Read today's astonishing story.",
                            "Read surprising fact from Black Box.",
                            "Your horoscope is quite surprising today.",
                            "Get inspired today with a great thought.",
                            "Visit this scenic place inside."};
    private Integer randomNumber;
    private Random random = new Random();
    @Override
    public void onReceive(Context context, Intent intent) {
        randomNumber = random.nextInt(5);
        nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Greet")
                        .setContentText(notification[randomNumber])
                        .setContentIntent(pendingIntent)
                        .setVibrate(new long[]{1000, 1000, 1000})
                        .setLights(Color.BLUE,3000,3000)
                        .setSound(uri)
                        .setAutoCancel(true);

        nm.notify(0, mBuilder.build());
    }
}
