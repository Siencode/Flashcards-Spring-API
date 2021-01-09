package io.siencode.flashcards.repo;

import io.siencode.flashcards.entity.FlashcardCategory;
import io.siencode.flashcards.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FlashcardCategoryRepository extends JpaRepository<FlashcardCategory, Long> {
    List<FlashcardCategory> findAllByUser(User user);
    Optional<FlashcardCategory> findByCategoryName(String categoryName);
}
