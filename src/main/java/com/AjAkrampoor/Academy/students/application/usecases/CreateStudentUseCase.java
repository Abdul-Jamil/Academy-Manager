package com.AjAkrampoor.Academy.students.application.usecases;

import com.AjAkrampoor.Academy.branches.domain.model.Branch;
import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.branches.domain.repository.BranchRepository;
import com.AjAkrampoor.Academy.roles.domain.model.Role;
import com.AjAkrampoor.Academy.roles.domain.repository.RoleRepository;
import com.AjAkrampoor.Academy.shared.vo.Name;
import com.AjAkrampoor.Academy.shared.vo.PhoneNumber;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import com.AjAkrampoor.Academy.students.domain.model.Student;
import com.AjAkrampoor.Academy.students.domain.model.StudentId;
import com.AjAkrampoor.Academy.students.domain.model.StudentStatus;
import com.AjAkrampoor.Academy.students.domain.repository.StudentRepository;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class CreateStudentUseCase {

    private final BranchRepository branchRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StaffRepository staffRepository;
    private final int MAX_RETRIES = 10;

    public CreateStudentUseCase(BranchRepository branchRepository, StudentRepository studentRepository, UserRepository userRepository, RoleRepository roleRepository, StaffRepository staffRepository) {
        this.branchRepository = branchRepository;
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.staffRepository = staffRepository;
    }

    @Transactional
    public Student execute(String userId, String firstName, String lastName, String guardianFirstName, String guardianLastName, String guardianPhoneNumber, String secondaryPhoneNumber, Long guardianTelegram, String branchId) {
        User user = userRepository.findById(UserId.fromString(userId)).orElseThrow(() -> new IllegalArgumentException("No such user found"));
        Role userRole = roleRepository.findById(user.getRoleId()).orElseThrow(() -> new IllegalArgumentException("No such role found"));
        Staff staff = staffRepository.findById(user.getStaffId()).orElseThrow(() -> new IllegalArgumentException("No such staff found"));

        BranchId finalBranchId = staff.getBranchId();

        if (branchId != null && userRole.isSuperAdmin()) {
            Branch branch = branchRepository.findById(BranchId.fromString(branchId))
                    .orElseThrow(() -> new IllegalArgumentException("No such branch found"));

            if (!branch.isActive()) {
                throw new IllegalArgumentException("Branch is not active");
            }
            finalBranchId = branch.getId();
        }

        Name studentName = new Name(firstName, lastName);
        Name guardianName = new Name(guardianFirstName, guardianLastName);
        PhoneNumber guardianNumber = new PhoneNumber(guardianPhoneNumber);
        PhoneNumber secondaryNumber = (secondaryPhoneNumber != null) ? new PhoneNumber(secondaryPhoneNumber) : null;

        Set<BranchId> branchIds = new HashSet<>();
        branchIds.add(finalBranchId);


        StudentId studentId;
        for (int i = 0; i < 10; i++) {
            studentId = StudentId.generate();
            if (!studentRepository.existsById(studentId)) {
                return studentRepository.save(new Student(studentId, studentName, guardianName, guardianNumber, secondaryNumber, guardianTelegram, StudentStatus.ACTIVE, branchIds));
            }
        }
        throw new IllegalArgumentException("Could not create student after several retries, please try again later");
    }
}
