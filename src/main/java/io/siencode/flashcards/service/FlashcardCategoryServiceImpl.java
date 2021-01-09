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
        // default category id = 1
        if (id == 1) {
            return true;
        } else if (flashcardRepository.existsById(id)) {
            String authorizedUserUsername = userService.getAuthorizedUser().getUsername();
            return flashcardRepository.getOne(id).getUser().getUsername().equals(authorizedUserUsername);
        } else {
            return false;
        }
    }

    @Override
    public Boolean flashcardCategoryIsExist(String categoryName) {
        List<FlashcardCategory> flashcardCategories = findAllUserFlashcardCategories();
        if (flashcardCategories == null) {
            return false;
        } else if (categoryName.toLowerCase().equals("default")) {
            return true;
        } else {
            return flashcardCategories.stream().anyMatch(flashcardCategory -> flashcardCategory.getCategoryName().equals(categoryName));
        }
    }

    @Override
    public void saveDefaultFlashcardCategories(String userName) {
        User user = userService.getUserByUsername(userName);
        FlashcardCategory defaultFlashcardCategory = new FlashcardCategory();
        defaultFlashcardCategory.setCategoryName("Default");
        defaultFlashcardCategory.setUser(user);
        flashcardCategoryRepository.save(defaultFlashcardCategory);
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
