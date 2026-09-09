package com.AjAkrampoor.Academy.courses.presentaion.controller;

import com.AjAkrampoor.Academy.courses.application.ClassScheduleResponseAssembler;
import com.AjAkrampoor.Academy.courses.application.ScheduleEntryResponseAssembler;
import com.AjAkrampoor.Academy.courses.application.dto.*;
import com.AjAkrampoor.Academy.courses.application.usecases.schedule.*;
import com.AjAkrampoor.Academy.courses.domain.model.ClassSchedule;
import com.AjAkrampoor.Academy.courses.domain.model.ScheduleEntry;
import com.AjAkrampoor.Academy.shared.security.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/schedule")
public class ClassScheduleController {

    private final CreateClassScheduleUseCase createSchedule;
    private final ActivateScheduleUseCase activateSchedule;
    private final DeactivateScheduleUseCase deactivateSchedule;
    private final GetMyScheduleUseCase getMySchedule;

    private final AddScheduleEntryUseCase addEntry;
    private final UpdateScheduleEntryUseCase updateEntry;
    private final RemoveScheduleEntryUseCase removeEntry;

    private final GetClassScheduleUseCase getSchedule;
    private final GetClassScheduleEntriesUseCase getEntries;
    private final GetScheduleByWeekdayUseCase getByWeekday;

    private final ClassScheduleResponseAssembler scheduleAssembler;
    private final ScheduleEntryResponseAssembler entryAssembler;

    public ClassScheduleController(
            CreateClassScheduleUseCase createSchedule,
            ActivateScheduleUseCase activateSchedule,
            DeactivateScheduleUseCase deactivateSchedule, GetMyScheduleUseCase getMySchedule,
            AddScheduleEntryUseCase addEntry,
            UpdateScheduleEntryUseCase updateEntry,
            RemoveScheduleEntryUseCase removeEntry,
            GetClassScheduleUseCase getSchedule,
            GetClassScheduleEntriesUseCase getEntries,
            GetScheduleByWeekdayUseCase getByWeekday,
            ClassScheduleResponseAssembler scheduleAssembler,
            ScheduleEntryResponseAssembler entryAssembler
    ) {
        this.createSchedule = createSchedule;
        this.activateSchedule = activateSchedule;
        this.deactivateSchedule = deactivateSchedule;
        this.getMySchedule = getMySchedule;
        this.addEntry = addEntry;
        this.updateEntry = updateEntry;
        this.removeEntry = removeEntry;
        this.getSchedule = getSchedule;
        this.getEntries = getEntries;
        this.getByWeekday = getByWeekday;
        this.scheduleAssembler = scheduleAssembler;
        this.entryAssembler = entryAssembler;
    }

