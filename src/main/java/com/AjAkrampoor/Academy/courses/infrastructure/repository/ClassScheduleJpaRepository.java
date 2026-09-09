package com.AjAkrampoor.Academy.courses.infrastructure.repository;

import com.AjAkrampoor.Academy.courses.domain.model.ClassId;
import com.AjAkrampoor.Academy.courses.domain.model.ClassScheduleId;
import com.AjAkrampoor.Academy.courses.infrastructure.persistence.ClassScheduleJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClassScheduleJpaRepository
        extends JpaRepository<ClassScheduleJpaEntity, ClassScheduleId> {

    @Query("""
            SELECT s
            FROM ClassScheduleJpaEntity s
            WHERE s.classId = :classId
              AND s.status = com.AjAkrampoor.Academy.courses.domain.model.ScheduleStatus.ACTIVE
            """)
    Optional<ClassScheduleJpaEntity> findActiveByClassId(
            @Param("classId") ClassId classId
    );

    @Query("""
            SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END
            FROM ClassScheduleJpaEntity s
            WHERE s.classId = :classId
              AND s.status = com.AjAkrampoor.Academy.courses.domain.model.ScheduleStatus.ACTIVE
              AND (:excludeScheduleId IS NULL OR s.id <> :excludeScheduleId)
            
              AND (
                    :effectiveTo IS NULL
                    OR s.effectiveFrom IS NULL
                    OR s.effectiveFrom <= :effectiveTo
                  )
            
              AND (
                    s.effectiveTo IS NULL
                    OR :effectiveFrom IS NULL
                    OR s.effectiveTo >= :effectiveFrom
                  )
            """)
    boolean existsActiveOverlappingSchedule(
            @Param("classId") ClassId classId,
            @Param("effectiveFrom") LocalDate effectiveFrom,
            @Param("effectiveTo") LocalDate effectiveTo,
            @Param("excludeScheduleId") ClassScheduleId excludeScheduleId
    );

    List<ClassScheduleJpaEntity> findAllByClassId(ClassId classId);
}
