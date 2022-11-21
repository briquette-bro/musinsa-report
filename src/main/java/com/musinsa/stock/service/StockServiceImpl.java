package com.musinsa.stock.service;

import com.musinsa.stock.domain.Stock;
import com.musinsa.stock.exception.ProductNotFoundException;
import com.musinsa.stock.model.dto.RequestDto;
import com.musinsa.stock.model.dto.ResponseDto;
import com.musinsa.stock.model.mapper.StockModelMapper;
import com.musinsa.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Stock service 구현
 */
@RequiredArgsConstructor
@Service
public class StockServiceImpl implements StockService {
    private final StockRepository stockRepository;
    @Transactional(readOnly = true)
    @Override
    public ResponseDto.StockDto loadStockByProductName(String productName) {
        List<Stock> stocks = stockRepository.findStockByProductName(productName);
        if(CollectionUtils.isEmpty(stocks))
            throw new ProductNotFoundException(String.format("%s 상품이 존재하지 않습니다.", productName));

        return StockModelMapper.toDto(stocks);
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseDto.StockDto loadStockByProductNameAndOptionName(String productName, String productOptionName) {
        Stock stock = stockRepository.findStockByProductNameAndProductOptionName(productName, productOptionName, false);
        if(stock == null)
            throw new ProductNotFoundException(String.format("%s(%s) 상품이 존재하지 않습니다.", productName, productOptionName));

        return StockModelMapper.toDto(stock);
    }

    @Transactional(readOnly = false)
    @Override
    public void updateQuantity(RequestDto.UpdateStockCommand command) {
        Stock stock = stockRepository.findStockByProductNameAndProductOptionName(command.getProductName()
                , command.getProductOptionName(), true);
        if(stock == null)
            throw new ProductNotFoundException(String.format("%s(%s) 상품이 존재하지 않습니다.", command.getProductName(), command.getProductOptionName()));
        int quantity = command.getQuantity();
        switch(command.getCommand()) {
            case INCREASE:
                stock.increase(quantity);
                break;
            case DECREASE:
                stock.decrease(quantity);
                break;
        }
    }
}
