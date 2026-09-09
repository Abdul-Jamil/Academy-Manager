package com.AjAkrampoor.Academy.courses.application;

import com.AjAkrampoor.Academy.courses.application.dto.ScheduleEntryResponse;
import com.AjAkrampoor.Academy.courses.domain.model.ScheduleEntry;
import com.AjAkrampoor.Academy.courses.domain.repository.CourseRepository;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import org.springframework.stereotype.Component;

@Component
public class ScheduleEntryResponseAssembler {

    private final CourseRepository courseRepository;
    private final StaffRepository staffRepository;

    public ScheduleEntryResponseAssembler(CourseRepository courseRepository, StaffRepository staffRepository) {
        this.courseRepository = courseRepository;
        this.staffRepository = staffRepository;
    }

    public ScheduleEntryResponse toResponse(ScheduleEntry entry) {

        var course = courseRepository.findById(entry.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("Course/subject not found"));

        Staff teacher = staffRepository.findById(entry.getTeacherId())
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found"));

        return new ScheduleEntryResponse(
                entry.getId().toString(),
                entry.getScheduleId().toString(),
                entry.getDayOfWeek(),
                entry.getPeriodNumber(),
                entry.getStartTime(),
                entry.getEndTime(),
                entry.getCourseId().toString(),
                course.getCourseName().getValue(),
                entry.getTeacherId().toString(),
                teacher.getName().getFullName()
        );
    }
}
