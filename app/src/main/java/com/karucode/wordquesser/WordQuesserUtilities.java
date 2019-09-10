package com.karucode.wordquesser;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static java.nio.file.Files.readAllLines;

public class WordQuesserUtilities extends AppCompatActivity {
//    private Path pathToWordsAndDefinitions = Paths.get("WordsAndDefinitions.txt");
    private HashMap<Integer, Word> wordsAndDefinitions = new HashMap<>();
    private Random random = new Random();
    int keyCounter = 0;



    private WordQuesserUtilities() {
    }

    private static final WordQuesserUtilities instance = new WordQuesserUtilities();

    public static WordQuesserUtilities getInstance() {
        return instance;
    }

    //creates a a list of randomly picked words from the words.txt file
    List<Integer> getRandomKeyList() {

        List<Integer> usedKeys = new ArrayList<>();
        int howManyWordsInRound = 3;

        for (int counter = 0; counter < howManyWordsInRound; counter++) {
            int randomNumber = random.nextInt(wordsAndDefinitions.size());

            if (!usedKeys.contains(randomNumber)) {

                usedKeys.add(randomNumber);
            } else {
                counter--;
            }

        }
        return usedKeys;
    }

    //prints out and returns correct answer key
    Integer getCorrectAnswerKey(List<Integer> randomKeyList) {

        int randomWordNumber = random.nextInt(randomKeyList.size());
        Integer randomKey = randomKeyList.get(randomWordNumber);
        return randomKey;

    }


    HashMap<Integer, Word> getWordsAndDefinitions() {
        return wordsAndDefinitions;
    }

    void readWordsToHashMap(Context context){
        BufferedReader reader;
        try{
            final InputStream file = context.getAssets().open("WordsAndDefinitions.txt");
            reader = new BufferedReader(new InputStreamReader(file));
            String line = reader.readLine();
            while(line != null){
                addWordToHasMap(line);
                line = reader.readLine();
            }
        } catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    void addWordToHasMap(String line) {
        String[] oneLine = line.split(" /// ");
        Word word = new Word(Integer.parseInt(oneLine[0]), oneLine[1], oneLine[2].substring(1));
        //substring kaotab [leliigse space'i definititsiooni eest
        wordsAndDefinitions.put(keyCounter, word);
        keyCounter++;
    }
}