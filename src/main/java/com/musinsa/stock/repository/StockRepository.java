package com.musinsa.stock.repository;

import com.musinsa.stock.domain.Stock;

import java.util.List;

public interface StockRepository {
    /**
     * 재고조회 - 상품명
     * @param productName 상품명
     * @return
     */
    public List<Stock> findStockByProductName(String productName);

    /**
     * 재고조회 - 상품명 & 상품 옵션명
     * @param productName 상품명
     * @param productOptionName 상품 옵션명
     * @param isLock pessimistic lock 여부
     * @return
     */
    public Stock findStockByProductNameAndProductOptionName(String productName, String productOptionName, boolean isLock);
}
