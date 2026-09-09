package com.AjAkrampoor.Academy.inventory.domain.model;

import com.AjAkrampoor.Academy.shared.vo.PhoneNumber;

import java.util.Objects;

public class Supplier {

    private SupplierId supplierId;
    private SupplierName supplierName;
    private PhoneNumber phone;
    private String address;
    private String description;
    private SupplierStatus supplierStatus;

    public Supplier(SupplierId supplierId, SupplierName supplierName, PhoneNumber phone, String address, String description, SupplierStatus supplierStatus) {
        this.supplierId = Objects.requireNonNull(supplierId, "Supplier id cannot be null");
        this.supplierName = Objects.requireNonNull(supplierName, "Supplier name cannot be null");
        this.phone = Objects.requireNonNull(phone, "Supplier phone cannot be null");
        this.address = address;
        this.description = description;
        this.supplierStatus = Objects.requireNonNull(supplierStatus, "Supplier status cannot be null");
    }

    public void updateName(SupplierName supplierName) {
        this.supplierName = supplierName;
    }

    public void updatePhone(PhoneNumber phone) {
        this.phone = phone;
    }

    public void updateAddress(String address) {
        this.address = address;
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public void activate() {
        supplierStatus = SupplierStatus.ACTIVE;
    }

    public void deactivate() {
        supplierStatus = SupplierStatus.INACTIVE;
    }

    public SupplierId getSupplierId() {
        return supplierId;
    }

    public SupplierName getSupplierName() {
        return supplierName;
    }

    public PhoneNumber getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public SupplierStatus getSupplierStatus() {
        return supplierStatus;
    }
}
