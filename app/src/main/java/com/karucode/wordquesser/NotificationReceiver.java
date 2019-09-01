package com.karucode.wordquesser;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


public class NotificationReceiver extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {



        String action = intent.getAction();
        String correctAnswer = intent.getStringExtra("corectAnswer");
        String answer1 = intent.getStringExtra("answer1");
        String answer2 = intent.getStringExtra("answer2");
        String answer3 = intent.getStringExtra("answer3");



        Toast.makeText(context, correctAnswer, Toast.LENGTH_SHORT).show();

    }
}
