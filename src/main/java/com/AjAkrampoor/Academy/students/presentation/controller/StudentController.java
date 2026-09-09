package com.AjAkrampoor.Academy.students.presentation.controller;

import com.AjAkrampoor.Academy.shared.dto.PaginatedResponse;
import com.AjAkrampoor.Academy.shared.dto.PaginationRequest;
import com.AjAkrampoor.Academy.shared.security.CustomUserDetails;
import com.AjAkrampoor.Academy.students.application.StudentResponseAssembler;
import com.AjAkrampoor.Academy.students.application.dto.StudentCreateRequest;
import com.AjAkrampoor.Academy.students.application.dto.StudentFilter;
import com.AjAkrampoor.Academy.students.application.dto.StudentResponse;
import com.AjAkrampoor.Academy.students.application.dto.StudentUpdateRequest;
import com.AjAkrampoor.Academy.students.application.usecases.*;
import com.AjAkrampoor.Academy.students.domain.model.Student;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    private final StudentResponseAssembler assembler;
    private final CreateStudentUseCase createStudent;
    private final AddStudentToBranchUseCase addStudentToBranch;
    private final GetAllStudentsUseCase getAllStudents;
    private final GetBranchStudentsUseCase getBranchStudents;
    private final UpdateStudentUseCase updateStudent;
    private final DeactivateStudentUseCase deactivateStudent;
    private final ReactivateStudentUseCase reactivateStudent;

    public StudentController(StudentResponseAssembler assembler,
                             CreateStudentUseCase createStudent, AddStudentToBranchUseCase addStudentToBranch,
                             GetAllStudentsUseCase getAllStudents,
                             GetBranchStudentsUseCase getBranchStudents,
                             UpdateStudentUseCase updateStudent,
                             DeactivateStudentUseCase deactivateStudent,
                             ReactivateStudentUseCase reactivateStudent) {
        this.assembler = assembler;
        this.createStudent = createStudent;
        this.addStudentToBranch = addStudentToBranch;
        this.getAllStudents = getAllStudents;
        this.getBranchStudents = getBranchStudents;
        this.updateStudent = updateStudent;
        this.deactivateStudent = deactivateStudent;
        this.reactivateStudent = reactivateStudent;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('STUDENT_READ_ALL')")
    public ResponseEntity<PaginatedResponse<StudentResponse>> getAllStudents(
            @ModelAttribute StudentFilter filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "studentId") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {

        PaginationRequest paginationRequest = new PaginationRequest(page, size, sortBy, sortDirection);
        PaginatedResponse<Student> studentPage = getAllStudents.execute(filter, paginationRequest);
        return ResponseEntity.ok(studentPage.map(assembler::toResponse));
    }

    @GetMapping("/branch/{branchId}")
    @PreAuthorize("hasAuthority('STUDENT_READ_BRANCH')")
    public ResponseEntity<PaginatedResponse<StudentResponse>> getBranchStudents(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable UUID branchId,
            @ModelAttribute StudentFilter filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "studentId") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {

        PaginationRequest paginationRequest = new PaginationRequest(page, size, sortBy, sortDirection);
        PaginatedResponse<Student> studentPage = getBranchStudents.execute(
                userDetails.getUserId(),
                filter,
                paginationRequest,
                branchId
        );
        PaginatedResponse<StudentResponse> response = studentPage.map(assembler::toResponse);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('STUDENT_CREATE')")
    public ResponseEntity<StudentResponse> create(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                  @RequestBody @Valid StudentCreateRequest request) {
        Student student = createStudent.execute(
                userDetails.getUserId(),
                request.getFirstName(),
                request.getLastname(),
                request.getGuardianFirstName(),
                request.getGuardianLastName(),
                request.getGuardianPhoneNumber(),
                request.getSecondaryPhoneNumber(),
                request.getGuardianTelegram(),
                request.getBranchId()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toResponse(student));
    }

    @PatchMapping("/{id}/branch")
    @PreAuthorize("hasAuthority('STUDENT_ADD_BRANCH')")
    public ResponseEntity<StudentResponse> addToBranch(@PathVariable String id, @RequestParam String branch) {
        Student student = addStudentToBranch.execute(id, branch);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toResponse(student));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('STUDENT_UPDATE')")
    public ResponseEntity<StudentResponse> update(@PathVariable String id,
                                                  @RequestBody @Valid StudentUpdateRequest request) {
        Student student = updateStudent.execute(
                id,
                request.getFirstName(),
                request.getLastName(),
                request.getGuardianFirstName(),
                request.getGuardianLastName(),
                request.getGuardianPhoneNumber(),
                request.getSecondaryPhoneNumber(),
                request.getGuardianTelegram()
        );

        return ResponseEntity.ok(assembler.toResponse(student));
    }

    @PatchMapping("/{id}/delete")
    @PreAuthorize("hasAuthority('STUDENT_DEACTIVATE')")
    public ResponseEntity<StudentResponse> deactivate(@PathVariable String id,
                                                      @AuthenticationPrincipal CustomUserDetails userDetails) {
        Student student = deactivateStudent.execute(userDetails.getUserId(), id);
        return ResponseEntity.ok(assembler.toResponse(student));
    }

    @PatchMapping("/{id}/restore")
    @PreAuthorize("hasAuthority('STUDENT_ACTIVATE')")
    public ResponseEntity<StudentResponse> reactivate(@PathVariable String id,
                                                      @AuthenticationPrincipal CustomUserDetails userDetails) {
        Student student = reactivateStudent.execute(id);
        return ResponseEntity.ok(assembler.toResponse(student));
    }
}
