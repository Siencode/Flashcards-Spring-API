package io.siencode.flashcards.service;

import io.siencode.flashcards.entity.Role;
import io.siencode.flashcards.entity.User;
import io.siencode.flashcards.model.UserModel;
import io.siencode.flashcards.repo.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public void saveUser(UserModel userModel) {
        User user = new User();
        user.setUsername(userModel.getUsername());
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));
        user.grantAuthority(Role.ROLE_USER);
        userRepository.save(user);

    }

    public Boolean userIsExist(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.isPresent();
    }

    public User getAuthorizedUser() {
        Authentication authenticator = SecurityContextHolder.getContext().getAuthentication();
        String userName = authenticator.getName();
        Optional<User> userOptional = userRepository.findByUsername(userName);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new RuntimeException(userName + " not found");
        }
    }
}
