package com.AjAkrampoor.Academy.students.infrastructure.repository;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.students.domain.model.StudentId;
import com.AjAkrampoor.Academy.students.infrastructure.persistence.StudentJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentJpaRepository extends JpaRepository<StudentJpaEntity, StudentId>, JpaSpecificationExecutor<StudentJpaEntity> {

    Page<StudentJpaEntity> findByAssignedBranches(BranchId branchId, Pageable pageable);
}
