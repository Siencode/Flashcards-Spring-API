package io.siencode.flashcards.repo;

import io.siencode.flashcards.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordRepository extends JpaRepository<Word, Long> {
}