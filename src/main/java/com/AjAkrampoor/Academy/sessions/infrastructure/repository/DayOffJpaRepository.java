package com.AjAkrampoor.Academy.sessions.infrastructure.repository;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.sessions.domain.model.DayOffId;
import com.AjAkrampoor.Academy.sessions.infrastructure.persistence.DayOffJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DayOffJpaRepository extends JpaRepository<DayOffJpaEntity, DayOffId> {
    Optional<DayOffJpaEntity> findByDate(LocalDate date);

    @Query("SELECT d FROM DayOffJpaEntity d WHERE d.date BETWEEN :start AND :end AND d.status = 'ACTIVE'")
    List<DayOffJpaEntity> findActiveByDateBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("SELECT d FROM DayOffJpaEntity d WHERE d.branchId IS NULL OR d.branchId = :branchId")
    Page<DayOffJpaEntity> findAllGlobalOrBranch(@Param("branchId") BranchId branchId, Pageable pageable);
}
