package com.AjAkrampoor.Academy.courses.domain.model;

import com.AjAkrampoor.Academy.shared.vo.Description;

import java.util.Objects;

public class Course {

    private CourseId courseId;
    private Description description;
    private CourseName courseName;
    private CourseStatus courseStatus;

    public Course(
            CourseId courseId,
            Description description,
            CourseName courseName,
            CourseStatus courseStatus
    ) {
        this.courseId = Objects.requireNonNull(courseId, "Course ID cannot be null");
        this.courseName = Objects.requireNonNull(courseName, "Course name cannot be null");
        this.description = description;
        this.courseStatus = Objects.requireNonNull(courseStatus, "Course status cannot be null");
    }

    public void updateDescription(Description description) {
        this.description = description;
    }

    public void updateName(CourseName courseName) {

        if (courseName == null) {
            throw new IllegalArgumentException("Course name cannot be null");
        }

        this.courseName = courseName;
    }

    public void activate() {
        this.courseStatus = CourseStatus.ACTIVE;
    }

    public void deactivate() {
        this.courseStatus = CourseStatus.INACTIVE;
    }

    public boolean isActive() {
        return this.courseStatus == CourseStatus.ACTIVE;
    }

    public boolean isInactive() {
        return this.courseStatus == CourseStatus.INACTIVE;
    }

    public CourseId getCourseId() {
        return courseId;
    }

    public Description getDescription() {
        return description;
    }

    public CourseName getCourseName() {
        return courseName;
    }

    public CourseStatus getCourseStatus() {
        return courseStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;
        return Objects.equals(courseId, course.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId);
    }
}
