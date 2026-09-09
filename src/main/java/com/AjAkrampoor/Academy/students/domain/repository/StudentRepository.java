package com.AjAkrampoor.Academy.students.domain.repository;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.students.application.dto.StudentFilter;
import com.AjAkrampoor.Academy.students.domain.model.Student;
import com.AjAkrampoor.Academy.students.domain.model.StudentId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    Optional<Student> findById(StudentId id);

    List<Student> findAll();

    Page<Student> findAll(Pageable pageable);

    Page<Student> findByAssignedBranches(BranchId branchId, Pageable pageable);

    Page<Student> findByFilter(StudentFilter filter, Pageable pageable);

    Student save(Student student);

    boolean existsById(StudentId studentId);
}
