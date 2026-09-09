package com.AjAkrampoor.Academy.inventory.infrastructure.repository;

import com.AjAkrampoor.Academy.inventory.domain.model.SaleId;
import com.AjAkrampoor.Academy.inventory.domain.model.SaleItem;
import com.AjAkrampoor.Academy.inventory.domain.repository.SaleItemRepository;
import com.AjAkrampoor.Academy.inventory.infrastructure.persistence.SaleItemJpaEntity;
import com.AjAkrampoor.Academy.inventory.presentation.mapper.SaleItemMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SaleItemRepositoryImpl implements SaleItemRepository {

    private final SaleItemJpaRepository jpaRepository;

    public SaleItemRepositoryImpl(SaleItemJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<SaleItem> findBySaleId(SaleId saleId) {
        return jpaRepository.findBySaleId(saleId).stream()
                .map(SaleItemMapper::toDomain)
                .toList();
    }

    @Override
    public SaleItem save(SaleItem saleItem) {
        SaleItemJpaEntity entity = SaleItemMapper.toEntity(saleItem);
        SaleItemJpaEntity saved = jpaRepository.save(entity);
        return SaleItemMapper.toDomain(saved);
    }

    @Override
    public List<SaleItem> saveAll(List<SaleItem> saleItems) {
        List<SaleItemJpaEntity> entities = saleItems.stream()
                .map(SaleItemMapper::toEntity)
                .toList();
        List<SaleItemJpaEntity> saved = jpaRepository.saveAll(entities);
        return saved.stream()
                .map(SaleItemMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteBySaleId(SaleId saleId) {
        jpaRepository.deleteBySaleId(saleId);
    }
}
