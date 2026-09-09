package com.AjAkrampoor.Academy.students.application;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.shared.vo.PhoneNumber;
import com.AjAkrampoor.Academy.students.application.dto.StudentResponse;
import com.AjAkrampoor.Academy.students.domain.model.Student;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class StudentResponseAssembler {

    public StudentResponse toResponse(Student student) {
        Set<String> branchIdStrings = student.getAssignedBranches().stream()
                .map(BranchId::asString)
                .collect(Collectors.toSet());

        return new StudentResponse
                (
                        student.getStudentId().getId(),
                        student.getStudentName().getFirstName(),
                        student.getStudentName().getLastName(),
                        student.getGuardianName().getFirstName(),
                        student.getGuardianName().getLastName(),
                        student.getGuardianNumber().getNumber(),
                        student.getSecondaryNumber()
                                .map(PhoneNumber::getNumber)
                                .orElse(null),
                        student.getGuardianTelegram(),
                        student.getStudentStatus(),
                        branchIdStrings
                );
    }
}
