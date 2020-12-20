package io.siencode.flashcards;

import io.siencode.flashcards.config.BCryptEncoderConfig;
import io.siencode.flashcards.entity.FlashcardCategory;
import io.siencode.flashcards.entity.Flashcard;
import io.siencode.flashcards.entity.Role;
import io.siencode.flashcards.entity.User;
import io.siencode.flashcards.repo.FlashcardCategoryRepository;
import io.siencode.flashcards.repo.SelectedFlashcardRepository;
import io.siencode.flashcards.repo.FlashcardRepository;
import io.siencode.flashcards.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadData {

    @Bean
    CommandLineRunner commandLineRunner(
            SelectedFlashcardRepository selectedFlashcardRepository, UserRepository userRepository, BCryptEncoderConfig bCryptEncoderConfig, FlashcardRepository flashcardRepository, FlashcardCategoryRepository flashcardCategoryRepository){
        return args -> {

            User user = new User();
            user.setUsername("Admin");
            user.setPassword(bCryptEncoderConfig.passwordEncoder().encode("password"));
            user.grantAuthority(Role.ROLE_ADMIN);


            FlashcardCategory flashcardCategory = new FlashcardCategory();
            flashcardCategory.setCategoryName("Default");

            Flashcard word = new Flashcard();
            word.setAccount(user);
            word.setFirstSentence("pies");
            word.setSecondSentence("dog");
            word.setFlashcardCategory(flashcardCategory);

            /* learning mode
            SelectedFlashcard selectedFlashcard = new SelectedFlashcard();
            selectedFlashcard.setLocalDate(LocalDate.now());
            selectedFlashcard.setWordEntity(word);
            selectedFlashcardRepository.save(selectedFlashcard);*/

            userRepository.save(user);
            flashcardCategoryRepository.save(flashcardCategory);
            flashcardRepository.save(word);

        };
    }


}
