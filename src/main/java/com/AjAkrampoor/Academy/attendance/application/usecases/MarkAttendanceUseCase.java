package com.AjAkrampoor.Academy.attendance.application.usecases;

import com.AjAkrampoor.Academy.attendance.domain.model.Attendance;
import com.AjAkrampoor.Academy.attendance.domain.model.AttendanceId;
import com.AjAkrampoor.Academy.attendance.domain.model.AttendanceStatus;
import com.AjAkrampoor.Academy.attendance.domain.repository.AttendanceRepository;
import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.courses.domain.model.ClassId;
import com.AjAkrampoor.Academy.courses.domain.model.CourseClass;
import com.AjAkrampoor.Academy.courses.domain.repository.CourseClassRepository;
import com.AjAkrampoor.Academy.sessions.domain.model.DayOff;
import com.AjAkrampoor.Academy.sessions.domain.repository.DayOffRepository;
import com.AjAkrampoor.Academy.shared.vo.Description;
import com.AjAkrampoor.Academy.students.domain.model.StudentId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class MarkAttendanceUseCase {

    private final AttendanceRepository attendanceRepository;
    private final CourseClassRepository courseClassRepository;
    private final DayOffRepository dayOffRepository;

    public MarkAttendanceUseCase(
            AttendanceRepository attendanceRepository, CourseClassRepository courseClassRepository, DayOffRepository dayOffRepository
    ) {
        this.attendanceRepository = attendanceRepository;
        this.courseClassRepository = courseClassRepository;
        this.dayOffRepository = dayOffRepository;
    }

    @Transactional
    public Attendance execute(String studentId, UUID classId, LocalDateTime attendanceTime, AttendanceStatus status, String description) {

        StudentId student = StudentId.from(studentId);

        ClassId clazz = new ClassId(classId);

        CourseClass courseClass = courseClassRepository.findById(clazz)
                .orElseThrow(() -> new IllegalArgumentException("Class not found."));

        if (attendanceTime == null) {
            throw new IllegalArgumentException("Attendance time cannot be null");
        }

        if (status == null) {
            throw new IllegalArgumentException("Attendance status cannot be null");
        }

        if (attendanceRepository.existsByStudentIdAndClassIdAndDate(student, clazz, attendanceTime.toLocalDate())) {
            throw new IllegalArgumentException("Attendance already exists for this student, class and date");
        }

        if (attendanceTime.getDayOfWeek() == DayOfWeek.FRIDAY) {
            throw new IllegalArgumentException("Attendance cannot be marked on Fridays.");
        }

        BranchId classBranchId = courseClass.getBranchId();
        List<DayOff> activeDayOffs = dayOffRepository.findActiveByDateBetween(LocalDate.from(attendanceTime), LocalDate.from(attendanceTime));
        boolean isDayOff = activeDayOffs.stream().anyMatch(dayOff ->
                dayOff.getBranchId() == null || dayOff.getBranchId().equals(classBranchId)
        );
        if (isDayOff) {
            throw new IllegalArgumentException("Cannot record attendance on a day that is marked as off.");
        }

        Description attendanceDescription = description == null || description.isBlank() ? null : new Description(description);

        Attendance attendance = new Attendance(
                AttendanceId.newId(),
                student,
                clazz,
                attendanceTime,
                status,
                attendanceDescription
        );

        return attendanceRepository.save(attendance);
    }
}
