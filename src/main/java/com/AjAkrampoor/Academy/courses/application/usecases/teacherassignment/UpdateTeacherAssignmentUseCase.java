package com.AjAkrampoor.Academy.courses.application.usecases.teacherassignment;

import com.AjAkrampoor.Academy.courses.application.TeacherAssignmentAccessService;
import com.AjAkrampoor.Academy.courses.domain.model.TeacherAssignment;
import com.AjAkrampoor.Academy.courses.domain.model.TeacherAssignmentId;
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
public class UpdateTeacherAssignmentUseCase {

    private final TeacherAssignmentRepository repository;
    private final TeacherAssignmentAccessService accessService;

    public UpdateTeacherAssignmentUseCase(
            TeacherAssignmentRepository repository,
            TeacherAssignmentAccessService accessService
    ) {
        this.repository = repository;
        this.accessService = accessService;
    }

    @Transactional
    public TeacherAssignment execute(
            String userId,
            UUID assignmentId,
            String teacherId,
            String description,
            Set<DayOfWeek> daysOfWeek,
            LocalTime startTime,
            LocalTime endTime,
            LocalDate effectiveFrom,
            LocalDate effectiveTo
    ) {
        TeacherAssignment assignment =
                repository.findById(
                        new TeacherAssignmentId(
                                assignmentId
                        )
                ).orElseThrow(
                        () -> new IllegalArgumentException(
                                "No such teacher assignment found"
                        )
                );

        accessService.getClassAndCheckAccess(
                userId,
                UUID.fromString(
                        assignment.getClassId().toString()
                )
        );

        accessService.getTeacher(teacherId);

        StaffId teacherIdValue = new StaffId(teacherId);

        if (repository.existsActiveAssignment(
                assignment.getClassId(),
                teacherIdValue,
                description,
                assignment.getId()
        )) {
            throw new IllegalStateException(
                    "Another active assignment with this description already exists"
            );
        }

        assignment.update(
                teacherIdValue,
                new Description(description),
                daysOfWeek,
                startTime,
                endTime,
                effectiveFrom,
                effectiveTo
        );

        return repository.save(assignment);
    }
}