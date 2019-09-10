package com.karucode.wordquesser;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;

import static com.karucode.wordquesser.Notification.CHANNEL_1_ID;


public class NotificationActionReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        String action = intent.getAction();
        String correctAnswer = intent.getStringExtra("correctAnswer");
        String definition = intent.getStringExtra("definition");
        Integer notificationId = intent.getIntExtra("notificationId", 1);

        if (action.equals(correctAnswer)) {

            android.app.Notification notification = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                    .setSmallIcon(R.drawable.ic_notification_1)
                    .setContentTitle(correctAnswer.toUpperCase())
                    .setContentText(definition)
                    .setOnlyAlertOnce(true)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(definition))
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .setColor(Color.GREEN)
                    .build();


            notificationManager.notify(notificationId, notification);

        } else  {

            android.app.Notification notification = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                    .setSmallIcon(R.drawable.ic_notification_1)
                    .setContentTitle("Correct was: " + correctAnswer.toUpperCase())
                    .setOnlyAlertOnce(true)
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .setColor(Color.RED)
                    .build();


            notificationManager.notify(notificationId, notification);

        }
    }
}
