package com.AjAkrampoor.Academy.courses.application.dto;

import com.AjAkrampoor.Academy.courses.domain.model.CourseStatus;

public class CourseResponse {

    private String courseId;
    private String description;
    private String courseName;
    private CourseStatus courseStatus;

    public CourseResponse(
            String courseId,
            String description,
            String courseName,
            CourseStatus courseStatus
    ) {
        this.courseId = courseId;
        this.description = description;
        this.courseName = courseName;
        this.courseStatus = courseStatus;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getDescription() {
        return description;
    }

    public String getCourseName() {
        return courseName;
    }

    public CourseStatus getCourseStatus() {
        return courseStatus;
    }
}
