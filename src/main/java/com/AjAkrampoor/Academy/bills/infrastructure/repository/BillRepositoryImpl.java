package com.AjAkrampoor.Academy.bills.infrastructure.repository;

import com.AjAkrampoor.Academy.bills.application.dto.BillFilter;
import com.AjAkrampoor.Academy.bills.domain.model.Bill;
import com.AjAkrampoor.Academy.bills.domain.model.BillId;
import com.AjAkrampoor.Academy.bills.domain.repository.BillRepository;
import com.AjAkrampoor.Academy.bills.infrastructure.persistence.BillJpaEntity;
import com.AjAkrampoor.Academy.bills.presentation.mapper.BillMapper;
import com.AjAkrampoor.Academy.enrollments.domain.model.EnrollmentId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class BillRepositoryImpl implements BillRepository {

    private final BillJpaRepository repository;

    public BillRepositoryImpl(BillJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Bill> findById(BillId id) {
        return repository.findById(id).map(BillMapper::toDomain);
    }

    @Override
    public Bill save(Bill bill) {
        BillJpaEntity save = repository.save(BillMapper.toEntity(bill));
        return BillMapper.toDomain(save);
    }

    @Override
    public boolean existsById(BillId id) {
        return repository.existsById(id);
    }

    @Override
    public Page<Bill> findAll(Pageable pageable, BillFilter billFilter) {
        List<Specification<BillJpaEntity>> specifications = BillSpecifications.getSpecifications(billFilter);

        Specification<BillJpaEntity> specs = specifications.stream()
                .reduce(Specification::and)
                .orElse(null);

        return repository.findAll(specs, pageable).map(BillMapper::toDomain);
    }

    @Override
    public List<Bill> findAllById(Set<BillId> ids) {
        return repository.findAllById(ids).stream()
                .map(BillMapper::toDomain)
                .toList();
    }

    @Override
    public List<Bill> findByEnrollmentId(EnrollmentId enrollmentId) {
        return repository.findByEnrollmentId(enrollmentId).stream()
                .map(BillMapper::toDomain)
                .toList();
    }
}
