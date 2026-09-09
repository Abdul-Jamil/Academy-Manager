package com.AjAkrampoor.Academy.courses.application.usecases.course;

import com.AjAkrampoor.Academy.courses.domain.model.Course;
import com.AjAkrampoor.Academy.courses.domain.model.CourseId;
import com.AjAkrampoor.Academy.courses.domain.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class GetCourseUseCase {

    private final CourseRepository courseRepository;

    public GetCourseUseCase(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Transactional(readOnly = true)
    public Course execute(UUID courseId) {

        return courseRepository.findById(new CourseId(courseId))
                .orElseThrow(() -> new IllegalArgumentException("No such course found"));
    }
}
