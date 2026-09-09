package com.AjAkrampoor.Academy.students.domain.model;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.shared.vo.Name;
import com.AjAkrampoor.Academy.shared.vo.PhoneNumber;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class Student {
    private StudentId studentId;
    private Name studentName;
    private Name guardianName;
    private PhoneNumber guardianNumber;
    private PhoneNumber secondaryNumber;
    private Long guardianTelegram;
    private StudentStatus studentStatus;
    private Set<BranchId> assignedBranches;

    public Student(StudentId studentId, Name studentName, Name guardianName, PhoneNumber guardianPhone, PhoneNumber secondaryNumber, Long guardianTelegram, StudentStatus studentStatus, Set<BranchId> assignedBranches) {
        this.studentId = Objects.requireNonNull(studentId, "Student ID cannot be null");
        this.studentName = Objects.requireNonNull(studentName, "Student name cannot be null");
        this.guardianName = Objects.requireNonNull(guardianName, "Guardian name cannot be null");
        this.guardianNumber = Objects.requireNonNull(guardianPhone, "Guardian phone cannot be null");
        this.secondaryNumber = secondaryNumber;
        this.guardianTelegram = guardianTelegram;
        this.studentStatus = Objects.requireNonNull(studentStatus, "Status cannot be null");
        this.assignedBranches = assignedBranches;
    }

    public void assignToBranch(BranchId branchId) {
        Objects.requireNonNull(branchId);

        assignedBranches.add(branchId);
    }

    public void removeFromBranch(BranchId branchId) {
        Objects.requireNonNull(branchId);

        assignedBranches.remove(branchId);
    }

    public void updateStudentName(String firstName, String lastName) {

        if (firstName == null && lastName == null) {
            return;
        }

        Name updated = this.studentName;

        if (firstName != null) {
            updated = updated.withFirstName(firstName);
        }

        if (lastName != null) {
            updated = updated.withLastName(lastName);
        }

        this.studentName = updated;
    }

    public void updateGuardianName(String firstName, String lastName) {

        if (firstName == null && lastName == null) {
            return;
        }

        Name updated = this.guardianName;

        if (firstName != null) {
            updated = updated.withFirstName(firstName);
        }

        if (lastName != null) {
            updated = updated.withLastName(lastName);
        }

        this.guardianName = updated;
    }

    public void updateGuardianNumber(PhoneNumber number) {
        this.guardianNumber = Objects.requireNonNull(number);
    }

    public void updateSecondaryNumber(PhoneNumber number) {
        this.secondaryNumber = number;
    }

    public void deactivate() {
        if (studentStatus == StudentStatus.INACTIVE) {
            return;
        }

        this.studentStatus = StudentStatus.INACTIVE;
    }

    public void reactivate() {
        if (studentStatus == StudentStatus.ACTIVE) {
            return;
        }

        this.studentStatus = StudentStatus.ACTIVE;
    }

    public void updateGuardianTelegram(Long value) {
        this.guardianTelegram = value;
    }

    public StudentId getStudentId() {
        return studentId;
    }

    public Name getStudentName() {
        return studentName;
    }

    public Name getGuardianName() {
        return guardianName;
    }

    public PhoneNumber getGuardianNumber() {
        return guardianNumber;
    }

    public Optional<PhoneNumber> getSecondaryNumber() {
        return Optional.ofNullable(secondaryNumber);
    }

    public Long getGuardianTelegram() {
        return guardianTelegram;
    }

    public StudentStatus getStudentStatus() {
        return studentStatus;
    }

    public boolean isActive() {
        return this.studentStatus == StudentStatus.ACTIVE;
    }

    public Set<BranchId> getAssignedBranches() {
        return Collections.unmodifiableSet(assignedBranches);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;

        return studentId.equals(student.studentId);
    }

    @Override
    public int hashCode() {
        return studentId.hashCode();
    }
}
