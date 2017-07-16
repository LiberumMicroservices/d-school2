package com.school.validator;

import com.school.models.School;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class SchoolValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return School.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        School school = (School) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "Required");

        if(school.getName().length() < 4 || school.getName().length() > 32)
            errors.rejectValue("name", "Size.schoolForm.name");

//        if (userService.findByUsername(user.getUsername()) != null)
//            errors.rejectValue("username", "Duplicate.userForm.username");
    }
}
