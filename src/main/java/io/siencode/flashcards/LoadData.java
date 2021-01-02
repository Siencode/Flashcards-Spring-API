package io.siencode.flashcards;

import io.siencode.flashcards.config.BCryptEncoderConfig;
import io.siencode.flashcards.entity.FlashcardCategory;
import io.siencode.flashcards.entity.Flashcard;
import io.siencode.flashcards.entity.Role;
import io.siencode.flashcards.entity.User;
import io.siencode.flashcards.repo.FlashcardCategoryRepository;
import io.siencode.flashcards.repo.FlashcardRepository;
import io.siencode.flashcards.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadData {

    @Bean
    CommandLineRunner commandLineRunner(
             UserRepository userRepository, BCryptEncoderConfig bCryptEncoderConfig, FlashcardRepository flashcardRepository, FlashcardCategoryRepository flashcardCategoryRepository){
        return args -> {

            User user = new User();
            user.setUsername("user");
            user.setPassword(bCryptEncoderConfig.passwordEncoder().encode("password"));
            user.grantAuthority(Role.ROLE_USER);


            FlashcardCategory flashcardCategory = new FlashcardCategory();
            flashcardCategory.setCategoryName("default");

            Flashcard word = new Flashcard();
            word.setUser(user);
            word.setFirstSentence("pies");
            word.setSecondSentence("dog");
            word.setFlashcardCategory(flashcardCategory);

            userRepository.save(user);
            flashcardCategoryRepository.save(flashcardCategory);
            flashcardRepository.save(word);

        };
    }


}
