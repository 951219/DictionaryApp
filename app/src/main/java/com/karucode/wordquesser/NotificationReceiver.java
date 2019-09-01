package com.karucode.wordquesser;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.NotificationManagerCompat;
import android.text.PrecomputedText;
import android.widget.Toast;


public class NotificationReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        String correctAnswer = intent.getStringExtra("corectAnswer");

        if (action.equals(correctAnswer)) {

            Toast.makeText(context, "You are correct", Toast.LENGTH_SHORT).show();
        } else  {
            Toast.makeText(context, "You are wrong", Toast.LENGTH_SHORT).show();
        }
    }
}
