package com.AjAkrampoor.Academy.salaries.infrastructure.repository;

import com.AjAkrampoor.Academy.salaries.domain.model.SalaryContractId;
import com.AjAkrampoor.Academy.salaries.infrastructure.persistence.SalaryContractJpaEntity;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SalaryContractJpaRepository
        extends JpaRepository<SalaryContractJpaEntity, SalaryContractId> {

    @Query("""
            SELECT s
            FROM SalaryContractJpaEntity s
            WHERE s.staffId = :staffId
              AND s.effectiveFrom <= :date
              AND (s.effectiveTo IS NULL OR s.effectiveTo >= :date)
            """)
    Optional<SalaryContractJpaEntity> findActiveByStaffId(
            @Param("staffId") StaffId staffId,
            @Param("date") LocalDate date
    );

    @Query("""
            SELECT s
            FROM SalaryContractJpaEntity s
            WHERE s.staffId = :staffId
            ORDER BY s.effectiveFrom DESC
            """)
    List<SalaryContractJpaEntity> findByStaffId(
            @Param("staffId") StaffId staffId
    );

    @Query("""
            SELECT s
            FROM SalaryContractJpaEntity s
            WHERE s.effectiveFrom <= :date
              AND (s.effectiveTo IS NULL OR s.effectiveTo >= :date)
            ORDER BY s.staffId
            """)
    List<SalaryContractJpaEntity> findActiveOn(
            @Param("date") LocalDate date
    );

    @Query("""
            SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END
            FROM SalaryContractJpaEntity s
            WHERE s.staffId = :staffId
              AND s.effectiveFrom <= COALESCE(:effectiveTo, s.effectiveFrom)
              AND (s.effectiveTo IS NULL OR s.effectiveTo >= :effectiveFrom)
            """)
    boolean existsOverlapping(
            @Param("staffId") StaffId staffId,
            @Param("effectiveFrom") LocalDate effectiveFrom,
            @Param("effectiveTo") LocalDate effectiveTo
    );
}