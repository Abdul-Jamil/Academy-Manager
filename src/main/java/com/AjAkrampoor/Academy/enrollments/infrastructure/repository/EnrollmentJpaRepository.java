package com.AjAkrampoor.Academy.enrollments.infrastructure.repository;

import com.AjAkrampoor.Academy.courses.domain.model.ClassId;
import com.AjAkrampoor.Academy.enrollments.domain.model.EnrollmentId;
import com.AjAkrampoor.Academy.enrollments.domain.model.EnrollmentStatus;
import com.AjAkrampoor.Academy.enrollments.infrastructure.persistence.EnrollmentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentJpaRepository extends JpaRepository<EnrollmentJpaEntity, EnrollmentId>, JpaSpecificationExecutor<EnrollmentJpaEntity> {

    boolean existsByStudentId_IdAndClassId_IdAndEnrollmentStatus(String studentIdValue, String classIdValue, EnrollmentStatus status);

    long countByClassIdAndEnrollmentStatus(ClassId classId, EnrollmentStatus status);

    @Query("SELECT e.classId, COUNT(e) FROM EnrollmentJpaEntity e " +
            "WHERE e.classId IN :classIds AND e.enrollmentStatus = :status " +
            "GROUP BY e.classId")
    List<Object[]> countByClassIdInAndEnrollmentStatus(@Param("classIds") List<ClassId> classIds,
                                                       @Param("status") EnrollmentStatus status);
}
