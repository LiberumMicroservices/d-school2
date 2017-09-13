package com.school.services.impl;

import com.school.models.Course;
import com.school.models.Room;
import com.school.models.Schedule;
import com.school.repositories.ScheduleRepository;
import com.school.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Override
    public void addSchedule(Schedule schedule) {
        scheduleRepository.save(schedule);
    }

    @Override
    public void save(Schedule schedule) {
        scheduleRepository.save(schedule);
    }

    @Override
    public void remove(Long id) {
        scheduleRepository.delete(id);
    }

    @Override
    public Schedule findById(Long id) {
        return scheduleRepository.findOne(id);
    }

    @Override
    public List<Schedule> findByRoom(Room room) {
        return scheduleRepository.findByRoom(room);
    }

    @Override
    public List<Schedule> findByCourse(Course course) {
        return scheduleRepository.findByCourse(course);
    }

    @Override
    public List<Schedule> FindByCourseAndRoom(Course course, Room room) {
        return scheduleRepository.findByCourseAndRoom(course, room);
    }
}
