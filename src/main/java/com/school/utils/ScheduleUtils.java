package com.school.utils;

import com.school.models.Schedule;
import com.school.models.ScheduleJson;

import java.util.List;

public interface ScheduleUtils {
    ScheduleJson toJsonObject(Schedule schedule);
    List<ScheduleJson> toJsonObjects(List<Schedule> schedules);
}
