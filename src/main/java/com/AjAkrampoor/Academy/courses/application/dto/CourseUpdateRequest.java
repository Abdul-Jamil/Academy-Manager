package com.AjAkrampoor.Academy.courses.application.dto;

import jakarta.validation.constraints.Size;

public class CourseUpdateRequest {

    @Size(
            min = 3,
            max = 80,
            message = "Course name must be between 3 and 80 characters"
    )
    private String courseName;

    @Size(
            max = 200,
            message = "Course description cannot exceed 200 characters"
    )
    private String description;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
