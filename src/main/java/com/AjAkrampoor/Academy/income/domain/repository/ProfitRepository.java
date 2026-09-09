package com.AjAkrampoor.Academy.income.domain.repository;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.income.domain.model.Profit;
import com.AjAkrampoor.Academy.income.domain.model.ProfitId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfitRepository {
    boolean existsById(ProfitId id);

    Optional<Profit> findById(ProfitId id);

    Profit save(Profit profit);

    Page<Profit> findAll(Pageable pageable);

    Page<Profit> findAllByBranchId(BranchId branchId, Pageable pageable);
}
