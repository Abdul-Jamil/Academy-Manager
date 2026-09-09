package com.AjAkrampoor.Academy.attendance.infrastructure.repository;

import com.AjAkrampoor.Academy.attendance.domain.model.AttendanceId;
import com.AjAkrampoor.Academy.attendance.domain.repository.AttendanceRepository;
import com.AjAkrampoor.Academy.attendance.infrastructure.persistence.AttendanceJpaEntity;
import com.AjAkrampoor.Academy.courses.domain.model.ClassId;
import com.AjAkrampoor.Academy.students.domain.model.StudentId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface AttendanceJpaRepository
        extends JpaRepository<AttendanceJpaEntity, AttendanceId>,
                JpaSpecificationExecutor<AttendanceJpaEntity> {

    Optional<AttendanceJpaEntity> findById(AttendanceId attendanceId);

    @Query("""
            SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END
            FROM AttendanceJpaEntity a
            WHERE a.studentId = :studentId
              AND a.classId = :classId
              AND a.attendanceTime >= :from
              AND a.attendanceTime < :to
            """)
    boolean existsByStudentIdAndClassIdAndDate(
            @Param("studentId") StudentId studentId,
            @Param("classId") ClassId classId,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );

    Page<AttendanceJpaEntity> findAll(
            Specification<AttendanceJpaEntity> specification,
            Pageable pageable
    );
}
