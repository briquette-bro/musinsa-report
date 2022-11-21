package com.musinsa.stock.repository;

import com.musinsa.stock.domain.Stock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StockRepositoryTest {
    @Autowired
    private StockRepository stockRepository;

    @DisplayName("재고 조회 - 상품명")
    @Test
    void findStockByProductName() {
        int expected = 2;
        String productName = "prd-a";
        List<Stock> stocks = stockRepository.findStockByProductName(productName);
        assertEquals(expected, stocks.size());
    }

    @DisplayName("재고 조회 - 상품명 & 상품 옵션명")
    @Test
    void findStockByProductNameAndProductOptionName() {
        int quantity = 0;
        String productName = "prd-a";
        String productOptionName = "opt-ab";

        Stock stock = stockRepository.findStockByProductNameAndProductOptionName(productName, productOptionName, false);

        assertEquals(productName, stock.getProductName());
        assertEquals(productOptionName, stock.getProductOptionName());
        assertEquals(quantity, stock.getProductQuantity());
    }
}