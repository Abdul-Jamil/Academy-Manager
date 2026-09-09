package com.AjAkrampoor.Academy.branches.application.usecases;

import com.AjAkrampoor.Academy.branches.domain.model.Branch;
import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.branches.domain.model.BranchName;
import com.AjAkrampoor.Academy.branches.domain.model.BranchStatus;
import com.AjAkrampoor.Academy.branches.domain.repository.BranchRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateBranchUseCase {
    private final BranchRepository branchRepository;
    private static final int MAX_RETRIES = 10;


    public CreateBranchUseCase(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    @Transactional
    public Branch execute(String name) {
        BranchName branchName = new BranchName(name);

        if (branchRepository.existsByName(branchName)) {
            throw new IllegalArgumentException("A branch with the name '" + name + "' already exists.");
        }

        BranchId branchId;
        for (int i = 0; i < MAX_RETRIES; i++) {
            branchId = BranchId.newId();
            if (!branchRepository.existsById(branchId)) {
                Branch branch = new Branch(branchId, branchName, BranchStatus.ACTIVE, false);
                return branchRepository.save(branch);

            }
        }
        throw new IllegalArgumentException("Failed to create branch after several retries, please try again later");
    }
}
