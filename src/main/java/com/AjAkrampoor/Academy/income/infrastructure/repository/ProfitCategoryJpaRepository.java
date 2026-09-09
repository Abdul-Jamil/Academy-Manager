package com.AjAkrampoor.Academy.income.infrastructure.repository;

import com.AjAkrampoor.Academy.income.domain.model.ProfitCategoryId;
import com.AjAkrampoor.Academy.income.domain.model.ProfitCategoryName;
import com.AjAkrampoor.Academy.income.infrastructure.persistence.ProfitCategoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfitCategoryJpaRepository extends JpaRepository<ProfitCategoryJpaEntity, ProfitCategoryId> {
    boolean existsByCategoryName(ProfitCategoryName categoryName);
}
