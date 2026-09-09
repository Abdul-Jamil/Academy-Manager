package com.AjAkrampoor.Academy.students.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class StudentCreateRequest {


    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    @Pattern(
            regexp = "^[\\p{L}\\s\\-']+$",
            message = "First name can only contain letters, spaces, hyphens, and apostrophes"
    )
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    @Pattern(
            regexp = "^[\\p{L}\\s\\-']+$",
            message = "Last name can only contain letters, spaces, hyphens, and apostrophes"
    )
    private String lastname;

    @NotBlank(message = "Guardian first name is required")
    @Size(min = 2, max = 50, message = "Guardian first name must be between 2 and 50 characters")
    @Pattern(
            regexp = "^[\\p{L}\\s\\-']+$",
            message = "Guardian first name can only contain letters, spaces, hyphens, and apostrophes"
    )
    private String guardianFirstName;

    @NotBlank(message = "Guardian last name is required")
    @Size(min = 2, max = 50, message = "Guardian last name must be between 2 and 50 characters")
    @Pattern(
            regexp = "^[\\p{L}\\s\\-']+$",
            message = "Guardian last name can only contain letters, spaces, hyphens, and apostrophes"
    )
    private String guardianLastName;

    @NotBlank(message = "Guardian phone number is required")
    @Size(min = 10, message = "Guardian phone number must be at least 10 digits")
    @Pattern(
            regexp = "^\\+?[0-9]+$",
            message = "Guardian phone number can only contain digits and an optional '+' at the beginning"
    )
    private String guardianPhoneNumber;

    @Size(min = 10, message = "Secondary phone number must be at least 10 digits")
    @Pattern(
            regexp = "^\\+?[0-9]+$",
            message = "Secondary phone number can only contain digits and an optional '+' at the beginning"
    )
    private String secondaryPhoneNumber;

    private Long guardianTelegram;

    @NotBlank
    private String branchId;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getGuardianFirstName() {
        return guardianFirstName;
    }

    public void setGuardianFirstName(String guardianFirstName) {
        this.guardianFirstName = guardianFirstName;
    }

    public String getGuardianLastName() {
        return guardianLastName;
    }

    public void setGuardianLastName(String guardianLastName) {
        this.guardianLastName = guardianLastName;
    }

    public String getGuardianPhoneNumber() {
        return guardianPhoneNumber;
    }

    public void setGuardianPhoneNumber(String guardianPhoneNumber) {
        this.guardianPhoneNumber = guardianPhoneNumber;
    }

    public String getSecondaryPhoneNumber() {
        return secondaryPhoneNumber;
    }

    public void setSecondaryPhoneNumber(String secondaryPhoneNumber) {
        this.secondaryPhoneNumber = secondaryPhoneNumber;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public Long getGuardianTelegram() {
        return guardianTelegram;
    }

    public void setGuardianTelegram(Long guardianTelegram) {
        this.guardianTelegram = guardianTelegram;
    }
}
