package io.siencode.flashcards.controller;

import io.siencode.flashcards.model.UserModel;
import io.siencode.flashcards.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public void userRegister(@RequestBody UserModel userModel) {
        if (userService.userIsExist(userModel.getUsername())) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "The username exists. Can't be duplicated.");
        } else {
            userService.saveUser(userModel);
        }
    }

}
