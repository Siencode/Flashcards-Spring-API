package io.siencode.flashcards.repo;

import io.siencode.flashcards.entity.SelectedWord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SelectedWordRepository extends JpaRepository<SelectedWord, Long> {
}
