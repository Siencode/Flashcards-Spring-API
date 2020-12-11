package io.siencode.flashcards.model;

public class FlashcardModel {

    private String firstSentence;
    private String secondSentence;
    private long flashcardCategoryId;

    public FlashcardModel(String firstSentence, String secondSentence, long flashcardCategoryId) {
        this.firstSentence = firstSentence;
        this.secondSentence = secondSentence;
        this.flashcardCategoryId = flashcardCategoryId;
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

    public long getFlashcardCategoryId() {
        return flashcardCategoryId;
    }

    public void setFlashcardCategoryId(long flashcardCategoryId) {
        this.flashcardCategoryId = flashcardCategoryId;
    }
}
