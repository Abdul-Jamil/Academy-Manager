package com.AjAkrampoor.Academy.courses.application.usecases.teacherassignment;

import com.AjAkrampoor.Academy.courses.application.TeacherAssignmentAccessService;
import com.AjAkrampoor.Academy.courses.domain.model.TeacherAssignment;
import com.AjAkrampoor.Academy.courses.domain.model.TeacherAssignmentId;
import com.AjAkrampoor.Academy.courses.domain.repository.TeacherAssignmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DeactivateTeacherAssignmentUseCase {

    private final TeacherAssignmentRepository repository;
    private final TeacherAssignmentAccessService accessService;

    public DeactivateTeacherAssignmentUseCase(
            TeacherAssignmentRepository repository,
            TeacherAssignmentAccessService accessService
    ) {
        this.repository = repository;
        this.accessService = accessService;
    }

    @Transactional
    public TeacherAssignment execute(
            String userId,
            UUID assignmentId
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

        assignment.deactivate();

        return repository.save(assignment);
    }
}