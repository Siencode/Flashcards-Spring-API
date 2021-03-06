package io.siencode.flashcards.controller;

import io.siencode.flashcards.model.UserModel;
import io.siencode.flashcards.service.implementation.FlashcardCategoryServiceImpl;
import io.siencode.flashcards.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final FlashcardCategoryServiceImpl flashcardCategoryService;

    public UserController(UserService userService, FlashcardCategoryServiceImpl flashcardCategoryService) {
        this.userService = userService;
        this.flashcardCategoryService = flashcardCategoryService;
    }

    @GetMapping("/userinfo")
    public String getUsername(Principal principal) {
        return principal.getName();
    }

    @PostMapping("/register")
    public void userRegister(@Valid @RequestBody UserModel userModel) {
        if (userService.userIsExist(userModel.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "The username exists. Can't be duplicated.");
        } else {
            userService.saveUser(userModel);
            flashcardCategoryService.saveDefaultFlashcardCategories(userModel.getUsername());
        }
    }

}
