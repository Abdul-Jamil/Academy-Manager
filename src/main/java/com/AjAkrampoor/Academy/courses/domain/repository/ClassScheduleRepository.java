package com.AjAkrampoor.Academy.courses.domain.repository;

import com.AjAkrampoor.Academy.courses.domain.model.ClassId;
import com.AjAkrampoor.Academy.courses.domain.model.ClassSchedule;
import com.AjAkrampoor.Academy.courses.domain.model.ClassScheduleId;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ClassScheduleRepository {

    boolean existsById(ClassScheduleId scheduleId);

    Optional<ClassSchedule> findById(ClassScheduleId scheduleId);

    Optional<ClassSchedule> findActiveByClassId(ClassId classId);

    boolean existsActiveOverlappingSchedule(
            ClassId classId,
            LocalDate effectiveFrom,
            LocalDate effectiveTo,
            ClassScheduleId excludeScheduleId
    );

    ClassSchedule save(ClassSchedule schedule);

    List<ClassSchedule> findAllByClassId(ClassId classId);
}
