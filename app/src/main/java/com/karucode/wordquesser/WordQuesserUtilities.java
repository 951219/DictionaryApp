package com.karucode.wordquesser;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static com.karucode.wordquesser.WordQuesserStartingScreenActivity.TEST_FILE_NAME;


public class WordQuesserUtilities {
    //extends AppCompatActivity

    private HashMap<Integer, Word> wordsAndDefinitions = new HashMap<>();
    private Random random = new Random();
    private int keyCounter = 0;


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


//     reads from assets, will crash the app if afterwards opening new DB
    void readWordsToHashMapFromAssets(Context context) {
        wordsAndDefinitions = new HashMap<>();
        BufferedReader reader;
        try {
            final InputStream file = context.getAssets().open(WordQuesserStartingScreenActivity.FILE_NAME);
            reader = new BufferedReader(new InputStreamReader(file));
            String line = reader.readLine();
            while (line != null) {
                addWordToHasMapFromDB(line);
                line = reader.readLine();
            }
            Log.d("DB", "loaded from asset FILE_NAME, size: "+ wordsAndDefinitions.size());
            Toast.makeText(context, "DB loaded from ASSETS, size: " + wordsAndDefinitions.size(), Toast.LENGTH_SHORT).show();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    void readWordsToHashMap(Context context) {
        FileInputStream fis = null;

        try {
            fis = context.openFileInput(TEST_FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String text;

            while ((text = br.readLine()) != null) {
                addWordToHasMapFromDB(text);
            }

            Log.d("DB", "loaded from txt TEST_FILE_NAME, size: "+ wordsAndDefinitions.size());
            Toast.makeText(context, "DB loaded from TXT, size: " + wordsAndDefinitions.size(), Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void addWordToHasMapFromDB(String line) {
        String[] oneLine = line.split(" /// ");
        Word word = new Word(Integer.parseInt(oneLine[0]), oneLine[1], oneLine[2].substring(1));
        //substring kaotab [leliigse space'i definititsiooni eest
        wordsAndDefinitions.put(keyCounter, word);
        keyCounter++;
    }

    public void emptyHashMap(){
        wordsAndDefinitions = null;
    }
}


// example of how to read from internal storage


//    public void load(View v) {
//        FileInputStream fis = null;
//
//        try {
//            fis = openFileInput(FILE_NAME);
//            InputStreamReader isr = new InputStreamReader(fis);
//            BufferedReader br = new BufferedReader(isr);
//            StringBuilder sb = new StringBuilder();
//            String text;
//
//            while ((text = br.readLine()) != null) {
//                sb.append(text).append("\n");
//            }
//
//            mEditText.setText(sb.toString());
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (fis != null) {
//                try {
//                    fis.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
