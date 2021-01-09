package io.siencode.flashcards.service;

import io.siencode.flashcards.entity.FlashcardCategory;
import io.siencode.flashcards.model.FlashcardCategoryModel;

import java.util.List;

public interface FlashcardCategoryService {

    List<FlashcardCategory> findAllUserFlashcardCategories();
    Boolean userFlashcardCategoryIsExist(Long id);
    Boolean userFlashcardCategoryIsExist(String categoryName);
    Boolean userFlashcardCategoryIsDefault(Long id);
    Long findUserDefaultCategoryId();
    void saveDefaultFlashcardCategories(String userName);
    void saveFlashcardCategories(FlashcardCategoryModel flashcardCategoryModel);
    void editFlashCardCategory(Long id, FlashcardCategoryModel flashcardCategoryModel);
    void deleteFlashcardCategory(Long id);
}
