package io.siencode.flashcards.repo;

import io.siencode.flashcards.entity.Flashcard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {
}