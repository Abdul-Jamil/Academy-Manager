package com.AjAkrampoor.Academy.courses.infrastructure.repository;

import com.AjAkrampoor.Academy.courses.domain.model.ClassId;
import com.AjAkrampoor.Academy.courses.domain.model.TeacherAssignmentId;
import com.AjAkrampoor.Academy.courses.domain.model.TeacherAssignmentStatus;
import com.AjAkrampoor.Academy.courses.infrastructure.persistence.TeacherAssignmentJpaEntity;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public interface TeacherAssignmentJpaRepository
        extends JpaRepository<
                TeacherAssignmentJpaEntity,
                TeacherAssignmentId
        > {

    List<TeacherAssignmentJpaEntity> findByClassId(
            ClassId classId
    );

    List<TeacherAssignmentJpaEntity> findByTeacherId(
            StaffId teacherId
    );

    List<TeacherAssignmentJpaEntity> findByClassIdAndStatus(
            ClassId classId,
            TeacherAssignmentStatus status
    );

    List<TeacherAssignmentJpaEntity> findByTeacherIdAndStatus(
            StaffId teacherId,
            TeacherAssignmentStatus status
    );

    @Query("""
            SELECT a
            FROM TeacherAssignmentJpaEntity a
            WHERE a.classId = :classId
              AND a.status = 'ACTIVE'
              AND (
                    a.effectiveFrom IS NULL
                    OR a.effectiveFrom <= :date
              )
              AND (
                    a.effectiveTo IS NULL
                    OR a.effectiveTo >= :date
              )
              AND :day MEMBER OF a.daysOfWeek
            """)
    List<TeacherAssignmentJpaEntity> findActiveByClassIdAndDate(
            @Param("classId") ClassId classId,
            @Param("date") LocalDate date,
            @Param("day") DayOfWeek day
    );

    @Query("""
            SELECT a
            FROM TeacherAssignmentJpaEntity a
            WHERE a.teacherId = :teacherId
              AND a.status = 'ACTIVE'
              AND (
                    a.effectiveFrom IS NULL
                    OR a.effectiveFrom <= :date
              )
              AND (
                    a.effectiveTo IS NULL
                    OR a.effectiveTo >= :date
              )
              AND :day MEMBER OF a.daysOfWeek
            """)
    List<TeacherAssignmentJpaEntity> findActiveByTeacherIdAndDate(
            @Param("teacherId") StaffId teacherId,
            @Param("date") LocalDate date,
            @Param("day") DayOfWeek day
    );

    @Query("""
            SELECT COUNT(a) > 0
            FROM TeacherAssignmentJpaEntity a
            WHERE a.classId = :classId
              AND a.teacherId = :teacherId
              AND LOWER(a.description.value) = LOWER(:description)
              AND a.status = 'ACTIVE'
              AND (
                    :excludedId IS NULL
                    OR a.id <> :excludedId
              )
            """)
    boolean existsActiveAssignment(
            @Param("classId") ClassId classId,
            @Param("teacherId") StaffId teacherId,
            @Param("description") String description,
            @Param("excludedId") TeacherAssignmentId excludedId
    );
}