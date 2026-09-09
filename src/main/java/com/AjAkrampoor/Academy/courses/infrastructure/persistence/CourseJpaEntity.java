package com.AjAkrampoor.Academy.courses.infrastructure.persistence;

import com.AjAkrampoor.Academy.courses.domain.model.CourseId;
import com.AjAkrampoor.Academy.courses.domain.model.CourseName;
import com.AjAkrampoor.Academy.courses.domain.model.CourseStatus;
import com.AjAkrampoor.Academy.shared.vo.Description;
import jakarta.persistence.*;

@Entity
@Table(name = "courses")
public class CourseJpaEntity {

    @EmbeddedId
    @AttributeOverride(
            name = "value",
            column = @Column(
                    name = "id",
                    nullable = false,
                    unique = true
            )
    )
    private CourseId courseId;

    @Embedded
    @AttributeOverride(
            name = "value",
            column = @Column(name = "description")
    )
    private Description description;

    @Embedded
    @AttributeOverride(
            name = "value",
            column = @Column(
                    name = "course_name",
                    nullable = false,
                    unique = true
            )
    )
    private CourseName courseName;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "course_status",
            nullable = false
    )
    private CourseStatus status;

    public CourseJpaEntity() {
    }

    public CourseId getCourseId() {
        return courseId;
    }

    public void setCourseId(CourseId courseId) {
        this.courseId = courseId;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public CourseName getCourseName() {
        return courseName;
    }

    public void setCourseName(CourseName courseName) {
        this.courseName = courseName;
    }

    public CourseStatus getStatus() {
        return status;
    }

    public void setStatus(CourseStatus status) {
        this.status = status;
    }
}
