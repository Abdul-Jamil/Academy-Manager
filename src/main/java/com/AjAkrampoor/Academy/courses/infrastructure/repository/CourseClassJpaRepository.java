package com.AjAkrampoor.Academy.courses.infrastructure.repository;

import com.AjAkrampoor.Academy.courses.domain.model.ClassId;
import com.AjAkrampoor.Academy.courses.infrastructure.persistence.CourseClassJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseClassJpaRepository
        extends JpaRepository<CourseClassJpaEntity, ClassId>,
        JpaSpecificationExecutor<CourseClassJpaEntity> {

    Page<CourseClassJpaEntity> findAll(Specification<CourseClassJpaEntity> specification, Pageable pageable);

}
