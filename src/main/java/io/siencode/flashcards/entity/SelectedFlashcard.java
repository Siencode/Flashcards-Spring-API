package io.siencode.flashcards.entity;

import javax.persistence.*;

@Entity
public class SelectedFlashcard {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    @JoinColumn  (name = "FLASHCARD_ID", referencedColumnName = "id")
    private Flashcard  flashcard;
    @ManyToOne
    @JoinColumn (name = "LEARNING_HISTORY_ID", referencedColumnName = "id")
    private LearningHistory learningHistory;
    private Boolean lastSelected;
    public long getId() {
        return id;
    }

    public Flashcard getFlashcard() {
        return flashcard;
    }

    public void setFlashcard(Flashcard flashcard) {
        this.flashcard = flashcard;
    }

    public LearningHistory getLearningHistory() {
        return learningHistory;
    }

    public void setLearningHistory(LearningHistory learningHistory) {
        this.learningHistory = learningHistory;
    }

    public Boolean getLastSelected() {
        return lastSelected;
    }

    public void isLastSelected(Boolean lastSelected) {
        this.lastSelected = lastSelected;
    }
}
