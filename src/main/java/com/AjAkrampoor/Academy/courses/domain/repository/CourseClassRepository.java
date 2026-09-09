package com.AjAkrampoor.Academy.courses.domain.repository;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.courses.application.dto.ClassFilter;
import com.AjAkrampoor.Academy.courses.domain.model.ClassId;
import com.AjAkrampoor.Academy.courses.domain.model.CourseClass;
import com.AjAkrampoor.Academy.courses.infrastructure.persistence.CourseClassJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public interface CourseClassRepository {

    boolean existsById(ClassId classId);

    Optional<CourseClass> findById(ClassId classId);

    CourseClass save(CourseClass courseClass);

    Page<CourseClass> findAll(
            Specification<CourseClassJpaEntity> specification,
            Pageable pageable
    );

    Page<CourseClass> findAll(
            ClassFilter filter,
            BranchId branchId,
            Pageable pageable
    );
}
