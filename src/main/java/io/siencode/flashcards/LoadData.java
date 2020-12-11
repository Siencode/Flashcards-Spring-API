package io.siencode.flashcards;

import io.siencode.flashcards.entity.Account;
import io.siencode.flashcards.entity.FlashcardCategory;
import io.siencode.flashcards.entity.SelectedFlashcard;
import io.siencode.flashcards.entity.Flashcard;
import io.siencode.flashcards.repo.AccountRepository;
import io.siencode.flashcards.repo.FlashcardCategoryRepository;
import io.siencode.flashcards.repo.SelectedFlashcardRepository;
import io.siencode.flashcards.repo.FlashcardRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class LoadData {

    @Bean
    CommandLineRunner commandLineRunner(AccountRepository accountRepository, SelectedFlashcardRepository selectedFlashcardRepository, FlashcardRepository flashcardRepository, FlashcardCategoryRepository flashcardCategoryRepository){
        return args -> {
            Account account = new Account();
            account.setUsername("admin");
            account.setPassword("password");

            FlashcardCategory flashcardCategory = new FlashcardCategory();
            flashcardCategory.setCategoryName("Default");

            Flashcard word = new Flashcard();
            word.setAccount(account);
            word.setFirstSentence("pies");
            word.setSecondSentence("dog");
            word.setFlashcardCategory(flashcardCategory);

            SelectedFlashcard selectedFlashcard = new SelectedFlashcard();
            selectedFlashcard.setLocalDate(LocalDate.now());
            selectedFlashcard.setWordEntity(word);

            accountRepository.save(account);
            flashcardCategoryRepository.save(flashcardCategory);
            flashcardRepository.save(word);
            selectedFlashcardRepository.save(selectedFlashcard);
        };
    }


}
