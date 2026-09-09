package com.AjAkrampoor.Academy.expenses.infrastruture.repository;

import com.AjAkrampoor.Academy.expenses.domain.model.ExpenseCategoryId;
import com.AjAkrampoor.Academy.expenses.domain.model.ExpenseCategoryName;
import com.AjAkrampoor.Academy.expenses.infrastruture.persistence.ExpenseCategoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseCategoryJpaRepository extends JpaRepository<ExpenseCategoryJpaEntity, ExpenseCategoryId> {
    boolean existsByName(ExpenseCategoryName name);
}
