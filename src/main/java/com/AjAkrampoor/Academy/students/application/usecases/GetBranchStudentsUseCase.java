package com.AjAkrampoor.Academy.students.application.usecases;

import com.AjAkrampoor.Academy.branches.domain.model.Branch;
import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.branches.domain.repository.BranchRepository;
import com.AjAkrampoor.Academy.roles.domain.model.Role;
import com.AjAkrampoor.Academy.roles.domain.repository.RoleRepository;
import com.AjAkrampoor.Academy.shared.dto.PaginatedResponse;
import com.AjAkrampoor.Academy.shared.dto.PaginationRequest;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import com.AjAkrampoor.Academy.students.application.dto.StudentFilter;
import com.AjAkrampoor.Academy.students.domain.model.Student;
import com.AjAkrampoor.Academy.students.domain.repository.StudentRepository;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class GetBranchStudentsUseCase {
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StaffRepository staffRepository;
    private final BranchRepository branchRepository;

    public GetBranchStudentsUseCase(StudentRepository studentRepository, UserRepository userRepository, RoleRepository roleRepository, StaffRepository staffRepository, BranchRepository branchRepository) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.staffRepository = staffRepository;
        this.branchRepository = branchRepository;
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<Student> execute(
            String userId,
            StudentFilter filter,
            PaginationRequest paginationRequest,
            UUID branchUUID
    ) {
        User user = userRepository.findById(UserId.fromString(userId))
                .orElseThrow(() -> new IllegalArgumentException("No such user found"));
        Role userRole = roleRepository.findById(user.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("No such role found"));
        Staff staff = staffRepository.findById(user.getStaffId())
                .orElseThrow(() -> new IllegalArgumentException("No such staff found"));

        BranchId finalBranchId;
        if (branchUUID != null) {
            Branch requestedBranch = branchRepository.findById(new BranchId(branchUUID))
                    .orElseThrow(() -> new IllegalArgumentException("No such branch found"));

            if (!userRole.isSuperAdmin()) {
                if (!requestedBranch.getId().equals(staff.getBranchId())) {
                    throw new IllegalArgumentException("Cannot access other branches' data");
                }
            }
            finalBranchId = requestedBranch.getId();
        } else {
            finalBranchId = staff.getBranchId();
        }

        if (filter == null) {
            filter = new StudentFilter();
        }
        filter.setBranchId(finalBranchId.asString());

        Pageable pageable = paginationRequest.toPageable();
        Page<Student> studentPage = studentRepository.findByFilter(filter, pageable);
        return new PaginatedResponse<>(studentPage);
    }
}
