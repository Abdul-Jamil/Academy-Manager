package com.AjAkrampoor.Academy.inventory.application.dto;

import org.openapitools.jackson.nullable.JsonNullable;

public class SupplierUpdateRequest {
    private JsonNullable<String> name = JsonNullable.undefined();
    private JsonNullable<String> phone = JsonNullable.undefined();
    private JsonNullable<String> address = JsonNullable.undefined();
    private JsonNullable<String> description = JsonNullable.undefined();

    // Getters and setters
    public JsonNullable<String> getName() {
        return name;
    }

    public void setName(JsonNullable<String> name) {
        this.name = name;
    }

    public JsonNullable<String> getPhone() {
        return phone;
    }

    public void setPhone(JsonNullable<String> phone) {
        this.phone = phone;
    }

    public JsonNullable<String> getAddress() {
        return address;
    }

    public void setAddress(JsonNullable<String> address) {
        this.address = address;
    }

    public JsonNullable<String> getDescription() {
        return description;
    }

    public void setDescription(JsonNullable<String> description) {
        this.description = description;
    }
}
