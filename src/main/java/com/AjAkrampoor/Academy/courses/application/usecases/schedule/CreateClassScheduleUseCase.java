package com.AjAkrampoor.Academy.courses.application.usecases.schedule;

import com.AjAkrampoor.Academy.courses.domain.model.*;
import com.AjAkrampoor.Academy.courses.domain.repository.ClassScheduleRepository;
import com.AjAkrampoor.Academy.courses.domain.repository.CourseClassRepository;
import com.AjAkrampoor.Academy.roles.domain.model.Role;
import com.AjAkrampoor.Academy.roles.domain.repository.RoleRepository;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class CreateClassScheduleUseCase {

    private static final int MAX_RETRIES = 10;

    private final CourseClassRepository courseClassRepository;
    private final ClassScheduleRepository classScheduleRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StaffRepository staffRepository;

    public CreateClassScheduleUseCase(
            CourseClassRepository courseClassRepository,
            ClassScheduleRepository classScheduleRepository, UserRepository userRepository, RoleRepository roleRepository, StaffRepository staffRepository
    ) {
        this.courseClassRepository = courseClassRepository;
        this.classScheduleRepository = classScheduleRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.staffRepository = staffRepository;
    }

    @Transactional
    public ClassSchedule execute(String userId, UUID classId, LocalDate effectiveFrom, LocalDate effectiveTo) {

        CourseClass courseClass = courseClassRepository.findById(new ClassId(classId))
                .orElseThrow(() -> new IllegalArgumentException("No such class found"));

        User user = userRepository.findById(UserId.fromString(userId))
                .orElseThrow(() -> new IllegalArgumentException("No such user found"));

        Staff staff = staffRepository.findById(user.getStaffId())
                .orElseThrow(() -> new IllegalArgumentException("No such staff found"));

        Role userRole = roleRepository.findById(user.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("No such role found"));

        if (!userRole.isSuperAdmin()) {
            if (!staff.getBranchId().equals(courseClass.getBranchId())) {
                throw new IllegalArgumentException("Cannot access other branches' data");
            }
        }

        ClassId id = new ClassId(classId);

        if (courseClass.isCanceled() || courseClass.isFinished()) {
            throw new IllegalStateException("Cannot create a schedule for an inactive class");
        }

        if (effectiveFrom == null) {
            effectiveFrom = courseClass.getStartDate();
        }

         /*
          New schedules are created inactive.
          This lets the administrator populate all schedule entries
          before activating the timetable.
         */
        for (int i = 0; i < MAX_RETRIES; i++) {
            ClassScheduleId scheduleId = ClassScheduleId.newId();

            if (!classScheduleRepository.existsById(scheduleId)) {
                ClassSchedule schedule = new ClassSchedule(
                        scheduleId,
                        id,
                        effectiveFrom,
                        effectiveTo,
                        ScheduleStatus.INACTIVE
                );

                return classScheduleRepository.save(schedule);
            }
        }

        throw new IllegalStateException("Could not create schedule after several retries");
    }
}
