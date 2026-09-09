package com.AjAkrampoor.Academy.sessions.application;

import com.AjAkrampoor.Academy.courses.domain.model.CourseClass;
import com.AjAkrampoor.Academy.courses.domain.repository.CourseClassRepository;
import com.AjAkrampoor.Academy.sessions.application.dto.SessionResponse;
import com.AjAkrampoor.Academy.sessions.domain.model.Session;
import com.AjAkrampoor.Academy.staff.domain.model.Staff;
import com.AjAkrampoor.Academy.staff.domain.repository.StaffRepository;
import com.AjAkrampoor.Academy.users.domain.model.User;
import com.AjAkrampoor.Academy.users.domain.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class SessionResponseAssembler {
    private final UserRepository userRepository;
    private final StaffRepository staffRepository;
    private final CourseClassRepository courseClassRepository;

    public SessionResponseAssembler(UserRepository userRepository, StaffRepository staffRepository, CourseClassRepository courseClassRepository) {
        this.userRepository = userRepository;
        this.staffRepository = staffRepository;
        this.courseClassRepository = courseClassRepository;
    }

    public SessionResponse toResponse(Session session) {

        String teacherName = null;
        if (session.getTeacherId() != null) {
            User user = userRepository.findById(session.getTeacherId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
            Staff staff = staffRepository.findById(user.getStaffId()).orElseThrow(() -> new IllegalArgumentException("Staff not found"));
            teacherName = staff.getName().getFullName();
        }

        CourseClass courseClass = courseClassRepository.findById(session.getClassId()).orElseThrow(() -> new IllegalArgumentException("Class not found"));

        String description = session.getDescription() == null ? null : session.getDescription().getValue();

        return new SessionResponse(
                session.getSessionId().toString(),
                session.getScheduleEntryId().toString(),
                courseClass.getDescription().toString(),
                teacherName,
                session.getSessionDate().getDate(),
                session.getTimeSlot().getStartTime(),
                session.getTimeSlot().getEndTime(),
                session.getStatus(),
                description
        );
    }
}
