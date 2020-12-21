package io.siencode.flashcards.service;

import io.siencode.flashcards.entity.FlashcardCategory;
import io.siencode.flashcards.entity.User;
import io.siencode.flashcards.model.FlashcardCategoryModel;
import io.siencode.flashcards.repo.FlashcardCategoryRepository;
import io.siencode.flashcards.repo.FlashcardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlashcardCategoryServiceImpl implements FlashcardCategoryService{

    private final FlashcardRepository flashcardRepository;
    private final FlashcardCategoryRepository flashcardCategoryRepository;
    private final UserService userService;

    public FlashcardCategoryServiceImpl(FlashcardRepository flashcardRepository, FlashcardCategoryRepository flashcardCategoryRepository, UserService userService) {
        this.flashcardRepository = flashcardRepository;
        this.flashcardCategoryRepository = flashcardCategoryRepository;
        this.userService = userService;
    }

    @Override
    public List<FlashcardCategory> findAllUserFlashcardCategories() {
        return flashcardCategoryRepository.findAllByUser(userService.getAuthorizedUser());
    }

    @Override
    public Boolean flashcardCategoryIsExist(Long id) {
        List<FlashcardCategory> flashcardCategories = findAllUserFlashcardCategories();
        if (id == 1) {
            return true;
        } else if (flashcardCategories == null || flashcardCategories.isEmpty()) {
            return false;
        } else {
            return flashcardCategories.stream().anyMatch(flashcardCategory -> flashcardCategory.getId() == id);
        }
    }

    @Override
    public Boolean flashcardCategoryIsExist(String categoryName) {
        List<FlashcardCategory> flashcardCategories = findAllUserFlashcardCategories();
        if (flashcardCategories == null || flashcardCategories.isEmpty()) {
            return false;
        } else {
            return flashcardCategories.stream().anyMatch(flashcardCategory -> flashcardCategory.getCategoryName().equals(categoryName));
        }
    }

    @Override
    public FlashcardCategory findFlashcardCategoryByID(Long id) {
        return flashcardCategoryRepository.findById(id).get();
    }

    @Override
    public void saveFlashcardCategories(FlashcardCategoryModel flashcardCategoryModel) {
        FlashcardCategory flashcardCategory = new FlashcardCategory();
        flashcardCategory.setCategoryName(flashcardCategoryModel.getCategoryName());
        flashcardCategory.setUser(userService.getAuthorizedUser());
        flashcardCategoryRepository.save(flashcardCategory);
    }

    @Override
    public void editFlashCardCategory(Long id, FlashcardCategoryModel flashcardCategoryModel) {
        flashcardCategoryRepository.findById(id).map(flashcardCategory -> {
            flashcardCategory.setCategoryName(flashcardCategoryModel.getCategoryName());
            flashcardCategory.setUser(userService.getAuthorizedUser());
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
