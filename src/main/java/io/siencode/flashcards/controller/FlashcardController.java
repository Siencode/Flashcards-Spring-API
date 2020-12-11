package io.siencode.flashcards.controller;

import io.siencode.flashcards.entity.Account;
import io.siencode.flashcards.entity.Flashcard;
import io.siencode.flashcards.model.FlashcardModel;
import io.siencode.flashcards.repo.AccountRepository;
import io.siencode.flashcards.repo.FlashcardRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class FlashcardController {

  private AccountRepository accountRepository;
  private FlashcardRepository flashcardRepository;

    public FlashcardController(AccountRepository accountRepository, FlashcardRepository flashcardRepository) {
        this.accountRepository = accountRepository;
        this.flashcardRepository = flashcardRepository;
    }

    @GetMapping("/flashcards")
    public List<Flashcard> getWords() {
        return flashcardRepository.findAll();
    }

    @PostMapping("/flashcard")
    public FlashcardModel addWord(@RequestBody FlashcardModel flashcardModel) {
        Account account = accountRepository.getOne(1l);
        Flashcard word = new Flashcard();
        word.setFirstSentence(flashcardModel.getFirstSentence());
        word.setSecondSentence(flashcardModel.getSecondSentence());
        word.setAccountEntity(account);
        flashcardRepository.save(word);
        return flashcardModel;
    }

    @PutMapping("/flashcard/{id}")
    public Flashcard replaceWord(@RequestBody FlashcardModel flashcardModel, @PathVariable Long id) {
        return flashcardRepository.findById(id).map(word -> {
            word.setFirstSentence(flashcardModel.getFirstSentence());
            word.setSecondSentence(flashcardModel.getSecondSentence());
            return flashcardRepository.save(word);
        }).orElseGet(() -> {
           Flashcard word = new Flashcard();
           word.setFirstSentence(flashcardModel.getFirstSentence());
           word.setSecondSentence(flashcardModel.getSecondSentence());
           flashcardRepository.save(word);
           return word;
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
