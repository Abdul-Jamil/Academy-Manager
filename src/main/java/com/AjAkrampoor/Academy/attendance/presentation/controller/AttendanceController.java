package com.AjAkrampoor.Academy.attendance.presentation.controller;

import com.AjAkrampoor.Academy.attendance.application.AttendanceResponseAssembler;
import com.AjAkrampoor.Academy.attendance.application.dto.AttendanceFilter;
import com.AjAkrampoor.Academy.attendance.application.dto.AttendanceResponse;
import com.AjAkrampoor.Academy.attendance.application.dto.MarkAttendanceRequest;
import com.AjAkrampoor.Academy.attendance.application.dto.UpdateAttendanceRequest;
import com.AjAkrampoor.Academy.attendance.application.usecases.GetAttendancesUseCase;
import com.AjAkrampoor.Academy.attendance.application.usecases.MarkAttendanceUseCase;
import com.AjAkrampoor.Academy.attendance.application.usecases.UpdateAttendanceUseCase;
import com.AjAkrampoor.Academy.attendance.domain.model.Attendance;
import com.AjAkrampoor.Academy.attendance.domain.model.AttendanceStatus;
import com.AjAkrampoor.Academy.shared.dto.PaginatedResponse;
import com.AjAkrampoor.Academy.shared.dto.PaginationRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceResponseAssembler assembler;
    private final MarkAttendanceUseCase markAttendance;
    private final UpdateAttendanceUseCase updateAttendance;
    private final GetAttendancesUseCase getAttendances;

    public AttendanceController(
            AttendanceResponseAssembler assembler,
            MarkAttendanceUseCase markAttendance,
            UpdateAttendanceUseCase updateAttendance,
            GetAttendancesUseCase getAttendances
    ) {
        this.assembler = assembler;
        this.markAttendance = markAttendance;
        this.updateAttendance = updateAttendance;
        this.getAttendances = getAttendances;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ATTENDANCE_READ')")
    public ResponseEntity<PaginatedResponse<AttendanceResponse>> getAttendances(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "attendanceTime") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection,
            @RequestParam(required = false) String studentId,
            @RequestParam(required = false) String classId,
            @RequestParam(required = false) LocalDate dateFrom,
            @RequestParam(required = false) LocalDate dateTo,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) AttendanceStatus status
    ) {

        AttendanceFilter filter = new AttendanceFilter();

        filter.setStudentId(studentId);
        filter.setClassId(classId);
        filter.setDateFrom(dateFrom);
        filter.setDateTo(dateTo);
        filter.setDescription(description);
        filter.setStatus(status);

        PaginationRequest paginationRequest = new PaginationRequest(page, size, sortBy, sortDirection);

        Page<Attendance> attendancePage = getAttendances.execute(filter, paginationRequest.toPageable());
        List<AttendanceResponse> responses = attendancePage.getContent().stream()
                .map(assembler::toResponse)
                .toList();

        Page<AttendanceResponse> responsePage = new PageImpl<>(responses, attendancePage.getPageable(), attendancePage.getTotalElements());

        return ResponseEntity.ok(new PaginatedResponse<>(responsePage));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ATTENDANCE_CREATE')")
    public ResponseEntity<AttendanceResponse> markAttendance(@Valid @RequestBody MarkAttendanceRequest request) {

        Attendance attendance = markAttendance.execute(
                request.getStudentId(),
                request.getClassId(),
                request.getAttendanceTime(),
                request.getStatus(),
                request.getDescription()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toResponse(attendance));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ATTENDANCE_UPDATE')")
    public ResponseEntity<AttendanceResponse> updateAttendance(@PathVariable UUID id, @Valid @RequestBody UpdateAttendanceRequest request) {

        Attendance attendance = updateAttendance.execute(
                id,
                request.getAttendanceTime(),
                request.getStatus(),
                request.getDescription()
        );

        return ResponseEntity.ok(assembler.toResponse(attendance));
    }
}
