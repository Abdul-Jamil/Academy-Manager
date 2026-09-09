package com.AjAkrampoor.Academy.income.application.usecases;

import com.AjAkrampoor.Academy.income.domain.model.ProfitCategory;
import com.AjAkrampoor.Academy.income.domain.repository.ProfitCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetIncomeCategoriesUseCase {
    private final ProfitCategoryRepository repository;

    public GetIncomeCategoriesUseCase(ProfitCategoryRepository repository) {
        this.repository = repository;
    }

    public List<ProfitCategory> execute() {
        return repository.findAll();
    }
}
