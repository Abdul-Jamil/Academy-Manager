package com.AjAkrampoor.Academy.students.presentation.mapper;

import com.AjAkrampoor.Academy.students.domain.model.Student;
import com.AjAkrampoor.Academy.students.infrastructure.persistence.StudentJpaEntity;

import java.util.HashSet;
import java.util.List;

public class StudentMapper {

    public static Student toDomain(StudentJpaEntity entity) {

        return new Student(entity.getStudentId(),
                entity.getStudentName(),
                entity.getGuardianName(),
                entity.getGuardianNumber(),
                entity.getSecondaryNumber(),
                entity.getGuardianTelegram(),
                entity.getStudentStatus(),
                entity.getAssignedBranches()
        );
    }

    public static StudentJpaEntity toJpaEntity(Student domain) {
        StudentJpaEntity studentJpaEntity = new StudentJpaEntity();

        studentJpaEntity.setStudentId(domain.getStudentId());
        studentJpaEntity.setStudentName(domain.getStudentName());
        studentJpaEntity.setGuardianName(domain.getGuardianName());
        studentJpaEntity.setGuardianNumber(domain.getGuardianNumber());
        if (domain.getSecondaryNumber().isPresent()) {
            studentJpaEntity.setSecondaryNumber(domain.getSecondaryNumber().get());
        }
        studentJpaEntity.setGuardianTelegram(domain.getGuardianTelegram());

        studentJpaEntity.setStudentStatus(domain.getStudentStatus());
        studentJpaEntity.setAssignedBranches(new HashSet<>(domain.getAssignedBranches()));

        return studentJpaEntity;
    }

    public static List<Student> toDomainList(List<StudentJpaEntity> entities) {
        return entities.stream()
                .map(StudentMapper::toDomain)
                .toList();
    }

    public static List<StudentJpaEntity> toJpaEntityList(List<Student> domains) {
        return domains.stream()
                .map(StudentMapper::toJpaEntity)
                .toList();
    }
}
