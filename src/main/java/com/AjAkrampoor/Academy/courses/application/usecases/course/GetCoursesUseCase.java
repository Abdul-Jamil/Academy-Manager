package com.AjAkrampoor.Academy.courses.application.usecases.course;

import com.AjAkrampoor.Academy.courses.application.dto.CourseFilter;
import com.AjAkrampoor.Academy.courses.domain.model.Course;
import com.AjAkrampoor.Academy.courses.domain.repository.CourseRepository;
import com.AjAkrampoor.Academy.courses.infrastructure.persistence.CourseJpaEntity;
import com.AjAkrampoor.Academy.courses.infrastructure.repository.CourseSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GetCoursesUseCase {

    private final CourseRepository courseRepository;

    public GetCoursesUseCase(
            CourseRepository courseRepository
    ) {
        this.courseRepository = courseRepository;
    }

    @Transactional(readOnly = true)
    public Page<Course> execute(CourseFilter filter, Pageable pageable) {

        List<Specification<CourseJpaEntity>> specifications = CourseSpecifications.getSpecifications(filter);
        Specification<CourseJpaEntity> combined = Specification.allOf(specifications);

        return courseRepository.findAll(combined, pageable);
    }
}
