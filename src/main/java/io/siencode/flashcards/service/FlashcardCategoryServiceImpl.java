package io.siencode.flashcards.service;

import io.siencode.flashcards.entity.FlashcardCategory;
import io.siencode.flashcards.model.FlashcardCategoryModel;
import io.siencode.flashcards.repo.FlashcardCategoryRepository;
import io.siencode.flashcards.repo.FlashcardRepository;
import io.siencode.flashcards.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlashcardCategoryServiceImpl implements FlashcardCategoryService{

    private final FlashcardRepository flashcardRepository;
    private final FlashcardCategoryRepository flashcardCategoryRepository;

    @Autowired
    public FlashcardCategoryServiceImpl(FlashcardRepository flashcardRepository, FlashcardCategoryRepository flashcardCategoryRepository) {
        this.flashcardRepository = flashcardRepository;
        this.flashcardCategoryRepository = flashcardCategoryRepository;
    }

    @Override
    public List<FlashcardCategory> findAllFlashcardCategories() {
        return flashcardCategoryRepository.findAll();
    }

    @Override
    public Boolean flashcardCategoryIsExist(Long id) {
        return flashcardCategoryRepository.existsById(id);
    }

    @Override
    public Boolean flashcardCategoryIsExist(String categoryName) {
        return flashcardCategoryRepository.existsByCategoryName(categoryName);
    }

    @Override
    public FlashcardCategory findFlashcardCategoryByID(Long id) {
        return flashcardCategoryRepository.findById(id).get();
    }

    @Override
    public void saveFlashcardCategories(FlashcardCategoryModel flashcardCategoryModel) {
        FlashcardCategory flashcardCategory = new FlashcardCategory();
        flashcardCategory.setCategoryName(flashcardCategoryModel.getCategoryName());
        flashcardCategoryRepository.save(flashcardCategory);
    }

    @Override
    public void editFlashCardCategory(Long id, FlashcardCategoryModel flashcardCategoryModel) {
        flashcardCategoryRepository.findById(id).map(flashcardCategory -> {
            flashcardCategory.setCategoryName(flashcardCategoryModel.getCategoryName());
            return flashcardCategoryRepository.save(flashcardCategory);
        }).orElseThrow();
    }

    @Override
    public void deleteFlashcardCategory(Long id) {
        FlashcardCategory defaultFlashcardCategory = flashcardCategoryRepository.findById(1l).get();
        FlashcardCategory flashcardCategoryToDelete = flashcardCategoryRepository.findById(id).get();
        // modification of flashcards to default categories before deletion
        flashcardRepository.findAll().stream()
                .filter(flashcard -> flashcard.getFlashcardCategory().getId() == flashcardCategoryToDelete.getId())
                .forEach(flashcard -> flashcard.setFlashcardCategory(defaultFlashcardCategory));
        flashcardCategoryRepository.deleteById(id);
    }
}
