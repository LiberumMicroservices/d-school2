package com.school.repositories;

import com.school.models.Course;
import com.school.models.Room;
import com.school.models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>{

    List<Schedule> findByDay(DayOfWeek day);
    List<Schedule> findByCourse(Course course);
    List<Schedule> findByRoom(Room room);
    List<Schedule> findByCourseAndRoom(Course course, Room room);
}
