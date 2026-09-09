package com.AjAkrampoor.Academy.courses.infrastructure.repository;

import com.AjAkrampoor.Academy.courses.domain.model.ClassId;
import com.AjAkrampoor.Academy.courses.domain.model.TeacherAssignment;
import com.AjAkrampoor.Academy.courses.domain.model.TeacherAssignmentId;
import com.AjAkrampoor.Academy.courses.domain.model.TeacherAssignmentStatus;
import com.AjAkrampoor.Academy.courses.domain.repository.TeacherAssignmentRepository;
import com.AjAkrampoor.Academy.courses.presentaion.mapper.TeacherAssignmentMapper;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class TeacherAssignmentRepositoryImpl
        implements TeacherAssignmentRepository {

    private final TeacherAssignmentJpaRepository repository;

    public TeacherAssignmentRepositoryImpl(
            TeacherAssignmentJpaRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public TeacherAssignment save(
            TeacherAssignment assignment
    ) {
        return TeacherAssignmentMapper.toDomain(
                repository.save(
                        TeacherAssignmentMapper.toEntity(
                                assignment
                        )
                )
        );
    }

    @Override
    public boolean existsById(
            TeacherAssignmentId id
    ) {
        return repository.existsById(id);
    }

    @Override
    public Optional<TeacherAssignment> findById(
            TeacherAssignmentId id
    ) {
        return repository.findById(id)
                .map(TeacherAssignmentMapper::toDomain);
    }

    @Override
    public List<TeacherAssignment> findByClassId(
            ClassId classId
    ) {
        return repository.findByClassId(classId)
                .stream()
                .map(TeacherAssignmentMapper::toDomain)
                .toList();
    }

    @Override
    public List<TeacherAssignment> findByTeacherId(
            StaffId teacherId
    ) {
        return repository.findByTeacherId(teacherId)
                .stream()
                .map(TeacherAssignmentMapper::toDomain)
                .toList();
    }

    @Override
    public List<TeacherAssignment> findActiveByClassId(
            ClassId classId
    ) {
        return repository.findByClassIdAndStatus(
                        classId,
                        TeacherAssignmentStatus.ACTIVE
                )
                .stream()
                .map(TeacherAssignmentMapper::toDomain)
                .toList();
    }

    @Override
    public List<TeacherAssignment> findActiveByTeacherId(
            StaffId teacherId
    ) {
        return repository.findByTeacherIdAndStatus(
                        teacherId,
                        TeacherAssignmentStatus.ACTIVE
                )
                .stream()
                .map(TeacherAssignmentMapper::toDomain)
                .toList();
    }

    @Override
    public List<TeacherAssignment> findActiveByClassIdAndDate(
            ClassId classId,
            LocalDate date,
            DayOfWeek dayOfWeek
    ) {
        return repository.findActiveByClassIdAndDate(
                        classId,
                        date,
                        dayOfWeek
                )
                .stream()
                .map(TeacherAssignmentMapper::toDomain)
                .toList();
    }

    @Override
    public List<TeacherAssignment> findActiveByTeacherIdAndDate(
            StaffId teacherId,
            LocalDate date,
            DayOfWeek dayOfWeek
    ) {
        return repository.findActiveByTeacherIdAndDate(
                        teacherId,
                        date,
                        dayOfWeek
                )
                .stream()
                .map(TeacherAssignmentMapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsActiveAssignment(
            ClassId classId,
            StaffId teacherId,
            String description,
            TeacherAssignmentId excludedId
    ) {
        return repository.existsActiveAssignment(
                classId,
                teacherId,
                description,
                excludedId
        );
    }
}