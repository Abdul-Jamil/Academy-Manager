package com.AjAkrampoor.Academy.courses.infrastructure.repository;

import com.AjAkrampoor.Academy.courses.domain.model.ClassScheduleId;
import com.AjAkrampoor.Academy.courses.domain.model.ScheduleEntry;
import com.AjAkrampoor.Academy.courses.domain.model.ScheduleEntryId;
import com.AjAkrampoor.Academy.courses.domain.repository.ScheduleEntryRepository;
import com.AjAkrampoor.Academy.courses.infrastructure.persistence.ScheduleEntryJpaEntity;
import com.AjAkrampoor.Academy.courses.presentaion.mapper.ScheduleEntryMapper;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ScheduleEntryRepositoryImpl
        implements ScheduleEntryRepository {

    private final ScheduleEntryJpaRepository repository;

    public ScheduleEntryRepositoryImpl(
            ScheduleEntryJpaRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public boolean existsById(ScheduleEntryId entryId) {
        return repository.existsById(entryId);
    }

    @Override
    public Optional<ScheduleEntry> findById(
            ScheduleEntryId entryId
    ) {
        return repository.findById(entryId)
                .map(ScheduleEntryMapper::toDomain);
    }

    @Override
    public ScheduleEntry save(ScheduleEntry entry) {
        ScheduleEntryJpaEntity saved = repository.save(ScheduleEntryMapper.toEntity(entry));

        return ScheduleEntryMapper.toDomain(saved);
    }

    @Override
    public void deleteById(ScheduleEntryId entryId) {
        repository.deleteById(entryId);
    }

    @Override
    public List<ScheduleEntry> findByScheduleId(ClassScheduleId scheduleId) {
        return repository.findByScheduleId(scheduleId)
                .stream()
                .map(ScheduleEntryMapper::toDomain)
                .toList();
    }

    @Override
    public List<ScheduleEntry> findByScheduleIdAndDayOfWeek(ClassScheduleId scheduleId, DayOfWeek dayOfWeek) {
        return repository.findByScheduleIdAndDayOfWeek(scheduleId, dayOfWeek)
                .stream()
                .map(ScheduleEntryMapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsEntryForScheduleDayAndPeriod(ClassScheduleId scheduleId, DayOfWeek dayOfWeek, int periodNumber, ScheduleEntryId excludeEntryId) {
        return repository.existsEntryForScheduleDayAndPeriod(
                scheduleId,
                dayOfWeek,
                periodNumber,
                excludeEntryId
        );
    }

    @Override
    public boolean existsTeacherTimeConflict(StaffId teacherId, DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime, LocalDate effectiveFrom, LocalDate effectiveTo, ScheduleEntryId excludeEntryId) {
        return repository.existsTeacherTimeConflict(
                teacherId,
                dayOfWeek,
                startTime,
                endTime,
                effectiveFrom,
                effectiveTo,
                excludeEntryId
        );
    }

    @Override
    public List<ScheduleEntry> findByTeacherId(StaffId teacherId) {
        return repository.findByTeacherId(teacherId)
                .stream()
                .map(ScheduleEntryMapper::toDomain)
                .toList();
    }

    @Override
    public List<ScheduleEntry> findByTeacherIdAndDayOfWeek(StaffId teacherId, DayOfWeek dayOfWeek) {
        return repository.findByTeacherIdAndDayOfWeek(teacherId, dayOfWeek)
                .stream()
                .map(ScheduleEntryMapper::toDomain)
                .toList();
    }
}
