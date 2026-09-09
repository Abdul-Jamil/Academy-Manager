package com.AjAkrampoor.Academy.courses.domain.repository;

import com.AjAkrampoor.Academy.courses.domain.model.ClassId;
import com.AjAkrampoor.Academy.courses.domain.model.TeacherAssignment;
import com.AjAkrampoor.Academy.courses.domain.model.TeacherAssignmentId;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TeacherAssignmentRepository {

    TeacherAssignment save(TeacherAssignment assignment);

    boolean existsById(TeacherAssignmentId id);

    Optional<TeacherAssignment> findById(
            TeacherAssignmentId id
    );

    List<TeacherAssignment> findByClassId(
            ClassId classId
    );

    List<TeacherAssignment> findByTeacherId(
            StaffId teacherId
    );

    List<TeacherAssignment> findActiveByClassId(
            ClassId classId
    );

    List<TeacherAssignment> findActiveByTeacherId(
            StaffId teacherId
    );

    List<TeacherAssignment> findActiveByClassIdAndDate(
            ClassId classId,
            LocalDate date,
            DayOfWeek dayOfWeek
    );

    List<TeacherAssignment> findActiveByTeacherIdAndDate(
            StaffId teacherId,
            LocalDate date,
            DayOfWeek dayOfWeek
    );

    boolean existsActiveAssignment(
            ClassId classId,
            StaffId teacherId,
            String description,
            TeacherAssignmentId excludedId
    );
}