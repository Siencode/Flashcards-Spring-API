package io.siencode.flashcards.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class FlashcardModel {

    @NotNull(message = "First sentence cannot be null")
    @Size(min = 1, max = 200, message = "Incorrect first sentence length")
    private String firstSentence;
    @Size(min = 1, max = 200, message = "Incorrect second sentence length")
    @NotNull(message = "Second sentence cannot be null")
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
