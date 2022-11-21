package com.musinsa.stock.model.mapper;

import com.musinsa.stock.domain.Stock;
import com.musinsa.stock.model.dto.ResponseDto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * entity <-> dto converter
 */
public class StockModelMapper {
    public static ResponseDto.StockDto toDto(Stock stock) {
        return ResponseDto.StockDto.builder()
                .productName(stock.getProductName())
                .productOptions(Arrays.asList(toProductOptions(stock))).build();
    }
    public static ResponseDto.StockDto toDto(List<Stock> stocks) {
        ResponseDto.StockDto stockDto = new ResponseDto.StockDto(stocks.get(0).getProductName());
        List<ResponseDto.ProductOption> options = stocks.stream().map(s -> toProductOptions(s)).collect(Collectors.toList());
        stockDto.setProductOptions(options);
        return stockDto;
    }
    public static ResponseDto.ProductOption toProductOptions(Stock stock) {
        return ResponseDto.ProductOption.builder()
                .name(stock.getProductOptionName())
                .quantity(stock.getProductQuantity()).build();
    }
}
