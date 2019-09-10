package com.karucode.wordquesser;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static com.karucode.wordquesser.Notification.CHANNEL_1_ID;
import static com.karucode.wordquesser.WordQuesserStartingScreenActivity.NOTIFICATION_ID;


public class RepeatingNotificationCreator extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {


        SharedPreferences settings = context.getSharedPreferences(NOTIFICATION_ID, 0);
        int notificationId = settings.getInt(NOTIFICATION_ID, 1);

        notificationId = notificationId +1;

        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("notificationId", notificationId);
        editor.commit();

        WordQuesserUtilities wordQuesserUtilities = WordQuesserUtilities.getInstance();
        HashMap<Integer, Word> list = wordQuesserUtilities.getWordsAndDefinitions();
        wordQuesserUtilities.readWordsToHashMap(context);


        List<Integer> randomKeyList = wordQuesserUtilities.getRandomKeyList();
        Integer correctAnswerKeyKey = wordQuesserUtilities.getCorrectAnswerKey(randomKeyList);
        String correctAnswerWord = list.get(correctAnswerKeyKey).getWord();

        Collections.shuffle(randomKeyList);

        String definition = list.get(correctAnswerKeyKey).getDefinition();
        String answer1 = list.get(randomKeyList.get(0)).getWord();
        String answer2 = list.get(randomKeyList.get(1)).getWord();
        String answer3 = list.get(randomKeyList.get(2)).getWord();


        Intent answer1Intent = new Intent(context, NotificationActionReceiver.class);
        answer1Intent.setAction(answer1);
        answer1Intent.putExtra("correctAnswer", correctAnswerWord);
        answer1Intent.putExtra("definition", definition);
        answer1Intent.putExtra("notificationId", notificationId);
        PendingIntent action1BroadCastIntent = PendingIntent.getBroadcast(context, 0, answer1Intent, PendingIntent.FLAG_UPDATE_CURRENT);


        Intent answer2Intent = new Intent(context, NotificationActionReceiver.class);
        answer2Intent.setAction(answer2);
        answer2Intent.putExtra("correctAnswer", correctAnswerWord);
        answer2Intent.putExtra("definition", definition);
        answer2Intent.putExtra("notificationId", notificationId);
        PendingIntent action2BroadCastIntent = PendingIntent.getBroadcast(context, 0, answer2Intent, PendingIntent.FLAG_UPDATE_CURRENT);


        Intent answer3Intent = new Intent(context, NotificationActionReceiver.class);
        answer3Intent.setAction(answer3);
        answer3Intent.putExtra("correctAnswer", correctAnswerWord);
        answer3Intent.putExtra("definition", definition);
        answer3Intent.putExtra("notificationId", notificationId);
        PendingIntent action3BroadCastIntent = PendingIntent.getBroadcast(context, 0, answer3Intent, PendingIntent.FLAG_UPDATE_CURRENT);



        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        android.app.Notification notification = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_notification_1)
                .setContentText(definition)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.BLUE)
                .setOnlyAlertOnce(true)
                .addAction(R.mipmap.ic_launcher, answer1, action1BroadCastIntent)
                .addAction(R.mipmap.ic_launcher, answer2, action2BroadCastIntent)
                .addAction(R.mipmap.ic_launcher, answer3, action3BroadCastIntent)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(definition))
                .build();

        notificationManager.notify(notificationId, notification);


    }
}
