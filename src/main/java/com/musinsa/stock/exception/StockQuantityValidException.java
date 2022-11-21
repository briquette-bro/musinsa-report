package com.musinsa.stock.exception;

/**
 * 상품 수량 검증 exception
 * 상품 수량 정책에 따라 유요하지 않을 경우 발생
 */
public class StockQuantityValidException extends RuntimeException{
    public StockQuantityValidException(String message) {
        super(message);
    }
}
