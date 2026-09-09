package com.AjAkrampoor.Academy.branches.application.usecases;

import com.AjAkrampoor.Academy.branches.domain.model.Branch;
import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.branches.domain.model.BranchStatus;
import com.AjAkrampoor.Academy.branches.domain.repository.BranchRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ReactivateBranchUseCase {

    private final BranchRepository branchRepository;

    public ReactivateBranchUseCase(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    @Transactional
    public Branch execute(String id) {
        BranchId branchId = BranchId.fromString(id);

        Branch branch = branchRepository.findById(branchId).orElseThrow(() -> new IllegalArgumentException("No such branch found"));
        if (branch.isDefault() || branch.getStatus() == BranchStatus.ACTIVE) {
            return branch;
        }
        branch.reactivate();
        return branchRepository.save(branch);
    }
}
