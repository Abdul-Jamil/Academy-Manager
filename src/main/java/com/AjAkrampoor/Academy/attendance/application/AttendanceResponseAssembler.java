package com.AjAkrampoor.Academy.attendance.application;

import com.AjAkrampoor.Academy.attendance.application.dto.AttendanceResponse;
import com.AjAkrampoor.Academy.attendance.domain.model.Attendance;
import com.AjAkrampoor.Academy.courses.domain.model.CourseClass;
import com.AjAkrampoor.Academy.courses.domain.repository.CourseClassRepository;
import com.AjAkrampoor.Academy.students.domain.model.Student;
import com.AjAkrampoor.Academy.students.domain.repository.StudentRepository;
import org.springframework.stereotype.Component;

@Component
public class AttendanceResponseAssembler {
    private final StudentRepository studentRepository;
    private final CourseClassRepository classRepository;

    public AttendanceResponseAssembler(StudentRepository studentRepository, CourseClassRepository classRepository) {
        this.studentRepository = studentRepository;
        this.classRepository = classRepository;
    }

    public AttendanceResponse toResponse(Attendance attendance) {

        Student student = studentRepository.findById(attendance.getStudentId()).orElseThrow(() -> new IllegalArgumentException("Student not found"));
        CourseClass courseClass = classRepository.findById(attendance.getClassId()).orElseThrow(() -> new IllegalArgumentException("Class not found"));

        String description =
                attendance.getDescription() == null
                        ? null
                        : attendance.getDescription().getValue();

        return new AttendanceResponse(
                attendance.getId().toString(),
                student.getStudentName().getFullName(),
                courseClass.getDescription().toString(),
                attendance.getAttendanceTime(),
                attendance.getStatus(),
                description
        );
    }
}
