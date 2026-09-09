package com.AjAkrampoor.Academy.bills.application;

import com.AjAkrampoor.Academy.bills.application.dto.BillResponse;
import com.AjAkrampoor.Academy.bills.domain.model.Bill;
import com.AjAkrampoor.Academy.branches.domain.model.Branch;
import com.AjAkrampoor.Academy.branches.domain.repository.BranchRepository;
import com.AjAkrampoor.Academy.courses.domain.model.CourseClass;
import com.AjAkrampoor.Academy.courses.domain.repository.CourseClassRepository;
import com.AjAkrampoor.Academy.enrollments.domain.model.Enrollment;
import com.AjAkrampoor.Academy.enrollments.domain.repository.EnrollmentRepository;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import com.AjAkrampoor.Academy.students.domain.model.Student;
import com.AjAkrampoor.Academy.students.domain.repository.StudentRepository;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BillResponseAssembler {

    private final UserRepository userRepository;
    private final StaffRepository staffRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseClassRepository classRepository;
    private final BranchRepository branchRepository;

    public BillResponseAssembler(UserRepository userRepository, StaffRepository staffRepository, EnrollmentRepository enrollmentRepository, StudentRepository studentRepository, CourseClassRepository classRepository, BranchRepository branchRepository) {
        this.userRepository = userRepository;
        this.staffRepository = staffRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.classRepository = classRepository;
        this.branchRepository = branchRepository;
    }


    public BillResponse toResponse(Bill bill, BigDecimal paidAmount) {

        Enrollment enrollment = enrollmentRepository.findById(bill.getEnrollmentId()).orElseThrow(() -> new IllegalArgumentException("Enrollment not found"));
        Student student = studentRepository.findById(enrollment.getStudentId()).orElseThrow(() -> new IllegalArgumentException("Student not found"));
        CourseClass courseClass = classRepository.findById(enrollment.getClassId()).orElseThrow(() -> new IllegalArgumentException("Class not found"));
        String className = (courseClass.getDescription() == null) ? null : courseClass.getDescription().toString();
        Branch branch = branchRepository.findById(bill.getBranchId()).orElseThrow(() -> new IllegalArgumentException("No such branch found"));

        String cancelledBy = null;
        if (bill.getCancelledBy() != null) {
            User user = userRepository.findById(bill.getCancelledBy()).orElseThrow(() -> new IllegalArgumentException("No such user found"));
            Staff staff = staffRepository.findById(user.getStaffId()).orElseThrow(() -> new IllegalArgumentException("No such staff found"));
            cancelledBy = staff.getName().toString();
        }
        BillResponse response = new BillResponse();
        response.setBillId(bill.getBillId().toString());
        response.setStudentName(student.getStudentName().getFullName());
        response.setClassName(className);
        response.setEnrollmentId(bill.getEnrollmentId().toString());
        response.setAmount(bill.getAmount().getAmount());
        response.setDiscount(bill.getDiscount() != null ? bill.getDiscount().getAmount() : BigDecimal.ZERO);
        response.setDescription(bill.getDescription() != null ? bill.getDescription().getValue() : null);
        response.setCreatedAt(bill.getCreatedAt());
        response.setBranchName(branch.getName().toString());
        response.setPaidAmount(paidAmount != null ? paidAmount : BigDecimal.ZERO);
        response.setStatus(bill.getStatus());
        response.setCancelledAt(bill.getCancelledAt());
        response.setCancelledBy(cancelledBy);
        return response;
    }
}
