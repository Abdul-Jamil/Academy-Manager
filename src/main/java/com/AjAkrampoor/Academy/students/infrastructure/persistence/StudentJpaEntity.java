package com.AjAkrampoor.Academy.students.infrastructure.persistence;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.shared.vo.Name;
import com.AjAkrampoor.Academy.shared.vo.PhoneNumber;
import com.AjAkrampoor.Academy.students.domain.model.StudentId;
import com.AjAkrampoor.Academy.students.domain.model.StudentStatus;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "students")
public class StudentJpaEntity {

    @EmbeddedId
    @AttributeOverride(name = "id", column = @Column(name = "id", nullable = false, unique = true))
    private StudentId studentId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "firstName", column = @Column(name = "first_name", nullable = false)),
            @AttributeOverride(name = "lastName", column = @Column(name = "last_name", nullable = false))
    })
    private Name studentName;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "firstName", column = @Column(name = "guardian_first_name", nullable = false)),
            @AttributeOverride(name = "lastName", column = @Column(name = "guardian_last_name", nullable = false))
    })
    private Name guardianName;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "number", column = @Column(name = "guardian_number", nullable = false))
    })
    private PhoneNumber guardianNumber;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "number", column = @Column(name = "secondary_number"))
    })
    private PhoneNumber secondaryNumber;

    private Long guardianTelegram;

    @Enumerated(EnumType.STRING)
    @Column(name = "student_status", nullable = false)
    private StudentStatus studentStatus;

    @ElementCollection
    @CollectionTable(
            name = "student_branches",
            joinColumns = @JoinColumn(name = "student_id")
    )
    @AttributeOverride(name = "id", column = @Column(name = "branch_id"))
    private Set<BranchId> assignedBranches;

    public StudentJpaEntity() {
    }

    public StudentId getStudentId() {
        return studentId;
    }

    public void setStudentId(StudentId studentId) {
        this.studentId = studentId;
    }

    public Name getStudentName() {
        return studentName;
    }

    public void setStudentName(Name studentName) {
        this.studentName = studentName;
    }

    public Name getGuardianName() {
        return guardianName;
    }

    public void setGuardianName(Name guardianName) {
        this.guardianName = guardianName;
    }

    public PhoneNumber getGuardianNumber() {
        return guardianNumber;
    }

    public void setGuardianNumber(PhoneNumber guardianNumber) {
        this.guardianNumber = guardianNumber;
    }

    public PhoneNumber getSecondaryNumber() {
        return secondaryNumber;
    }

    public void setSecondaryNumber(PhoneNumber secondaryNumber) {
        this.secondaryNumber = secondaryNumber;
    }

    public StudentStatus getStudentStatus() {
        return studentStatus;
    }

    public void setStudentStatus(StudentStatus studentStatus) {
        this.studentStatus = studentStatus;
    }

    public Set<BranchId> getAssignedBranches() {
        return assignedBranches;
    }

    public void setAssignedBranches(Set<BranchId> assignedBranches) {
        this.assignedBranches = assignedBranches;
    }

    public Long getGuardianTelegram() {
        return guardianTelegram;
    }

    public void setGuardianTelegram(Long guardianTelegram) {
        this.guardianTelegram = guardianTelegram;
    }
}