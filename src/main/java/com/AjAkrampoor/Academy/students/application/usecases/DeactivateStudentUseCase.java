package com.AjAkrampoor.Academy.students.application.usecases;

import com.AjAkrampoor.Academy.enrollments.domain.repository.EnrollmentRepository;
import com.AjAkrampoor.Academy.roles.domain.repository.RoleRepository;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import com.AjAkrampoor.Academy.students.domain.model.Student;
import com.AjAkrampoor.Academy.students.domain.model.StudentId;
import com.AjAkrampoor.Academy.students.domain.repository.StudentRepository;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeactivateStudentUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StaffRepository staffRepository;
    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;

    public DeactivateStudentUseCase(UserRepository userRepository, RoleRepository roleRepository, StaffRepository staffRepository, StudentRepository studentRepository, EnrollmentRepository enrollmentRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.staffRepository = staffRepository;
        this.studentRepository = studentRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Transactional
    public Student execute(String userId, String studentId) {
        Student student = studentRepository.findById(StudentId.from(studentId)).orElseThrow(() -> new IllegalArgumentException("No such student found"));

        if (!student.isActive()) {
            return student;
        }

        student.deactivate();

        return studentRepository.save(student);

    }
}
