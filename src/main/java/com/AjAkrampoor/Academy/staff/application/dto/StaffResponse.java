package com.AjAkrampoor.Academy.staff.application.dto;

import com.AjAkrampoor.Academy.staff.domain.model.StaffStatus;

import java.time.LocalDateTime;

public class StaffResponse {
    private String staffId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String description;
    private StaffStatus status;
    private LocalDateTime terminationDate;
    private String branchName;
    private boolean hasUser;

    public StaffResponse(String staffId, String firstName, String lastName, String phoneNumber, String description, StaffStatus status, LocalDateTime terminationDate, String branchName, boolean hasUser) {
        this.staffId = staffId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.status = status;
        this.terminationDate = terminationDate;
        this.branchName = branchName;
        this.hasUser = hasUser;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StaffStatus getStatus() {
        return status;
    }

    public void setStatus(StaffStatus status) {
        this.status = status;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public boolean isHasUser() {
        return hasUser;
    }
}
