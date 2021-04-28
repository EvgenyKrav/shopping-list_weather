package com.evgeny.kravchenko.shoppinglist.constraints;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = UserExistsConstraintValidator.class)
public @interface UserExistsConstraint {
    String message() default "Пользователь уже зарегистрирован";
    Class[] groups() default {};
    Class[] payload() default {};
}
