package io.siencode.flashcards.controller;

import io.siencode.flashcards.entity.FlashcardCategory;
import io.siencode.flashcards.model.FlashcardCategoryModel;
import io.siencode.flashcards.service.FlashcardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class FlashcardCategoryController {

    private final FlashcardServiceImpl flashcardService;

    @Autowired
    public FlashcardCategoryController(FlashcardServiceImpl flashcardService) {
        this.flashcardService = flashcardService;
    }

    @GetMapping("/categories")
    public List<FlashcardCategory> getFlashcardCategories() {
        List<FlashcardCategory> flashcardCategories = flashcardService.findAllFlashcardCategories();
        if (flashcardCategories == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            return flashcardCategories;
        }
    }

    @GetMapping("/category/{id}")
    public FlashcardCategory getFlashcardCategory(@PathVariable Long id) {
        if (flashcardService.flashcardCategoryIsExist(id)) {
            return flashcardService.findFlashcardCategoryByID(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/category/{id}")
    public void deleteCategory(@PathVariable Long id) {
        if (flashcardService.flashcardCategoryIsExist(id)) {
            flashcardService.deleteFlashcardCategory(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/category")
    public void addCategory(@RequestBody FlashcardCategoryModel flashcardCategoryModel) {
        flashcardService.saveFlashcardCategories(flashcardCategoryModel);
    }

    @PutMapping("/category/{id}")
    public void editCategory(@PathVariable Long id, @RequestBody FlashcardCategoryModel flashcardCategoryModel) {
        if (flashcardService.flashcardCategoryIsExist(id)) {
            flashcardService.editFlashCardCategory(id, flashcardCategoryModel);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
