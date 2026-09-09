package com.AjAkrampoor.Academy.courses.presentaion.controller;

import com.AjAkrampoor.Academy.courses.application.ClassResponseAssembler;
import com.AjAkrampoor.Academy.courses.application.ClassScheduleResponseAssembler;
import com.AjAkrampoor.Academy.courses.application.ScheduleEntryResponseAssembler;
import com.AjAkrampoor.Academy.courses.application.dto.*;
import com.AjAkrampoor.Academy.courses.application.usecases.courseclass.*;
import com.AjAkrampoor.Academy.courses.application.usecases.schedule.*;
import com.AjAkrampoor.Academy.courses.domain.model.ClassSchedule;
import com.AjAkrampoor.Academy.courses.domain.model.CourseClass;
import com.AjAkrampoor.Academy.courses.domain.model.ScheduleEntry;
import com.AjAkrampoor.Academy.shared.dto.PaginatedResponse;
import com.AjAkrampoor.Academy.shared.dto.PaginationRequest;
import com.AjAkrampoor.Academy.shared.security.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/class")
public class ClassController {

    private final CreateClassUseCase createClass;
    private final GetClassUseCase getClass;
    private final GetClassesUseCase getClasses;
    private final UpdateClassDescriptionUseCase updateDescription;
    private final UpdateClassDurationUseCase updateDuration;
    private final UpdateClassDateRangeUseCase updateDateRange;
    private final ChangeClassStatusUseCase changeStatus;
    private final ExtendClassDurationUseCase extendDuration;

    private final GetClassScheduleUseCase getSchedule;
    private final GetClassSchedulesByClassUseCase getClassSchedulesByClass;
    private final GetCurrentClassScheduleUseCase getCurrentSchedule;
    private final GetClassScheduleEntriesUseCase getScheduleEntries;
    private final GetScheduleByWeekdayUseCase getScheduleByWeekday;

    private final ClassResponseAssembler classAssembler;
    private final ClassScheduleResponseAssembler scheduleAssembler;

