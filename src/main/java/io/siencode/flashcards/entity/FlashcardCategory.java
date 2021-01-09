package io.siencode.flashcards.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class FlashcardCategory {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    private String categoryName;

    @JsonIgnore
    @ManyToOne
    @JoinColumn (name = "USER_ID", referencedColumnName = "id")
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
