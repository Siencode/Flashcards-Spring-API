package io.siencode.flashcards.controller;

import io.siencode.flashcards.entity.FlashcardCategory;
import io.siencode.flashcards.model.FlashcardCategoryModel;
import io.siencode.flashcards.service.FlashcardCategoryServiceImpl;
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

    private final FlashcardCategoryServiceImpl flashcardCategoryService;

    @Autowired
    public FlashcardCategoryController(FlashcardCategoryServiceImpl flashcardCategoryService) {
        this.flashcardCategoryService = flashcardCategoryService;
    }

    @GetMapping("/categories")
    public List<FlashcardCategory> getFlashcardCategories() {
        List<FlashcardCategory> flashcardCategories = flashcardCategoryService.findAllFlashcardCategories();
        if (flashcardCategories == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            return flashcardCategories;
        }
    }

    @GetMapping("/category/{id}")
    public FlashcardCategory getFlashcardCategory(@PathVariable Long id) {
        if (flashcardCategoryService.flashcardCategoryIsExist(id)) {
            return flashcardCategoryService.findFlashcardCategoryByID(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category does not exist. ID: " + id);
        }
    }

    @DeleteMapping("/category/{id}")
    public void deleteCategory(@PathVariable Long id) {
        if (id == 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The default category cannot be deleted");
        }
        else if (flashcardCategoryService.flashcardCategoryIsExist(id)) {
            flashcardCategoryService.deleteFlashcardCategory(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category does not exist. ID:" + id);
        }
    }

    @PostMapping("/category")
    public void addCategory(@RequestBody FlashcardCategoryModel flashcardCategoryModel) {
        if (flashcardCategoryService.flashcardCategoryIsExist(flashcardCategoryModel.getCategoryName())){
            flashcardCategoryService.saveFlashcardCategories(flashcardCategoryModel);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The category exists. Can't be duplicated.");
        } else {
            flashcardCategoryService.saveFlashcardCategories(flashcardCategoryModel);
        }
    }

    @PutMapping("/category/{id}")
    public void editCategory(@PathVariable Long id, @RequestBody FlashcardCategoryModel flashcardCategoryModel) {
        if (flashcardCategoryService.flashcardCategoryIsExist(id)) {
            flashcardCategoryService.editFlashCardCategory(id, flashcardCategoryModel);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category does not exist. ID: " + id);
        }
    }

}
