package com.AjAkrampoor.Academy.courses.application.usecases.teacherassignment;

import com.AjAkrampoor.Academy.courses.domain.model.TeacherAssignment;
import com.AjAkrampoor.Academy.courses.domain.model.TeacherAssignmentId;
import com.AjAkrampoor.Academy.courses.domain.repository.TeacherAssignmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class GetTeacherAssignmentUseCase {

    private final TeacherAssignmentRepository repository;

    public GetTeacherAssignmentUseCase(
            TeacherAssignmentRepository repository
    ) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public TeacherAssignment execute(
            UUID assignmentId
    ) {
        return repository.findById(
                        new TeacherAssignmentId(
                                assignmentId
                        )
                )
                .orElseThrow(
                        () -> new IllegalArgumentException(
                                "No such teacher assignment found"
                        )
                );
    }
}