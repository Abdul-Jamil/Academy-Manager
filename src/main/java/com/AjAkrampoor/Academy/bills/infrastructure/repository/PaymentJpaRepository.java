package com.AjAkrampoor.Academy.bills.infrastructure.repository;

import com.AjAkrampoor.Academy.bills.domain.model.BillId;
import com.AjAkrampoor.Academy.bills.domain.model.PaymentId;
import com.AjAkrampoor.Academy.bills.infrastructure.persistence.PaymentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PaymentJpaRepository extends JpaRepository<PaymentJpaEntity, PaymentId>, JpaSpecificationExecutor<PaymentJpaEntity> {
    @Query("SELECT p.billId, SUM(p.amount.amount) " +
            "FROM PaymentJpaEntity p " +
            "WHERE p.billId IN :billIds AND p.status = 'PAID' " +
            "GROUP BY p.billId")
    List<Object[]> sumPaidAmountByBillIds(@Param("billIds") Set<BillId> billIds);
}
