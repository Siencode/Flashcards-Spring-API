package io.siencode.flashcards.service.implementation;

import io.siencode.flashcards.entity.*;
import io.siencode.flashcards.repo.LearningHistoryRepository;
import io.siencode.flashcards.repo.SelectedFlashcardRepository;
import io.siencode.flashcards.service.FlashcardService;
import io.siencode.flashcards.service.LearningService;
import io.siencode.flashcards.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class LearningServiceImpl implements LearningService {

    private final UserService userService;
    private final LearningHistoryRepository learningHistoryRepository;
    private final SelectedFlashcardRepository selectedFlashcardRepository;
    private final FlashcardService flashcardService;

    @Autowired
    public LearningServiceImpl(UserService userService, LearningHistoryRepository learningHistoryRepository, SelectedFlashcardRepository selectedFlashcardRepository, FlashcardService flashcardService) {
        this.userService = userService;
        this.learningHistoryRepository = learningHistoryRepository;
        this.selectedFlashcardRepository = selectedFlashcardRepository;
        this.flashcardService = flashcardService;
    }

    @Override
    public LearningHistory getLastLearning() {
            List<LearningHistory> learningHistoryList = learningHistoryRepository.findAllByUser(userService.getAuthorizedUser());
            Optional<LearningHistory> learningHistoryOptional = learningHistoryList.stream().max(Comparator.comparing(LearningHistory::getCreatedDateTime));
            if (learningHistoryOptional.isPresent()) {
                return learningHistoryOptional.get();
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Last learning not found");
            }
    }

    @Override
    public LearningHistory createNewLearning() {
        LearningHistory learningHistory = new LearningHistory();
        learningHistory.setCreatedDateTime(LocalDateTime.now());
        learningHistory.setUser(userService.getAuthorizedUser());
        learningHistoryRepository.save(learningHistory);
        return learningHistory;
    }

    @Override
    public void shuffleAndSaveNewSelectedFlashcards(Long categoryId, LearningHistory learningHistory) {
        List<Flashcard> flashcardList = flashcardService.findAllUserFlashcardsByCategory(categoryId);
        if (flashcardList.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Selected category is empty");
        flashcardList = new ArrayList<Flashcard>(flashcardList);
        Collections.shuffle(flashcardList);
        flashcardList.forEach(flashcard -> {
            SelectedFlashcard selectedFlashcard = new SelectedFlashcard();
            selectedFlashcard.setFirstSentence(flashcard.getFirstSentence());
            selectedFlashcard.setSecondSentence(flashcard.getSecondSentence());
            selectedFlashcard.isLastSelected(Boolean.FALSE);
            selectedFlashcard.setLearningHistory(learningHistory);
            selectedFlashcardRepository.save(selectedFlashcard);
        });
    }

    @Transactional
    @Override
    public void deleteLastSelectedFlashcards() {
        selectedFlashcardRepository.deleteByLearningHistoryId(getLastLearning().getId());
    }

    @Override
    public SelectedFlashcard getNextFlashcardById() {
        Long learningID = getLastLearning().getId();
        List<SelectedFlashcard> flashcardList = selectedFlashcardRepository.findAllByLearningHistoryId(learningID);
        Long currentSelectedFlashcardId = getCurrentFlashcardIdAndChangeStatus(learningID);
        SelectedFlashcard nextFlashcard = flashcardList.stream()
                .filter(selectedFlashcard -> selectedFlashcard.getId() >  currentSelectedFlashcardId)
                .findFirst()
                .orElse(flashcardList.stream()
                        .findFirst()
                        .orElseThrow());
        changeSelectedFlashcardStatus(nextFlashcard.getId(), true);
        return nextFlashcard;
    }

    @Override
    public Long getCurrentFlashcardIdAndChangeStatus(Long learnID) {
        List<SelectedFlashcard> flashcardList = selectedFlashcardRepository.findAllByLearningHistoryId(learnID);
        Long currentFlashcardId = flashcardList.stream().filter(SelectedFlashcard::getLastSelected).findFirst().orElse(flashcardList.stream().findFirst().orElseThrow()).getId();
        changeSelectedFlashcardStatus(currentFlashcardId, false);
        return currentFlashcardId;
    }

    @Override
    public void changeSelectedFlashcardStatus(Long flashcardId,Boolean isSelected) {
        selectedFlashcardRepository.findById(flashcardId).map(selectedFlashcard -> {
            selectedFlashcard.isLastSelected(isSelected);
            return selectedFlashcardRepository.save(selectedFlashcard);
        }).orElseThrow();
    }

    @Override
    public SelectedFlashcard getCurrentFlashcard() {
        Long learningID = getLastLearning().getId();
        List<SelectedFlashcard> flashcardList = selectedFlashcardRepository.findAllByLearningHistoryId(learningID);
        return flashcardList.stream().filter(SelectedFlashcard::getLastSelected).findFirst().orElse(flashcardList.stream().findFirst().orElseThrow());
    }

    @Override
    public int numberOfUserLearning() {
        return learningHistoryRepository.findAllByUser(userService.getAuthorizedUser()).size();
    }
}
