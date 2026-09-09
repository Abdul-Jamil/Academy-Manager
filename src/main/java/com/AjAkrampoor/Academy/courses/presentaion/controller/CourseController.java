package com.AjAkrampoor.Academy.courses.presentaion.controller;

import com.AjAkrampoor.Academy.courses.application.CourseResponseAssembler;
import com.AjAkrampoor.Academy.courses.application.dto.CourseCreateRequest;
import com.AjAkrampoor.Academy.courses.application.dto.CourseFilter;
import com.AjAkrampoor.Academy.courses.application.dto.CourseResponse;
import com.AjAkrampoor.Academy.courses.application.dto.CourseUpdateRequest;
import com.AjAkrampoor.Academy.courses.application.usecases.course.*;
import com.AjAkrampoor.Academy.courses.domain.model.Course;
import com.AjAkrampoor.Academy.shared.dto.PaginatedResponse;
import com.AjAkrampoor.Academy.shared.dto.PaginationRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    private final CourseResponseAssembler assembler;
    private final GetCoursesUseCase getCourses;
    private final GetCourseUseCase getCourse;
    private final CreateCourseUseCase createCourse;
    private final UpdateCourseUseCase updateCourse;
    private final ActivateCourseUseCase activateCourse;
    private final DeactivateCourseUseCase deactivateCourse;

    public CourseController(
            CourseResponseAssembler assembler,
            GetCoursesUseCase getCourses,
            GetCourseUseCase getCourse,
            CreateCourseUseCase createCourse,
            UpdateCourseUseCase updateCourse,
            ActivateCourseUseCase activateCourse,
            DeactivateCourseUseCase deactivateCourse
    ) {
        this.assembler = assembler;
        this.getCourses = getCourses;
        this.getCourse = getCourse;
        this.createCourse = createCourse;
        this.updateCourse = updateCourse;
        this.activateCourse = activateCourse;
        this.deactivateCourse = deactivateCourse;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('COURSE_READ')")
    public ResponseEntity<PaginatedResponse<CourseResponse>> getCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "courseId.value") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) com.AjAkrampoor.Academy.courses.domain.model.CourseStatus status
    ) {

        CourseFilter filter = new CourseFilter();
        filter.setName(name);
        filter.setStatus(status);

        PaginationRequest paginationRequest =
                new PaginationRequest(
                        page,
                        size,
                        sortBy,
                        sortDirection
                );

        Page<Course> coursePage =
                getCourses.execute(
                        filter,
                        paginationRequest.toPageable()
                );

        List<CourseResponse> responses =
                coursePage.getContent()
                        .stream()
                        .map(assembler::toResponse)
                        .toList();

        Page<CourseResponse> responsePage = new PageImpl<>(responses, coursePage.getPageable(), coursePage.getTotalElements());
        return ResponseEntity.ok(new PaginatedResponse<>(responsePage)
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('COURSE_READ')")
    public ResponseEntity<CourseResponse> getCourse(@PathVariable UUID id) {
        return ResponseEntity.ok(assembler.toResponse(getCourse.execute(id)));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('COURSE_CREATE')")
    public ResponseEntity<CourseResponse> createCourse(@Valid @RequestBody CourseCreateRequest request) {
        Course course = createCourse.execute(request.getCourseName(), request.getDescription());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(assembler.toResponse(course));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('COURSE_UPDATE')")
    public ResponseEntity<CourseResponse> updateCourse(@PathVariable UUID id, @Valid @RequestBody CourseUpdateRequest request) {

        Course course = updateCourse.execute(id, request.getCourseName(), request.getDescription());
        return ResponseEntity.ok(assembler.toResponse(course));
    }

    @PatchMapping("/{id}/activate")
    @PreAuthorize("hasAuthority('COURSE_ACTIVATE')")
    public ResponseEntity<CourseResponse> activateCourse(@PathVariable UUID id) {

        Course course = activateCourse.execute(id);
        return ResponseEntity.ok(assembler.toResponse(course));
    }

    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasAuthority('COURSE_DEACTIVATE')")
    public ResponseEntity<CourseResponse> deactivateCourse(@PathVariable UUID id) {

        Course course = deactivateCourse.execute(id);
        return ResponseEntity.ok(assembler.toResponse(course));
    }
}
