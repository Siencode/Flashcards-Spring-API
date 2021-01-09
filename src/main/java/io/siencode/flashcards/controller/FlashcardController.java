package io.siencode.flashcards.controller;

import io.siencode.flashcards.entity.Flashcard;
import io.siencode.flashcards.model.FlashcardModel;
import io.siencode.flashcards.service.FlashcardCategoryServiceImpl;
import io.siencode.flashcards.service.FlashcardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class FlashcardController {

    private final FlashcardServiceImpl flashcardService;
    private final FlashcardCategoryServiceImpl flashcardCategoryService;

    @Autowired
    public FlashcardController(FlashcardServiceImpl flashcardService, FlashcardCategoryServiceImpl flashcardCategoryService) {
        this.flashcardService = flashcardService;
        this.flashcardCategoryService = flashcardCategoryService;
    }

    @GetMapping("/flashcards")
    public List<Flashcard> getWords() {
        List<Flashcard> flashcards = flashcardService.findAllUserFlashcards();
        if (flashcards == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No flashcard added");
        } else {
            return flashcards;
        }
    }

    @GetMapping("/flashcards/{id}")
    public List<Flashcard> getWords(@PathVariable Long id) {
        List<Flashcard> flashcards = flashcardService.findAllFlashcardsByCategory(id);
        if (flashcards == null || flashcards.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Flashcards do not exist. Category ID:" + id);
        } else {
            return flashcards;
        }
    }

    @PostMapping("/flashcard")
    public void addWord(@Valid @RequestBody FlashcardModel flashcardModel) {
        flashcardService.saveFlashcard(flashcardModel);
    }

    @PutMapping("/flashcard/{id}")
    public void editFlashcard(@Valid @RequestBody FlashcardModel flashcardModel, @PathVariable Long id) {
        if (flashcardService.flashcardIsExist(id) && flashcardCategoryService.userFlashcardCategoryIsExist(flashcardModel.getFlashcardCategoryId())) {
            flashcardService.editFlashcard(id, flashcardModel);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Flashcard or category do not exist");
        }
    }

    @DeleteMapping("/flashcard/{id}")
    public void removeWord(@PathVariable Long id){
        if (flashcardService.flashcardIsExist(id)) {
            flashcardService.deleteFlashcard(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Flashcard does not exist. ID:" + id);
        }
    }
}
