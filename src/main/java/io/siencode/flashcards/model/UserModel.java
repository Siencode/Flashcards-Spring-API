package io.siencode.flashcards.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserModel {

    @NotNull(message = "Username cannot be null")
    @Size(min = 5, max = 20, message = "Incorrect username length")
    private String username;
    @NotNull(message = "Password cannot be null")
    @Size(min = 5, max = 20, message = "Incorrect password length")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
