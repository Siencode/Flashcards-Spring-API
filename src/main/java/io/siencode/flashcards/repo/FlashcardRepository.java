package io.siencode.flashcards.repo;

import io.siencode.flashcards.entity.Flashcard;
import io.siencode.flashcards.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {
    List<Flashcard> findAllByUser(User user);

}