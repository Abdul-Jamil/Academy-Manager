package com.AjAkrampoor.Academy.students.infrastructure.repository;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.students.application.dto.StudentFilter;
import com.AjAkrampoor.Academy.students.domain.model.Student;
import com.AjAkrampoor.Academy.students.domain.model.StudentId;
import com.AjAkrampoor.Academy.students.domain.repository.StudentRepository;
import com.AjAkrampoor.Academy.students.infrastructure.persistence.StudentJpaEntity;
import com.AjAkrampoor.Academy.students.presentation.mapper.StudentMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class StudentRepositoryImpl implements StudentRepository {
    private final StudentJpaRepository repository;

    public StudentRepositoryImpl(StudentJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Student> findById(StudentId id) {
        return repository.findById(id).map(StudentMapper::toDomain);

    }

    @Override
    public List<Student> findAll() {
        List<StudentJpaEntity> entities = repository.findAll();
        return StudentMapper.toDomainList(entities);
    }

    @Override
    public Page<Student> findAll(Pageable pageable) {
        Page<StudentJpaEntity> entityPage = repository.findAll(pageable);
        return entityPage.map(StudentMapper::toDomain);
    }

    @Override
    public Page<Student> findByAssignedBranches(BranchId branchId, Pageable pageable) {
        Page<StudentJpaEntity> entityPage = repository.findByAssignedBranches(branchId, pageable);
        return entityPage.map(StudentMapper::toDomain);
    }

    @Override
    public Page<Student> findByFilter(StudentFilter filter, Pageable pageable) {
        List<Specification<StudentJpaEntity>> specs = StudentSpecifications.getSpecifications(filter);
        Specification<StudentJpaEntity> combinedSpec = specs.stream()
                .reduce(Specification::and)
                .orElse(null);
        Page<StudentJpaEntity> entityPage = repository.findAll(combinedSpec, pageable);
        return entityPage.map(StudentMapper::toDomain);
    }

    @Override
    public Student save(Student student) {
        StudentJpaEntity jpaEntity = StudentMapper.toJpaEntity(student);
        return StudentMapper.toDomain(repository.save(jpaEntity));
    }

    @Override
    public boolean existsById(StudentId studentId) {
        return repository.existsById(studentId);
    }
}
