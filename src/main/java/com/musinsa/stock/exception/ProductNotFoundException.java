package com.musinsa.stock.exception;

/**
 * 상품 정보 관련 exception
 * - 상품이 존재하지 않을 경우 상황에 따라 처리
 */
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
