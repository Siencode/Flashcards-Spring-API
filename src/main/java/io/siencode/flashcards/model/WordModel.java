package io.siencode.flashcards.model;

public class WordModel {

    private String word_PL;
    private String word_ENG;

    public WordModel(String word_PL, String word_ENG) {
        this.word_PL = word_PL;
        this.word_ENG = word_ENG;
    }

    public String getWord_PL() {
        return word_PL;
    }

    public void setWord_PL(String word_PL) {
        this.word_PL = word_PL;
    }

    public String getWord_ENG() {
        return word_ENG;
    }

    public void setWord_ENG(String word_ENG) {
        this.word_ENG = word_ENG;
    }
}
