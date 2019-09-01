package com.karucode.wordquesser;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static com.karucode.wordquesser.Notification.CHANNEL_1_ID;
import static com.karucode.wordquesser.Notification.CHANNEL_2_ID;



public class WordQuesserStartingScreenActivity extends AppCompatActivity {



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



//        //for cehcking if the hasmap is empty or not
//        if (list.isEmpty()) {
//            Toast.makeText(WordQuesserStartingScreenActivity.this, "DB empty", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(WordQuesserStartingScreenActivity.this, "DB not empty", Toast.LENGTH_SHORT).show();
//        }



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



    public void sendOnChannel1(View v) {


        List<Integer> randomKeyList = wordQuesserUtilities.getRandomKeyList();
        Integer correctAnswerKeyKey = wordQuesserUtilities.getCorrectAnswerKey(randomKeyList);




        Collections.shuffle(randomKeyList);

        String message = list.get(correctAnswerKeyKey).getDefinition();
        String answer1 = list.get(randomKeyList.get(0)).getWord();
        String answer2 = list.get(randomKeyList.get(1)).getWord();
        String answer3 = list.get(randomKeyList.get(2)).getWord();





        Intent answer1Intent = new Intent(this, NotificationReceiver.class);
        answer1Intent.setAction(answer1);
        answer1Intent.putExtra("corectAnswer", list.get(correctAnswerKeyKey).getWord());
        PendingIntent action1BroadCastIntent = PendingIntent.getBroadcast(this, 0, answer1Intent, PendingIntent.FLAG_UPDATE_CURRENT);


        Intent answer2Intent = new Intent(this, NotificationReceiver.class);
        answer2Intent.setAction(answer2);
        answer2Intent.putExtra("corectAnswer", list.get(correctAnswerKeyKey).getWord());
        PendingIntent action2BroadCastIntent = PendingIntent.getBroadcast(this, 0, answer2Intent, PendingIntent.FLAG_UPDATE_CURRENT);


        Intent answer3Intent = new Intent(this, NotificationReceiver.class);
        answer3Intent.setAction(answer3);
        answer3Intent.putExtra("corectAnswer", list.get(correctAnswerKeyKey).getWord());
        PendingIntent action3BroadCastIntent = PendingIntent.getBroadcast(this, 0, answer3Intent, PendingIntent.FLAG_UPDATE_CURRENT);


        //-----

        Intent broadCastIntent = new Intent(this, NotificationReceiver.class);
        broadCastIntent.putExtra("corectAnswer", list.get(correctAnswerKeyKey).getWord());

        android.app.Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_notification_1)
//                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.BLUE)
//                .setAutoCancel(true)
//                .setOnlyAlertOnce(true)
//                .setContentIntent(contentIntent) //happens when pressing notification
                .addAction(R.mipmap.ic_launcher, answer1, action1BroadCastIntent)
                .addAction(R.mipmap.ic_launcher, answer2, action2BroadCastIntent)
                .addAction(R.mipmap.ic_launcher, answer3, action3BroadCastIntent)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .build();

        notificationManager.notify(1, notification);
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
