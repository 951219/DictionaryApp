package com.karucode.wordquesser;

public class Word {
    private String word;
    private String definition;
    private Integer attempts;


    public Word(int attempts, String word, String definition) {
        this.word = word;
        this.definition = definition;
        this.attempts = attempts;
    }

    public Word() {

    }

    public String getWord() {
        return word;
    }


    public void setWord(String word) {
        this.word = word;
    }



    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }


    public Integer getAttempts() {
        return attempts;
    }

    public void setAttempts(Integer attempts){
        this.attempts = attempts;
    }

    //TODO attempts in Word object


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

