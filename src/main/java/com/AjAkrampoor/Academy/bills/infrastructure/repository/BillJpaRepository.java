package com.AjAkrampoor.Academy.bills.infrastructure.repository;

import com.AjAkrampoor.Academy.bills.domain.model.BillId;
import com.AjAkrampoor.Academy.bills.infrastructure.persistence.BillJpaEntity;
import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.enrollments.domain.model.EnrollmentId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillJpaRepository extends JpaRepository<BillJpaEntity, BillId>, JpaSpecificationExecutor<BillJpaEntity> {
    Page<BillJpaEntity> findByBranchId(BranchId branchId, Pageable pageable);

    List<BillJpaEntity> findByEnrollmentId(EnrollmentId enrollmentId);
}
