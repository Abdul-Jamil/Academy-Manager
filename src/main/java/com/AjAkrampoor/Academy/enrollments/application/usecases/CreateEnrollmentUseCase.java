package com.AjAkrampoor.Academy.enrollments.application.usecases;

import com.AjAkrampoor.Academy.bills.application.usecases.CreateBillUseCase;
import com.AjAkrampoor.Academy.courses.domain.model.ClassId;
import com.AjAkrampoor.Academy.courses.domain.model.CourseClass;
import com.AjAkrampoor.Academy.courses.domain.repository.CourseClassRepository;
import com.AjAkrampoor.Academy.enrollments.domain.model.Enrollment;
import com.AjAkrampoor.Academy.enrollments.domain.model.EnrollmentId;
import com.AjAkrampoor.Academy.enrollments.domain.model.EnrollmentStatus;
import com.AjAkrampoor.Academy.enrollments.domain.repository.EnrollmentRepository;
import com.AjAkrampoor.Academy.roles.domain.model.Role;
import com.AjAkrampoor.Academy.roles.domain.repository.RoleRepository;
import com.AjAkrampoor.Academy.shared.vo.Description;
import com.AjAkrampoor.Academy.shared.vo.Money;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import com.AjAkrampoor.Academy.students.domain.model.Student;
import com.AjAkrampoor.Academy.students.domain.model.StudentId;
import com.AjAkrampoor.Academy.students.domain.repository.StudentRepository;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class CreateEnrollmentUseCase {
    private final UserRepository userRepository;
    private final CourseClassRepository classRepository;
    private final StaffRepository staffRepository;
    private final RoleRepository roleRepository;
    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final CreateBillUseCase createBill;


    public CreateEnrollmentUseCase(UserRepository userRepository, CourseClassRepository classRepository, StaffRepository staffRepository, RoleRepository roleRepository, StudentRepository studentRepository, EnrollmentRepository enrollmentRepository, CreateBillUseCase createBill) {
        this.userRepository = userRepository;
        this.classRepository = classRepository;
        this.staffRepository = staffRepository;
        this.roleRepository = roleRepository;
        this.studentRepository = studentRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.createBill = createBill;
    }


    @Transactional
    public Enrollment execute(String userId, String studentId, UUID classId, String descriptionStr, BigDecimal discountAmount) {
        User user = userRepository.findById(UserId.fromString(userId)).orElseThrow(() -> new IllegalArgumentException("No such user found"));
        Role userRole = roleRepository.findById(user.getRoleId()).orElseThrow(() -> new IllegalArgumentException("No such role found"));
        Staff staff = staffRepository.findById(user.getStaffId()).orElseThrow(() -> new IllegalArgumentException("No such staff found"));
        CourseClass courseClass = classRepository.findById(new ClassId(classId)).orElseThrow(() -> new IllegalArgumentException("No such class found"));
        if (!courseClass.isActive() && !courseClass.isPending()) {
            throw new IllegalArgumentException("Cannot enroll in a class that is not active or pending");
        }
        Student student = studentRepository.findById(StudentId.from(studentId)).orElseThrow(() -> new IllegalArgumentException("No such student found"));
        Money discount = new Money(discountAmount);

        boolean alreadyEnrolled = enrollmentRepository.existsActiveEnrollment(student.getStudentId(), courseClass.getId());
        if (alreadyEnrolled) {
            throw new IllegalStateException("Student is already enrolled in this class");
        }

        if (!userRole.isSuperAdmin()) {
            if (!staff.getBranchId().equals(courseClass.getBranchId()) || !student.getAssignedBranches().contains(staff.getBranchId())) {
                throw new IllegalArgumentException("Cannot access other branches' info");
            }
        }


        EnrollmentId enrollmentId;
        for (int i = 0; i < 10; i++) {
            enrollmentId = EnrollmentId.newId();
            if (!enrollmentRepository.existsById(enrollmentId)) {
                Enrollment enrollment = new Enrollment
                        (
                                enrollmentId,
                                null, //Description
                                student.getStudentId(),
                                courseClass.getId(),
                                LocalDate.now(),
                                user.getUserId(),
                                EnrollmentStatus.ENROLLED
                        );

                if (descriptionStr != null) {
                    Description description = new Description(descriptionStr);
                    enrollment.updateDescription(description);
                }

                Enrollment save = enrollmentRepository.save(enrollment);
                createBill.execute
                        (
                                UUID.fromString(save.getEnrollmentId().toString()),
                                courseClass.getFee().getAmount(),
                                discount.getAmount(),
                                descriptionStr,
                                UUID.fromString(courseClass.getBranchId().toString())
                        );
                return save;
            }
        }

        throw new IllegalArgumentException("Could not save enrollment after several retries");
    }
}
