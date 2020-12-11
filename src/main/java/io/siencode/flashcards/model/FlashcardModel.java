package io.siencode.flashcards.model;

public class FlashcardModel {

    private String firstSentence;
    private String secondSentence;

    public FlashcardModel(String firstSentence, String secondSentence) {
        this.firstSentence = firstSentence;
        this.secondSentence = secondSentence;
    }

    public String getFirstSentence() {
        return firstSentence;
    }

    public void setFirstSentence(String firstSentence) {
        this.firstSentence = firstSentence;
    }

    public String getSecondSentence() {
        return secondSentence;
    }

    public void setSecondSentence(String secondSentence) {
        this.secondSentence = secondSentence;
    }
}
