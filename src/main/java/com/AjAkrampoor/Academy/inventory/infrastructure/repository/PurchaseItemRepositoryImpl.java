package com.AjAkrampoor.Academy.inventory.infrastructure.repository;

import com.AjAkrampoor.Academy.inventory.domain.model.PurchaseId;
import com.AjAkrampoor.Academy.inventory.domain.model.PurchaseItem;
import com.AjAkrampoor.Academy.inventory.domain.repository.PurchaseItemRepository;
import com.AjAkrampoor.Academy.inventory.infrastructure.persistence.PurchaseItemJpaEntity;
import com.AjAkrampoor.Academy.inventory.presentation.mapper.PurchaseItemMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PurchaseItemRepositoryImpl implements PurchaseItemRepository {

    private final PurchaseItemJpaRepository jpaRepository;

    public PurchaseItemRepositoryImpl(PurchaseItemJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<PurchaseItem> findByPurchaseId(PurchaseId purchaseId) {
        return jpaRepository.findByPurchaseId(purchaseId).stream()
                .map(PurchaseItemMapper::toDomain)
                .toList();
    }

    @Override
    public PurchaseItem save(PurchaseItem purchaseItem) {
        PurchaseItemJpaEntity entity = PurchaseItemMapper.toEntity(purchaseItem);
        PurchaseItemJpaEntity saved = jpaRepository.save(entity);
        return PurchaseItemMapper.toDomain(saved);
    }

    @Override
    public List<PurchaseItem> saveAll(List<PurchaseItem> purchaseItems) {
        List<PurchaseItemJpaEntity> entities = purchaseItems.stream()
                .map(PurchaseItemMapper::toEntity)
                .toList();
        List<PurchaseItemJpaEntity> saved = jpaRepository.saveAll(entities);
        return saved.stream()
                .map(PurchaseItemMapper::toDomain)
                .toList();
    }
}
