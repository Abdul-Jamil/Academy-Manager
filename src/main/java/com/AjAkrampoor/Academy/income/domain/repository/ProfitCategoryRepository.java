package com.AjAkrampoor.Academy.income.domain.repository;

import com.AjAkrampoor.Academy.income.domain.model.ProfitCategory;
import com.AjAkrampoor.Academy.income.domain.model.ProfitCategoryId;
import com.AjAkrampoor.Academy.income.domain.model.ProfitCategoryName;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfitCategoryRepository {
    boolean existsByName(ProfitCategoryName name);

    boolean existsById(ProfitCategoryId id);

    ProfitCategory save(ProfitCategory category);

    Optional<ProfitCategory> findById(ProfitCategoryId id);

    List<ProfitCategory> findAll();
}
