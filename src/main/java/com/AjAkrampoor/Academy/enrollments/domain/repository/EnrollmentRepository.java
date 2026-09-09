package com.AjAkrampoor.Academy.enrollments.domain.repository;

import com.AjAkrampoor.Academy.courses.domain.model.ClassId;
import com.AjAkrampoor.Academy.enrollments.application.dto.EnrollmentFilter;
import com.AjAkrampoor.Academy.enrollments.domain.model.Enrollment;
import com.AjAkrampoor.Academy.enrollments.domain.model.EnrollmentId;
import com.AjAkrampoor.Academy.students.domain.model.StudentId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EnrollmentRepository {

    Page<Enrollment> findAll(EnrollmentFilter filter, Pageable pageable);

    Optional<Enrollment> findById(EnrollmentId enrollmentId);

    boolean existsById(EnrollmentId enrollmentId);

    Enrollment save(Enrollment enrollment);

    boolean existsActiveEnrollment(StudentId studentId, ClassId id);

    long countActiveEnrollmentsByClassId(ClassId classId);

    Map<ClassId, Long> countActiveEnrollmentsByClassIds(List<ClassId> classIds);
}
