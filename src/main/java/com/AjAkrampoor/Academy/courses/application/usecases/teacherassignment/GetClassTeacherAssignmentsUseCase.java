package com.AjAkrampoor.Academy.courses.application.usecases.teacherassignment;

import com.AjAkrampoor.Academy.courses.application.TeacherAssignmentAccessService;
import com.AjAkrampoor.Academy.courses.domain.model.ClassId;
import com.AjAkrampoor.Academy.courses.domain.model.TeacherAssignment;
import com.AjAkrampoor.Academy.courses.domain.repository.TeacherAssignmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class GetClassTeacherAssignmentsUseCase {

    private final TeacherAssignmentRepository repository;
    private final TeacherAssignmentAccessService accessService;

    public GetClassTeacherAssignmentsUseCase(
            TeacherAssignmentRepository repository,
            TeacherAssignmentAccessService accessService
    ) {
        this.repository = repository;
        this.accessService = accessService;
    }

    @Transactional(readOnly = true)
    public List<TeacherAssignment> execute(
            String userId,
            UUID classId
    ) {
        accessService.getClassAndCheckAccess(
                userId,
                classId
        );

        return repository.findByClassId(
                new ClassId(classId)
        );
    }
}