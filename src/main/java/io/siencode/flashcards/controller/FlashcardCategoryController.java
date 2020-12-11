package io.siencode.flashcards.controller;

import io.siencode.flashcards.entity.FlashcardCategory;
import io.siencode.flashcards.repo.FlashcardCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api")
@Slf4j
public class FlashcardCategoryController {

    private FlashcardCategoryRepository flashcardCategoryRepository;

    public FlashcardCategoryController(FlashcardCategoryRepository flashcardCategoryRepository) {
        this.flashcardCategoryRepository = flashcardCategoryRepository;
    }

    @GetMapping("/categories")
    public List<FlashcardCategory> getFlashcardCategories() {
        return flashcardCategoryRepository.findAll();
    }

    @GetMapping("/category/{id}")
    public FlashcardCategory getFlashcardCategory(@PathVariable Long id) {
        Optional<FlashcardCategory> optional = flashcardCategoryRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/category/{id}")
    public void deleteCategory(@PathVariable Long id) {
        Optional<FlashcardCategory> optional = flashcardCategoryRepository.findById(id);
        if (optional.isPresent()) {
            flashcardCategoryRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/category")
    public FlashcardCategory category(@RequestBody FlashcardCategory category) {
        FlashcardCategory flashcardCategory = new FlashcardCategory();
        flashcardCategory.setCategoryName(category.getCategoryName());
        flashcardCategoryRepository.save(flashcardCategory);
        return flashcardCategory;
    }


}
