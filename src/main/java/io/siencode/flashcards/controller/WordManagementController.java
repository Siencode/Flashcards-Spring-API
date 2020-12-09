package io.siencode.flashcards.controller;

import io.siencode.flashcards.entity.Account;
import io.siencode.flashcards.entity.Word;
import io.siencode.flashcards.model.WordModel;
import io.siencode.flashcards.repo.AccountRepository;
import io.siencode.flashcards.repo.SelectedWordRepository;
import io.siencode.flashcards.repo.WordRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class WordManagementController {

  private AccountRepository accountRepository;
  private SelectedWordRepository selectedWordRepository;
  private WordRepository wordRepository;

    public WordManagementController(AccountRepository accountRepository, SelectedWordRepository selectedWordRepository, WordRepository wordRepository) {
        this.accountRepository = accountRepository;
        this.selectedWordRepository = selectedWordRepository;
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



}
