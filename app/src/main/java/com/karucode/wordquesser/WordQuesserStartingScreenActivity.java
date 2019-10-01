package com.karucode.wordquesser;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;


import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;


public class WordQuesserStartingScreenActivity extends AppCompatActivity {


    private static final String SWITCH_KEY = "switchKey";
    private static final String MILLIS_BEFORE = "millisInterval";
    public static final String NOTIFICATION_ID = "notificationId";
    public static final String FILE_NAME = "WordsAndDefinitions.txt";
    public static final String TEST_FILE_NAME = "TEST_WordsAndDefinitions.txt";
    public static final String TEXT_FILES_FOLDER = "/textfiles";
    private long millisInterval;

    private WordQuesserUtilities wordQuesserUtilities;
    HashMap<Integer, Word> list = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_quesser_starting_screen);

        File mFolder = new File(getFilesDir() + TEXT_FILES_FOLDER);
        File imgFile = new File(mFolder.getAbsolutePath() + TEST_FILE_NAME);
        if (!mFolder.exists()) {
            mFolder.mkdir();
        }
        if (!imgFile.exists()) {
            try {
                imgFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Log.d("Exists", "textfile");
        }



        wordQuesserUtilities = WordQuesserUtilities.getInstance();
        wordQuesserUtilities.readWordsToHashMap(this);
        list = wordQuesserUtilities.getWordsAndDefinitions();


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

                sendNotificationOnChannel1();
            }
        });

        buttonCheckNotification.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(WordQuesserStartingScreenActivity.this, "LOOOOONG pressed", Toast.LENGTH_SHORT).show();
                sendOnChannel2(v);
                return true;
            }
        });


        Switch sw = findViewById(R.id.wordquesser_hourly_switch);

        SharedPreferences settings = getSharedPreferences(SWITCH_KEY, 0);
        boolean switchKeyValueBefore = settings.getBoolean("switchkey", false);
        millisInterval = settings.getLong(MILLIS_BEFORE, 900000);
        sw.setChecked(switchKeyValueBefore);// gets value from shared preferences and changed to switch to last value
        changeSwitch(switchKeyValueBefore, millisInterval);
        Log.d("Switch loaded saved value: ", switchKeyValueBefore + " : " + millisInterval);

        String[] intervalList = getResources().getStringArray(R.array.choose_interval);

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (isChecked) {
                    // The toggle is enabled
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(WordQuesserStartingScreenActivity.this);
                    mBuilder.setTitle("Choose the interval");

                    mBuilder.setSingleChoiceItems(intervalList, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            int minutes = Integer.parseInt(intervalList[i]);
                            millisInterval = minutes * 60 * 1000;

                            SharedPreferences.Editor editor = settings.edit();
                            editor.putBoolean("switchkey", isChecked);
                            editor.putLong("millisInterval", millisInterval);
                            editor.commit();

                            changeSwitch(true, millisInterval);
                            Log.d("Switched", "ON " + millisInterval);

                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog mDialog = mBuilder.create();
                    mDialog.show();


                } else {
                    // The toggle is disabled
                    changeSwitch(false, millisInterval);
                    Log.d("Switched", "OFF");

                }
            }
        });


        //for checking if the hashmap(db) is empty or not
        if (!list.isEmpty()) {
            Toast.makeText(WordQuesserStartingScreenActivity.this, "DB loaded, size: " +list.size(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(WordQuesserStartingScreenActivity.this, "DB not loaded", Toast.LENGTH_SHORT).show();
        }


    }

    private void startGame() {
        Intent intent = new Intent(WordQuesserStartingScreenActivity.this, WordQuesserGame.class);
        startActivity(intent);
    }

    private void lookDb() {
        Intent intent = new Intent(WordQuesserStartingScreenActivity.this, LookDbActivity.class);
        startActivity(intent);

    }

    private void addWord() {
        Intent intent = new Intent(WordQuesserStartingScreenActivity.this, AddWordActivity.class);
        startActivity(intent);
    }

    private void changeSwitch(boolean switchState, long timeInMillis) {
        Intent intent = new Intent(this, RepeatingNotificationCreator.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        if (switchState) {

            Calendar calendar = Calendar.getInstance();
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), timeInMillis, pendingIntent); // here you can change the interval of the notification

        } else {
            alarmManager.cancel(pendingIntent);
        }
    }

    public void sendNotificationOnChannel1() {
        Intent intent = new Intent(WordQuesserStartingScreenActivity.this, RepeatingNotificationCreator.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        try {
            pendingIntent.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    public void sendOnChannel2(View v) {

//        String title = "Channel 2";
//        String message = "Text channel 2";
//
//        android.app.NotificationChannels notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
//                .setSmallIcon(R.drawable.ic_notification_2)
//                .setContentTitle(title)
//                .setContentText(message)
//                .setPriority(NotificationCompat.PRIORITY_LOW)
//                .build();
//
//        notificationManager.notify(1, notification);


    }

}
