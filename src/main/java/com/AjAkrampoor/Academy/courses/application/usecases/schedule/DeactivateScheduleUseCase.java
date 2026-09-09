package com.AjAkrampoor.Academy.courses.application.usecases.schedule;

import com.AjAkrampoor.Academy.courses.domain.model.ClassSchedule;
import com.AjAkrampoor.Academy.courses.domain.model.ClassScheduleId;
import com.AjAkrampoor.Academy.courses.domain.model.CourseClass;
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

import java.util.UUID;

@Service
public class DeactivateScheduleUseCase {

    private final ClassScheduleRepository classScheduleRepository;
    private final CourseClassRepository courseClassRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StaffRepository staffRepository;

    public DeactivateScheduleUseCase(
            ClassScheduleRepository classScheduleRepository, CourseClassRepository courseClassRepository, UserRepository userRepository, RoleRepository roleRepository, StaffRepository staffRepository
    ) {
        this.classScheduleRepository = classScheduleRepository;
        this.courseClassRepository = courseClassRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.staffRepository = staffRepository;
    }

    @Transactional
    public ClassSchedule execute(String userId, UUID scheduleId) {

        ClassSchedule schedule = classScheduleRepository.findById(new ClassScheduleId(scheduleId))
                .orElseThrow(() -> new IllegalArgumentException("No such schedule found"));

        CourseClass courseClass = courseClassRepository.findById(schedule.getClassId())
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

        if (!schedule.isActive()) {
            throw new IllegalStateException("Schedule is already inactive");
        }

        schedule.deactivate();

        return classScheduleRepository.save(schedule);
    }
}
