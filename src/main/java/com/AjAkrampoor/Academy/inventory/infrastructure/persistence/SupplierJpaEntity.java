package com.AjAkrampoor.Academy.inventory.infrastructure.persistence;

import com.AjAkrampoor.Academy.inventory.domain.model.SupplierId;
import com.AjAkrampoor.Academy.inventory.domain.model.SupplierName;
import com.AjAkrampoor.Academy.inventory.domain.model.SupplierStatus;
import com.AjAkrampoor.Academy.shared.vo.PhoneNumber;
import jakarta.persistence.*;

@Entity
@Table(name = "suppliers")
public class SupplierJpaEntity {

    @EmbeddedId
    @AttributeOverride(
            name = "id",
            column = @Column(
                    name = "supplier_id",
                    nullable = false,
                    updatable = false,
                    unique = true
            )
    )
    private SupplierId supplierId;

    @Embedded
    @AttributeOverride(
            name = "value",
            column = @Column(
                    name = "supplier_name",
                    nullable = false
            )
    )
    private SupplierName supplierName;

    @Embedded
    @AttributeOverride(name = "number", column = @Column(name = "phone", nullable = false))
    private PhoneNumber phone;

    @Column(length = 255)
    private String address;

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SupplierStatus supplierStatus;

    public SupplierJpaEntity() {
    }

    public SupplierId getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(SupplierId supplierId) {
        this.supplierId = supplierId;
    }

    public SupplierName getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(SupplierName supplierName) {
        this.supplierName = supplierName;
    }

    public PhoneNumber getPhone() {
        return phone;
    }

    public void setPhone(PhoneNumber phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SupplierStatus getSupplierStatus() {
        return supplierStatus;
    }

    public void setSupplierStatus(SupplierStatus supplierStatus) {
        this.supplierStatus = supplierStatus;
    }
}
