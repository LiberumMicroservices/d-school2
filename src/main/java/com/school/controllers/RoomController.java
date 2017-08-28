package com.school.controllers;

import com.school.models.Room;
import com.school.services.RoomService;
import com.school.validator.RoomValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    RoomValidator roomValidator;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/rooms", method = RequestMethod.GET)
    public String schools(Model model) {
        List<Room> rooms = roomService.findAllFromCurrentSchool();
        model.addAttribute("rooms", rooms);
        return "rooms";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BOSS', 'ROLE_MANAGER')")
    @RequestMapping(value = "/addroom", method = RequestMethod.GET)
    public String addroom(Model model){

        model.addAttribute("roomForm", new Room());

        return "addroom";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BOSS', 'ROLE_MANAGER')")
    @RequestMapping(value = "/addroom", method = RequestMethod.POST)
    public String addroom(@ModelAttribute("roomForm") Room roomForm, BindingResult bindingResult){
        roomValidator.validate(roomForm, bindingResult);

        if(bindingResult.hasErrors())
            return "addroom";

        roomService.addRoom(roomForm);

        return "redirect:/rooms";
    }

}
