package com.AjAkrampoor.Academy.attendance.application.usecases;

import com.AjAkrampoor.Academy.attendance.domain.model.Attendance;
import com.AjAkrampoor.Academy.attendance.domain.model.AttendanceId;
import com.AjAkrampoor.Academy.attendance.domain.model.AttendanceStatus;
import com.AjAkrampoor.Academy.attendance.domain.repository.AttendanceRepository;
import com.AjAkrampoor.Academy.shared.vo.Description;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UpdateAttendanceUseCase {

    private final AttendanceRepository attendanceRepository;

    public UpdateAttendanceUseCase(
            AttendanceRepository attendanceRepository
    ) {
        this.attendanceRepository = attendanceRepository;
    }

    @Transactional
    public Attendance execute(UUID attendanceId, LocalDateTime attendanceTime, AttendanceStatus status, String description) {
        Attendance attendance =
                attendanceRepository.findById(new AttendanceId(attendanceId))
                        .orElseThrow(() -> new IllegalArgumentException("No such attendance found"));

        if (attendanceTime == null) {
            throw new IllegalArgumentException("Attendance time cannot be null");
        }

        if (status == null) {
            throw new IllegalArgumentException("Attendance status cannot be null");
        }

        if (!attendance
                .getAttendanceTime()
                .toLocalDate()
                .equals(attendanceTime.toLocalDate())) {

            boolean duplicate = attendanceRepository.existsByStudentIdAndClassIdAndDate(
                    attendance.getStudentId(),
                    attendance.getClassId(),
                    attendanceTime.toLocalDate());

            if (duplicate) {
                throw new IllegalArgumentException("Attendance already exists for this student, class and date"
                );
            }
        }

        Description attendanceDescription =
                description == null || description.isBlank()
                        ? null
                        : new Description(description);

        attendance.update(
                attendanceTime,
                status,
                attendanceDescription
        );

        return attendanceRepository.save(attendance);
    }
}
