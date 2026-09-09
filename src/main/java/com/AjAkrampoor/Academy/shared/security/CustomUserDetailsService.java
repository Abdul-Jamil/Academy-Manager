package com.AjAkrampoor.Academy.shared.security;

import com.AjAkrampoor.Academy.branches.domain.model.Branch;
import com.AjAkrampoor.Academy.branches.domain.repository.BranchRepository;
import com.AjAkrampoor.Academy.roles.domain.model.Role;
import com.AjAkrampoor.Academy.roles.domain.repository.RoleRepository;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.model.UserName;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BranchRepository branchRepository;
    private final StaffRepository staffRepository;

    public CustomUserDetailsService(UserRepository userRepository, RoleRepository roleRepository, BranchRepository branchRepository, StaffRepository staffRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.branchRepository = branchRepository;
        this.staffRepository = staffRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserName userName = new UserName(username);

        User user = userRepository.findByUserName(userName).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        Staff staff = staffRepository.findById(user.getStaffId()).orElseThrow(() -> new UsernameNotFoundException("Staff for user not found: " + username));

        if (!staff.isActive()) {
            throw new DisabledException("User is disabled, cannot login");
        }

        Branch branch = branchRepository.findById(staff.getBranchId()).orElseThrow(() -> new InternalAuthenticationServiceException("User branch not found, contact system admin"));
        if (!branch.isActive()) {
            throw new DisabledException("Branch is disabled, cannot login");
        }

        Role role = roleRepository.findById(user.getRoleId())
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Role not found for user: " + username));

        return new CustomUserDetails(user, role, branch.getId(), staff);
    }
}