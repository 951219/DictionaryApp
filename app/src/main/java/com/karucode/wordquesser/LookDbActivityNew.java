package com.karucode.wordquesser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;


public class LookDbActivityNew extends AppCompatActivity implements WordAdapter.OnWordListener{


    HashMap<Integer, Word> list = new HashMap<>();
    private static final String TAG = "LookDbActivityNew";


//    private HashMap<Integer, Word> list = WordQuesserUtilities.getInstance().getWordsAndDefinitions();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_db_new);
        Log.d(TAG, "onCreate: started");


        list = WordQuesserUtilities.getInstance().getWordsAndDefinitions();
        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: started");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        WordAdapter adapter = new WordAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


    @Override
    public void OnWordClick(int position) {

//        /What happenes when block is pressed
       Word word = list.get(position);

        Toast.makeText(this,word.getWord(),Toast.LENGTH_SHORT).show();
    }
}

