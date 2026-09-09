package com.AjAkrampoor.Academy.students.application.usecases;

import com.AjAkrampoor.Academy.shared.vo.PhoneNumber;
import com.AjAkrampoor.Academy.students.domain.model.Student;
import com.AjAkrampoor.Academy.students.domain.model.StudentId;
import com.AjAkrampoor.Academy.students.domain.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateStudentUseCase {

    private static final String NO_NUMBER = "0";

    private final StudentRepository studentRepository;

    public UpdateStudentUseCase(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Transactional
    public Student execute(
            String studentId,
            String firstName,
            String lastName,
            String guardianFirstName,
            String guardianLastName,
            String guardianPhoneNumber,
            String secondaryPhoneNumber,
            Long guardianTelegram
    ) {

        Student student = studentRepository.findById(StudentId.from(studentId)).orElseThrow(() -> new IllegalArgumentException("No such student found"));

        student.updateStudentName(firstName, lastName);
        student.updateGuardianName(guardianFirstName, guardianLastName);

        if (guardianPhoneNumber != null) {
            student.updateGuardianNumber(new PhoneNumber(guardianPhoneNumber));
        }

        if (secondaryPhoneNumber != null) {

            if (NO_NUMBER.equals(secondaryPhoneNumber)) {
                student.updateSecondaryNumber(null);
            } else {
                student.updateSecondaryNumber(new PhoneNumber(secondaryPhoneNumber));
            }
        }

        if (guardianTelegram != null) {
            student.updateGuardianTelegram(guardianTelegram);
        }
        return studentRepository.save(student);
    }
}
