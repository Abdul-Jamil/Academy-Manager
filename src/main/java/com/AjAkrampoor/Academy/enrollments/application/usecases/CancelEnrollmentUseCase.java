package com.AjAkrampoor.Academy.enrollments.application.usecases;

import com.AjAkrampoor.Academy.bills.domain.model.Bill;
import com.AjAkrampoor.Academy.bills.domain.repository.BillRepository;
import com.AjAkrampoor.Academy.courses.domain.model.CourseClass;
import com.AjAkrampoor.Academy.courses.domain.repository.CourseClassRepository;
import com.AjAkrampoor.Academy.enrollments.domain.model.Enrollment;
import com.AjAkrampoor.Academy.enrollments.domain.model.EnrollmentId;
import com.AjAkrampoor.Academy.enrollments.domain.repository.EnrollmentRepository;
import com.AjAkrampoor.Academy.roles.domain.model.Role;
import com.AjAkrampoor.Academy.roles.domain.repository.RoleRepository;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CancelEnrollmentUseCase {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseClassRepository classRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StaffRepository staffRepository;
    private final BillRepository billRepository;

    public CancelEnrollmentUseCase(EnrollmentRepository enrollmentRepository, CourseClassRepository classRepository, UserRepository userRepository, RoleRepository roleRepository, StaffRepository staffRepository, BillRepository billRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.classRepository = classRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.staffRepository = staffRepository;
        this.billRepository = billRepository;
    }

    @Transactional
    public Enrollment execute(String userId, UUID enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(new EnrollmentId(enrollmentId)).orElseThrow(() -> new IllegalArgumentException("No such enrollment found"));
        User user = userRepository.findById(UserId.fromString(userId)).orElseThrow(() -> new IllegalArgumentException("No such user found"));
        Role userRole = roleRepository.findById(user.getRoleId()).orElseThrow(() -> new IllegalArgumentException("No such role found"));
        Staff staff = staffRepository.findById(user.getStaffId()).orElseThrow(() -> new IllegalArgumentException("No such staff found"));
        CourseClass courseClass = classRepository.findById(enrollment.getClassId()).orElseThrow(() -> new IllegalArgumentException("No such class found"));

        if (!courseClass.isActive()) {
            throw new IllegalArgumentException("Class is not active");
        }

        if (!userRole.isSuperAdmin()) {
            if (!courseClass.getBranchId().equals(staff.getBranchId())) {
                throw new IllegalArgumentException("Cannot access other branches' data");
            }
        }

        if (enrollment.isCancelled()) {
            return enrollment;
        }

        if (enrollment.isTransferred()) {
            throw new IllegalArgumentException("Cannot cancel a transferred enrollment");
        }

        if (enrollment.isEnrolled()) {
            enrollment.cancel(user.getUserId());
        }

        Enrollment saved = enrollmentRepository.save(enrollment);

        List<Bill> bills = billRepository.findByEnrollmentId(enrollment.getEnrollmentId());
        for (Bill bill : bills) {
            bill.cancel(user.getUserId());
            billRepository.save(bill);
        }

        return saved;

    }
}
