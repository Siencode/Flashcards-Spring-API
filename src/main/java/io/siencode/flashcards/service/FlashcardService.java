package io.siencode.flashcards.service;

import io.siencode.flashcards.entity.Flashcard;
import io.siencode.flashcards.entity.FlashcardCategory;
import io.siencode.flashcards.model.FlashcardCategoryModel;
import io.siencode.flashcards.model.FlashcardModel;

import java.util.List;

public interface FlashcardService {

    List<Flashcard> findAllFlashcards();
    Boolean flashcardIsExist(Long id);
    void saveFlashcard(FlashcardModel flashcardModel);
    void editFlashcard(Long id, FlashcardModel flashcardModel);
    void deleteFlashcard(Long id);

    List<FlashcardCategory> findAllFlashcardCategories();
    Boolean flashcardCategoryIsExist(Long id);
    FlashcardCategory findFlashcardCategoryByID(Long id);
    void saveFlashcardCategories(FlashcardCategoryModel flashcardCategoryModel);
    void editFlashCardCategory(Long id, FlashcardCategoryModel flashcardCategoryModel);
    void deleteFlashcardCategory(Long id);

}
