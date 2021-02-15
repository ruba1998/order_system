package com.example.order_system.domain.validator;

import com.example.order_system.domain.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordsMatchValidator implements ConstraintValidator<PasswordMatch, User> {

    @Override
    public void initialize(PasswordMatch passwordsMatch){
    }

    public boolean isValid(User user, ConstraintValidatorContext context) {
        return user.getPassword().equals(user.getConfirmPassword());
    }

}
