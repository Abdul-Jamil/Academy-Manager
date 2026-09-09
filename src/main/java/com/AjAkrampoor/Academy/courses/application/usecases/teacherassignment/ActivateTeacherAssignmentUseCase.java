package com.AjAkrampoor.Academy.courses.application.usecases.teacherassignment;

import com.AjAkrampoor.Academy.courses.application.TeacherAssignmentAccessService;
import com.AjAkrampoor.Academy.courses.domain.model.TeacherAssignment;
import com.AjAkrampoor.Academy.courses.domain.model.TeacherAssignmentId;
import com.AjAkrampoor.Academy.courses.domain.repository.TeacherAssignmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ActivateTeacherAssignmentUseCase {

    private final TeacherAssignmentRepository repository;
    private final TeacherAssignmentAccessService accessService;

    public ActivateTeacherAssignmentUseCase(
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

        if (repository.existsActiveAssignment(
                assignment.getClassId(),
                assignment.getTeacherId(),
                assignment.getDescription().getValue(),
                assignment.getId()
        )) {
            throw new IllegalStateException(
                    "Another active assignment with the same teacher and description already exists"
            );
        }

        assignment.activate();

        return repository.save(assignment);
    }
}