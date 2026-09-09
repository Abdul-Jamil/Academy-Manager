package com.AjAkrampoor.Academy.students.application.dto;

import com.AjAkrampoor.Academy.students.domain.model.StudentStatus;

import java.util.Set;

public class StudentResponse {
    private String studentId;
    private String firstName;
    private String lastName;
    private String guardianFirstName;
    private String guardianLastName;
    private String guardianNumber;
    private String secondaryNumber;
    private Long guardianTelegram;
    private StudentStatus studentStatus;
    private Set<String> assignedBranches;

    public StudentResponse(String studentId, String firstName, String lastName, String guardianFirstName, String guardianLastName, String guardianNumber, String secondaryNumber, Long guardianTelegram, StudentStatus studentStatus, Set<String> assignedBranches) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.guardianFirstName = guardianFirstName;
        this.guardianLastName = guardianLastName;
        this.guardianNumber = guardianNumber;
        this.secondaryNumber = secondaryNumber;
        this.guardianTelegram = guardianTelegram;
        this.studentStatus = studentStatus;
        this.assignedBranches = assignedBranches;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGuardianFirstName() {
        return guardianFirstName;
    }

    public String getGuardianLastName() {
        return guardianLastName;
    }

    public String getGuardianNumber() {
        return guardianNumber;
    }

    public String getSecondaryNumber() {
        return secondaryNumber;
    }

    public Long getGuardianTelegram() {
        return guardianTelegram;
    }

    public StudentStatus getStudentStatus() {
        return studentStatus;
    }

    public Set<String> getAssignedBranches() {
        return assignedBranches;
    }
}
