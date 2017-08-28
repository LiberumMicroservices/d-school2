package com.school.validator;

import com.school.models.Room;
import com.school.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class RoomValidator implements Validator{

    @Autowired
    RoomService roomService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Room.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Room room = (Room) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "Required");
        if(room.getName().length() < 4 || room.getName().length() > 32)
            errors.rejectValue("name", "Size.roomForm.name");
        if(roomService.findByName(room.getName()) != null)
            errors.rejectValue("name", "Duplicate.name");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "Required");
        if(room.getAddress().length() < 10 || room.getAddress().length() > 64)
            errors.rejectValue("address", "Size.address");
    }
}
