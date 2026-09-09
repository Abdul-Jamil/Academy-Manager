package com.AjAkrampoor.Academy.courses.application.usecases.course;

import com.AjAkrampoor.Academy.courses.domain.model.Course;
import com.AjAkrampoor.Academy.courses.domain.model.CourseId;
import com.AjAkrampoor.Academy.courses.domain.model.CourseName;
import com.AjAkrampoor.Academy.courses.domain.repository.CourseRepository;
import com.AjAkrampoor.Academy.shared.vo.Description;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UpdateCourseUseCase {

    private final CourseRepository courseRepository;

    public UpdateCourseUseCase(
            CourseRepository courseRepository
    ) {
        this.courseRepository = courseRepository;
    }

    @Transactional
    public Course execute(UUID courseId, String newName, String newDescription) {

        Course course = courseRepository.findById(new CourseId(courseId))
                .orElseThrow(() -> new IllegalArgumentException("No such course found"));

        if (newDescription != null) {
            course.updateDescription(new Description(newDescription));
        }

        if (newName != null && !newName.isBlank()) {
            CourseName newCourseName = new CourseName(newName);
            boolean changed = !newCourseName.equals(course.getCourseName());

            if (changed && courseRepository.existsByCourseName(newCourseName)) {
                throw new IllegalArgumentException("A course with this name already exists");
            }

            if (changed) {
                course.updateName(newCourseName);
            }
        }
        return courseRepository.save(course);
    }
}
