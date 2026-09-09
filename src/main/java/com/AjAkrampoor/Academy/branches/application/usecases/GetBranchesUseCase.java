package com.AjAkrampoor.Academy.branches.application.usecases;

import com.AjAkrampoor.Academy.branches.domain.model.Branch;
import com.AjAkrampoor.Academy.branches.domain.repository.BranchRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetBranchesUseCase {
    private final BranchRepository branchRepository;

    public GetBranchesUseCase(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    public List<Branch> execute() {
        return branchRepository.findAll();
    }
}
