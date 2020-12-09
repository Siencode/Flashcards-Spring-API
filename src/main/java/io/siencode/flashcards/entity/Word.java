package io.siencode.flashcards.entity;

import javax.persistence.*;

@Entity
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String word_PL;
    private String word_ENG;
    @ManyToOne
    @JoinColumn (name = "ACC_ID", referencedColumnName = "id")
    Account account;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Account getAccountEntity() {
        return account;
    }

    public void setAccountEntity(Account account) {
        this.account = account;
    }
}
