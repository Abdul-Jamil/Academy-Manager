package com.AjAkrampoor.Academy.courses.infrastructure.repository;

import com.AjAkrampoor.Academy.courses.domain.model.Course;
import com.AjAkrampoor.Academy.courses.domain.model.CourseId;
import com.AjAkrampoor.Academy.courses.domain.model.CourseName;
import com.AjAkrampoor.Academy.courses.domain.repository.CourseRepository;
import com.AjAkrampoor.Academy.courses.infrastructure.persistence.CourseJpaEntity;
import com.AjAkrampoor.Academy.courses.presentaion.mapper.CourseMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CourseRepositoryImpl
        implements CourseRepository {

    private final CourseJpaRepository repository;

    public CourseRepositoryImpl(
            CourseJpaRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public Optional<Course> findById(CourseId id) {

        return repository.findById(id)
                .map(CourseMapper::toDomain);
    }

    @Override
    public Page<Course> findAll(
            Pageable pageable
    ) {

        return repository.findAll(pageable)
                .map(CourseMapper::toDomain);
    }

    @Override
    public Page<Course> findAll(
            Specification<CourseJpaEntity> specification,
            Pageable pageable
    ) {

        return repository.findAll(
                        specification,
                        pageable
                )
                .map(CourseMapper::toDomain);
    }

    @Override
    public boolean existsById(CourseId id) {
        return repository.existsById(id);
    }

    @Override
    public boolean existsByCourseName(
            CourseName courseName
    ) {
        return repository.existsByCourseName(courseName);
    }

    @Override
    public Course save(Course course) {

        CourseJpaEntity saved =
                repository.save(
                        CourseMapper.toEntity(course)
                );

        return CourseMapper.toDomain(saved);
    }
}