    @PostMapping("/class/{classId}")
    @PreAuthorize("hasAuthority('CLASS_SCHEDULE_CREATE')")
    public ResponseEntity<ClassScheduleResponse> createSchedule(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable UUID classId, @Valid @RequestBody ClassScheduleCreateRequest request) {
        ClassSchedule schedule = createSchedule.execute(userDetails.getUserId(), classId, request.getEffectiveFrom(), request.getEffectiveTo());

        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleAssembler.toResponse(schedule, List.of()));
    }

    @GetMapping("/{scheduleId}")
    @PreAuthorize("hasAuthority('CLASS_SCHEDULE_READ')")
    public ResponseEntity<ClassScheduleResponse> getSchedule(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable UUID scheduleId) {
        ClassSchedule schedule = getSchedule.execute(userDetails.getUserId(), scheduleId);

        List<ScheduleEntry> entries = getEntries.execute(userDetails.getUserId(), scheduleId);

        return ResponseEntity.ok(scheduleAssembler.toResponse(schedule, entries));
    }


    @PatchMapping("/{scheduleId}/activate")
    @PreAuthorize("hasAuthority('CLASS_SCHEDULE_ACTIVATE')")
    public ResponseEntity<ClassScheduleResponse> activate(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable UUID scheduleId) {
        ClassSchedule schedule = activateSchedule.execute(userDetails.getUserId(), scheduleId);

        return ResponseEntity.ok(scheduleAssembler.toResponse(schedule, getEntries.execute(userDetails.getUserId(), scheduleId))
        );
    }

    @PatchMapping("/{scheduleId}/deactivate")
    @PreAuthorize("hasAuthority('CLASS_SCHEDULE_DEACTIVATE')")
    public ResponseEntity<ClassScheduleResponse> deactivate(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable UUID scheduleId) {
        ClassSchedule schedule = deactivateSchedule.execute(userDetails.getUserId(), scheduleId);

        return ResponseEntity.ok(scheduleAssembler.toResponse(schedule, getEntries.execute(userDetails.getUserId(), scheduleId)));
    }

    @PostMapping("/{scheduleId}/entries")
    @PreAuthorize("hasAuthority('CLASS_SCHEDULE_ENTRY_CREATE')")
    public ResponseEntity<ScheduleEntryResponse> addEntry(@PathVariable UUID scheduleId, @Valid @RequestBody ScheduleEntryCreateRequest request) {
        ScheduleEntry entry =
                addEntry.execute(
                        scheduleId,
                        request.getDayOfWeek(),
                        request.getPeriodNumber(),
                        request.getStartTime(),
                        request.getEndTime(),
                        request.getCourseId(),
                        request.getTeacherId()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(entryAssembler.toResponse(entry));
    }

    @PatchMapping("/entries/{entryId}")
    @PreAuthorize("hasAuthority('CLASS_SCHEDULE_ENTRY_UPDATE')")
    public ResponseEntity<ScheduleEntryResponse> updateEntry(@PathVariable UUID entryId, @Valid @RequestBody ScheduleEntryUpdateRequest request) {
        ScheduleEntry entry =
                updateEntry.execute(
                        entryId,
                        request.getDayOfWeek(),
                        request.getPeriodNumber(),
                        request.getStartTime(),
                        request.getEndTime(),
                        request.getCourseId(),
                        request.getTeacherId()
                );

        return ResponseEntity.ok(entryAssembler.toResponse(entry));
    }

    @DeleteMapping("/entries/{entryId}")
    @PreAuthorize("hasAuthority('CLASS_SCHEDULE_ENTRY_DELETE')")
    public ResponseEntity<Void> removeEntry(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable UUID entryId) {
        removeEntry.execute(userDetails.getUserId(), entryId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{scheduleId}/entries")
    @PreAuthorize("hasAuthority('CLASS_SCHEDULE_READ')")
    public ResponseEntity<List<ScheduleEntryResponse>> getEntries(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable UUID scheduleId) {
        return ResponseEntity.ok(
                getEntries.execute(userDetails.getUserId(), scheduleId)
                        .stream()
                        .map(entryAssembler::toResponse)
                        .toList()
        );
    }

    @GetMapping("/my-schedule")
    @PreAuthorize("hasAuthority('CLASS_SCHEDULE_READ')")
    public ResponseEntity<List<ScheduleEntryResponse>> getMySchedule(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        List<ScheduleEntry> entries = getMySchedule.execute(userDetails.getUserId());
        List<ScheduleEntryResponse> responses = entries.stream()
                .map(entryAssembler::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{scheduleId}/entries/{weekday}")
    @PreAuthorize("hasAuthority('CLASS_SCHEDULE_READ')")
    public ResponseEntity<List<ScheduleEntryResponse>> getByWeekday(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable UUID scheduleId, @PathVariable DayOfWeek weekday) {
        return ResponseEntity.ok(
                getByWeekday.execute(userDetails.getUserId(), scheduleId, weekday)
                        .stream()
                        .map(entryAssembler::toResponse)
                        .toList()
        );
    }
}
