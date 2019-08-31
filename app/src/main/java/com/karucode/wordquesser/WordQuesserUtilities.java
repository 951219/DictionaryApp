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
    private Path pathToWordsAndDefinitions = Paths.get("WordsAndDefinitions.txt");
    private HashMap<Integer, Word> wordsAndDefinitions = new HashMap<>();
    private Random random = new Random();
    private Scanner scanner = new Scanner(System.in);
    int keyCounter = 0;

    WordQuesserUtilities() {
        if (!Files.exists(pathToWordsAndDefinitions)) {
            try {
                Files.createFile(pathToWordsAndDefinitions);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


//        List<Integer> randomKeyList = getRandomKeyList();
//        Integer correctAnswerKeyKey = getCorrectAnswerKey(randomKeyList);
//        checkIfCorrect(correctAnswerKeyKey);
    }


    //reads word objects into hasmhap
    void refreshWordsAndDefinitions() {

    }


    //creates a a list of randomly picked words from the words.txt file
    List<Integer> getRandomKeyList() {

        List<Integer> usedKeys = new ArrayList<>();
        int howManyWordsInRound = 4;

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
//        String words = "";
//        for (Integer number : randomKeyList) {
//            words += wordsAndDefinitions.get(number).getWord() + ", ";
//        }

//        System.out.println(words);
        int randomWordNumber = random.nextInt(randomKeyList.size());
        Integer randomKey = randomKeyList.get(randomWordNumber);
//        System.out.println(wordsAndDefinitions.get(randomKey).getDefinition());


        return randomKey;

    }

//    void checkIfCorrect(Integer randomKey) {
//
//        System.out.println("Write the correct word");
//        String userInput = scanner.nextLine();
//
//        if (userInput.equalsIgnoreCase(wordsAndDefinitions.get(randomKey).getWord())) {
//            System.out.println("You are correct.");
//            wordsAndDefinitions.get(randomKey).deductOneFromAttempts();
//        } else {
//            wordsAndDefinitions.get(randomKey).addOneToAttempts();
//            System.out.println("Wrong answer.\n rightone is " + wordsAndDefinitions.get(randomKey).getWord());
//        }
//    }

    HashMap<Integer, Word> getWordsAndDefinitions() {
        return wordsAndDefinitions;
    }

    void addWordToHasMap(String line) {
        String[] oneLine = line.split(" ///");
        Word word = new Word(Integer.parseInt(oneLine[0]), oneLine[1], oneLine[2]);
        wordsAndDefinitions.put(keyCounter, word);
        keyCounter++;
    }
}