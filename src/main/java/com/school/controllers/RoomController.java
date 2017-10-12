package com.school.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.models.ResponseMsg;
import com.school.models.Room;
import com.school.services.RoomService;
import com.school.utils.RoomUtils;
import com.school.validator.RoomValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    RoomValidator roomValidator;

    @Autowired
    RoomUtils roomUtils;

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

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BOSS', 'ROLE_MANAGER')")
    @RequestMapping(value = "/roomdetails", method = RequestMethod.GET)
    public String roomdetails(Model model, HttpServletRequest request){

        Room room;
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            room = roomService.findById(id);
        }catch (Exception e){
            return "redirect:/error?message=user not found";
        }

        model.addAttribute("room", room);

        return "roomdetails";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BOSS', 'ROLE_MANAGER')")
    @RequestMapping(value = "/editroom", method = RequestMethod.POST)
    public @ResponseBody
    String editroom(@RequestParam() String value, @RequestParam() Long pk, @RequestParam() String name) throws JsonProcessingException {

        String message = roomUtils.editRoomOneValue(name, value, pk);
        ObjectMapper mapper = new ObjectMapper();
        ResponseMsg responseMsg = new ResponseMsg();

        if(message.equals("ok")) {
            responseMsg.setSuccess(true);
        } else {
            responseMsg.setSuccess(false);
            responseMsg.setMsg(message);
        }

        return mapper.writeValueAsString(responseMsg);
    }

}
