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
    Account account;

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

    public Account getAccountEntity() {
        return account;
    }

    public void setAccountEntity(Account account) {
        this.account = account;
    }
}
