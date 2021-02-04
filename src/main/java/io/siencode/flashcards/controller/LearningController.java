package io.siencode.flashcards.controller;

import io.siencode.flashcards.entity.LearningHistory;
import io.siencode.flashcards.entity.SelectedFlashcard;
import io.siencode.flashcards.service.implementation.LearningServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class LearningController {

    private final LearningServiceImpl learningServiceImpl;

    @Autowired
    public LearningController(LearningServiceImpl learningServiceImpl) {
        this.learningServiceImpl = learningServiceImpl;
    }

    @GetMapping("/learn/new")
    public void createNewLearning(@RequestParam(name = "category") Long categoryId) {
        if (learningServiceImpl.numberOfUserLearning() > 0)
            learningServiceImpl.deleteLastSelectedFlashcards();
        LearningHistory newLearningHistory = learningServiceImpl.createNewLearning();
        learningServiceImpl.shuffleAndSaveNewSelectedFlashcards(categoryId, newLearningHistory);
    }

    @GetMapping("/learn/last")
    public LearningHistory getLastLearning() {
        return learningServiceImpl.getLastLearning();
    }


    @GetMapping("/learn/flashcard/next")
    public SelectedFlashcard getNextSelectedFlashcard() {
            return learningServiceImpl.getNextFlashcardById();
    }

    @GetMapping("/learn/flashcard/current")
    public SelectedFlashcard getCurrentSelectedFlashcard() {
            return learningServiceImpl.getCurrentFlashcard();
    }

}