package com.school.validator;

import com.school.models.School;
import com.school.services.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class SchoolValidator implements Validator {

    @Autowired
    private SchoolService schoolService;

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
        if(schoolService.findByName(school.getName()) != null)
            errors.rejectValue("name", "Duplicate.userForm.username");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nameBrand", "Required");
        if(school.getNameBrand().length() < 4 || school.getNameBrand().length() > 32)
            errors.rejectValue("nameBrand", "Size.schoolForm.name");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Required");
        if(school.getEmail().length() < 4 || school.getEmail().length() > 32)
            errors.rejectValue("email", "Size.schoolForm.name");
        if(schoolService.findByEmail(school.getEmail()) != null)
            errors.rejectValue("email", "Duplicate.userForm.email");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "Required");
        if(school.getPhone().length() != 13)
            errors.rejectValue("phone", "Size.schoolForm.phone");
        if(schoolService.findByPhone(school.getPhone()) != null)
            errors.rejectValue("phone", "Duplicate.schoolForm.phone");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "Required");
        if(school.getAddress().length() < 4 || school.getAddress().length() > 32)
            errors.rejectValue("address", "Size.schoolForm.name");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "Required");
        if(school.getDescription().length() < 10 || school.getDescription().length() > 501)
            errors.rejectValue("description", "Size.schoolForm.description");
    }
}
