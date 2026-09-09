package com.AjAkrampoor.Academy.staff.domain.model;

import com.AjAkrampoor.Academy.branches.domain.model.BranchId;
import com.AjAkrampoor.Academy.shared.vo.Description;
import com.AjAkrampoor.Academy.shared.vo.Name;
import com.AjAkrampoor.Academy.shared.vo.PhoneNumber;

import java.time.LocalDateTime;
import java.util.Objects;

public class Staff {
    private StaffId staffId;
    private Name name;
    private PhoneNumber phoneNumber;
    private Description description;
    private StaffStatus status;
    private LocalDateTime terminationDate;
    private BranchId branchId;

    public Staff(StaffId staffId, Name name, PhoneNumber phoneNumber, Description description, StaffStatus status, LocalDateTime terminationDate, BranchId branchId) {
        this.staffId = Objects.requireNonNull(staffId, "staffId cannot be null");
        this.name = Objects.requireNonNull(name, "name cannot be null");
        this.phoneNumber = Objects.requireNonNull(phoneNumber, "phoneNumber cannot be null");
        this.description = Objects.requireNonNull(description, "description cannot be null");
        this.status = Objects.requireNonNull(status, "status cannot be null");
        this.terminationDate = terminationDate;
        this.branchId = Objects.requireNonNull(branchId, "branchId cannot be null");
    }

    public void updateName(String firstName, String lastName) {

        if (firstName == null && lastName == null) {
            return;
        }

        Name updated = this.name;

        if (firstName != null) {
            updated = updated.withFirstName(firstName);
        }

        if (lastName != null) {
            updated = updated.withLastName(lastName);
        }

        this.name = updated;
    }

    public void updatePhoneNumber(PhoneNumber phoneNumber) {
        if (phoneNumber == null) {
            throw new IllegalArgumentException("phoneNumber is null");
        }
        this.phoneNumber = phoneNumber;
    }

    public void updateDescription(Description description) {
        if (description == null) {
            throw new IllegalArgumentException("description is null");
        }
        this.description = description;
    }

    public void activate() {
        if (status == StaffStatus.ACTIVE) {
            return;
        }
        status = StaffStatus.ACTIVE;
        this.terminationDate = null;
    }

    public void deactivate() {
        if (status == StaffStatus.INACTIVE) {
            throw new IllegalArgumentException("status is INACTIVE");
        }
        status = StaffStatus.INACTIVE;
        this.terminationDate = LocalDateTime.now();
    }

    public boolean isActive() {
        return status == StaffStatus.ACTIVE;
    }

    public StaffId getStaffId() {
        return staffId;
    }

    public StaffId getId() {
        return staffId;
    }

    public Name getName() {
        return name;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public Description getDescription() {
        return description;
    }

    public StaffStatus getStatus() {
        return status;
    }

    public LocalDateTime getTerminationDate() {
        return terminationDate;
    }

    public BranchId getBranchId() {
        return branchId;
    }
}
