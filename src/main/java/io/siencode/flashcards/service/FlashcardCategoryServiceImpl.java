package io.siencode.flashcards.service;

import io.siencode.flashcards.entity.Flashcard;
import io.siencode.flashcards.entity.FlashcardCategory;
import io.siencode.flashcards.entity.User;
import io.siencode.flashcards.model.FlashcardCategoryModel;
import io.siencode.flashcards.repo.FlashcardCategoryRepository;
import io.siencode.flashcards.repo.FlashcardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

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
    public Page<Flashcard> findAllByFlashcardCategory(FlashcardCategory flashcardCategory, Pageable pageable) {
        return flashcardRepository.findAllByFlashcardCategory(flashcardCategory, pageable);
    }

    @Override
    public Boolean userFlashcardCategoryIsExist(Long id) {
        if (flashcardCategoryRepository.existsById(id)) {
            String authorizedUserUsername = userService.getAuthorizedUser().getUsername();
            return flashcardCategoryRepository.getOne(id).getUser().getUsername().equals(authorizedUserUsername);
        } else {
            return false;
        }
    }

    @Override
    public Boolean userFlashcardCategoryIsExist(String categoryName) {
        List<FlashcardCategory> flashcardCategories = findAllUserFlashcardCategories();
        if (flashcardCategories == null) {
            return false;
        } else if (categoryName.toLowerCase().equals("Default")) {
            return true;
        } else {
            return flashcardCategories.stream().anyMatch(flashcardCategory -> flashcardCategory.getCategoryName().equals(categoryName));
        }
    }

    @Override
    public Boolean userFlashcardCategoryIsDefault(Long id) {
        return flashcardCategoryRepository.getOne(id).getCategoryName().equals("Default");
    }

    @Override
    public Long findUserDefaultCategoryId() {
        List<FlashcardCategory> flashcardCategoryList = flashcardCategoryRepository.findAllByUser(userService.getAuthorizedUser());
        OptionalLong optionalLong = flashcardCategoryList.stream().filter(flashcardCategory -> flashcardCategory.getCategoryName().equals("Default")).mapToLong(FlashcardCategory::getId).findFirst();
        if (optionalLong.isPresent()) {
            return optionalLong.getAsLong();
        } else {
            throw new NullPointerException();
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
        Long categoryId = findUserDefaultCategoryId();
        FlashcardCategory defaultFlashcardCategory = flashcardCategoryRepository.findById(categoryId).get();
        FlashcardCategory flashcardCategoryToDelete = flashcardCategoryRepository.findById(id).get();
        // modification of flashcards to default categories before deletion
        flashcardRepository.findAll().stream()
                .filter(flashcard -> flashcard.getFlashcardCategory().getId() == flashcardCategoryToDelete.getId())
                .forEach(flashcard -> flashcard.setFlashcardCategory(defaultFlashcardCategory));
        flashcardCategoryRepository.deleteById(id);
    }

    @Override
    public Optional<FlashcardCategory> getOptionalFlashcardCategoryById(Long id) {
        return flashcardCategoryRepository.findById(id);
    }
}
