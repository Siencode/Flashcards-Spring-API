package io.siencode.flashcards.entity;

import javax.persistence.*;

@Entity
public class Flashcard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstSentence;
    private String secondSentence;
    @ManyToOne
    @JoinColumn (name = "ACC_ID", referencedColumnName = "id")
    private Account account;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public FlashcardCategory getFlashcardCategory() {
        return flashcardCategory;
    }

    public void setFlashcardCategory(FlashcardCategory flashcardCategory) {
        this.flashcardCategory = flashcardCategory;
    }
}
