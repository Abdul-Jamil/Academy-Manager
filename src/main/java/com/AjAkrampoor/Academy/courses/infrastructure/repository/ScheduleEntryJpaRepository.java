package com.AjAkrampoor.Academy.courses.infrastructure.repository;

import com.AjAkrampoor.Academy.courses.domain.model.ClassScheduleId;
import com.AjAkrampoor.Academy.courses.domain.model.ScheduleEntryId;
import com.AjAkrampoor.Academy.courses.infrastructure.persistence.ScheduleEntryJpaEntity;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ScheduleEntryJpaRepository
        extends JpaRepository<ScheduleEntryJpaEntity, ScheduleEntryId> {

    List<ScheduleEntryJpaEntity> findByScheduleId(
            ClassScheduleId scheduleId
    );

    List<ScheduleEntryJpaEntity> findByScheduleIdAndDayOfWeek(
            ClassScheduleId scheduleId,
            DayOfWeek dayOfWeek
    );

    @Query("""
            SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END
            FROM ScheduleEntryJpaEntity e
            WHERE e.scheduleId = :scheduleId
              AND e.dayOfWeek = :dayOfWeek
              AND e.periodNumber = :periodNumber
              AND (:excludeEntryId IS NULL OR e.id <> :excludeEntryId)
            """)
    boolean existsEntryForScheduleDayAndPeriod(
            @Param("scheduleId") ClassScheduleId scheduleId,
            @Param("dayOfWeek") DayOfWeek dayOfWeek,
            @Param("periodNumber") int periodNumber,
            @Param("excludeEntryId") ScheduleEntryId excludeEntryId
    );

    @Query("""
            SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END
            FROM ScheduleEntryJpaEntity e,
                 ClassScheduleJpaEntity s
            WHERE e.scheduleId = s.id
            
              AND e.teacherId = :teacherId
              AND e.dayOfWeek = :dayOfWeek
            
              AND e.startTime IS NOT NULL
              AND e.endTime IS NOT NULL
              AND :startTime IS NOT NULL
              AND :endTime IS NOT NULL
            
              AND e.startTime < :endTime
              AND e.endTime > :startTime
            
              AND (:excludeEntryId IS NULL OR e.id <> :excludeEntryId)
            
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
            
              AND s.status =
                    com.AjAkrampoor.Academy.courses.domain.model.ScheduleStatus.ACTIVE
            """)
    boolean existsTeacherTimeConflict(
            @Param("teacherId") StaffId teacherId,
            @Param("dayOfWeek") DayOfWeek dayOfWeek,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime,
            @Param("effectiveFrom") LocalDate effectiveFrom,
            @Param("effectiveTo") LocalDate effectiveTo,
            @Param("excludeEntryId") ScheduleEntryId excludeEntryId
    );

    List<ScheduleEntryJpaEntity> findByTeacherId(
            StaffId teacherId
    );

    List<ScheduleEntryJpaEntity> findByTeacherIdAndDayOfWeek(
            StaffId teacherId,
            DayOfWeek dayOfWeek
    );
}
