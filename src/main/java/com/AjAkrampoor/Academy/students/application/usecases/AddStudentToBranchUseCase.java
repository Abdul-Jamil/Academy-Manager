package com.AjAkrampoor.Academy.students.application.usecases;

import com.AjAkrampoor.Academy.branches.domain.model.Branch;
import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.branches.domain.repository.BranchRepository;
import com.AjAkrampoor.Academy.students.domain.model.Student;
import com.AjAkrampoor.Academy.students.domain.model.StudentId;
import com.AjAkrampoor.Academy.students.domain.repository.StudentRepository;
import org.springframework.stereotype.Service;


@Service
public class AddStudentToBranchUseCase {
    private final StudentRepository studentRepository;
    private final BranchRepository branchRepository;

    public AddStudentToBranchUseCase(StudentRepository studentRepository, BranchRepository branchRepository) {
        this.studentRepository = studentRepository;
        this.branchRepository = branchRepository;
    }

    public Student execute(String studentId, String branchIdStr) {
        Branch branch = branchRepository.findById(BranchId.fromString(branchIdStr)).orElseThrow(() -> new IllegalArgumentException("Branch cannot be null"));
        if (!branch.isActive()) {
            throw new IllegalArgumentException("Branch is inactive");
        }
        Student student = studentRepository.findById(StudentId.from(studentId)).orElseThrow(() -> new IllegalArgumentException("Student cannot be null"));
        if (student.getAssignedBranches().contains(branch.getId())) {
            return student;
        }
        student.assignToBranch(branch.getId());
        return studentRepository.save(student);
    }
}
