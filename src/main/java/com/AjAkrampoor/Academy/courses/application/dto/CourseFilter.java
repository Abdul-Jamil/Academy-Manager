package com.AjAkrampoor.Academy.courses.application.dto;

import com.AjAkrampoor.Academy.courses.domain.model.CourseStatus;

public class CourseFilter {

    private String name;
    private CourseStatus status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CourseStatus getStatus() {
        return status;
    }

    public void setStatus(CourseStatus status) {
        this.status = status;
    }
}
