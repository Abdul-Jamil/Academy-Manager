package com.AjAkrampoor.Academy.courses.domain.repository;

import com.AjAkrampoor.Academy.courses.domain.model.Course;
import com.AjAkrampoor.Academy.courses.domain.model.CourseId;
import com.AjAkrampoor.Academy.courses.domain.model.CourseName;
import com.AjAkrampoor.Academy.courses.infrastructure.persistence.CourseJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public interface CourseRepository {

    Optional<Course> findById(CourseId id);

    Page<Course> findAll(
            Pageable pageable
    );

    Page<Course> findAll(
            Specification<CourseJpaEntity> specification,
            Pageable pageable
    );

    boolean existsById(CourseId id);

    boolean existsByCourseName(CourseName courseName);

    Course save(Course course);
}
