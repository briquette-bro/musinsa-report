package com.musinsa.stock.service;

import com.musinsa.stock.model.dto.RequestDto;
import com.musinsa.stock.model.dto.ResponseDto;

/**
 * stock service interface
 */
public interface StockService {
    /**
     * 상품 재고 조회
     * @param productName 상품명
     * @return
     */
    public ResponseDto.StockDto loadStockByProductName(String productName);

    /**
     * 상품 재고 조회
     * @param productName 상품명
     * @param productOptionName 상품 옵션명
     * @return
     */
    public ResponseDto.StockDto loadStockByProductNameAndOptionName(String productName, String productOptionName);

    /**
     * 상품 재고 수량 업데이트
     * @param command 요청 parameter
     */
    public void updateQuantity(RequestDto.UpdateStockCommand command);
}
