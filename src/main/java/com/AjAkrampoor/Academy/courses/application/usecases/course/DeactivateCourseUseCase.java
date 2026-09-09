package com.AjAkrampoor.Academy.courses.application.usecases.course;

import com.AjAkrampoor.Academy.courses.domain.model.Course;
import com.AjAkrampoor.Academy.courses.domain.model.CourseId;
import com.AjAkrampoor.Academy.courses.domain.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DeactivateCourseUseCase {

    private final CourseRepository courseRepository;

    public DeactivateCourseUseCase(
            CourseRepository courseRepository
    ) {
        this.courseRepository = courseRepository;
    }

    @Transactional
    public Course execute(UUID courseId) {

        Course course = courseRepository.findById(new CourseId(courseId))
                .orElseThrow(() -> new IllegalArgumentException("No such course found"));

        if (course.isActive()) {
            course.deactivate();
        }

        return courseRepository.save(course);
    }
}
