package com.AjAkrampoor.Academy.inventory.application;

import com.AjAkrampoor.Academy.inventory.application.dto.ProductCategoryResponse;
import com.AjAkrampoor.Academy.inventory.domain.model.ProductCategory;
import org.springframework.stereotype.Component;

@Component
public class ProductCategoryResponseAssembler {
    public ProductCategoryResponse toResponse(ProductCategory category) {
        String description = (category.getDescription() != null) ? category.getDescription().getValue() : null;
        return new ProductCategoryResponse(
                category.getCategoryId().toString(),
                category.getCategoryName().getValue(),
                description,
                category.getCategoryStatus()
        );
    }
}
