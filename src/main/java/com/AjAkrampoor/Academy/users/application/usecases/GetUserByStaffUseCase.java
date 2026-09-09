package com.AjAkrampoor.Academy.users.application.usecases;

import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class GetUserByStaffUseCase {

    private final UserRepository userRepository;

    public GetUserByStaffUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(String staffId) {
        return userRepository.findByStaffId(StaffId.from(staffId)).orElseThrow(() -> new IllegalArgumentException("No such staff found"));
    }
}
