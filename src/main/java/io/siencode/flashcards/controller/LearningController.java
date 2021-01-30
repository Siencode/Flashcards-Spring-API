package io.siencode.flashcards.controller;

import io.siencode.flashcards.entity.LearningHistory;
import io.siencode.flashcards.entity.SelectedFlashcard;
import io.siencode.flashcards.service.LearningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class LearningController {

    private final LearningService learningService;

    @Autowired
    public LearningController(LearningService learningService) {
        this.learningService = learningService;
    }

    @GetMapping("/learn/new")
    public LearningHistory createNewLearning(@RequestParam(name = "category") Long categoryId){
                return learningService.createNewLearning(categoryId);
    }

    @GetMapping("/learn/last")
    public LearningHistory getLastLearning() {
        return learningService.getLastLearning();
    }


    @GetMapping("/learn/flashcard/next")
    public SelectedFlashcard getNextSelectedFlashcard(@RequestParam(name = "id") Long learnID) {
        if (learningService.learningHistoryIdIsExistForUser(learnID)) {
            return learningService.getNextFlashcardById(learnID);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID is not found for user");
        }
    }

    @GetMapping("/learn/flashcard/current")
    public SelectedFlashcard getCurrentSelectedFlashcard(@RequestParam(name = "id") Long learnID) {
        if (learningService.learningHistoryIdIsExistForUser(learnID)) {
            return learningService.getCurrentFlashcard(learnID);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID is not found for user");
        }
    }



}