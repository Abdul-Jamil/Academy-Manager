package com.AjAkrampoor.Academy.inventory.domain.repository;

import com.AjAkrampoor.Academy.inventory.domain.model.PurchaseId;
import com.AjAkrampoor.Academy.inventory.domain.model.PurchaseItem;

import java.util.List;

public interface PurchaseItemRepository {
    List<PurchaseItem> findByPurchaseId(PurchaseId purchaseId);

    PurchaseItem save(PurchaseItem purchaseItem);

    List<PurchaseItem> saveAll(List<PurchaseItem> purchaseItems);
}
