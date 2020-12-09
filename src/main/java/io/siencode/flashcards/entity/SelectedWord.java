package io.siencode.flashcards.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class SelectedWord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDate localDate;
    @ManyToOne
    @JoinColumn (name = "WORD_ID", referencedColumnName = "id")
    Word wordEntity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public Word getWordEntity() {
        return wordEntity;
    }

    public void setWordEntity(Word wordEntity) {
        this.wordEntity = wordEntity;
    }
}
