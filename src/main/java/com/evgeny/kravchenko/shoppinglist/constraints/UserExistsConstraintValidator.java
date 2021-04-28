package com.evgeny.kravchenko.shoppinglist.constraints;

import com.evgeny.kravchenko.shoppinglist.persist.User;
import com.evgeny.kravchenko.shoppinglist.persist.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserExistsConstraintValidator implements ConstraintValidator<UserExistsConstraint, String> {

   private final UserRepository userRepository;

   @Autowired
   public UserExistsConstraintValidator(UserRepository userRepository) {
      this.userRepository = userRepository;
   }
   public void initialize(UserExistsConstraint constraint) {
   }

   public boolean isValid(String username, ConstraintValidatorContext ctx) {
      String dbUserName = userRepository.findByUsername(username).map(User::getUsername).orElse("");

      return !dbUserName.equals(username);
   }
}
