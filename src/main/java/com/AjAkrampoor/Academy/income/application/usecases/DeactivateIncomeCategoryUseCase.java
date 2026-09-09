package com.AjAkrampoor.Academy.income.application.usecases;

import com.AjAkrampoor.Academy.income.domain.model.ProfitCategory;
import com.AjAkrampoor.Academy.income.domain.model.ProfitCategoryId;
import com.AjAkrampoor.Academy.income.domain.repository.ProfitCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class DeactivateIncomeCategoryUseCase {
    private final ProfitCategoryRepository repository;

    public DeactivateIncomeCategoryUseCase(ProfitCategoryRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ProfitCategory execute(UUID categoryId) {
        ProfitCategory category = repository.findById(new ProfitCategoryId(categoryId))
                .orElseThrow(() -> new IllegalArgumentException("No such category found"));

        if (!category.isActive()) {
            return category;
        }

        category.deactivate();
        return repository.save(category);
    }
}
