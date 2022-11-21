package com.musinsa.stock.controller;

import com.musinsa.stock.model.ApiResponse;
import com.musinsa.stock.model.dto.RequestDto;
import com.musinsa.stock.model.dto.ResponseDto;
import com.musinsa.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product-stock")
public class StockController {
    private final StockService stockService;

    @GetMapping("{productName}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<ResponseDto.StockDto>> stock(@PathVariable String productName,
                                                                   @RequestParam(required = false) String productOptionName) {
        ResponseDto.StockDto stock = null;
        if(StringUtils.hasText(productOptionName))
            stock = stockService.loadStockByProductNameAndOptionName(productName, productOptionName);
        else
            stock = stockService.loadStockByProductName(productName);

        ApiResponse body = ApiResponse.builder().payload(stock).build();
        return ResponseEntity.ok(body);
    }

    @PutMapping("{productName}/quantity")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> updateQuantity(@PathVariable String productName,
                                               @RequestBody @Valid RequestDto.UpdateStockCommand command) {
        command.setProductName(productName);
        stockService.updateQuantity(command);

        return ResponseEntity.noContent().build();
    }
}
