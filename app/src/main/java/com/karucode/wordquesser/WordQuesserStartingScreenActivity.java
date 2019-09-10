package com.karucode.wordquesser;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static com.karucode.wordquesser.Notification.CHANNEL_1_ID;
import static com.karucode.wordquesser.Notification.CHANNEL_2_ID;



public class WordQuesserStartingScreenActivity extends AppCompatActivity {


    private static final String PREFS_NAME = "switchkey";
    private static final String NOTIFICATION_ID = "notificationId";
    private NotificationManagerCompat notificationManager;
    private WordQuesserUtilities wordQuesserUtilities;
    HashMap<Integer, Word> list = new HashMap<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_quesser_starting_screen);


        wordQuesserUtilities = new WordQuesserUtilities();
        notificationManager = NotificationManagerCompat.from(this);


        Button buttonStartGame = findViewById(R.id.button_wordquesser_start_game);
        buttonStartGame.setOnClickListener(V -> startGame());


        Button buttonLookDB = findViewById(R.id.button_wordquesser_look_db);
        buttonLookDB.setOnClickListener(V -> lookDb());


        Button buttonAddWord = findViewById(R.id.button_wordquesser_add_word);
        buttonAddWord.setOnClickListener(V -> addWord());


        Button buttonCheckNotification = findViewById(R.id.button_wordquesser_check_notification);
        buttonCheckNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendOnChannel1(v);
            }
        });

        buttonCheckNotification.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(WordQuesserStartingScreenActivity.this, "Send via channel 2", Toast.LENGTH_SHORT).show();
                sendOnChannel2(v);
                return true;
            }
        });



        Switch sw = findViewById(R.id.wordquesser_hourly_switch);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean valueBefore = settings.getBoolean("switchkey", false);
        sw.setChecked(valueBefore);  // gets value from shared preferences

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    // The toggle is enabled
                    changeSwitch(true);


                } else {
                    // The toggle is disabled
                    changeSwitch(false);
                }

                // saves to sharedprefs
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("switchkey", isChecked);
                editor.commit();
            }


        });




// works wit this one
        BufferedReader reader;
        try{
            final InputStream file = getAssets().open("WordsAndDefinitions.txt");
            reader = new BufferedReader(new InputStreamReader(file));
            String line = reader.readLine();
            while(line != null){
                wordQuesserUtilities.addWordToHasMap(line);
                line = reader.readLine();
            }
        } catch(IOException ioe){
            ioe.printStackTrace();
        }
        list = wordQuesserUtilities.getWordsAndDefinitions();



        //for cehcking if the hasmap is empty or not
        if (list.isEmpty()) {
            Toast.makeText(WordQuesserStartingScreenActivity.this, "DB empty", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(WordQuesserStartingScreenActivity.this, "DB not empty", Toast.LENGTH_SHORT).show();
        }



    }

    private void startGame() {
        Intent intent = new Intent(WordQuesserStartingScreenActivity.this, WordQuesserGame.class);
        startActivity(intent);
    }

    private void lookDb() {
        Intent intent = new Intent(WordQuesserStartingScreenActivity.this, LookDb.class);
        startActivity(intent);

    }

    private void addWord() {
        Intent intent = new Intent(WordQuesserStartingScreenActivity.this, AddWordActivity.class);
        startActivity(intent);
    }

    private void changeSwitch(boolean switchState){
        Intent intent = new Intent(getApplicationContext(), RepeatingNotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1 ,intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        if (switchState){

            Calendar calendar = Calendar.getInstance();
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 60000, pendingIntent);

        } else{
            alarmManager.cancel(pendingIntent);
        }
    }



    public void sendOnChannel1(View v) {


        SharedPreferences settings = getSharedPreferences(NOTIFICATION_ID, 0);
        int notificationId = settings.getInt("notificationId", 1);

        notificationId = notificationId +1;

        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("notificationId", notificationId);
        editor.commit();





        List<Integer> randomKeyList = wordQuesserUtilities.getRandomKeyList();
        Integer correctAnswerKeyKey = wordQuesserUtilities.getCorrectAnswerKey(randomKeyList);
        String correctAnswerWord = list.get(correctAnswerKeyKey).getWord();

        Collections.shuffle(randomKeyList);

        String definition = list.get(correctAnswerKeyKey).getDefinition();
        String answer1 = list.get(randomKeyList.get(0)).getWord();
        String answer2 = list.get(randomKeyList.get(1)).getWord();
        String answer3 = list.get(randomKeyList.get(2)).getWord();


        Intent answer1Intent = new Intent(this, NotificationReceiver.class);
        answer1Intent.setAction(answer1);
        answer1Intent.putExtra("correctAnswer", correctAnswerWord);
        answer1Intent.putExtra("definition", definition);
        answer1Intent.putExtra("notificationId", notificationId);
        PendingIntent action1BroadCastIntent = PendingIntent.getBroadcast(this, 0, answer1Intent, PendingIntent.FLAG_UPDATE_CURRENT);


        Intent answer2Intent = new Intent(this, NotificationReceiver.class);
        answer2Intent.setAction(answer2);
        answer2Intent.putExtra("correctAnswer", correctAnswerWord);
        answer2Intent.putExtra("definition", definition);
        answer2Intent.putExtra("notificationId", notificationId);
        PendingIntent action2BroadCastIntent = PendingIntent.getBroadcast(this, 0, answer2Intent, PendingIntent.FLAG_UPDATE_CURRENT);


        Intent answer3Intent = new Intent(this, NotificationReceiver.class);
        answer3Intent.setAction(answer3);
        answer3Intent.putExtra("correctAnswer", correctAnswerWord);
        answer3Intent.putExtra("definition", definition);
        answer3Intent.putExtra("notificationId", notificationId);
        PendingIntent action3BroadCastIntent = PendingIntent.getBroadcast(this, 0, answer3Intent, PendingIntent.FLAG_UPDATE_CURRENT);


        //-----

//        Intent broadCastIntent = new Intent(this, NotificationReceiver.class);
//        broadCastIntent.putExtra("corectAnswer", correctAnswerWord);

        android.app.Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_notification_1)
//                .setContentTitle(title)
                .setContentText(definition)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.BLUE)
//                .setAutoCancel(true)
//                .setOnlyAlertOnce(true)
//                .setContentIntent(contentIntent) //happens when pressing notification
                .addAction(R.mipmap.ic_launcher, answer1, action1BroadCastIntent)
                .addAction(R.mipmap.ic_launcher, answer2, action2BroadCastIntent)
                .addAction(R.mipmap.ic_launcher, answer3, action3BroadCastIntent)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(definition))
                .build();

        notificationManager.notify(notificationId, notification);


    }


    public void sendOnChannel2(View v) {

        String title = "Channel 2";
        String message = "Text channel 2";

        android.app.Notification notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_notification_2)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        notificationManager.notify(1, notification);


    }



}
