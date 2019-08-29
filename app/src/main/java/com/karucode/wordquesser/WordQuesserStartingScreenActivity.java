package com.karucode.wordquesser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class WordQuesserStartingScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_quesser_starting_screen);

        Button buttonStartGame = findViewById(R.id.button_wordquesser_start_game);
        buttonStartGame.setOnClickListener(V -> startGame());


        Button buttonLookDB = findViewById(R.id.button_wordquesser_look_db);
        buttonLookDB.setOnClickListener(V -> lookDb());


        Button buttonAddWord = findViewById(R.id.button_wordquesser_add_word);
        buttonAddWord.setOnClickListener(V -> addWord());


        Button buttonCheckNotification = findViewById(R.id.button_wordquesser_check_notification);
        buttonCheckNotification.setOnClickListener(V -> testNotification());
    }

    private void startGame() {
        Intent intent = new Intent(WordQuesserStartingScreenActivity.this, WordQuesserGame.class);
        startActivity(intent);
    }

    private void lookDb() {

    }

    private void addWord() {
        Intent intent = new Intent(WordQuesserStartingScreenActivity.this, AddWordActivity.class);
        startActivity(intent);

    }

    private void testNotification() {



    }
}
