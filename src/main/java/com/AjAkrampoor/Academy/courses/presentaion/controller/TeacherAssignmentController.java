package com.AjAkrampoor.Academy.courses.presentaion.controller;

import com.AjAkrampoor.Academy.courses.application.TeacherAssignmentResponseAssembler;
import com.AjAkrampoor.Academy.courses.application.dto.TeacherAssignmentCreateRequest;
import com.AjAkrampoor.Academy.courses.application.dto.TeacherAssignmentResponse;
import com.AjAkrampoor.Academy.courses.application.dto.TeacherAssignmentUpdateRequest;
import com.AjAkrampoor.Academy.courses.application.usecases.teacherassignment.ActivateTeacherAssignmentUseCase;
import com.AjAkrampoor.Academy.courses.application.usecases.teacherassignment.AssignTeacherToClassUseCase;
import com.AjAkrampoor.Academy.courses.application.usecases.teacherassignment.DeactivateTeacherAssignmentUseCase;
import com.AjAkrampoor.Academy.courses.application.usecases.teacherassignment.GetClassTeacherAssignmentsUseCase;
import com.AjAkrampoor.Academy.courses.application.usecases.teacherassignment.GetTeacherAssignmentUseCase;
import com.AjAkrampoor.Academy.courses.application.usecases.teacherassignment.GetTeacherAssignmentsForDateUseCase;
import com.AjAkrampoor.Academy.courses.application.usecases.teacherassignment.GetTeacherAssignmentsUseCase;
import com.AjAkrampoor.Academy.courses.application.usecases.teacherassignment.UpdateTeacherAssignmentUseCase;
import com.AjAkrampoor.Academy.courses.domain.model.TeacherAssignment;
import com.AjAkrampoor.Academy.shared.security.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/teacher-assignments")
public class TeacherAssignmentController {

    private final AssignTeacherToClassUseCase assignTeacher;
    private final UpdateTeacherAssignmentUseCase updateAssignment;
    private final ActivateTeacherAssignmentUseCase activateAssignment;
    private final DeactivateTeacherAssignmentUseCase deactivateAssignment;
    private final GetTeacherAssignmentUseCase getAssignment;
    private final GetClassTeacherAssignmentsUseCase getClassAssignments;
    private final GetTeacherAssignmentsUseCase getTeacherAssignments;
    private final GetTeacherAssignmentsForDateUseCase getTeacherAssignmentsForDate;

    private final TeacherAssignmentResponseAssembler assembler;

    public TeacherAssignmentController(
            AssignTeacherToClassUseCase assignTeacher,
            UpdateTeacherAssignmentUseCase updateAssignment,
            ActivateTeacherAssignmentUseCase activateAssignment,
            DeactivateTeacherAssignmentUseCase deactivateAssignment,
            GetTeacherAssignmentUseCase getAssignment,
            GetClassTeacherAssignmentsUseCase getClassAssignments,
            GetTeacherAssignmentsUseCase getTeacherAssignments,
            GetTeacherAssignmentsForDateUseCase getTeacherAssignmentsForDate,
            TeacherAssignmentResponseAssembler assembler
    ) {
        this.assignTeacher = assignTeacher;
        this.updateAssignment = updateAssignment;
        this.activateAssignment = activateAssignment;
        this.deactivateAssignment = deactivateAssignment;
        this.getAssignment = getAssignment;
        this.getClassAssignments = getClassAssignments;
        this.getTeacherAssignments = getTeacherAssignments;
        this.getTeacherAssignmentsForDate = getTeacherAssignmentsForDate;
        this.assembler = assembler;
    }

    @PostMapping("/classes/{classId}")
    @PreAuthorize("hasAuthority('CLASS_TEACHER_ASSIGNMENT_CREATE')")
    public ResponseEntity<TeacherAssignmentResponse> create(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable UUID classId,
            @Valid @RequestBody TeacherAssignmentCreateRequest request
    ) {
        TeacherAssignment assignment =
                assignTeacher.execute(
                        userDetails.getUserId(),
                        classId,
                        request.getTeacherId(),
                        request.getDescription(),
                        request.getDaysOfWeek(),
                        request.getStartTime(),
                        request.getEndTime(),
                        request.getEffectiveFrom(),
                        request.getEffectiveTo()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        assembler.toResponse(assignment)
                );
    }

