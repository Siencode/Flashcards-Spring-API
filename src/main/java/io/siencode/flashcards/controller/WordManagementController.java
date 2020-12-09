package io.siencode.flashcards.controller;

import io.siencode.flashcards.entity.Account;
import io.siencode.flashcards.entity.Word;
import io.siencode.flashcards.model.WordModel;
import io.siencode.flashcards.repo.AccountRepository;
import io.siencode.flashcards.repo.WordRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class WordManagementController {

  private AccountRepository accountRepository;
  private WordRepository wordRepository;

    public WordManagementController(AccountRepository accountRepository, WordRepository wordRepository) {
        this.accountRepository = accountRepository;
        this.wordRepository = wordRepository;
    }

    @GetMapping("/words")
    public List<Word> getWords() {
        return wordRepository.findAll();
    }

    @PostMapping("/word")
    public WordModel addWord(@RequestBody WordModel wordModel) {
        Account account = accountRepository.getOne(1l);
        Word word = new Word();
        word.setWord_ENG(wordModel.getWord_ENG());
        word.setWord_PL(wordModel.getWord_PL());
        word.setAccountEntity(account);
        wordRepository.save(word);
        return wordModel;
    }

    @PutMapping("/word/{id}")
    public Word replaceWord(@RequestBody WordModel wordModel, @PathVariable Long id) {
        return wordRepository.findById(id).map(word -> {
            word.setWord_PL(wordModel.getWord_PL());
            word.setWord_ENG(wordModel.getWord_ENG());
            return wordRepository.save(word);
        }).orElseGet(() -> {
           Word word = new Word();
           word.setWord_ENG(wordModel.getWord_ENG());
           word.setWord_PL(wordModel.getWord_PL());
           wordRepository.save(word);
           return word;
        });
    }

    @DeleteMapping("/word/{id}")
    public void removeWord(@PathVariable Long id){
        Optional<Word> optionalWord = wordRepository.findById(id);
        if (optionalWord.isPresent()) {
            wordRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
