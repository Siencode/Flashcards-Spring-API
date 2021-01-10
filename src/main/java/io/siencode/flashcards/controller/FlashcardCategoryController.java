package io.siencode.flashcards.controller;

import io.siencode.flashcards.entity.FlashcardCategory;
import io.siencode.flashcards.model.FlashcardCategoryModel;
import io.siencode.flashcards.service.FlashcardCategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
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
    public List<FlashcardCategory> getUserFlashcardCategories() {
        List<FlashcardCategory> flashcardCategories = flashcardCategoryService.findAllUserFlashcardCategories();
        if (flashcardCategories == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            return flashcardCategories;
        }
    }

    @DeleteMapping("/category/{id}")
    public void deleteCategory(@PathVariable Long id) {
         if (flashcardCategoryService.userFlashcardCategoryIsExist(id)) {
             if (flashcardCategoryService.userFlashcardCategoryIsDefault(id)) {
                 throw new ResponseStatusException(HttpStatus.CONFLICT, "The default category cannot be deleted");
             } else {
                 flashcardCategoryService.deleteFlashcardCategory(id);
             }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category does not exist for user. ID:" + id);
        }
    }

    @PostMapping("/category")
    public void addCategory(@Valid @RequestBody FlashcardCategoryModel flashcardCategoryModel) {
        if (flashcardCategoryService.userFlashcardCategoryIsExist(flashcardCategoryModel.getCategoryName())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "The category exists. Can't be duplicated.");
        } else {
            flashcardCategoryService.saveFlashcardCategories(flashcardCategoryModel);
        }
    }

    @PutMapping("/category/{id}")
    public void editCategory(@PathVariable Long id, @Valid @RequestBody FlashcardCategoryModel flashcardCategoryModel) {
         if (flashcardCategoryService.userFlashcardCategoryIsExist(id)) {
             if (flashcardCategoryService.userFlashcardCategoryIsExist(flashcardCategoryModel.getCategoryName())) {
                 throw new ResponseStatusException(HttpStatus.CONFLICT, "The category exists. Can't be duplicated.");
             } else if (flashcardCategoryService.userFlashcardCategoryIsDefault(id)) {
                 throw new ResponseStatusException(HttpStatus.CONFLICT, "The default category cannot be edited");
             } else {
                 flashcardCategoryService.editFlashCardCategory(id, flashcardCategoryModel);
             }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category does not exist for user. ID: " + id);
        }
    }

}