    @PutMapping("/{assignmentId}")
    @PreAuthorize("hasAuthority('CLASS_TEACHER_ASSIGNMENT_UPDATE')")
    public ResponseEntity<TeacherAssignmentResponse> update(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable UUID assignmentId,
            @Valid @RequestBody TeacherAssignmentUpdateRequest request
    ) {
        TeacherAssignment assignment =
                updateAssignment.execute(
                        userDetails.getUserId(),
                        assignmentId,
                        request.getTeacherId(),
                        request.getDescription(),
                        request.getDaysOfWeek(),
                        request.getStartTime(),
                        request.getEndTime(),
                        request.getEffectiveFrom(),
                        request.getEffectiveTo()
                );

        return ResponseEntity.ok(
                assembler.toResponse(assignment)
        );
    }

    @PatchMapping("/{assignmentId}/activate")
    @PreAuthorize("hasAuthority('CLASS_TEACHER_ASSIGNMENT_UPDATE')")
    public ResponseEntity<TeacherAssignmentResponse> activate(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable UUID assignmentId
    ) {
        return ResponseEntity.ok(
                assembler.toResponse(
                        activateAssignment.execute(
                                userDetails.getUserId(),
                                assignmentId
                        )
                )
        );
    }

    @PatchMapping("/{assignmentId}/deactivate")
    @PreAuthorize("hasAuthority('CLASS_TEACHER_ASSIGNMENT_UPDATE')")
    public ResponseEntity<TeacherAssignmentResponse> deactivate(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable UUID assignmentId
    ) {
        return ResponseEntity.ok(
                assembler.toResponse(
                        deactivateAssignment.execute(
                                userDetails.getUserId(),
                                assignmentId
                        )
                )
        );
    }

    @GetMapping("/{assignmentId}")
    @PreAuthorize("hasAuthority('CLASS_TEACHER_ASSIGNMENT_READ')")
    public ResponseEntity<TeacherAssignmentResponse> get(
            @PathVariable UUID assignmentId
    ) {
        return ResponseEntity.ok(
                assembler.toResponse(
                        getAssignment.execute(assignmentId)
                )
        );
    }

    @GetMapping("/classes/{classId}")
    @PreAuthorize("hasAuthority('CLASS_TEACHER_ASSIGNMENT_READ')")
    public ResponseEntity<List<TeacherAssignmentResponse>> getForClass(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable UUID classId
    ) {
        return ResponseEntity.ok(
                getClassAssignments
                        .execute(
                                userDetails.getUserId(),
                                classId
                        )
                        .stream()
                        .map(assembler::toResponse)
                        .toList()
        );
    }

    @GetMapping("/teachers/{teacherId}")
    @PreAuthorize("hasAuthority('CLASS_TEACHER_ASSIGNMENT_READ')")
    public ResponseEntity<List<TeacherAssignmentResponse>> getForTeacher(
            @PathVariable String teacherId
    ) {
        return ResponseEntity.ok(
                getTeacherAssignments
                        .execute(teacherId)
                        .stream()
                        .map(assembler::toResponse)
                        .toList()
        );
    }

    @GetMapping("/teachers/{teacherId}/date")
    @PreAuthorize("hasAuthority('CLASS_TEACHER_ASSIGNMENT_READ')")
    public ResponseEntity<List<TeacherAssignmentResponse>> getForTeacherOnDate(
            @PathVariable String teacherId,
            @RequestParam LocalDate date
    ) {
        return ResponseEntity.ok(
                getTeacherAssignmentsForDate
                        .execute(
                                teacherId,
                                date
                        )
                        .stream()
                        .map(assembler::toResponse)
                        .toList()
        );
    }
}