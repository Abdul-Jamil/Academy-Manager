package com.AjAkrampoor.Academy.courses.application.usecases.teacherassignment;

import com.AjAkrampoor.Academy.courses.domain.model.TeacherAssignment;
import com.AjAkrampoor.Academy.courses.domain.repository.TeacherAssignmentRepository;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class GetTeacherAssignmentsForDateUseCase {

    private final TeacherAssignmentRepository repository;

    public GetTeacherAssignmentsForDateUseCase(
            TeacherAssignmentRepository repository
    ) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<TeacherAssignment> execute(
            String teacherId,
            LocalDate date
    ) {
        if (date == null) {
            throw new IllegalArgumentException(
                    "Date cannot be null"
            );
        }

        return repository.findActiveByTeacherIdAndDate(
                new StaffId(teacherId),
                date,
                date.getDayOfWeek()
        );
    }
}