package io.siencode.flashcards.controller;

import io.siencode.flashcards.entity.User;
import io.siencode.flashcards.model.UserModel;
import io.siencode.flashcards.repo.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserController(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public void userRegister(@RequestBody UserModel userModel) {
        Optional<User> userOptional = userRepository.findByUsername(userModel.getUsername());
        if (userOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "The username exists. Can't be duplicated.");
        } else {
            User user = new User();
            user.setUsername(userModel.getUsername());
            user.setPassword(passwordEncoder.encode(userModel.getPassword()));
            userRepository.save(user);
        }
    }

}
