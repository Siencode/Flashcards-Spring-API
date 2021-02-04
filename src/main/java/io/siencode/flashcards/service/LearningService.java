package io.siencode.flashcards.service;

import io.siencode.flashcards.entity.LearningHistory;
import io.siencode.flashcards.entity.SelectedFlashcard;

public interface LearningService {

    LearningHistory createNewLearning();
    LearningHistory getLastLearning();
    SelectedFlashcard getCurrentFlashcard();
    SelectedFlashcard getNextFlashcardById();
    Long getCurrentFlashcardIdAndChangeStatus(Long learnID);
    void shuffleAndSaveNewSelectedFlashcards(Long categoryId, LearningHistory learningHistory);
    void changeSelectedFlashcardStatus(Long flashcardId,Boolean isSelected);
    void deleteLastSelectedFlashcards();
    int numberOfUserLearning();

}
