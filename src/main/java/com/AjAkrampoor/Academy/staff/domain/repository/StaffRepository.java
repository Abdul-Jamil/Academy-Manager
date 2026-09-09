package com.AjAkrampoor.Academy.staff.domain.repository;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;

import java.util.List;
import java.util.Optional;

public interface StaffRepository {
    List<Staff> findAll();

    Optional<Staff> findById(StaffId id);

    Staff save(Staff staff);

    boolean existsById(StaffId id);

    List<Staff> findAllByBranchId(BranchId branchId);

    List<Staff> findAllActive();

    List<Staff> findAllActiveByBranch(BranchId branchId);
}
