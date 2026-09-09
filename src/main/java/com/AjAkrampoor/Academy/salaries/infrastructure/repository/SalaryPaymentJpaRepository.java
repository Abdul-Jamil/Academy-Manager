package com.AjAkrampoor.Academy.salaries.infrastructure.repository;

import com.AjAkrampoor.Academy.salaries.domain.model.SalaryPaymentId;
import com.AjAkrampoor.Academy.salaries.infrastructure.persistence.SalaryPaymentJpaEntity;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalaryPaymentJpaRepository
        extends JpaRepository<SalaryPaymentJpaEntity, SalaryPaymentId> {

    @Query("""
            SELECT p
            FROM SalaryPaymentJpaEntity p
            WHERE p.staffId = :staffId
              AND p.salaryMonth.year = :year
              AND p.salaryMonth.month = :month
            ORDER BY p.paymentDate DESC
            """)
    List<SalaryPaymentJpaEntity> findByStaffAndMonth(
            @Param("staffId") StaffId staffId,
            @Param("year") int year,
            @Param("month") int month
    );

    @Query("""
            SELECT p
            FROM SalaryPaymentJpaEntity p
            WHERE p.salaryMonth.year = :year
              AND p.salaryMonth.month = :month
            ORDER BY p.paymentDate DESC
            """)
    List<SalaryPaymentJpaEntity> findByMonth(
            @Param("year") int year,
            @Param("month") int month
    );

    @Query("""
            SELECT p
            FROM SalaryPaymentJpaEntity p
            WHERE p.staffId = :staffId
            ORDER BY p.paymentDate DESC
            """)
    List<SalaryPaymentJpaEntity> findByStaff(
            @Param("staffId") StaffId staffId
    );

    @Query("""
            SELECT p
            FROM SalaryPaymentJpaEntity p
            ORDER BY p.paymentDate DESC
            """)
    List<SalaryPaymentJpaEntity> findAllPayments();

    @Override
    Optional<SalaryPaymentJpaEntity> findById(SalaryPaymentId id);
}