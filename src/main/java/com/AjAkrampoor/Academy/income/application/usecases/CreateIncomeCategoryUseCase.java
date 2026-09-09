package com.AjAkrampoor.Academy.income.application.usecases;

import com.AjAkrampoor.Academy.income.domain.model.CategoryStatus;
import com.AjAkrampoor.Academy.income.domain.model.ProfitCategory;
import com.AjAkrampoor.Academy.income.domain.model.ProfitCategoryId;
import com.AjAkrampoor.Academy.income.domain.model.ProfitCategoryName;
import com.AjAkrampoor.Academy.income.domain.repository.ProfitCategoryRepository;
import com.AjAkrampoor.Academy.shared.vo.Description;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateIncomeCategoryUseCase {
    private final ProfitCategoryRepository repository;

    public CreateIncomeCategoryUseCase(ProfitCategoryRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ProfitCategory execute(String nameStr, String descriptionStr) {
        ProfitCategoryName name = new ProfitCategoryName(nameStr);
        Description description = (descriptionStr != null && !descriptionStr.isBlank()) ? new Description(descriptionStr) : null;

        if (repository.existsByName(name)) {
            throw new IllegalArgumentException("This category name already exists");
        }

        ProfitCategoryId id;
        for (int i = 0; i < 10; i++) {
            id = ProfitCategoryId.newId();
            if (!repository.existsById(id)) {
                return repository.save(new ProfitCategory(id, name, description, CategoryStatus.ACTIVE));
            }
        }

        throw new IllegalArgumentException("Could not create profit category after several retries.");
    }
}
