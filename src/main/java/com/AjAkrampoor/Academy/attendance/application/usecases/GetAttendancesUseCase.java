package com.AjAkrampoor.Academy.attendance.application.usecases;

import com.AjAkrampoor.Academy.attendance.application.dto.AttendanceFilter;
import com.AjAkrampoor.Academy.attendance.domain.model.Attendance;
import com.AjAkrampoor.Academy.attendance.domain.repository.AttendanceRepository;
import com.AjAkrampoor.Academy.attendance.infrastructure.persistence.AttendanceJpaEntity;
import com.AjAkrampoor.Academy.attendance.infrastructure.repository.AttendanceSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GetAttendancesUseCase {

    private final AttendanceRepository attendanceRepository;

    public GetAttendancesUseCase(
            AttendanceRepository attendanceRepository
    ) {
        this.attendanceRepository = attendanceRepository;
    }

    @Transactional(readOnly = true)
    public Page<Attendance> execute(AttendanceFilter filter, Pageable pageable) {

        List<Specification<AttendanceJpaEntity>> specifications = AttendanceSpecifications.getSpecifications(filter);

        Specification<AttendanceJpaEntity> combined = Specification.allOf(specifications);

        return attendanceRepository.findAll(combined, pageable);
    }
}
