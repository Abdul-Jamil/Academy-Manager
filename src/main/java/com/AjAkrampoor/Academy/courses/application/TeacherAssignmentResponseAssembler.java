package com.AjAkrampoor.Academy.courses.application;

import com.AjAkrampoor.Academy.courses.application.dto.TeacherAssignmentResponse;
import com.AjAkrampoor.Academy.courses.domain.model.TeacherAssignment;
import org.springframework.stereotype.Component;

@Component
public class TeacherAssignmentResponseAssembler {

    public TeacherAssignmentResponse toResponse(
            TeacherAssignment assignment
    ) {
        return new TeacherAssignmentResponse(
                assignment.getId().toString(),
                assignment.getClassId().toString(),
                assignment.getTeacherId().toString(),
                assignment.getDescription().getValue(),
                assignment.getDaysOfWeek(),
                assignment.getStartTime(),
                assignment.getEndTime(),
                assignment.getEffectiveFrom(),
                assignment.getEffectiveTo(),
                assignment.getStatus()
        );
    }
}