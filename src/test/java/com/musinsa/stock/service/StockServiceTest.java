package com.musinsa.stock.service;

import com.musinsa.stock.model.dto.RequestDto;
import com.musinsa.stock.model.dto.ResponseDto;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class StockServiceTest {
    private final Logger logger = LoggerFactory.getLogger(StockServiceTest.class);
    @Autowired
    private StockService stockService;
    private final String productName = "prd-a";
    private final String productOptionName = "opt-aa";
    private final int quantity = 10;
    private ResponseDto.StockDto currentStock = null;
    @BeforeEach
    void initStockQuantity() {
        RequestDto.UpdateStockCommand command = new RequestDto.UpdateStockCommand();
        command.setProductName(productName);
        command.setProductOptionName(productOptionName);
        command.setQuantity(quantity);
        command.setCommand(RequestDto.QuantityCommand.INCREASE);
        stockService.updateQuantity(command);

        currentStock = stockService.loadStockByProductName(productName);
    }

    @DisplayName("상품 재고 조회 - 상품명")
    @Test
    @Order(1)
    void loadStockByProductName() {
        ResponseDto.StockDto stock = stockService.loadStockByProductName(productName);

        assertEquals(currentStock.totalQuantity(), stock.totalQuantity());
    }

    @DisplayName("상품 제고 조회 - 상품명 + 상품 옵션명")
    @Test
    @Order(2)
    void loadStockByProductNameAndOptionName() {
        ResponseDto.StockDto stock = stockService.loadStockByProductNameAndOptionName(productName, productOptionName);

        assertEquals(currentStock.totalQuantity(), stock.totalQuantity());
        assertEquals(productOptionName, stock.getProductOptions().stream().filter(o -> o.getName().equals(productOptionName)).findFirst().get().getName());
    }

    @DisplayName("상품 재고 수량 업데이트")
    @Test
    void updateQuantity() throws InterruptedException{
        int executeCount = 100;
        String productName = "prd-a";
        String productOptionName = "opt-aa";

        RequestDto.UpdateStockCommand updateCommand = new RequestDto.UpdateStockCommand();
        updateCommand.setProductName(productName);
        updateCommand.setProductOptionName(productOptionName);
        updateCommand.setQuantity(10);
        updateCommand.setCommand(RequestDto.QuantityCommand.INCREASE);
        // 재고 증가/감소 시 동시성 제어를 테스트 하기 위해 multi thread 환경 구성
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        CountDownLatch countDownLatch = new CountDownLatch(executeCount);
        for(int i = 0; i < executeCount; i++) {
            executorService.submit(() -> {
                try {
                    stockService.updateQuantity(updateCommand);
                } catch (ObjectOptimisticLockingFailureException oe) {
                    oe.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();

        ResponseDto.StockDto stock = stockService.loadStockByProductNameAndOptionName(productName, productOptionName);
        assertEquals((updateCommand.getQuantity() * executeCount) + currentStock.totalQuantity(), stock.totalQuantity());
    }
}