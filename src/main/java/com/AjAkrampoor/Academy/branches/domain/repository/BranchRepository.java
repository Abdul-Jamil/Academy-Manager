package com.AjAkrampoor.Academy.branches.domain.repository;

import com.AjAkrampoor.Academy.branches.domain.model.Branch;
import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.branches.domain.model.BranchName;

import java.util.List;
import java.util.Optional;

public interface BranchRepository {
    List<Branch> findAll();

    Optional<Branch> findById(BranchId id);

    Branch save(Branch branch);

    boolean existsById(BranchId id);

    boolean existsByName(BranchName name);
}
