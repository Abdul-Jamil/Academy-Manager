package com.AjAkrampoor.Academy.courses.application.usecases.schedule;

import com.AjAkrampoor.Academy.courses.domain.model.*;
import com.AjAkrampoor.Academy.courses.domain.repository.ClassScheduleRepository;
import com.AjAkrampoor.Academy.courses.domain.repository.CourseRepository;
import com.AjAkrampoor.Academy.courses.domain.repository.ScheduleEntryRepository;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

@Service
public class UpdateScheduleEntryUseCase {

    private final ClassScheduleRepository classScheduleRepository;
    private final ScheduleEntryRepository scheduleEntryRepository;
    private final CourseRepository courseRepository;
    private final StaffRepository staffRepository;

    public UpdateScheduleEntryUseCase(
            ClassScheduleRepository classScheduleRepository,
            ScheduleEntryRepository scheduleEntryRepository,
            CourseRepository courseRepository,
            StaffRepository staffRepository
    ) {
        this.classScheduleRepository = classScheduleRepository;
        this.scheduleEntryRepository = scheduleEntryRepository;
        this.courseRepository = courseRepository;
        this.staffRepository = staffRepository;
    }

    @Transactional
    public ScheduleEntry execute(UUID entryId, DayOfWeek dayOfWeek, int periodNumber, LocalTime startTime, LocalTime endTime, UUID courseId, String teacherId) {
        ScheduleEntryId id = new ScheduleEntryId(entryId);

        ScheduleEntry entry = scheduleEntryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No such schedule entry found"));

        ClassSchedule schedule = classScheduleRepository.findById(entry.getScheduleId())
                .orElseThrow(() -> new IllegalArgumentException("No such schedule found"));

        if (schedule.isActive()) {
            throw new IllegalStateException("Deactivate the schedule before modifying its entries");
        }

        CourseId subjectId = new CourseId(courseId);

        if (!courseRepository.findById(subjectId)
                .map(Course::isActive)
                .orElse(false)) {
            throw new IllegalArgumentException("Course/subject does not exist or is inactive"
            );
        }

        StaffId staffId = new StaffId(teacherId);

        staffRepository.findById(staffId).orElseThrow(() -> new IllegalArgumentException("Teacher not found"));

        if (scheduleEntryRepository.existsEntryForScheduleDayAndPeriod(
                schedule.getId(),
                dayOfWeek,
                periodNumber,
                id
        )) {
            throw new IllegalStateException("This period is already assigned in the schedule");
        }

        if (scheduleEntryRepository.existsTeacherTimeConflict(
                staffId,
                dayOfWeek,
                startTime,
                endTime,
                schedule.getEffectiveFrom(),
                schedule.getEffectiveTo(),
                id
        )) {
            throw new IllegalStateException("Teacher already has a class during this time");
        }

        entry.update(dayOfWeek, periodNumber, startTime, endTime, subjectId, staffId);

        return scheduleEntryRepository.save(entry);
    }
}
