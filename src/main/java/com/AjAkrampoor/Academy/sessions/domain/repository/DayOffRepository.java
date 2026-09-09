package com.AjAkrampoor.Academy.sessions.domain.repository;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.sessions.domain.model.DayOff;
import com.AjAkrampoor.Academy.sessions.domain.model.DayOffId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DayOffRepository {
    DayOff save(DayOff dayOff);

    boolean existsById(DayOffId dayOffId);

    Optional<DayOff> findById(DayOffId dayOffId);

    Optional<DayOff> findByDate(LocalDate date);

    List<DayOff> findActiveByDateBetween(LocalDate start, LocalDate end);

    Page<DayOff> findAll(Pageable pageable);

    Page<DayOff> findAllGlobalOrBranch(BranchId branchId, Pageable pageable);
}