    public ClassController(
            CreateClassUseCase createClass,
            GetClassUseCase getClass,
            GetClassesUseCase getClasses,
            UpdateClassDescriptionUseCase updateDescription,
            UpdateClassDurationUseCase updateDuration,
            UpdateClassDateRangeUseCase updateDateRange,
            ChangeClassStatusUseCase changeStatus,
            ExtendClassDurationUseCase extendDuration, GetClassScheduleUseCase getSchedule, GetClassSchedulesByClassUseCase getClassSchedulesByClass,
            GetCurrentClassScheduleUseCase getCurrentSchedule,
            GetClassScheduleEntriesUseCase getScheduleEntries,
            GetScheduleByWeekdayUseCase getScheduleByWeekday,
            ClassResponseAssembler classAssembler,
            ClassScheduleResponseAssembler scheduleAssembler
    ) {
        this.createClass = createClass;
        this.getClass = getClass;
        this.getClasses = getClasses;
        this.updateDescription = updateDescription;
        this.updateDuration = updateDuration;
        this.updateDateRange = updateDateRange;
        this.changeStatus = changeStatus;
        this.extendDuration = extendDuration;
        this.getSchedule = getSchedule;
        this.getClassSchedulesByClass = getClassSchedulesByClass;
        this.getCurrentSchedule = getCurrentSchedule;
        this.getScheduleEntries = getScheduleEntries;
        this.getScheduleByWeekday = getScheduleByWeekday;
        this.classAssembler = classAssembler;
        this.scheduleAssembler = scheduleAssembler;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('CLASS_READ')")
    public ResponseEntity<PaginatedResponse<ClassResponse>> getAll(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ModelAttribute ClassFilter filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection
    ) {
        if ("id".equals(sortBy)) {
            sortBy = "id.id";
        }

        PaginationRequest paginationRequest =
                new PaginationRequest(
                        page,
                        size,
                        sortBy,
                        sortDirection
                );

        Page<CourseClass> classPage = getClasses.execute(userDetails.getUserId(), filter, paginationRequest.toPageable());

        List<ClassResponse> responses = classPage.getContent()
                .stream()
                .map(classAssembler::toResponse)
                .toList();

        Page<ClassResponse> responsePage = new PageImpl<>(responses, classPage.getPageable(), classPage.getTotalElements());

        return ResponseEntity.ok(new PaginatedResponse<>(responsePage));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('CLASS_READ')")
    public ResponseEntity<ClassResponse> getOne(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable UUID id) {
        return ResponseEntity.ok(classAssembler.toResponse(getClass.execute(userDetails.getUserId(), id))
        );
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CLASS_CREATE')")
    public ResponseEntity<ClassResponse> create(
            @Valid @RequestBody ClassCreateRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        CourseClass courseClass =
                createClass.execute(
                        request.getDuration(),
                        request.getStartDate(),
                        request.getDescription(),
                        request.getFee(),
                        request.getClassType(),
                        request.getBranchId(),
                        request.isPending(),
                        userDetails.getUserId()
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(classAssembler.toResponse(courseClass));
    }

    @PatchMapping("/{id}/description")
    @PreAuthorize("hasAuthority('CLASS_UPDATE_DESCRIPTION')")
    public ResponseEntity<ClassResponse> updateDescription(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateClassDescriptionRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        CourseClass courseClass =
                updateDescription.execute(
                        userDetails.getUserId(),
                        id,
                        request.getDescription()
                );

        return ResponseEntity.ok(
                classAssembler.toResponse(courseClass)
        );
    }

    @PatchMapping("/{id}/duration")
    @PreAuthorize("hasAuthority('CLASS_UPDATE_DURATION')")
    public ResponseEntity<ClassResponse> updateDuration(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateClassDurationRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        CourseClass courseClass =
                updateDuration.execute(
                        userDetails.getUserId(),
                        id,
                        request.getDuration()
                );

        return ResponseEntity.ok(
                classAssembler.toResponse(courseClass)
        );
    }

    @PatchMapping("/{id}/date-range")
    @PreAuthorize("hasAuthority('CLASS_UPDATE_DATE_RANGE')")
    public ResponseEntity<ClassResponse> updateDateRange(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateClassDateRangeRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        CourseClass courseClass =
                updateDateRange.execute(
                        userDetails.getUserId(),
                        id,
                        request.getStartDate()
                );

        return ResponseEntity.ok(
                classAssembler.toResponse(courseClass)
        );
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAuthority('CLASS_CHANGE_STATUS')")
    public ResponseEntity<ClassResponse> changeStatus(
            @PathVariable UUID id,
            @Valid @RequestBody ChangeClassStatusRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        CourseClass courseClass =
                changeStatus.execute(
                        userDetails.getUserId(),
                        id,
                        request.getStatus(),
                        request.getStartDate()
                );

        return ResponseEntity.ok(
                classAssembler.toResponse(courseClass)
        );
    }

    @PatchMapping("/{id}/extend")
    @PreAuthorize("hasAuthority('CLASS_EXTEND')")
    public ResponseEntity<ClassResponse> extendDuration(@PathVariable UUID id, @RequestParam int addedDays, @AuthenticationPrincipal CustomUserDetails userDetails) {
        CourseClass courseClass = extendDuration.execute(userDetails.getUserId(), id, addedDays);

        return ResponseEntity.ok(classAssembler.toResponse(courseClass)
        );
    }

    @GetMapping("/{id}/schedule")
    @PreAuthorize("hasAuthority('CLASS_SCHEDULE_READ')")
    public ResponseEntity<ClassScheduleResponse> getCurrentSchedule(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable UUID id) {
        ClassSchedule schedule = getCurrentSchedule.execute(userDetails.getUserId(), id);

        List<ScheduleEntry> entries = getScheduleEntries.execute(userDetails.getUserId(), UUID.fromString(schedule.getId().toString()));

        return ResponseEntity.ok(scheduleAssembler.toResponse(schedule, entries));
    }

    @GetMapping("/{id}/schedule/{weekday}")
    @PreAuthorize("hasAuthority('CLASS_SCHEDULE_READ')")
    public ResponseEntity<List<ScheduleEntryResponse>> getScheduleByWeekday(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable UUID id, @PathVariable DayOfWeek weekday) {
        ClassSchedule schedule = getCurrentSchedule.execute(userDetails.getUserId(), id);

        List<ScheduleEntry> entries = getScheduleByWeekday.execute(userDetails.getUserId(), UUID.fromString(schedule.getId().toString()), weekday);

        return ResponseEntity.ok(entries.stream()
                .map(new ScheduleEntryResponseAssembler(null, null)::toResponse)
                .toList()
        );
    }

    @GetMapping("/schedule/{scheduleId}")
    @PreAuthorize("hasAuthority('CLASS_SCHEDULE_READ')")
    public ResponseEntity<ClassScheduleResponse> getSchedule(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable UUID scheduleId) {
        ClassSchedule schedule = getSchedule.execute(userDetails.getUserId(), scheduleId);

        List<ScheduleEntry> entries = getScheduleEntries.execute(userDetails.getUserId(), scheduleId);

        return ResponseEntity.ok(scheduleAssembler.toResponse(schedule, entries)
        );
    }

    @GetMapping("/{id}/schedules")
    @PreAuthorize("hasAuthority('CLASS_SCHEDULE_READ')")
    public ResponseEntity<List<ClassScheduleResponse>> getClassSchedules(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable UUID id) {
        List<ClassSchedule> schedules = getClassSchedulesByClass.execute(userDetails.getUserId(), id);

        return ResponseEntity.ok(schedules.stream()
                .map(schedule
                        -> scheduleAssembler.toResponse(schedule, getScheduleEntries.execute(userDetails.getUserId(), UUID.fromString(schedule.getId().toString()))))
                .toList()
        );
    }
}
