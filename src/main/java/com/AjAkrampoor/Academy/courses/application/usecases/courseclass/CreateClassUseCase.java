package com.AjAkrampoor.Academy.courses.application.usecases.courseclass;

import com.AjAkrampoor.Academy.branches.domain.model.Branch;
import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.branches.domain.repository.BranchRepository;
import com.AjAkrampoor.Academy.courses.domain.model.*;
import com.AjAkrampoor.Academy.courses.domain.repository.CourseClassRepository;
import com.AjAkrampoor.Academy.roles.domain.model.Role;
import com.AjAkrampoor.Academy.roles.domain.repository.RoleRepository;
import com.AjAkrampoor.Academy.shared.vo.Description;
import com.AjAkrampoor.Academy.shared.vo.Money;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class CreateClassUseCase {

    private static final int MAX_RETRIES = 10;

    private final CourseClassRepository courseClassRepository;
    private final BranchRepository branchRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final StaffRepository staffRepository;

    public CreateClassUseCase(
            CourseClassRepository courseClassRepository,
            BranchRepository branchRepository,
            RoleRepository roleRepository,
            UserRepository userRepository,
            StaffRepository staffRepository
    ) {
        this.courseClassRepository = courseClassRepository;
        this.branchRepository = branchRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.staffRepository = staffRepository;
    }

    @Transactional
    public CourseClass execute(int duration, LocalDate startDate, String descriptionStr, BigDecimal feeAmount, ClassType classType, UUID branchId, boolean isPending, String userId) {

        if (duration < 1) {
            throw new IllegalArgumentException("Duration cannot be less than 1 day");
        }

        if (!isPending && startDate == null) {
            throw new IllegalArgumentException(
                    "Start date must be provided for classes that are not pending"
            );
        }

        Branch requestedBranch = branchRepository.findById(new BranchId(branchId))
                .orElseThrow(() -> new IllegalArgumentException("No such branch found"));

        User user = userRepository.findById(UserId.fromString(userId))
                .orElseThrow(() -> new IllegalArgumentException("No such user found"));

        Staff currentStaff = staffRepository.findById(user.getStaffId())
                .orElseThrow(() -> new IllegalArgumentException("No such staff found"));

        Role userRole = roleRepository.findById(user.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("No such role found"));

        if (!userRole.isSuperAdmin()) {
            if (!requestedBranch.getId().equals(currentStaff.getBranchId())) {
                throw new IllegalArgumentException(
                        "Cannot create a class in another branch"
                );
            }
        }

        Money fee = new Money(feeAmount);
        DurationDays durationDays = new DurationDays(duration);
        Description description = new Description(descriptionStr);

        ClassStatus status = isPending ? ClassStatus.PENDING : ClassStatus.ACTIVE;

        LocalDate actualStartDate = isPending ? null : startDate;

        for (int i = 0; i < MAX_RETRIES; i++) {
            ClassId classId = ClassId.newId();

            if (!courseClassRepository.existsById(classId)) {
                CourseClass courseClass = new CourseClass(classId, durationDays, actualStartDate, null, description, fee, classType, status, requestedBranch.getId()
                );

                return courseClassRepository.save(courseClass);
            }
        }

        throw new IllegalStateException("Could not create class after several retries");
    }
}
