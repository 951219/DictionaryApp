package com.karucode.wordquesser;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class WordQuesserUtilities extends AppCompatActivity {

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
        wordsAndDefinitions = new HashMap<>();
        BufferedReader reader;
        try{
            final InputStream file = context.getAssets().open(WordQuesserStartingScreenActivity.FILE_NAME);
            reader = new BufferedReader(new InputStreamReader(file));
            String line = reader.readLine();
            while(line != null){
                addWordToHasMapFromDB(line);
                line = reader.readLine();
            }
        } catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    void addWordToHasMapFromDB(String line) {
        String[] oneLine = line.split(" /// ");
        Word word = new Word(Integer.parseInt(oneLine[0]), oneLine[1], oneLine[2].substring(1));
        //substring kaotab [leliigse space'i definititsiooni eest
        wordsAndDefinitions.put(keyCounter, word);
        keyCounter++;
    }

//    public String readWordsFromHashmapToString(HashMap<Integer, Word> list){
//
//        String line = "";
////        for (int i = 0; i < list.size(); i++) {
//            Word word = list.get(list.size()-1);
//            line.concat(word.getAttempts() + " /// " + word.getWord() + " /// " + word.getDefinition());
////        }
//
//
//
//      return line;
//    }
}