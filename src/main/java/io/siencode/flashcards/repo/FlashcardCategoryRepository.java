package io.siencode.flashcards.repo;

import io.siencode.flashcards.entity.FlashcardCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlashcardCategoryRepository extends JpaRepository<FlashcardCategory, Long> {
}
