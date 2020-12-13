package io.siencode.flashcards.controller;

import io.siencode.flashcards.entity.Flashcard;
import io.siencode.flashcards.model.FlashcardModel;
import io.siencode.flashcards.service.FlashcardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class FlashcardController {

    private final FlashcardServiceImpl flashcardService;

    @Autowired
    public FlashcardController(FlashcardServiceImpl flashcardService) {
        this.flashcardService = flashcardService;
    }

    @GetMapping("/flashcards")
    public List<Flashcard> getWords() {
        List<Flashcard> flashcards = flashcardService.findAllFlashcards();
        if (flashcards == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            return flashcards;
        }
    }

    @PostMapping("/flashcard")
    public void addWord(@RequestBody FlashcardModel flashcardModel) {
        flashcardService.saveFlashcard(flashcardModel);
    }

    @PutMapping("/flashcard/{id}")
    public void editFlashcard(@RequestBody FlashcardModel flashcardModel, @PathVariable Long id) {
        if (flashcardService.flashcardIsExist(id)) {
            flashcardService.editFlashcard(id, flashcardModel);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/flashcard/{id}")
    public void removeWord(@PathVariable Long id){
        if (flashcardService.flashcardIsExist(id)) {
            flashcardService.deleteFlashcard(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
