package io.siencode.flashcards.repo;

import io.siencode.flashcards.entity.LearningHistory;
import io.siencode.flashcards.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LearningHistoryRepository extends JpaRepository<LearningHistory, Long> {
   List<LearningHistory> findAllByUser (User user);
   boolean existsByIdAndUserId(Long aLong, Long userId);
}
