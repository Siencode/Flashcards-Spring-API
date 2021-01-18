package io.siencode.flashcards.repo;

import io.siencode.flashcards.entity.Flashcard;
import io.siencode.flashcards.entity.FlashcardCategory;
import io.siencode.flashcards.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface FlashcardRepository extends PagingAndSortingRepository<Flashcard, Long> {
    List<Flashcard> findAllByUser(User user);
    List<Flashcard> findAll();
    Page<Flashcard> findAllByFlashcardCategory(FlashcardCategory flashcardCategory, Pageable pageable);
}