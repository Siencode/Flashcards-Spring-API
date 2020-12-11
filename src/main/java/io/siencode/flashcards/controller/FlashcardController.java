package io.siencode.flashcards.controller;

import io.siencode.flashcards.entity.Account;
import io.siencode.flashcards.entity.Flashcard;
import io.siencode.flashcards.entity.FlashcardCategory;
import io.siencode.flashcards.model.FlashcardModel;
import io.siencode.flashcards.repo.AccountRepository;
import io.siencode.flashcards.repo.FlashcardCategoryRepository;
import io.siencode.flashcards.repo.FlashcardRepository;
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
public class FlashcardController {

  private AccountRepository accountRepository;
  private FlashcardRepository flashcardRepository;
  private FlashcardCategoryRepository flashcardCategoryRepository;

    public FlashcardController(AccountRepository accountRepository, FlashcardRepository flashcardRepository, FlashcardCategoryRepository flashcardCategoryRepository) {
        this.accountRepository = accountRepository;
        this.flashcardRepository = flashcardRepository;
        this.flashcardCategoryRepository = flashcardCategoryRepository;
    }

    @GetMapping("/flashcards")
    public List<Flashcard> getWords() {
        return flashcardRepository.findAll();
    }

    @PostMapping("/flashcard")
    public FlashcardModel addWord(@RequestBody FlashcardModel flashcardModel) {
        Account account = accountRepository.getOne(1l);
        Flashcard flashcard = new Flashcard();
        flashcard.setFirstSentence(flashcardModel.getFirstSentence());
        flashcard.setSecondSentence(flashcardModel.getSecondSentence());
        flashcard.setAccount(account);
        if (flashcardCategoryRepository.existsById(flashcardModel.getFlashcardCategoryId())) {
            flashcard.setFlashcardCategory(flashcardCategoryRepository.getOne(flashcardModel.getFlashcardCategoryId()));
        } else {
            flashcard.setFlashcardCategory(flashcardCategoryRepository.getOne(1l));
        }
        flashcardRepository.save(flashcard);
        return flashcardModel;
    }

    @PutMapping("/flashcard/{id}")
    public Flashcard replaceWord(@RequestBody FlashcardModel flashcardModel, @PathVariable Long id) {
        return flashcardRepository.findById(id).map(word -> {
            word.setFirstSentence(flashcardModel.getFirstSentence());
            word.setSecondSentence(flashcardModel.getSecondSentence());
            return flashcardRepository.save(word);
        }).orElseGet(() -> {
           Flashcard flashcard = new Flashcard();
            flashcard.setFirstSentence(flashcardModel.getFirstSentence());
            flashcard.setSecondSentence(flashcardModel.getSecondSentence());
           flashcardRepository.save(flashcard);
           return flashcard;
        });
    }

    @DeleteMapping("/flashcard/{id}")
    public void removeWord(@PathVariable Long id){
        Optional<Flashcard> optionalWord = flashcardRepository.findById(id);
        if (optionalWord.isPresent()) {
            flashcardRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
