package com.school.utils.impl;

import com.school.models.Room;
import com.school.services.RoomService;
import com.school.utils.RoomUtils;
import com.school.validator.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoomUtilsImpl implements RoomUtils {

    @Autowired
    RoomService roomService;

    @Autowired
    ValidatorUtil validatorUtil;

    @Override
    public String editRoomOneValue(String name, String value, Long id) {
        try {
            Room room = roomService.findById(id);

            switch (name){
                case "name":
                    if(value.length() < 6 || value.length() > 32)
                        return "between 6 and 32 characters";
                    room.setName(value);
                    break;

                case "phone1":
                    if(!validatorUtil.validatePhoneUA(value))
                        return "Invalid phone format";

                    room.setPhone1(value); break;

                case "address":
                    if(value.length() < 10 || value.length() > 64)
                        return "between 10 and 64 characters";
                    room.setAddress(value); break;

                case "description": room.setDescription(value); break;

                default: return name + " is not found";
            }

            roomService.save(room);
            return "ok";
        }catch (Exception e){
            return "error update";
        }
    }
}
