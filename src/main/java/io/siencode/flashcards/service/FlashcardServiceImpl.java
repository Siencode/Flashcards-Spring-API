package io.siencode.flashcards.service;

import io.siencode.flashcards.entity.Account;
import io.siencode.flashcards.entity.Flashcard;
import io.siencode.flashcards.entity.FlashcardCategory;
import io.siencode.flashcards.model.FlashcardCategoryModel;
import io.siencode.flashcards.model.FlashcardModel;
import io.siencode.flashcards.repo.AccountRepository;
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
    private final AccountRepository accountRepository;

    @Autowired
    public FlashcardServiceImpl(FlashcardRepository flashcardRepository, FlashcardCategoryRepository flashcardCategoryRepository, AccountRepository accountRepository) {
        this.flashcardRepository = flashcardRepository;
        this.flashcardCategoryRepository = flashcardCategoryRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Flashcard> findAllFlashcards() {
        return flashcardRepository.findAll();
    }

    @Override
    public Boolean flashcardIsExist(Long id) {
        return flashcardRepository.existsById(id);
    }

    @Override
    public void saveFlashcard(FlashcardModel flashcardModel) {
        try {
            //default account
        Account account = accountRepository.getOne(1l);
        FlashcardCategory flashcardCategory = flashcardCategoryRepository.getOne(flashcardModel.getFlashcardCategoryId());
        Flashcard flashcard = new Flashcard();
        flashcard.setAccount(account);
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

    @Override
    public List<FlashcardCategory> findAllFlashcardCategories() {
        return flashcardCategoryRepository.findAll();
    }

    @Override
    public Boolean flashcardCategoryIsExist(Long id) {
        return flashcardCategoryRepository.existsById(id);
    }

    @Override
    public Boolean flashcardCategoryIsExist(String categoryName) {
        return flashcardCategoryRepository.existsByCategoryName(categoryName);
    }

    @Override
    public FlashcardCategory findFlashcardCategoryByID(Long id) {
        return flashcardCategoryRepository.findById(id).get();
    }

    @Override
    public void saveFlashcardCategories(FlashcardCategoryModel flashcardCategoryModel) {
        FlashcardCategory flashcardCategory = new FlashcardCategory();
        flashcardCategory.setCategoryName(flashcardCategoryModel.getCategoryName());
        flashcardCategoryRepository.save(flashcardCategory);
    }

    @Override
    public void editFlashCardCategory(Long id, FlashcardCategoryModel flashcardCategoryModel) {
        flashcardCategoryRepository.findById(id).map(flashcardCategory -> {
            flashcardCategory.setCategoryName(flashcardCategoryModel.getCategoryName());
            return flashcardCategoryRepository.save(flashcardCategory);
        }).orElseThrow();
    }

    @Override
    public void deleteFlashcardCategory(Long id) {
        flashcardCategoryRepository.deleteById(id);
    }
}
