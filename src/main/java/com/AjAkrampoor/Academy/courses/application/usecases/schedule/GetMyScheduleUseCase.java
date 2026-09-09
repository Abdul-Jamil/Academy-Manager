package com.AjAkrampoor.Academy.courses.application.usecases.schedule;

import com.AjAkrampoor.Academy.courses.domain.model.ScheduleEntry;
import com.AjAkrampoor.Academy.courses.domain.repository.ScheduleEntryRepository;
import com.AjAkrampoor.Academy.staff.domain.model.StaffId;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
public class GetMyScheduleUseCase {

    private final UserRepository userRepository;
    private final StaffRepository staffRepository;
    private final ScheduleEntryRepository scheduleEntryRepository;

    public GetMyScheduleUseCase(
            UserRepository userRepository,
            StaffRepository staffRepository,
            ScheduleEntryRepository scheduleEntryRepository
    ) {
        this.userRepository = userRepository;
        this.staffRepository = staffRepository;
        this.scheduleEntryRepository = scheduleEntryRepository;
    }

    @Transactional(readOnly = true)
    public List<ScheduleEntry> execute(String userId) {

        User user = userRepository.findById(UserId.fromString(userId))
                .orElseThrow(() -> new IllegalArgumentException("No such user found"));

        StaffId staffId = user.getStaffId();

        staffRepository.findById(staffId)
                .orElseThrow(() -> new IllegalArgumentException("No such staff found"));

        return scheduleEntryRepository
                .findByTeacherId(staffId)
                .stream()
                .sorted(Comparator.comparing(ScheduleEntry::getDayOfWeek)
                        .thenComparing(ScheduleEntry::getPeriodNumber))
                .toList();
    }
}
