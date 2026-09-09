package com.AjAkrampoor.Academy.salaries.infrastructure.repository;

import com.AjAkrampoor.Academy.salaries.domain.model.MonthlySalaryId;
import com.AjAkrampoor.Academy.salaries.infrastructure.persistence.MonthlySalaryJpaEntity;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MonthlySalaryJpaRepository
        extends JpaRepository<MonthlySalaryJpaEntity, MonthlySalaryId> {

    @Query("""
            SELECT s
            FROM MonthlySalaryJpaEntity s
            WHERE s.staffId = :staffId
              AND s.salaryMonth.year = :year
              AND s.salaryMonth.month = :month
            """)
    Optional<MonthlySalaryJpaEntity> findByStaffAndMonth(
            @Param("staffId") StaffId staffId,
            @Param("year") int year,
            @Param("month") int month
    );

    @Query("""
            SELECT s
            FROM MonthlySalaryJpaEntity s
            WHERE s.salaryMonth.year = :year
              AND s.salaryMonth.month = :month
            ORDER BY s.staffId
            """)
    List<MonthlySalaryJpaEntity> findByMonth(
            @Param("year") int year,
            @Param("month") int month
    );

    @Query("""
            SELECT s
            FROM MonthlySalaryJpaEntity s
            WHERE s.staffId = :staffId
            ORDER BY s.salaryMonth.year DESC, s.salaryMonth.month DESC
            """)
    List<MonthlySalaryJpaEntity> findByStaff(
            @Param("staffId") StaffId staffId
    );

    @Query("""
            SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END
            FROM MonthlySalaryJpaEntity s
            WHERE s.staffId = :staffId
              AND s.salaryMonth.year = :year
              AND s.salaryMonth.month = :month
            """)
    boolean existsByStaffAndMonth(
            @Param("staffId") StaffId staffId,
            @Param("year") int year,
            @Param("month") int month
    );
}