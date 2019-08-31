package com.karucode.wordquesser;

public class Word {
    private String word;
    private String definition;
    private int attempts;


    public Word(int attempts, String word, String definition) {
        this.word = word;
        this.definition = definition;
        this.attempts = attempts;
    }

    public String getWord() {
        return word;
    }

    public String getDefinition() {
        return definition;
    }

//    public int getAttempts() {
//        return attempts;
//    }
//
//    // should be in another class?
//
//    public void addOneToAttempts() {
//        attempts++;
//    }
//
//    public void deductOneFromAttempts() {
//        if (attempts > 0) {
//            attempts--;
//        }
//    }
}
