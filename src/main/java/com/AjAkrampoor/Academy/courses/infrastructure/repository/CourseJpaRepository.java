package com.AjAkrampoor.Academy.courses.infrastructure.repository;

import com.AjAkrampoor.Academy.courses.domain.model.CourseId;
import com.AjAkrampoor.Academy.courses.domain.model.CourseName;
import com.AjAkrampoor.Academy.courses.infrastructure.persistence.CourseJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseJpaRepository
        extends JpaRepository<CourseJpaEntity, CourseId>,
                JpaSpecificationExecutor<CourseJpaEntity> {

    boolean existsByCourseName(CourseName courseName);
}
