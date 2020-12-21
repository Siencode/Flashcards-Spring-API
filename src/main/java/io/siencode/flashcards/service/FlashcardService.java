package io.siencode.flashcards.service;

import io.siencode.flashcards.entity.Flashcard;
import io.siencode.flashcards.model.FlashcardModel;

import java.util.List;

public interface FlashcardService {

    List<Flashcard> findAllUserFlashcards();
    Boolean flashcardIsExist(Long id);
    void saveFlashcard(FlashcardModel flashcardModel);
    void editFlashcard(Long id, FlashcardModel flashcardModel);
    void deleteFlashcard(Long id);
    List<Flashcard> findAllFlashcardsByCategory(Long categoryId);


}
