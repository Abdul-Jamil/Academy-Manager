package com.AjAkrampoor.Academy.branches.infrastructure.repository;

import com.AjAkrampoor.Academy.branches.domain.model.Branch;
import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.branches.domain.model.BranchName;
import com.AjAkrampoor.Academy.branches.domain.repository.BranchRepository;
import com.AjAkrampoor.Academy.branches.infrastructure.persistence.BranchJpaEntity;
import com.AjAkrampoor.Academy.branches.presentation.mapper.BranchMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BranchRepositoryImpl implements BranchRepository {

    private final BranchRepositoryJpa repository;

    public BranchRepositoryImpl(BranchRepositoryJpa repository) {
        this.repository = repository;
    }

    @Override
    public List<Branch> findAll() {
        List<BranchJpaEntity> all = repository.findAll();
        return BranchMapper.toDomainList(all);
    }

    @Override
    public Optional<Branch> findById(BranchId id) {
        return repository.findById(id).map(BranchMapper::toDomain);
    }

    @Override
    public Branch save(Branch branch) {
        repository.save(BranchMapper.toJpaEntity(branch));
        return branch;
    }

    @Override
    public boolean existsById(BranchId id) {
        return repository.existsById(id);
    }

    @Override
    public boolean existsByName(BranchName name) {
        return repository.existsByBranchName(name);
    }
}
