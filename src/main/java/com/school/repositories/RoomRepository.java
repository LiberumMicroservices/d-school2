package com.school.repositories;

import com.school.models.Room;
import com.school.models.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Room findRoomByName(String name);
    List<Room> findBySchool(School school);
}
