package com.AjAkrampoor.Academy.courses.application;

import com.AjAkrampoor.Academy.branches.domain.model.Branch;
import com.AjAkrampoor.Academy.branches.domain.repository.BranchRepository;
import com.AjAkrampoor.Academy.courses.application.dto.ClassResponse;
import com.AjAkrampoor.Academy.courses.domain.model.CourseClass;
import com.AjAkrampoor.Academy.enrollments.domain.repository.EnrollmentRepository;
import org.springframework.stereotype.Component;

@Component
public class ClassResponseAssembler {

    private final EnrollmentRepository enrollmentRepository;
    private final BranchRepository branchRepository;

    public ClassResponseAssembler(EnrollmentRepository enrollmentRepository, BranchRepository branchRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.branchRepository = branchRepository;
    }

    public ClassResponse toResponse(CourseClass courseClass) {
        long enrolledStudents = enrollmentRepository.countActiveEnrollmentsByClassId(courseClass.getId());
        return toResponse(courseClass, enrolledStudents);
    }

    public ClassResponse toResponse(CourseClass courseClass, long enrolledStudents) {
        String description = courseClass.getDescription() == null
                ? null
                : courseClass.getDescription().getValue();

        Branch branch = branchRepository.findById(courseClass.getBranchId()).orElseThrow(() -> new IllegalArgumentException("Branch not found"));

        return new ClassResponse(
                courseClass.getId().toString(),
                courseClass.getDuration().getDuration(),
                courseClass.getStartDate(),
                courseClass.getEndDate(),
                description,
                courseClass.getFee().getAmount(),
                courseClass.getClassType(),
                (int) enrolledStudents,
                courseClass.getClassStatus(),
                branch.getName().toString()
        );
    }
}
