package com.school.services;

import com.school.models.Course;
import com.school.models.Room;
import com.school.models.Schedule;

import java.util.List;

public interface ScheduleService {

    void addSchedule(Schedule schedule);
    void save(Schedule schedule);
    void remove(Long id);
    Schedule findById(Long id);
    List<Schedule> findByRoom(Room room);
    List<Schedule> findByCourse(Course course);
    List<Schedule> FindByCourseAndRoom(Course course, Room room);
}