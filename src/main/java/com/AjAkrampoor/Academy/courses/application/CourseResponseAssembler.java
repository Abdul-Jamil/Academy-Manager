package com.AjAkrampoor.Academy.courses.application;

import com.AjAkrampoor.Academy.courses.application.dto.CourseResponse;
import com.AjAkrampoor.Academy.courses.domain.model.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseResponseAssembler {

    public CourseResponse toResponse(Course course) {

        String description =
                course.getDescription() == null
                        ? null
                        : course.getDescription().getValue();

        return new CourseResponse(
                course.getCourseId().toString(),
                description,
                course.getCourseName().getValue(),
                course.getCourseStatus()
        );
    }
}
