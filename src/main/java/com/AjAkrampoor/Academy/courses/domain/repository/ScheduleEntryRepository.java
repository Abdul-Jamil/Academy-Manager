package com.AjAkrampoor.Academy.courses.domain.repository;

import com.AjAkrampoor.Academy.courses.domain.model.ClassScheduleId;
import com.AjAkrampoor.Academy.courses.domain.model.ScheduleEntry;
import com.AjAkrampoor.Academy.courses.domain.model.ScheduleEntryId;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ScheduleEntryRepository {

    boolean existsById(ScheduleEntryId entryId);

    Optional<ScheduleEntry> findById(ScheduleEntryId entryId);

    ScheduleEntry save(ScheduleEntry entry);

    void deleteById(ScheduleEntryId entryId);

    List<ScheduleEntry> findByScheduleId(ClassScheduleId scheduleId);

    List<ScheduleEntry> findByScheduleIdAndDayOfWeek(
            ClassScheduleId scheduleId,
            DayOfWeek dayOfWeek
    );

    boolean existsEntryForScheduleDayAndPeriod(
            ClassScheduleId scheduleId,
            DayOfWeek dayOfWeek,
            int periodNumber,
            ScheduleEntryId excludeEntryId
    );

    boolean existsTeacherTimeConflict(
            StaffId teacherId,
            DayOfWeek dayOfWeek,
            LocalTime startTime,
            LocalTime endTime,
            LocalDate effectiveFrom,
            LocalDate effectiveTo,
            ScheduleEntryId excludeEntryId
    );

    List<ScheduleEntry> findByTeacherId(StaffId teacherId);

    List<ScheduleEntry> findByTeacherIdAndDayOfWeek(
            StaffId teacherId,
            DayOfWeek dayOfWeek
    );
}
