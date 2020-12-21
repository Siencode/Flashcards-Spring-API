package io.siencode.flashcards.service;

import io.siencode.flashcards.entity.Flashcard;
import io.siencode.flashcards.entity.FlashcardCategory;
import io.siencode.flashcards.entity.User;
import io.siencode.flashcards.model.FlashcardModel;
import io.siencode.flashcards.repo.FlashcardCategoryRepository;
import io.siencode.flashcards.repo.FlashcardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlashcardServiceImpl implements FlashcardService{

    private final FlashcardRepository flashcardRepository;
    private final FlashcardCategoryRepository flashcardCategoryRepository;
    private final UserService userService;

    @Autowired
    public FlashcardServiceImpl(FlashcardRepository flashcardRepository, FlashcardCategoryRepository flashcardCategoryRepository, UserService userService) {
        this.flashcardRepository = flashcardRepository;
        this.flashcardCategoryRepository = flashcardCategoryRepository;
        this.userService = userService;
    }

    @Override
    public List<Flashcard> findAllUserFlashcards() {
        return flashcardRepository.findAllByUser(userService.getAuthorizedUser());
    }

    @Override
    public Boolean flashcardIsExist(Long id) {
        List<Flashcard> flashcardList = findAllUserFlashcards();
        if (flashcardList == null || flashcardList.isEmpty()) {
            return false;
        } else {
            return flashcardList.stream().anyMatch(flashcard -> flashcard.getId() == id);
        }
    }

    @Override
    public void saveFlashcard(FlashcardModel flashcardModel) {
        try {
        User user = userService.getAuthorizedUser();
        FlashcardCategory flashcardCategory = flashcardCategoryRepository.getOne(flashcardModel.getFlashcardCategoryId());
        Flashcard flashcard = new Flashcard();
        flashcard.setUser(user);
        flashcard.setFlashcardCategory(flashcardCategory);
        flashcard.setFirstSentence(flashcardModel.getFirstSentence());
        flashcard.setSecondSentence(flashcardModel.getSecondSentence());
        flashcardRepository.save(flashcard);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void editFlashcard(Long id, FlashcardModel flashcardModel) {
        try {
               flashcardRepository.findById(id).map(flashcard -> {
               flashcard.setFirstSentence(flashcardModel.getFirstSentence());
               flashcard.setSecondSentence(flashcardModel.getSecondSentence());
               FlashcardCategory flashcardCategory = flashcardCategoryRepository.findById(flashcardModel.getFlashcardCategoryId()).get();
               flashcard.setFlashcardCategory(flashcardCategory);
               return flashcardRepository.save(flashcard);
           }).orElseThrow();
       } catch (Exception exception) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void deleteFlashcard(Long id) {
        flashcardRepository.deleteById(id);
    }

    @Override
    public List<Flashcard> findAllFlashcardsByCategory(Long categoryId) {
        return flashcardRepository.findAll().stream().filter(flashcard -> flashcard.getFlashcardCategory().getId() == categoryId).collect(Collectors.toList());
    }

}
