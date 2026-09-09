package com.AjAkrampoor.Academy.inventory.application.usecases.category;

import com.AjAkrampoor.Academy.inventory.domain.model.ProductCategory;
import com.AjAkrampoor.Academy.inventory.domain.model.ProductCategoryId;
import com.AjAkrampoor.Academy.inventory.domain.repository.ProductCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ActivateProductCategoryUseCase {
    private final ProductCategoryRepository repository;

    public ActivateProductCategoryUseCase(ProductCategoryRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ProductCategory execute(UUID categoryUUID) {
        ProductCategory category = repository.findById(new ProductCategoryId(categoryUUID))
                .orElseThrow(() -> new IllegalArgumentException("Product category not found"));

        if (category.isActive()) {
            return category;
        }

        category.activate();
        return repository.save(category);
    }
}
