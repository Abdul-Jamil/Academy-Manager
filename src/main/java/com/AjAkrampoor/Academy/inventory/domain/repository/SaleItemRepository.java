package com.AjAkrampoor.Academy.inventory.domain.repository;

import com.AjAkrampoor.Academy.inventory.domain.model.SaleId;
import com.AjAkrampoor.Academy.inventory.domain.model.SaleItem;

import java.util.List;

public interface SaleItemRepository {
    List<SaleItem> findBySaleId(SaleId saleId);

    SaleItem save(SaleItem saleItem);

    List<SaleItem> saveAll(List<SaleItem> saleItems);

    void deleteBySaleId(SaleId saleId); // optional for cancelling, if we want to remove items
}
