package com.AjAkrampoor.Academy.courses.application.usecases.teacherassignment;

import com.AjAkrampoor.Academy.courses.domain.model.TeacherAssignment;
import com.AjAkrampoor.Academy.courses.domain.repository.TeacherAssignmentRepository;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GetTeacherAssignmentsUseCase {

    private final TeacherAssignmentRepository repository;

    public GetTeacherAssignmentsUseCase(
            TeacherAssignmentRepository repository
    ) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<TeacherAssignment> execute(
            String teacherId
    ) {
        return repository.findByTeacherId(
                new StaffId(teacherId)
        );
    }
}