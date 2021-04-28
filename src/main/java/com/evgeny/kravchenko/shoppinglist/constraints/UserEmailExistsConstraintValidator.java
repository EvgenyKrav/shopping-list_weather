package com.evgeny.kravchenko.shoppinglist.constraints;

import com.evgeny.kravchenko.shoppinglist.persist.User;
import com.evgeny.kravchenko.shoppinglist.persist.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserEmailExistsConstraintValidator implements ConstraintValidator<UserEmailExistsConstraint, String> {

    private final UserRepository userRepository;

    @Autowired
    public UserEmailExistsConstraintValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(UserEmailExistsConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext ctx) {

        if (email.isBlank()) {
            return true;
        }

        String dbEmail = userRepository.findByEmail(email).map(User::getEmail).orElse("");
        return !dbEmail.equals(email);
    }
}
