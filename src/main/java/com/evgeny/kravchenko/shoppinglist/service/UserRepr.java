package com.evgeny.kravchenko.shoppinglist.service;

import com.evgeny.kravchenko.shoppinglist.constraints.UserEmailExistsConstraint;
import com.evgeny.kravchenko.shoppinglist.constraints.UserExistsConstraint;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserRepr {

    private Long id;

    @NotBlank(message = "Не должно быть пустым")
    @UserEmailExistsConstraint
    @Email
    private String email;

    @NotBlank
    @UserExistsConstraint
    private String username;

    @NotBlank
    @Size(min = 8, max = 256, message = "Пароль должен быть не менее 8 символов")
    private String password;

    @NotBlank
    @Size(min = 8, max = 256, message = "Пароль должен быть не менее 8 символов")
    private String repeatPassword;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}
