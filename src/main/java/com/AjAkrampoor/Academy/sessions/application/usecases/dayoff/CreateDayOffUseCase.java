package com.AjAkrampoor.Academy.sessions.application.usecases.dayoff;

import com.AjAkrampoor.Academy.branches.domain.model.Branch;
import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.branches.domain.repository.BranchRepository;
import com.AjAkrampoor.Academy.roles.domain.model.Role;
import com.AjAkrampoor.Academy.roles.domain.model.RoleId;
import com.AjAkrampoor.Academy.roles.domain.repository.RoleRepository;
import com.AjAkrampoor.Academy.sessions.domain.model.DayOff;
import com.AjAkrampoor.Academy.sessions.domain.model.DayOffId;
import com.AjAkrampoor.Academy.sessions.domain.model.DayOffStatus;
import com.AjAkrampoor.Academy.sessions.domain.repository.DayOffRepository;
import com.AjAkrampoor.Academy.shared.vo.Description;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class CreateDayOffUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StaffRepository staffRepository;
    private final DayOffRepository dayOffRepository;
    private final BranchRepository branchRepository;

    private static final int MAX_ATTEMPTS = 10;

    public CreateDayOffUseCase(UserRepository userRepository,
                               RoleRepository roleRepository,
                               StaffRepository staffRepository,
                               DayOffRepository dayOffRepository,
                               BranchRepository branchRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.staffRepository = staffRepository;
        this.dayOffRepository = dayOffRepository;
        this.branchRepository = branchRepository;
    }

    @Transactional
    public DayOff execute(String userId, LocalDate date, String descriptionStr, UUID branchUUID) {
        User user = findUser(userId);
        Role userRole = findRole(user.getRoleId());
        Staff staff = findStaff(user.getStaffId());

        validateDate(date);

        Branch targetBranch = resolveTargetBranch(branchUUID, userRole, staff);
        Description description = descriptionStr != null ? new Description(descriptionStr) : null;

        DayOffId dayOffId = generateUniqueDayOffId();

        BranchId branchId = targetBranch != null ? targetBranch.getId() : null;
        return dayOffRepository.save(
                new DayOff(dayOffId, date, description, DayOffStatus.ACTIVE, user.getUserId(), branchId)
        );
    }

    private User findUser(String userId) {
        return userRepository.findById(UserId.fromString(userId))
                .orElseThrow(() -> new IllegalArgumentException("No such user found"));
    }

    private Role findRole(RoleId roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("No such role found"));
    }

    private Staff findStaff(StaffId staffId) {
        return staffRepository.findById(staffId)
                .orElseThrow(() -> new IllegalArgumentException("No such staff found"));
    }

    private void validateDate(LocalDate date) {
        LocalDate today = LocalDate.now();
        if (date.isBefore(today) || date.equals(today)) {
            throw new IllegalArgumentException("Day off must be scheduled for tomorrow or a future date.");
        }
    }

    private Branch resolveTargetBranch(UUID branchUUID, Role userRole, Staff staff) {
        if (userRole.isSuperAdmin()) {
            return branchUUID != null ? findBranch(branchUUID) : null;
        }

        if (branchUUID == null) {
            throw new IllegalArgumentException("Cannot create a global day off");
        }

        Branch requestedBranch = findBranch(branchUUID);
        if (!requestedBranch.getId().equals(staff.getBranchId())) {
            throw new IllegalArgumentException("Cannot access other branches' data");
        }
        return requestedBranch;
    }

    private Branch findBranch(UUID branchUUID) {
        return branchRepository.findById(new BranchId(branchUUID))
                .orElseThrow(() -> new IllegalArgumentException("No such branch found"));
    }

    private DayOffId generateUniqueDayOffId() {
        for (int i = 0; i < MAX_ATTEMPTS; i++) {
            DayOffId candidate = DayOffId.newId();
            if (!dayOffRepository.existsById(candidate)) {
                return candidate;
            }
        }
        throw new IllegalArgumentException("Could not create day off after several retries, please try again.");
    }
}