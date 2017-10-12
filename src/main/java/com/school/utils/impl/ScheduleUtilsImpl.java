package com.school.utils.impl;

import com.school.models.Schedule;
import com.school.models.ScheduleJson;
import com.school.utils.ScheduleUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduleUtilsImpl implements ScheduleUtils {
    @Override
    public ScheduleJson toJsonObject(Schedule schedule) {
        ScheduleJson scheduleJson = new ScheduleJson();
        scheduleJson.setId(schedule.getId());
        scheduleJson.setDay(schedule.getDay().name());
        scheduleJson.setTime(schedule.getTime().toString());
        scheduleJson.setCourse(schedule.getCourse().getName());
        scheduleJson.setRoom(schedule.getRoom().getName());
        scheduleJson.setAddress(schedule.getRoom().getAddress());

        return scheduleJson;
    }

    @Override
    public List<ScheduleJson> toJsonObjects(List<Schedule> schedules) {
        List<ScheduleJson> res = new ArrayList<>();
        for (Schedule s: schedules)
            res.add(toJsonObject(s));

        return res;
    }
}
