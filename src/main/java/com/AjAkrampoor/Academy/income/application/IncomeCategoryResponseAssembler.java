package com.AjAkrampoor.Academy.income.application;

import com.AjAkrampoor.Academy.income.application.dto.IncomeCategoryResponse;
import com.AjAkrampoor.Academy.income.domain.model.ProfitCategory;
import org.springframework.stereotype.Component;

@Component
public class IncomeCategoryResponseAssembler {
    public IncomeCategoryResponse toResponse(ProfitCategory category) {
        String description = (category.getDescription() != null) ? category.getDescription().getValue() : null;
        return new IncomeCategoryResponse(
                category.getCategoryId().toString(),
                category.getCategoryName().getValue(),
                description,
                category.getCategoryStatus()
        );
    }
}
