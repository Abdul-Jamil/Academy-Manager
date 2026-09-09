package com.AjAkrampoor.Academy.income.application.usecases;

import com.AjAkrampoor.Academy.income.domain.model.ProfitCategory;
import com.AjAkrampoor.Academy.income.domain.model.ProfitCategoryId;
import com.AjAkrampoor.Academy.income.domain.model.ProfitCategoryName;
import com.AjAkrampoor.Academy.income.domain.repository.ProfitCategoryRepository;
import com.AjAkrampoor.Academy.shared.vo.Description;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UpdateIncomeCategoryUseCase {
    private final ProfitCategoryRepository repository;

    public UpdateIncomeCategoryUseCase(ProfitCategoryRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ProfitCategory execute(UUID categoryUUID, String newNameStr, String descriptionStr) {
        ProfitCategory category = repository.findById(new ProfitCategoryId(categoryUUID))
                .orElseThrow(() -> new IllegalArgumentException("No such income category found"));

        if (!category.isActive()) {
            throw new IllegalStateException("Income category is not active.");
        }

        ProfitCategoryName name = new ProfitCategoryName(newNameStr);
        if (repository.existsByName(name)) {
            throw new IllegalStateException("Income category already exists.");
        }

        Description description = (descriptionStr == null) ? null : new Description(descriptionStr);
        category.updateName(name);
        category.updateDescription(description);

        return repository.save(category);
    }
}
