package com.AjAkrampoor.Academy.branches.application.usecases;

import com.AjAkrampoor.Academy.branches.domain.model.Branch;
import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.branches.domain.model.BranchName;
import com.AjAkrampoor.Academy.branches.domain.repository.BranchRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RenameBranchUseCase {

    private final BranchRepository branchRepository;

    public RenameBranchUseCase(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    @Transactional
    public Branch execute(String id, String name) {
        BranchId branchId = BranchId.fromString(id);
        BranchName newName = new BranchName(name);

        if (branchRepository.existsByName(newName)) {
            throw new IllegalArgumentException("A branch with the name '" + name + "' already exists.");
        }

        Branch branch = branchRepository.findById(branchId).orElseThrow(() -> new IllegalArgumentException("No such branch found"));

        branch.rename(newName);
        return branchRepository.save(branch);
    }
}
