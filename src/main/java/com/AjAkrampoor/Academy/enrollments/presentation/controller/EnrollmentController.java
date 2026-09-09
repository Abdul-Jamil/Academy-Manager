package com.AjAkrampoor.Academy.enrollments.presentation.controller;

import com.AjAkrampoor.Academy.enrollments.application.EnrollmentResponseAssembler;
import com.AjAkrampoor.Academy.enrollments.application.dto.EnrollmentCreateRequest;
import com.AjAkrampoor.Academy.enrollments.application.dto.EnrollmentResponse;
import com.AjAkrampoor.Academy.enrollments.application.dto.EnrollmentSearchRequest;
import com.AjAkrampoor.Academy.enrollments.application.usecases.*;
import com.AjAkrampoor.Academy.enrollments.domain.model.Enrollment;
import com.AjAkrampoor.Academy.shared.dto.PaginatedResponse;
import com.AjAkrampoor.Academy.shared.dto.PaginationRequest;
import com.AjAkrampoor.Academy.shared.security.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("/api/enrollments")
public class EnrollmentController {
    private final CancelEnrollmentUseCase cancelEnrollment;
    private final CreateEnrollmentUseCase createEnrollment;
    private final GetEnrollmentsUseCase getEnrollments;
    private final TransferEnrollmentUseCase transferEnrollment;
    private final UpdateEnrollmentDescriptionUseCase updateEnrollmentDescription;
    private final EnrollmentResponseAssembler assembler;

    public EnrollmentController(CancelEnrollmentUseCase cancelEnrollment,
                                CreateEnrollmentUseCase createEnrollment,
                                GetEnrollmentsUseCase getEnrollments,
                                TransferEnrollmentUseCase transferEnrollment,
                                UpdateEnrollmentDescriptionUseCase updateEnrollmentDescription,
                                EnrollmentResponseAssembler assembler) {
        this.cancelEnrollment = cancelEnrollment;
        this.createEnrollment = createEnrollment;
        this.getEnrollments = getEnrollments;
        this.transferEnrollment = transferEnrollment;
        this.updateEnrollmentDescription = updateEnrollmentDescription;
        this.assembler = assembler;
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('ENROLLMENT_READ')")
    public ResponseEntity<PaginatedResponse<EnrollmentResponse>> get(
            @ModelAttribute EnrollmentSearchRequest searchRequest,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {
        PaginationRequest paginationRequest = new PaginationRequest(page, size, sortBy, sortDirection);
        PaginatedResponse<Enrollment> enrollmentPage = getEnrollments.execute(searchRequest, userDetails.getUserId(), paginationRequest);
        return ResponseEntity.ok(enrollmentPage.map(assembler::toResponse));
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ENROLLMENT_CREATE')")
    public ResponseEntity<EnrollmentResponse> create(@Valid @RequestBody EnrollmentCreateRequest request,
                                                     @AuthenticationPrincipal CustomUserDetails userDetails) {
        Enrollment enrollment = createEnrollment.execute(userDetails.getUserId(),
                request.getStudentId(),
                request.getClassId(),
                request.getDescription(),
                request.getDiscount());
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toResponse(enrollment));
    }

    @PatchMapping("/{id}/description")
    @PreAuthorize("hasAuthority('ENROLLMENT_UPDATE_DESCRIPTION')")
    public ResponseEntity<EnrollmentResponse> updateDescription(@PathVariable UUID id,
                                                                @AuthenticationPrincipal CustomUserDetails userDetails,
                                                                @RequestParam(required = false) String description) {
        Enrollment enrollment = updateEnrollmentDescription.execute(id, userDetails.getUserId(), description);
        return ResponseEntity.ok(assembler.toResponse(enrollment));
    }

    @PatchMapping("/{id}/transfer")
    @PreAuthorize("hasAuthority('ENROLLMENT_TRANSFER')")
    public ResponseEntity<List<EnrollmentResponse>> transfer(
            @PathVariable UUID id,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam UUID newClass) {
        List<Enrollment> enrollmentList = transferEnrollment.execute(id, userDetails.getUserId(), newClass);
        List<EnrollmentResponse> responses = enrollmentList.stream()
                .map(assembler::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/{id}/cancel")
    @PreAuthorize("hasAuthority('ENROLLMENT_CANCEL')")
    public ResponseEntity<EnrollmentResponse> cancel(@PathVariable UUID id,
                                                     @AuthenticationPrincipal CustomUserDetails userDetails) {
        Enrollment enrollment = cancelEnrollment.execute(userDetails.getUserId(), id);
        return ResponseEntity.ok(assembler.toResponse(enrollment));
    }
}
