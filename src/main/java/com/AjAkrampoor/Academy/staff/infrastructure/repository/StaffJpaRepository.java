package com.AjAkrampoor.Academy.staff.infrastructure.repository;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import com.AjAkrampoor.Academy.staff.domain.model.StaffStatus;
import com.AjAkrampoor.Academy.staff.infrastructure.persistence.StaffJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StaffJpaRepository extends JpaRepository<StaffJpaEntity, StaffId> {

    List<StaffJpaEntity> findAllByBranchId(BranchId branchId);

    List<StaffJpaEntity> findAllByStatus(StaffStatus status);

    List<StaffJpaEntity> findAllByBranchIdAndStatus(BranchId branchId, StaffStatus status);
}
