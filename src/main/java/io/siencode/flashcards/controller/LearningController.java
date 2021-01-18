package io.siencode.flashcards.controller;

import io.siencode.flashcards.entity.Flashcard;
import io.siencode.flashcards.service.FlashcardCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class LearningController {

    private final FlashcardCategoryService flashcardCategoryService;

    @Autowired
    public LearningController(FlashcardCategoryService flashcardCategoryService) {
        this.flashcardCategoryService = flashcardCategoryService;
    }

    @GetMapping("/learn")
    public Page<Flashcard> flashcardPage(@RequestParam(name = "category") Long categoryId, @RequestParam Integer size, @RequestParam(name = "page") Integer pageNumber) {
        try {
            if (flashcardCategoryService.getOptionalFlashcardCategoryById(categoryId).isEmpty())
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category does not exist for user");
                Pageable pageable = PageRequest.of(pageNumber, size, Sort.unsorted());
                Page<Flashcard> page = flashcardCategoryService.findAllByFlashcardCategory(flashcardCategoryService.getOptionalFlashcardCategoryById(categoryId).get(), pageable);
                if (page.isEmpty())
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Page is empty");
                return page;

        } catch (ResponseStatusException responseStatusException) {
            throw responseStatusException;
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}