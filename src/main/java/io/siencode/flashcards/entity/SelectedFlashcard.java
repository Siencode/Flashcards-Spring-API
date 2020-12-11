package io.siencode.flashcards.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class SelectedFlashcard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDate localDate;
    @ManyToOne
    @JoinColumn (name = "FLASHCARD_ID", referencedColumnName = "id")
    Flashcard wordEntity;

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

    public Flashcard getWordEntity() {
        return wordEntity;
    }

    public void setWordEntity(Flashcard wordEntity) {
        this.wordEntity = wordEntity;
    }
}
