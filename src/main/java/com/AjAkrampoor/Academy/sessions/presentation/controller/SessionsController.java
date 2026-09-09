package com.AjAkrampoor.Academy.sessions.presentation.controller;

import com.AjAkrampoor.Academy.sessions.application.DayOffResponseAssembler;
import com.AjAkrampoor.Academy.sessions.application.SessionResponseAssembler;
import com.AjAkrampoor.Academy.sessions.application.dto.CreateSessionRequest;
import com.AjAkrampoor.Academy.sessions.application.dto.DayOffResponse;
import com.AjAkrampoor.Academy.sessions.application.dto.SessionResponse;
import com.AjAkrampoor.Academy.sessions.application.usecases.dayoff.*;
import com.AjAkrampoor.Academy.sessions.application.usecases.session.CancelSessionUseCase;
import com.AjAkrampoor.Academy.sessions.application.usecases.session.CreateSessionUseCase;
import com.AjAkrampoor.Academy.sessions.application.usecases.session.GetClassSessionsUseCase;
import com.AjAkrampoor.Academy.sessions.domain.model.DayOff;
import com.AjAkrampoor.Academy.sessions.domain.model.Session;
import com.AjAkrampoor.Academy.shared.dto.PaginatedResponse;
import com.AjAkrampoor.Academy.shared.dto.PaginationRequest;
import com.AjAkrampoor.Academy.shared.security.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/sessions")
public class SessionsController {

    private final CreateSessionUseCase createSession;
    private final CancelSessionUseCase cancelSession;
    private final GetClassSessionsUseCase getClassScheduleSessions;

    private final GetDayOffsUseCase getDayOffs;
    private final CreateDayOffUseCase createDayOff;
    private final UpdateDayOffDescriptionUseCase updateDayOffDescription;
    private final ActivateDayOffUseCase activateDayOff;
    private final DeactivateDayOffUseCase deactivateDayOff;

    private final SessionResponseAssembler sessionResponseAssembler;
    private final DayOffResponseAssembler dayOffResponseAssembler;

    public SessionsController(
            CreateSessionUseCase createSessionUseCase,
            CancelSessionUseCase cancelSessionUseCase,
            GetClassSessionsUseCase getClassScheduleSessionsUseCase, GetDayOffsUseCase getDayOffs, CreateDayOffUseCase createDayOff, UpdateDayOffDescriptionUseCase updateDayOffDescription, ActivateDayOffUseCase activateDayOff, DeactivateDayOffUseCase deactivateDayOff,
            SessionResponseAssembler responseAssembler, DayOffResponseAssembler dayOffResponseAssembler
    ) {
        this.createSession = createSessionUseCase;
        this.cancelSession = cancelSessionUseCase;
        this.getClassScheduleSessions = getClassScheduleSessionsUseCase;
        this.getDayOffs = getDayOffs;
        this.createDayOff = createDayOff;
        this.updateDayOffDescription = updateDayOffDescription;
        this.activateDayOff = activateDayOff;
        this.deactivateDayOff = deactivateDayOff;
        this.sessionResponseAssembler = responseAssembler;
        this.dayOffResponseAssembler = dayOffResponseAssembler;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('SESSION_CREATE')")
    public SessionResponse create(@AuthenticationPrincipal CustomUserDetails userDetails, @Valid @RequestBody CreateSessionRequest request) {

        Session session = createSession.execute(
                userDetails.getUserId(),
                request.getScheduleEntryId(),
                request.getTeacherId(),
                request.getSessionDate(),
                request.getDescription()
        );

        return sessionResponseAssembler.toResponse(session);
    }

    @PatchMapping("/{sessionId}/cancel")
    @PreAuthorize("hasAuthority('SESSION_CANCEL')")
    public SessionResponse cancel(@PathVariable UUID sessionId) {

        Session session = cancelSession.execute(sessionId);
        return sessionResponseAssembler.toResponse(session);
    }

    @GetMapping("/class/{classId}")
    @PreAuthorize("hasAuthority('SESSION_READ')")
    public List<SessionResponse> getClassSessions(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable UUID classId, @RequestParam LocalDate from, @RequestParam LocalDate to) {

        return getClassScheduleSessions
                .execute(userDetails.getUserId(), classId, from, to)
                .stream()
                .map(sessionResponseAssembler::toResponse)
                .toList();
    }

    @GetMapping("/day-off")
    @PreAuthorize("hasAuthority('SESSION_DAYOFF_READ')")
    public ResponseEntity<PaginatedResponse<DayOffResponse>> getDayOffs(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "dayOffId") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {

        PaginationRequest paginationRequest = new PaginationRequest(page, size, sortBy, sortDirection);
        Pageable pageable = paginationRequest.toPageable();
        Page<DayOff> dayOffPage = getDayOffs.execute(userDetails.getUserId(), pageable);
        PaginatedResponse<DayOffResponse> paginatedResponse = new PaginatedResponse<>(dayOffPage.map(dayOffResponseAssembler::toResponse));
        return ResponseEntity.ok(paginatedResponse);
    }

    @PostMapping("/day-off")
    @PreAuthorize("hasAuthority('SESSION_DAYOFF_CREATE')")
    public ResponseEntity<DayOffResponse> createDayOff(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam LocalDate date, @RequestParam String description, @RequestParam(required = false) UUID branchId) {
        DayOff dayOff = createDayOff.execute(userDetails.getUserId(), date, description, branchId);
        return ResponseEntity.ok(dayOffResponseAssembler.toResponse(dayOff));
    }


    @PutMapping("/day-off/{id}")
    @PreAuthorize("hasAuthority('SESSION_DAYOFF_UPDATE')")
    public ResponseEntity<DayOffResponse> updateDayOff(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable UUID id, @RequestParam String description) {
        DayOff dayOff = updateDayOffDescription.execute(userDetails.getUserId(), id, description);
        return ResponseEntity.ok(dayOffResponseAssembler.toResponse(dayOff));
    }

    @PutMapping("/day-off/{id}/delete")
    @PreAuthorize("hasAuthority('SESSION_DAYOFF_DEACTIVATE')")
    public ResponseEntity<DayOffResponse> deactivateDayOff(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable UUID dayOffId) {
        DayOff dayOff = deactivateDayOff.execute(userDetails.getUserId(), dayOffId);
        return ResponseEntity.ok(dayOffResponseAssembler.toResponse(dayOff));
    }

    @PutMapping("/day-off/{id}/restore")
    @PreAuthorize("hasAuthority('SESSION_DAYOFF_REACTIVATE')")
    public ResponseEntity<DayOffResponse> activateDayOff(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable UUID dayOffId) {
        DayOff dayOff = activateDayOff.execute(userDetails.getUserId(), dayOffId);
        return ResponseEntity.ok(dayOffResponseAssembler.toResponse(dayOff));
    }

}
