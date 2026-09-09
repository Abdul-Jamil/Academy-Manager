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

import java.time.DayOfWeek;
import java.util.Comparator;
import java.util.List;

@Service
public class GetMyScheduleByWeekdayUseCase {

    private final UserRepository userRepository;
    private final StaffRepository staffRepository;
    private final ScheduleEntryRepository scheduleEntryRepository;

    public GetMyScheduleByWeekdayUseCase(
            UserRepository userRepository,
            StaffRepository staffRepository,
            ScheduleEntryRepository scheduleEntryRepository
    ) {
        this.userRepository = userRepository;
        this.staffRepository = staffRepository;
        this.scheduleEntryRepository = scheduleEntryRepository;
    }

    @Transactional(readOnly = true)
    public List<ScheduleEntry> execute(
            String userId,
            DayOfWeek dayOfWeek
    ) {

        if (dayOfWeek == null) {
            throw new IllegalArgumentException("Day of week cannot be null");
        }

        User user = userRepository.findById(UserId.fromString(userId))
                .orElseThrow(() -> new IllegalArgumentException("No such user found"));

        StaffId staffId = user.getStaffId();

        staffRepository.findById(staffId)
                .orElseThrow(() -> new IllegalArgumentException("No such staff found"));

        return scheduleEntryRepository.findByTeacherIdAndDayOfWeek(staffId, dayOfWeek)
                .stream()
                .sorted(Comparator.comparing(ScheduleEntry::getPeriodNumber))
                .toList();
    }
}
