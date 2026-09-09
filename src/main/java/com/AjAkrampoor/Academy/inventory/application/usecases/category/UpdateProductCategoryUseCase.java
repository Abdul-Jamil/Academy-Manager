package com.AjAkrampoor.Academy.inventory.application.usecases.category;

import com.AjAkrampoor.Academy.inventory.domain.model.ProductCategory;
import com.AjAkrampoor.Academy.inventory.domain.model.ProductCategoryId;
import com.AjAkrampoor.Academy.inventory.domain.model.ProductCategoryName;
import com.AjAkrampoor.Academy.inventory.domain.repository.ProductCategoryRepository;
import com.AjAkrampoor.Academy.shared.vo.Description;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UpdateProductCategoryUseCase {
    private final ProductCategoryRepository repository;

    public UpdateProductCategoryUseCase(ProductCategoryRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ProductCategory execute(UUID categoryUUID, String newName, String descriptionStr) {
        ProductCategory category = repository.findById(new ProductCategoryId(categoryUUID))
                .orElseThrow(() -> new IllegalArgumentException("Product category not found"));

        if (!category.isActive()) {
            throw new IllegalStateException("Cannot update an inactive category.");
        }

        ProductCategoryName name = new ProductCategoryName(newName);
        if (repository.existsByName(name)) {
            throw new IllegalStateException("Product category name already exists.");
        }

        Description description = (descriptionStr == null) ? null : new Description(descriptionStr);
        category.updateName(name);
        category.updateDescription(description);

        return repository.save(category);
    }
}
