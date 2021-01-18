package io.siencode.flashcards.service;

import io.siencode.flashcards.entity.Flashcard;
import io.siencode.flashcards.entity.FlashcardCategory;
import io.siencode.flashcards.model.FlashcardCategoryModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface FlashcardCategoryService {

    List<FlashcardCategory> findAllUserFlashcardCategories();
    Page<Flashcard> findAllByFlashcardCategory(FlashcardCategory flashcardCategory, Pageable pageable);
    Boolean userFlashcardCategoryIsExist(Long id);
    Boolean userFlashcardCategoryIsExist(String categoryName);
    Boolean userFlashcardCategoryIsDefault(Long id);
    Long findUserDefaultCategoryId();
    void saveDefaultFlashcardCategories(String userName);
    void saveFlashcardCategories(FlashcardCategoryModel flashcardCategoryModel);
    void editFlashCardCategory(Long id, FlashcardCategoryModel flashcardCategoryModel);
    void deleteFlashcardCategory(Long id);
    Optional<FlashcardCategory> getOptionalFlashcardCategoryById(Long id);
}
