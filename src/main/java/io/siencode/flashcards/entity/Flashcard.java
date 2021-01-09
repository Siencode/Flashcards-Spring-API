package io.siencode.flashcards.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Flashcard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstSentence;
    private String secondSentence;
    @JsonIgnore
    @ManyToOne
    @JoinColumn (name = "USER_ID", referencedColumnName = "id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "id")
    private FlashcardCategory flashcardCategory;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public FlashcardCategory getFlashcardCategory() {
        return flashcardCategory;
    }

    public void setFlashcardCategory(FlashcardCategory flashcardCategory) {
        this.flashcardCategory = flashcardCategory;
    }
}
