package com.AjAkrampoor.Academy.inventory.application.usecases.category;

import com.AjAkrampoor.Academy.inventory.domain.model.ProductCategory;
import com.AjAkrampoor.Academy.inventory.domain.repository.ProductCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetProductCategoriesUseCase {
    private final ProductCategoryRepository repository;

    public GetProductCategoriesUseCase(ProductCategoryRepository repository) {
        this.repository = repository;
    }

    public List<ProductCategory> execute() {
        return repository.findAll();
    }
}
