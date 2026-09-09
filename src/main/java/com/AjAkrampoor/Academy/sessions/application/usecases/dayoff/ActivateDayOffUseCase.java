package com.AjAkrampoor.Academy.sessions.application.usecases.dayoff;

import com.AjAkrampoor.Academy.roles.domain.model.Role;
import com.AjAkrampoor.Academy.roles.domain.repository.RoleRepository;
import com.AjAkrampoor.Academy.sessions.domain.model.DayOff;
import com.AjAkrampoor.Academy.sessions.domain.model.DayOffId;
import com.AjAkrampoor.Academy.sessions.domain.repository.DayOffRepository;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ActivateDayOffUseCase {

    private final UserRepository userRepository;
    private final DayOffRepository dayOffRepository;
    private final RoleRepository roleRepository;
    private final StaffRepository staffRepository;

    public ActivateDayOffUseCase(UserRepository userRepository, DayOffRepository dayOffRepository, RoleRepository roleRepository, StaffRepository staffRepository) {
        this.userRepository = userRepository;
        this.dayOffRepository = dayOffRepository;
        this.roleRepository = roleRepository;
        this.staffRepository = staffRepository;
    }

    @Transactional
    public DayOff execute(String userId, UUID dayOffUUID) {
        User user = userRepository.findById(UserId.fromString(userId)).orElseThrow(() -> new IllegalArgumentException("No such user found"));
        Role userRole = roleRepository.findById(user.getRoleId()).orElseThrow(() -> new IllegalArgumentException("No such role found"));
        Staff staff = staffRepository.findById(user.getStaffId()).orElseThrow(() -> new IllegalArgumentException("No such staff found"));
        DayOff dayOff = dayOffRepository.findById(new DayOffId(dayOffUUID)).orElseThrow(() -> new IllegalArgumentException("No such day off found"));

        if (!userRole.isSuperAdmin()) {
            if (dayOff.getBranchId() == null) {
                throw new IllegalArgumentException("Cannot edit global day off record");
            }

            if (!dayOff.getBranchId().equals(staff.getBranchId())) {
                throw new IllegalArgumentException("Cannot access other branches' data");
            }
        }

        if (dayOff.isActive()) {
            return dayOff;
        }
        dayOff.restore();

        return dayOffRepository.save(dayOff);
    }
}
