package io.siencode.flashcards.service;

import io.siencode.flashcards.entity.*;
import io.siencode.flashcards.repo.LearningHistoryRepository;
import io.siencode.flashcards.repo.SelectedFlashcardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class LearningService {

    private final UserService userService;
    private final LearningHistoryRepository learningHistoryRepository;
    private final SelectedFlashcardRepository selectedFlashcardRepository;
    private final FlashcardService flashcardService;

    @Autowired
    public LearningService(UserService userService, LearningHistoryRepository learningHistoryRepository, SelectedFlashcardRepository selectedFlashcardRepository, FlashcardService flashcardService) {
        this.userService = userService;
        this.learningHistoryRepository = learningHistoryRepository;
        this.selectedFlashcardRepository = selectedFlashcardRepository;
        this.flashcardService = flashcardService;
    }

    public LearningHistory getLastLearning() {
        User user = userService.getAuthorizedUser();
            List<LearningHistory> learningHistoryList = learningHistoryRepository.findAllByUser(user);
            Optional<LearningHistory> learningHistoryOptional = learningHistoryList.stream().max(Comparator.comparing(LearningHistory::getCreatedDateTime));
            if (learningHistoryOptional.isPresent()) {
                return learningHistoryOptional.get();
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Last learning not found");
            }
    }

    @Transactional
    public void createNewLearning (Long flashcardCategoryID) {
        LearningHistory learningHistory = new LearningHistory();
        learningHistory.setCreatedDateTime(LocalDateTime.now());
        learningHistory.setUser(userService.getAuthorizedUser());
        learningHistoryRepository.save(learningHistory);
        learningHistoryRepository.flush();

        List<Flashcard> flashcardList = flashcardService.findAllUserFlashcardsByCategory(flashcardCategoryID);
        deleteOldSelectedFlashcards();
        shuffleAndSaveNewSelectedFlashcards(flashcardList, learningHistory);
    }

    private void shuffleAndSaveNewSelectedFlashcards(List<Flashcard> flashcards, LearningHistory learningHistory) {
            if (flashcards.isEmpty())
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Selected category is empty");
            flashcards = new ArrayList<Flashcard>(flashcards);;
            Collections.shuffle(flashcards);
            flashcards.forEach(flashcard -> {
                SelectedFlashcard selectedFlashcard = new SelectedFlashcard();
                selectedFlashcard.setFirstSentence(flashcard.getFirstSentence());
                selectedFlashcard.setSecondSentence(flashcard.getSecondSentence());
                selectedFlashcard.isLastSelected(Boolean.FALSE);
                selectedFlashcard.setLearningHistory(learningHistory);
                selectedFlashcardRepository.save(selectedFlashcard);
            });
    }

    void deleteOldSelectedFlashcards() {
        selectedFlashcardRepository.deleteByLearningHistoryId(getLastLearning().getId());
    }

    public SelectedFlashcard getNextFlashcardById() {
        Long learnID = getLastLearning().getId();
        List<SelectedFlashcard> flashcardList = selectedFlashcardRepository.findAllByLearningHistoryId(learnID);
        Long currentSelectedFlashcardId = getCurrentFlashcardIdAndChangeStatus(learnID);
        SelectedFlashcard nextFlashcard = flashcardList.stream()
                .filter(selectedFlashcard -> selectedFlashcard.getId() != currentSelectedFlashcardId)
                .filter(selectedFlashcard -> selectedFlashcard.getId() >  currentSelectedFlashcardId)
                .findFirst()
                .orElse(flashcardList.stream()
                        .findFirst()
                        .orElseThrow());
        changeSelectedFlashcardStatus(nextFlashcard.getId(), true);
        return nextFlashcard;
    }

    public Long getCurrentFlashcardIdAndChangeStatus(Long learnID) {
        List<SelectedFlashcard> flashcardList = selectedFlashcardRepository.findAllByLearningHistoryId(learnID);
        Long currentFlashcardId = flashcardList.stream().filter(SelectedFlashcard::getLastSelected).findFirst().orElse(flashcardList.stream().findFirst().orElseThrow()).getId();
        changeSelectedFlashcardStatus(currentFlashcardId, false);
        return currentFlashcardId;
    }

    void changeSelectedFlashcardStatus(Long flashcardId,Boolean isSelected) {
        selectedFlashcardRepository.findById(flashcardId).map(selectedFlashcard -> {
            selectedFlashcard.isLastSelected(isSelected);
            return selectedFlashcardRepository.save(selectedFlashcard);
        }).orElseThrow();
    }

    public SelectedFlashcard getCurrentFlashcard() {
        Long learnID = getLastLearning().getId();
        List<SelectedFlashcard> flashcardList = selectedFlashcardRepository.findAllByLearningHistoryId(learnID);
        return flashcardList.stream().filter(SelectedFlashcard::getLastSelected).findFirst().orElse(flashcardList.stream().findFirst().orElseThrow());
    }
}
