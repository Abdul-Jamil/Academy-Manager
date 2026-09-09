package com.AjAkrampoor.Academy.inventory.application.dto;

import org.openapitools.jackson.nullable.JsonNullable;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductUpdateRequest {
    private JsonNullable<String> productName = JsonNullable.undefined();
    private JsonNullable<String> description = JsonNullable.undefined();
    private JsonNullable<BigDecimal> sellingPrice = JsonNullable.undefined();
    private UUID categoryId;

    public JsonNullable<String> getProductName() {
        return productName;
    }

    public void setProductName(JsonNullable<String> productName) {
        this.productName = productName;
    }

    public JsonNullable<String> getDescription() {
        return description;
    }

    public void setDescription(JsonNullable<String> description) {
        this.description = description;
    }

    public JsonNullable<BigDecimal> getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(JsonNullable<BigDecimal> sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }
}
