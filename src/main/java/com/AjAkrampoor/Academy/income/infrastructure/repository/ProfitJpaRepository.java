package com.AjAkrampoor.Academy.income.infrastructure.repository;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.income.domain.model.ProfitId;
import com.AjAkrampoor.Academy.income.infrastructure.persistence.ProfitJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfitJpaRepository extends JpaRepository<ProfitJpaEntity, ProfitId> {
    Page<ProfitJpaEntity> findAllByBranchId(BranchId branchId, Pageable pageable);
}
