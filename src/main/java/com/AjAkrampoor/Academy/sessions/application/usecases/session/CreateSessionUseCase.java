package com.AjAkrampoor.Academy.sessions.application.usecases.session;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.courses.domain.model.*;
import com.AjAkrampoor.Academy.courses.domain.repository.ClassScheduleRepository;
import com.AjAkrampoor.Academy.courses.domain.repository.CourseClassRepository;
import com.AjAkrampoor.Academy.courses.domain.repository.ScheduleEntryRepository;
import com.AjAkrampoor.Academy.roles.domain.model.Role;
import com.AjAkrampoor.Academy.roles.domain.repository.RoleRepository;
import com.AjAkrampoor.Academy.sessions.domain.model.*;
import com.AjAkrampoor.Academy.sessions.domain.repository.DayOffRepository;   // <-- new import
import com.AjAkrampoor.Academy.sessions.domain.repository.SessionRepository;
import com.AjAkrampoor.Academy.shared.vo.Description;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.model.UserId;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class CreateSessionUseCase {

    private static final int MAX_RETRIES = 10;

    private final SessionRepository sessionRepository;
    private final ScheduleEntryRepository scheduleEntryRepository;
    private final ClassScheduleRepository classScheduleRepository;
    private final CourseClassRepository courseClassRepository;
    private final DayOffRepository dayOffRepository;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StaffRepository staffRepository;


    public CreateSessionUseCase(
            SessionRepository sessionRepository,
            ScheduleEntryRepository scheduleEntryRepository,
            ClassScheduleRepository classScheduleRepository,
            CourseClassRepository courseClassRepository,
            DayOffRepository dayOffRepository, UserRepository userRepository, RoleRepository roleRepository, StaffRepository staffRepository
    ) {
        this.sessionRepository = sessionRepository;
        this.scheduleEntryRepository = scheduleEntryRepository;
        this.classScheduleRepository = classScheduleRepository;
        this.courseClassRepository = courseClassRepository;
        this.dayOffRepository = dayOffRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.staffRepository = staffRepository;
    }

    @Transactional
    public Session execute(String userId, UUID scheduleEntryId, String teacherId, LocalDate sessionDate,
                           String description) {


        ScheduleEntryId entryId = new ScheduleEntryId(scheduleEntryId);

        ScheduleEntry entry = scheduleEntryRepository.findById(entryId)
                .orElseThrow(() -> new IllegalArgumentException("No such schedule entry found"));

        ClassSchedule schedule = classScheduleRepository.findById(entry.getScheduleId())
                .orElseThrow(() -> new IllegalArgumentException("No such schedule found"));

        ClassId classId = schedule.getClassId();

        CourseClass courseClass = courseClassRepository.findById(classId)
                .orElseThrow(() -> new IllegalArgumentException("No such class found"));

        User user = userRepository.findById(UserId.fromString(userId))
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Role userRole = roleRepository.findById(user.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        Staff staff = staffRepository.findById(user.getStaffId())
                .orElseThrow(() -> new IllegalArgumentException("Staff not found"));

        if (!userRole.isSuperAdmin()) {
            if (courseClass.getBranchId().equals(staff.getBranchId())) {
                throw new IllegalArgumentException("Cannot access other branches' data");
            }
        }

        if (!courseClass.isActive()) {
            throw new IllegalStateException("Cannot create a session for an inactive class");
        }

        if (sessionDate.getDayOfWeek() == DayOfWeek.FRIDAY) {
            throw new IllegalArgumentException("Sessions cannot be scheduled on Fridays.");
        }

        BranchId classBranchId = courseClass.getBranchId();
        List<DayOff> activeDayOffs = dayOffRepository.findActiveByDateBetween(sessionDate, sessionDate);
        boolean isDayOff = activeDayOffs.stream().anyMatch(dayOff ->
                dayOff.getBranchId() == null || dayOff.getBranchId().equals(classBranchId)
        );
        if (isDayOff) {
            throw new IllegalArgumentException("Cannot create a session on a day that is marked as off.");
        }

        SessionDate date = new SessionDate(sessionDate);

        boolean alreadyHappened = sessionRepository.existsByScheduleEntryIdAndSessionDateAndStatus(
                entryId, date, SessionStatus.HAPPENED
        );

        if (alreadyHappened) {
            throw new IllegalStateException("A happened session already exists for this schedule entry on this date");
        }

        TimeSlot timeSlot = new TimeSlot(entry.getStartTime(), entry.getEndTime());
        Description sessionDescription = (description == null || description.isBlank())
                ? null : new Description(description);

        UserId teacher = UserId.fromString(teacherId);

        for (int i = 0; i < MAX_RETRIES; i++) {
            SessionId sessionId = SessionId.newId();
            if (!sessionRepository.existsById(sessionId)) {
                Session session = new Session(sessionId, entryId, classId, teacher,
                        date, timeSlot, SessionStatus.HAPPENED, sessionDescription);
                return sessionRepository.save(session);
            }
        }

        throw new IllegalStateException("Could not create session after several retries");
    }
}