package io.siencode.flashcards.repo;

import io.siencode.flashcards.entity.SelectedFlashcard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SelectedFlashcardRepository extends JpaRepository<SelectedFlashcard, Long> {
    List<SelectedFlashcard> findAllByLearningHistoryId(Long id);
    void deleteByLearningHistoryId(Long id);
}
