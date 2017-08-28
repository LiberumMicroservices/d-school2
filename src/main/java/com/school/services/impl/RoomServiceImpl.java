package com.school.services.impl;

import com.school.models.Room;
import com.school.models.School;
import com.school.repositories.RoomRepository;
import com.school.services.RoomService;
import com.school.services.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    SchoolService schoolService;

    @Override
    public void addRoom(Room room) {
        room.setSchool(schoolService.currentSchool());
        room.setEnabled(true);
        roomRepository.save(room);
    }

    @Override
    public void save(Room room) {
        roomRepository.save(room);
    }

    @Override
    public void deleteById(Long id) {
        roomRepository.delete(id);
    }

    @Override
    public void block(Room room) {
        room.setEnabled(false);
        save(room);
    }

    @Override
    public void unblock(Room room) {
        room.setEnabled(true);
        save(room);
    }

    @Override
    public Room findById(Long id) {
        return roomRepository.findOne(id);
    }

    @Override
    public Room findByName(String name) {
        return roomRepository.findRoomByName(name);
    }

    @Override
    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    @Override
    public List<Room> findAllFromSchool(School school) {
        return roomRepository.findBySchool(school);
    }

    @Override
    public List<Room> findAllFromCurrentSchool() {
        return findAllFromSchool(schoolService.currentSchool());
    }
}
