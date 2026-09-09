package com.AjAkrampoor.Academy.branches.presentation.controller;

import com.AjAkrampoor.Academy.branches.application.BranchResponseAssembler;
import com.AjAkrampoor.Academy.branches.application.dto.BranchRequest;
import com.AjAkrampoor.Academy.branches.application.dto.BranchResponse;
import com.AjAkrampoor.Academy.branches.application.usecases.*;
import com.AjAkrampoor.Academy.branches.domain.model.Branch;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branch")
public class BranchController {
    private final BranchResponseAssembler assembler;
    private final GetBranchesUseCase getBranches;
    private final CreateBranchUseCase createBranch;
    private final DeactivateBranchUseCase deactivateBranch;
    private final ReactivateBranchUseCase reactivateBranch;
    private final RenameBranchUseCase renameBranch;

    public BranchController(BranchResponseAssembler assembler,
                            GetBranchesUseCase getBranches,
                            CreateBranchUseCase createBranch,
                            DeactivateBranchUseCase deactivateBranch,
                            ReactivateBranchUseCase reactivateBranch,
                            RenameBranchUseCase renameBranch) {
        this.assembler = assembler;
        this.getBranches = getBranches;
        this.createBranch = createBranch;
        this.deactivateBranch = deactivateBranch;
        this.reactivateBranch = reactivateBranch;
        this.renameBranch = renameBranch;
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('BRANCH_READ')")
    public ResponseEntity<List<BranchResponse>> getAll() {
        List<Branch> branches = getBranches.execute();
        List<BranchResponse> response = branches.stream()
                .map(assembler::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('BRANCH_CREATE')")
    public ResponseEntity<BranchResponse> create(@Valid @RequestBody BranchRequest request) {
        Branch branch = createBranch.execute(request.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toResponse(branch));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('BRANCH_UPDATE')")
    public ResponseEntity<BranchResponse> rename(@PathVariable String id, @RequestParam String newName) {
        Branch branch = renameBranch.execute(id, newName);
        return ResponseEntity.ok(assembler.toResponse(branch));
    }

    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasAuthority('BRANCH_DEACTIVATE')")
    public ResponseEntity<BranchResponse> deactivate(@PathVariable String id) {
        Branch branch = deactivateBranch.execute(id);
        return ResponseEntity.ok(assembler.toResponse(branch));
    }

    @PatchMapping("/{id}/reactivate")
    @PreAuthorize("hasAuthority('BRANCH_ACTIVATE')")
    public ResponseEntity<BranchResponse> reactivate(@PathVariable String id) {
        Branch branch = reactivateBranch.execute(id);
        return ResponseEntity.ok(assembler.toResponse(branch));
    }
}