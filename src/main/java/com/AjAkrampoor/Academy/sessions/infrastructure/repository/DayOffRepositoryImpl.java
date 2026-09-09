package com.AjAkrampoor.Academy.sessions.infrastructure.repository;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.sessions.domain.model.DayOff;
import com.AjAkrampoor.Academy.sessions.domain.model.DayOffId;
import com.AjAkrampoor.Academy.sessions.domain.repository.DayOffRepository;
import com.AjAkrampoor.Academy.sessions.infrastructure.persistence.DayOffJpaEntity;
import com.AjAkrampoor.Academy.sessions.presentation.mapper.DayOffMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class DayOffRepositoryImpl implements DayOffRepository {

    private final DayOffJpaRepository repository;

    public DayOffRepositoryImpl(DayOffJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public DayOff save(DayOff dayOff) {
        DayOffJpaEntity saved = repository.save(DayOffMapper.toEntity(dayOff));
        return DayOffMapper.toDomain(saved);
    }

    @Override
    public boolean existsById(DayOffId dayOffId) {
        return repository.existsById(dayOffId);
    }

    @Override
    public Optional<DayOff> findById(DayOffId dayOffId) {
        return repository.findById(dayOffId).map(DayOffMapper::toDomain);
    }

    @Override
    public Optional<DayOff> findByDate(LocalDate date) {
        return repository.findByDate(date).map(DayOffMapper::toDomain);
    }

    @Override
    public List<DayOff> findActiveByDateBetween(LocalDate start, LocalDate end) {
        return repository.findActiveByDateBetween(start, end).stream()
                .map(DayOffMapper::toDomain)
                .toList();
    }

    @Override
    public Page<DayOff> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(DayOffMapper::toDomain);
    }

    @Override
    public Page<DayOff> findAllGlobalOrBranch(BranchId branchId, Pageable pageable) {
        return repository.findAllGlobalOrBranch(branchId, pageable).map(DayOffMapper::toDomain);
    }

}
