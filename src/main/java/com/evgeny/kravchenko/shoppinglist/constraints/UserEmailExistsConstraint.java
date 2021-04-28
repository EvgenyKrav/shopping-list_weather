package com.evgeny.kravchenko.shoppinglist.constraints;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = UserEmailExistsConstraintValidator.class)
public @interface UserEmailExistsConstraint {
    String message() default "Пользователь с такой почтой уже зарегистрирован";
    Class[] groups() default {};
    Class[] payload() default {};
}
