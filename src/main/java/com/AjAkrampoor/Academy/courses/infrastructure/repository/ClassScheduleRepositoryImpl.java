package com.AjAkrampoor.Academy.courses.infrastructure.repository;

import com.AjAkrampoor.Academy.courses.domain.model.ClassId;
import com.AjAkrampoor.Academy.courses.domain.model.ClassSchedule;
import com.AjAkrampoor.Academy.courses.domain.model.ClassScheduleId;
import com.AjAkrampoor.Academy.courses.domain.repository.ClassScheduleRepository;
import com.AjAkrampoor.Academy.courses.infrastructure.persistence.ClassScheduleJpaEntity;
import com.AjAkrampoor.Academy.courses.presentaion.mapper.ClassScheduleMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class ClassScheduleRepositoryImpl implements ClassScheduleRepository {

    private final ClassScheduleJpaRepository repository;

    public ClassScheduleRepositoryImpl(
            ClassScheduleJpaRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public boolean existsById(ClassScheduleId scheduleId) {
        return repository.existsById(scheduleId);
    }

    @Override
    public Optional<ClassSchedule> findById(
            ClassScheduleId scheduleId
    ) {
        return repository.findById(scheduleId)
                .map(ClassScheduleMapper::toDomain);
    }

    @Override
    public Optional<ClassSchedule> findActiveByClassId(
            ClassId classId
    ) {
        return repository.findActiveByClassId(classId)
                .map(ClassScheduleMapper::toDomain);
    }

    @Override
    public boolean existsActiveOverlappingSchedule(
            ClassId classId,
            LocalDate effectiveFrom,
            LocalDate effectiveTo,
            ClassScheduleId excludeScheduleId
    ) {
        return repository.existsActiveOverlappingSchedule(
                classId,
                effectiveFrom,
                effectiveTo,
                excludeScheduleId
        );
    }

    @Override
    public ClassSchedule save(ClassSchedule schedule) {
        ClassScheduleJpaEntity saved =
                repository.save(
                        ClassScheduleMapper.toEntity(schedule)
                );

        return ClassScheduleMapper.toDomain(saved);
    }

    @Override
    public List<ClassSchedule> findAllByClassId(ClassId classId) {

        return repository.findAllByClassId(classId)
                .stream()
                .map(ClassScheduleMapper::toDomain)
                .toList();
    }
}
