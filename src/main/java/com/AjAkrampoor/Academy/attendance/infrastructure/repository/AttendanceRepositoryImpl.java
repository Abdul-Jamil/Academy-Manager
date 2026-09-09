package com.AjAkrampoor.Academy.attendance.infrastructure.repository;

import com.AjAkrampoor.Academy.attendance.domain.model.Attendance;
import com.AjAkrampoor.Academy.attendance.domain.model.AttendanceId;
import com.AjAkrampoor.Academy.attendance.domain.repository.AttendanceRepository;
import com.AjAkrampoor.Academy.attendance.infrastructure.persistence.AttendanceJpaEntity;
import com.AjAkrampoor.Academy.attendance.presentation.mapper.AttendanceMapper;
import com.AjAkrampoor.Academy.courses.domain.model.ClassId;
import com.AjAkrampoor.Academy.students.domain.model.StudentId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class AttendanceRepositoryImpl implements AttendanceRepository {

    private final AttendanceJpaRepository repository;

    public AttendanceRepositoryImpl(
            AttendanceJpaRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public boolean existsById(AttendanceId attendanceId) {
        return repository.existsById(attendanceId);
    }

    @Override
    public Optional<Attendance> findById(AttendanceId attendanceId) {
        return repository.findById(attendanceId).map(AttendanceMapper::toDomain);
    }

    @Override
    public Attendance save(Attendance attendance) {
        AttendanceJpaEntity saved = repository.save(AttendanceMapper.toEntity(attendance));

        return AttendanceMapper.toDomain(saved);
    }

    @Override
    public boolean existsByStudentIdAndClassIdAndDate(StudentId studentId, ClassId classId, LocalDate date) {
        LocalDateTime from = date.atStartOfDay();
        LocalDateTime to = date.plusDays(1).atStartOfDay();
        return repository.existsByStudentIdAndClassIdAndDate(studentId, classId, from, to);
    }

    @Override
    public Page<Attendance> findAll(Specification<AttendanceJpaEntity> specification, Pageable pageable) {
        return repository.findAll(specification, pageable).map(AttendanceMapper::toDomain);
    }
}
