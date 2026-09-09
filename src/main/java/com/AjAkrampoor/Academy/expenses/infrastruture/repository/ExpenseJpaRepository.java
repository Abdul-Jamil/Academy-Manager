package com.AjAkrampoor.Academy.expenses.infrastruture.repository;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.expenses.domain.model.ExpenseId;
import com.AjAkrampoor.Academy.expenses.infrastruture.persistence.ExpenseJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseJpaRepository extends JpaRepository<ExpenseJpaEntity, ExpenseId> {
    Page<ExpenseJpaEntity> findAllByBranchId(BranchId branchId, Pageable pageable);
}
