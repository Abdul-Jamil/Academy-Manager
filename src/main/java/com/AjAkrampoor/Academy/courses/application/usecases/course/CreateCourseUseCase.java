package com.AjAkrampoor.Academy.courses.application.usecases.course;

import com.AjAkrampoor.Academy.courses.domain.model.Course;
import com.AjAkrampoor.Academy.courses.domain.model.CourseId;
import com.AjAkrampoor.Academy.courses.domain.model.CourseName;
import com.AjAkrampoor.Academy.courses.domain.model.CourseStatus;
import com.AjAkrampoor.Academy.courses.domain.repository.CourseRepository;
import com.AjAkrampoor.Academy.shared.vo.Description;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateCourseUseCase {

    private static final int MAX_RETRIES = 10;

    private final CourseRepository courseRepository;

    public CreateCourseUseCase(
            CourseRepository courseRepository
    ) {
        this.courseRepository = courseRepository;
    }

    @Transactional
    public Course execute(String courseName, String courseDescription) {

        CourseName name = new CourseName(courseName);

        if (courseRepository.existsByCourseName(name)) {
            throw new IllegalArgumentException("A course with the name '" + courseName + "' already exists");
        }

        Description description = courseDescription == null || courseDescription.isBlank() ? null : new Description(courseDescription);

        for (int i = 0; i < MAX_RETRIES; i++) {
            CourseId courseId = CourseId.newId();
            if (!courseRepository.existsById(courseId)) {
                return courseRepository.save(new Course(courseId, description, name, CourseStatus.ACTIVE));
            }
        }

        throw new IllegalStateException("Could not create course after several retries"
        );
    }
}
