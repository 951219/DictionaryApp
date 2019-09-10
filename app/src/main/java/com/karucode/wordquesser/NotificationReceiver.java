package com.karucode.wordquesser;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import static com.karucode.wordquesser.Notification.CHANNEL_1_ID;


public class NotificationReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);





        String action = intent.getAction();
        String correctAnswer = intent.getStringExtra("correctAnswer");
        String definition = intent.getStringExtra("definition");
        Integer notificationId = intent.getIntExtra("notificationId", 1);

        if (action.equals(correctAnswer)) {

//            Toast.makeText(context, "You are correct", Toast.LENGTH_SHORT).show();


            android.app.Notification notification = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                    .setSmallIcon(R.drawable.ic_notification_1)
                    .setContentTitle(correctAnswer.toUpperCase())
                    .setContentText(definition)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(definition))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .setColor(Color.BLUE)
                    .build();


            notificationManager.notify(notificationId, notification);

        } else  {


            Toast.makeText(context, "You are wrong", Toast.LENGTH_SHORT).show();



        }
    }
}
