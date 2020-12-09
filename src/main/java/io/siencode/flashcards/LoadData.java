package io.siencode.flashcards;

import io.siencode.flashcards.entity.Account;
import io.siencode.flashcards.entity.SelectedWord;
import io.siencode.flashcards.entity.Word;
import io.siencode.flashcards.repo.AccountRepository;
import io.siencode.flashcards.repo.SelectedWordRepository;
import io.siencode.flashcards.repo.WordRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class LoadData {

    @Bean
    CommandLineRunner commandLineRunner(AccountRepository accountRepository, SelectedWordRepository selectedWordRepository, WordRepository wordRepository){
        return args -> {
            Account account = new Account();
            account.setUsername("admin");
            account.setPassword("password");

            Word word = new Word();
            word.setAccountEntity(account);
            word.setWord_ENG("dog");
            word.setWord_PL("pies");

            SelectedWord selectedWord = new SelectedWord();
            selectedWord.setLocalDate(LocalDate.now());
            selectedWord.setWordEntity(word);

            accountRepository.save(account);
            wordRepository.save(word);
            selectedWordRepository.save(selectedWord);
        };
    }


}
