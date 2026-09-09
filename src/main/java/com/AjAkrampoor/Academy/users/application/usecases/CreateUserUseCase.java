package com.AjAkrampoor.Academy.users.application.usecases;

import com.AjAkrampoor.Academy.roles.domain.model.Role;
import com.AjAkrampoor.Academy.roles.domain.model.RoleId;
import com.AjAkrampoor.Academy.roles.domain.repository.RoleRepository;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import com.AjAkrampoor.Academy.users.domain.model.*;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class CreateUserUseCase {

    private static final int MAX_RETRIES = 10;
    private static final String SUPERADMIN = "SUPERADMIN";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StaffRepository staffRepository;

    public CreateUserUseCase(UserRepository userRepository, RoleRepository roleRepository, StaffRepository staffRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.staffRepository = staffRepository;
    }

    public User execute(String username, String password, UUID roleId, String staffId) {

        UserName userName = new UserName(username);

        validateUsername(userName);

        Role role = validateAndGetRole(roleId);
        StaffId staff = validateAndGetStaff(staffId);

        if (userRepository.findByStaffId(staff).isPresent()) {
            throw new IllegalArgumentException("A user already exists for this staff member");
        }

        User user = User.create(generateUniqueUserId(), userName, Password.createFromRaw(password), Language.EN, role.getId(), staff);

        return userRepository.save(user);
    }

    private void validateUsername(UserName userName) {
        if (userRepository.existsByUserName(userName)) {
            throw new IllegalArgumentException("Username already exists: " + userName.getValue());
        }
    }

    private StaffId validateAndGetStaff(String staffId) {
        StaffId id = StaffId.from(staffId);
        Staff staff = staffRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No such staff found"));

        if (!staff.isActive()) {
            throw new IllegalArgumentException("Staff is not active");
        }

        return id;
    }

    private Role validateAndGetRole(UUID roleId) {
        Role role = roleRepository.findById(new RoleId(roleId)).orElseThrow(() -> new IllegalArgumentException("No such role found"));

        if (!role.isActive()) {
            throw new IllegalArgumentException("Cannot assign inactive role to users");
        }

        if (SUPERADMIN.equals(role.getName().toString())) {
            throw new IllegalArgumentException("Can't assign root admin role to users");
        }

        return role;
    }

    private UserId generateUniqueUserId() {
        for (int i = 0; i < MAX_RETRIES; i++) {
            UserId id = UserId.newId();

            if (!userRepository.existsById(id)) {
                return id;
            }
        }

        throw new IllegalStateException("Couldn't create user after several retries, please try again later");
    }
}
