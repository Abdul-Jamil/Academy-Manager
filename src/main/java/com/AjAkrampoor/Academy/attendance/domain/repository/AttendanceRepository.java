package com.AjAkrampoor.Academy.attendance.domain.repository;

import com.AjAkrampoor.Academy.attendance.domain.model.Attendance;
import com.AjAkrampoor.Academy.attendance.domain.model.AttendanceId;
import com.AjAkrampoor.Academy.attendance.infrastructure.persistence.AttendanceJpaEntity;
import com.AjAkrampoor.Academy.courses.domain.model.ClassId;
import com.AjAkrampoor.Academy.students.domain.model.StudentId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Optional;

public interface AttendanceRepository {

    boolean existsById(AttendanceId attendanceId);

    Optional<Attendance> findById(AttendanceId attendanceId);

    Attendance save(Attendance attendance);

    boolean existsByStudentIdAndClassIdAndDate(
            StudentId studentId,
            ClassId classId,
            LocalDate date
    );

    Page<Attendance> findAll(
            Specification<AttendanceJpaEntity> specification,
            Pageable pageable
    );
}
