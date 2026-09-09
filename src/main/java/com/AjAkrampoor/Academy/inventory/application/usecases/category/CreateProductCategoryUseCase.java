package com.AjAkrampoor.Academy.inventory.application.usecases.category;

import com.AjAkrampoor.Academy.inventory.domain.model.CategoryStatus;
import com.AjAkrampoor.Academy.inventory.domain.model.ProductCategory;
import com.AjAkrampoor.Academy.inventory.domain.model.ProductCategoryId;
import com.AjAkrampoor.Academy.inventory.domain.model.ProductCategoryName;
import com.AjAkrampoor.Academy.inventory.domain.repository.ProductCategoryRepository;
import com.AjAkrampoor.Academy.shared.vo.Description;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateProductCategoryUseCase {
    private final ProductCategoryRepository repository;

    public CreateProductCategoryUseCase(ProductCategoryRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ProductCategory execute(String nameStr, String descriptionStr) {
        ProductCategoryName name = new ProductCategoryName(nameStr);
        Description description = (descriptionStr != null && !descriptionStr.isBlank()) ? new Description(descriptionStr) : null;

        if (repository.existsByName(name)) {
            throw new IllegalArgumentException("Product category name already exists");
        }

        // Retry up to 10 times to get a unique ID
        for (int i = 0; i < 10; i++) {
            ProductCategoryId id = ProductCategoryId.newId();
            if (!repository.existsById(id)) {
                ProductCategory category = new ProductCategory(id, name, description, CategoryStatus.ACTIVE);
                return repository.save(category);
            }
        }

        throw new IllegalArgumentException("Could not create product category after several retries.");
    }
}
