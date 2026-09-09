package com.AjAkrampoor.Academy.students.application.usecases;

import com.AjAkrampoor.Academy.shared.dto.PaginatedResponse;
import com.AjAkrampoor.Academy.shared.dto.PaginationRequest;
import com.AjAkrampoor.Academy.students.application.dto.StudentFilter;
import com.AjAkrampoor.Academy.students.domain.model.Student;
import com.AjAkrampoor.Academy.students.domain.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetAllStudentsUseCase {

    private final StudentRepository studentRepository;

    public GetAllStudentsUseCase(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<Student> execute(StudentFilter filter, PaginationRequest paginationRequest) {
        Pageable pageable = paginationRequest.toPageable();
        Page<Student> studentPage = studentRepository.findByFilter(filter, pageable);
        return new PaginatedResponse<>(studentPage);
    }
}