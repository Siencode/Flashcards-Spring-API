package io.siencode.flashcards.entity;

import javax.persistence.*;

@Entity
public class SelectedFlashcard {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private long id;
    private String firstSentence;
    private String secondSentence;
    @ManyToOne
    @JoinColumn (name = "LEARNING_HISTORY_ID", referencedColumnName = "id")
    private LearningHistory learningHistory;
    private Boolean lastSelected;
    public long getId() {
        return id;
    }

    public LearningHistory getLearningHistory() {
        return learningHistory;
    }

    public void setLearningHistory(LearningHistory learningHistory) {
        this.learningHistory = learningHistory;
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

    public Boolean getLastSelected() {
        return lastSelected;
    }

    public void isLastSelected(Boolean lastSelected) {
        this.lastSelected = lastSelected;
    }
}
