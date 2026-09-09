package com.AjAkrampoor.Academy.students.infrastructure.repository;

import com.AjAkrampoor.Academy.students.application.dto.StudentFilter;
import com.AjAkrampoor.Academy.students.infrastructure.persistence.StudentJpaEntity;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class StudentSpecifications {

    public static List<Specification<StudentJpaEntity>> getSpecifications(StudentFilter filter) {
        List<Specification<StudentJpaEntity>> specs = new ArrayList<>();
        if (filter == null) return specs;

        if (filter.getStudentId() != null && !filter.getStudentId().isEmpty()) {
            specs.add((root, query, cb) ->
                    cb.equal(root.get("studentId").get("id"), filter.getStudentId()));
        }

        // Student first name – case-insensitive contains
        if (filter.getFirstName() != null && !filter.getFirstName().isEmpty()) {
            specs.add((root, query, cb) ->
                    cb.like(cb.lower(root.get("studentName").get("firstName")),
                            "%" + filter.getFirstName().toLowerCase() + "%"));
        }

        // Student last name
        if (filter.getLastName() != null && !filter.getLastName().isEmpty()) {
            specs.add((root, query, cb) ->
                    cb.like(cb.lower(root.get("studentName").get("lastName")),
                            "%" + filter.getLastName().toLowerCase() + "%"));
        }

        // Guardian first name
        if (filter.getGuardianFirstName() != null && !filter.getGuardianFirstName().isEmpty()) {
            specs.add((root, query, cb) ->
                    cb.like(cb.lower(root.get("guardianName").get("firstName")),
                            "%" + filter.getGuardianFirstName().toLowerCase() + "%"));
        }

        // Guardian last name
        if (filter.getGuardianLastName() != null && !filter.getGuardianLastName().isEmpty()) {
            specs.add((root, query, cb) ->
                    cb.like(cb.lower(root.get("guardianName").get("lastName")),
                            "%" + filter.getGuardianLastName().toLowerCase() + "%"));
        }

        // Guardian phone number (partial match)
        if (filter.getGuardianPhoneNumber() != null && !filter.getGuardianPhoneNumber().isEmpty()) {
            specs.add((root, query, cb) ->
                    cb.like(root.get("guardianNumber").get("number"),
                            "%" + filter.getGuardianPhoneNumber() + "%"));
        }

        // Secondary phone number (partial match)
        if (filter.getSecondaryPhoneNumber() != null && !filter.getSecondaryPhoneNumber().isEmpty()) {
            specs.add((root, query, cb) ->
                    cb.like(root.get("secondaryNumber").get("number"),
                            "%" + filter.getSecondaryPhoneNumber() + "%"));
        }

        // Status exact match
        if (filter.getStatus() != null) {
            specs.add((root, query, cb) ->
                    cb.equal(root.get("studentStatus"), filter.getStatus()));
        }

        // Branch ID – students that have the given branch in assignedBranches
        if (filter.getBranchId() != null && !filter.getBranchId().isEmpty()) {
            specs.add((root, query, cb) -> {
                Join<?, ?> branches = root.join("assignedBranches");
                assert query != null;
                query.distinct(true);
                return cb.equal(branches.get("id"), filter.getBranchId());
            });
        }

        return specs;
    }
}