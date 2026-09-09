package com.AjAkrampoor.Academy.staff.infrastructure.repository;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import com.AjAkrampoor.Academy.staff.domain.model.StaffStatus;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import com.AjAkrampoor.Academy.staff.infrastructure.persistence.StaffJpaEntity;
import com.AjAkrampoor.Academy.staff.presentation.mapper.StaffMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class StaffRepositoryImpl implements StaffRepository {
    private final StaffJpaRepository repository;

    public StaffRepositoryImpl(StaffJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Staff> findAll() {
        List<StaffJpaEntity> all = repository.findAll();
        return StaffMapper.toDomainList(all);
    }

    @Override
    public Optional<Staff> findById(StaffId id) {
        return repository.findById(id).map(StaffMapper::toDomain);
    }

    @Override
    public Staff save(Staff staff) {
        StaffJpaEntity saved = repository.save(StaffMapper.toJpaEntity(staff));
        return StaffMapper.toDomain(saved);
    }

    @Override
    public boolean existsById(StaffId id) {
        return repository.existsById(id);
    }

    @Override
    public List<Staff> findAllByBranchId(BranchId branchId) {
        List<StaffJpaEntity> all = repository.findAllByBranchId(branchId);
        return StaffMapper.toDomainList(all);
    }

    @Override
    public List<Staff> findAllActive() {
        List<StaffJpaEntity> activeEntities = repository.findAllByStatus(StaffStatus.ACTIVE);
        return StaffMapper.toDomainList(activeEntities);
    }

    @Override
    public List<Staff> findAllActiveByBranch(BranchId branchId) {
        List<StaffJpaEntity> activeByBranch = repository.findAllByBranchIdAndStatus(branchId, StaffStatus.ACTIVE);
        return StaffMapper.toDomainList(activeByBranch);
    }
}
