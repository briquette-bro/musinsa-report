package com.musinsa.stock.model.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class ResponseDto {
    @Data
    @SuperBuilder
    public static class StockDto {
        private String productName;

        private List<ProductOption> productOptions;
        public StockDto(String productName) {
            this.productName = productName;
        }
        @JsonGetter
        public int totalQuantity() {
            if(CollectionUtils.isEmpty(productOptions)) return 0;

            return productOptions.stream().mapToInt(o -> o.getQuantity()).sum();
        }
    }
    @Data
    @SuperBuilder
    public static class ProductOption {
        private String name;
        private int quantity;
    }
}
