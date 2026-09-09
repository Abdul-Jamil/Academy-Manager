package com.AjAkrampoor.Academy.courses.application.usecases.teacherassignment;

import com.AjAkrampoor.Academy.courses.application.TeacherAssignmentAccessService;
import com.AjAkrampoor.Academy.courses.domain.model.ClassId;
import com.AjAkrampoor.Academy.courses.domain.model.CourseClass;
import com.AjAkrampoor.Academy.courses.domain.model.TeacherAssignment;
import com.AjAkrampoor.Academy.courses.domain.model.TeacherAssignmentId;
import com.AjAkrampoor.Academy.courses.domain.model.TeacherAssignmentStatus;
import com.AjAkrampoor.Academy.courses.domain.repository.TeacherAssignmentRepository;
import com.AjAkrampoor.Academy.shared.vo.Description;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

@Service
public class AssignTeacherToClassUseCase {

    private final TeacherAssignmentRepository repository;
    private final TeacherAssignmentAccessService accessService;

    public AssignTeacherToClassUseCase(
            TeacherAssignmentRepository repository,
            TeacherAssignmentAccessService accessService
    ) {
        this.repository = repository;
        this.accessService = accessService;
    }

    @Transactional
    public TeacherAssignment execute(
            String userId,
            UUID classId,
            String teacherId,
            String description,
            Set<DayOfWeek> daysOfWeek,
            LocalTime startTime,
            LocalTime endTime,
            LocalDate effectiveFrom,
            LocalDate effectiveTo
    ) {
        CourseClass courseClass =
                accessService.getClassAndCheckAccess(
                        userId,
                        classId
                );

        accessService.getTeacher(teacherId);

        ClassId classIdValue = courseClass.getId();
        StaffId teacherIdValue = new StaffId(teacherId);

        if (repository.existsActiveAssignment(
                classIdValue,
                teacherIdValue,
                description,
                null
        )) {
            throw new IllegalStateException(
                    "This teacher already has an active assignment with this description for the class"
            );
        }

        TeacherAssignment assignment =
                new TeacherAssignment(
                        TeacherAssignmentId.newId(),
                        classIdValue,
                        teacherIdValue,
                        new Description(description),
                        daysOfWeek,
                        startTime,
                        endTime,
                        effectiveFrom,
                        effectiveTo,
                        TeacherAssignmentStatus.ACTIVE
                );

        return repository.save(assignment);
    }
}