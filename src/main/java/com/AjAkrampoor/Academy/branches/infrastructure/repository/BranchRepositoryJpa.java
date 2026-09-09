package com.AjAkrampoor.Academy.branches.infrastructure.repository;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.branches.domain.model.BranchName;
import com.AjAkrampoor.Academy.branches.infrastructure.persistence.BranchJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepositoryJpa extends JpaRepository<BranchJpaEntity, BranchId> {
    boolean existsByBranchName(BranchName branchName);
}
