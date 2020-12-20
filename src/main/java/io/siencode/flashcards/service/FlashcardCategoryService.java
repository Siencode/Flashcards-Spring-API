package io.siencode.flashcards.service;

import io.siencode.flashcards.entity.FlashcardCategory;
import io.siencode.flashcards.model.FlashcardCategoryModel;

import java.util.List;

public interface FlashcardCategoryService {

    List<FlashcardCategory> findAllFlashcardCategories();
    Boolean flashcardCategoryIsExist(Long id);
    Boolean flashcardCategoryIsExist(String categoryName);
    FlashcardCategory findFlashcardCategoryByID(Long id);
    void saveFlashcardCategories(FlashcardCategoryModel flashcardCategoryModel);
    void editFlashCardCategory(Long id, FlashcardCategoryModel flashcardCategoryModel);
    void deleteFlashcardCategory(Long id);
}
