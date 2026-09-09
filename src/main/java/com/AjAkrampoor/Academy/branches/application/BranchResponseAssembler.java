package com.AjAkrampoor.Academy.branches.application;

import com.AjAkrampoor.Academy.branches.application.dto.BranchResponse;
import com.AjAkrampoor.Academy.branches.domain.model.Branch;
import org.springframework.stereotype.Component;

@Component
public class BranchResponseAssembler {

    public BranchResponse toResponse(Branch branch) {
        return new BranchResponse(
                branch.getId().asString(),
                branch.getName().getValue(),
                branch.getStatus(),
                branch.isDefault()
        );
    }
}
