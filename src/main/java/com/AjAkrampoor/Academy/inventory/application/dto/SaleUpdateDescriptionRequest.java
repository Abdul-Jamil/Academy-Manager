package com.AjAkrampoor.Academy.inventory.application.dto;

import org.openapitools.jackson.nullable.JsonNullable;

public class SaleUpdateDescriptionRequest {
    private JsonNullable<String> description = JsonNullable.undefined();

    public JsonNullable<String> getDescription() {
        return description;
    }

    public void setDescription(JsonNullable<String> description) {
        this.description = description;
    }
}
