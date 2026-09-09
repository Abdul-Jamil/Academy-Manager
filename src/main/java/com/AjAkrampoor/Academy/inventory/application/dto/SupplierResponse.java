package com.AjAkrampoor.Academy.inventory.application.dto;

import com.AjAkrampoor.Academy.inventory.domain.model.SupplierStatus;

public class SupplierResponse {
    private String supplierId;
    private String supplierName;
    private String phone;
    private String address;
    private String description;
    private SupplierStatus supplierStatus;

    public SupplierResponse(String supplierId, String supplierName, String phone, String address,
                            String description, SupplierStatus supplierStatus) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.phone = phone;
        this.address = address;
        this.description = description;
        this.supplierStatus = supplierStatus;
    }

    // Getters
    public String getSupplierId() {
        return supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getPhone() {
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
