package com.AjAkrampoor.Academy.enrollments.infrastructure.repository;

import com.AjAkrampoor.Academy.courses.domain.model.ClassId;
import com.AjAkrampoor.Academy.enrollments.application.dto.EnrollmentFilter;
import com.AjAkrampoor.Academy.enrollments.domain.model.Enrollment;
import com.AjAkrampoor.Academy.enrollments.domain.model.EnrollmentId;
import com.AjAkrampoor.Academy.enrollments.domain.model.EnrollmentStatus;
import com.AjAkrampoor.Academy.enrollments.domain.repository.EnrollmentRepository;
import com.AjAkrampoor.Academy.enrollments.infrastructure.persistence.EnrollmentJpaEntity;
import com.AjAkrampoor.Academy.enrollments.presentation.mapper.EnrollmentMapper;
import com.AjAkrampoor.Academy.students.domain.model.StudentId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class EnrollmentRepositoryImpl implements EnrollmentRepository {

    private final EnrollmentJpaRepository repository;

    public EnrollmentRepositoryImpl(EnrollmentJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<Enrollment> findAll(EnrollmentFilter filter, Pageable pageable) {
        Specification<EnrollmentJpaEntity> spec = EnrollmentSpecifications.filter(filter);
        return repository.findAll(spec, pageable).map(EnrollmentMapper::toDomain);
    }

    @Override
    public Optional<Enrollment> findById(EnrollmentId enrollmentId) {
        return repository.findById(enrollmentId).map(EnrollmentMapper::toDomain);
    }

    @Override
    public boolean existsById(EnrollmentId enrollmentId) {
        return repository.existsById(enrollmentId);
    }

    @Override
    public Enrollment save(Enrollment enrollment) {
        EnrollmentJpaEntity save = repository.save(EnrollmentMapper.toEntity(enrollment));
        return EnrollmentMapper.toDomain(save);
    }

    @Override
    public boolean existsActiveEnrollment(StudentId studentId, ClassId id) {
        EnrollmentStatus activeStatus = EnrollmentStatus.ENROLLED;

        return repository.existsByStudentId_IdAndClassId_IdAndEnrollmentStatus(studentId.getId(), id.toString(), activeStatus);
    }

    @Override
    public long countActiveEnrollmentsByClassId(ClassId classId) {
        return repository.countByClassIdAndEnrollmentStatus(classId, EnrollmentStatus.ENROLLED);
    }

    @Override
    public Map<ClassId, Long> countActiveEnrollmentsByClassIds(List<ClassId> classIds) {
        if (classIds == null || classIds.isEmpty()) {
            return Map.of();
        }
        List<Object[]> results = repository.countByClassIdInAndEnrollmentStatus(classIds, EnrollmentStatus.ENROLLED);
        return results.stream().collect(Collectors.toMap(
                arr -> (ClassId) arr[0],
                arr -> (Long) arr[1]
        ));
    }
}
