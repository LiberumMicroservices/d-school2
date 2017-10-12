package com.school.services;

import com.school.models.Room;
import com.school.models.School;

import java.util.List;

public interface RoomService {
    void addRoom(Room room);
    void save(Room room);
    void deleteById(Long id);
    void block(Room room);
    void unblock(Room room);
    Room findById(Long id);
    Room findByName(String name);
    List<Room> findAll();
    List<Room> findAllFromSchool(School school);
    List<Room> findAllFromCurrentSchool();
}
