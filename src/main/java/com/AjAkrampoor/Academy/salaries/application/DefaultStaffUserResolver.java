package com.AjAkrampoor.Academy.salaries.application;

import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class DefaultStaffUserResolver
        implements StaffUserResolver {

    private final UserRepository userRepository;

    public DefaultStaffUserResolver(
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    @Override
    public UserId resolveUserId(StaffId staffId) {

        User user = userRepository
                .findByStaffId(staffId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "No user found for staff member: "
                                        + staffId
                        )
                );

        return user.getUserId();
    }
}