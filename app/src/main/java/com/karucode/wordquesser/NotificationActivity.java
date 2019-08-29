package com.karucode.wordquesser;

import android.graphics.Color;
import android.os.Bundle;


import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import static com.karucode.wordquesser.Notification.CHANNEL_1_ID;
import static com.karucode.wordquesser.Notification.CHANNEL_2_ID;

public class NotificationActivity extends AppCompatActivity {

    private NotificationManagerCompat notificationManager;
    private EditText editTextTitle;
    private EditText editTextMessage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        notificationManager = NotificationManagerCompat.from(this);

        editTextTitle = findViewById(R.id.exit_text_title);
        editTextMessage = findViewById(R.id.exit_text_message);

    }

    public void sendOnChannel1(View v){

        String title = editTextTitle.getText().toString();
        String message = editTextMessage.getText().toString();

        android.app.Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_notification_1)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE).build();

        notificationManager.notify(1, notification);
    }

    public void sendOnChannel2(View v){

        String title = editTextTitle.getText().toString();
        String message = editTextMessage.getText().toString();

        android.app.Notification notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_notification_2)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        notificationManager.notify(1, notification);

    }
}
