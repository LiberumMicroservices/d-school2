package com.school.validator;

import com.school.models.User;
import com.school.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "Required");

        if(user.getUsername().length() < 6 || user.getUsername().length() > 32)
            errors.rejectValue("username", "Size.userForm.username");
//TODO рега по мылу
//        if (userService.findByUsername(user.getUsername()) != null)
//            errors.rejectValue("username", "Duplicate.userForm.username");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");

        if (user.getPassword().length() < 6 || user.getPassword().length() > 32)
            errors.rejectValue("password", "Size.userForm.password");

        if (!user.getConfirmPassword().equals(user.getPassword()))
            errors.rejectValue("confirmPassword", "Diff.userForm.passwordConfirm");

        if(user.getEmail().length() < 6 || user.getEmail().length() > 32)
            errors.rejectValue("email", "Size.userForm.email");

        if (userService.findByEmail(user.getEmail()) != null)
            errors.rejectValue("email", "Duplicate.userForm.email");

    }
}
