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
             UserRepository userRepository, BCryptEncoderConfig bCryptEncoderConfig, FlashcardCategoryRepository flashcardCategoryRepository){
        return args -> {

            User user = new User();
            user.setUsername("Administrator");
            user.setPassword(bCryptEncoderConfig.passwordEncoder().encode("password"));
            user.grantAuthority(Role.ROLE_USER);


            FlashcardCategory flashcardCategory = new FlashcardCategory();
            flashcardCategory.setCategoryName("default");

            userRepository.save(user);
            flashcardCategoryRepository.save(flashcardCategory);
        };
    }


}
