package com.musinsa.stock.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class RequestDto {
    @Data
    @NoArgsConstructor
    public static class UpdateStockCommand {
        @NotBlank(message = "상품명은 필수 입니다.")
        private String productName;
        @NotBlank(message = "상품 옵션명은 필수 입니다.")
        private String productOptionName;
        @Min(value = 0, message = "수량은 0보다 커야 합니다.")
        private int quantity;
        @NotNull(message = "command는 INCREASE | DECREASE만 사용 가능 합니다.")
        private QuantityCommand command;
    }

    public enum QuantityCommand {
        INCREASE, DECREASE;
        @JsonCreator
        public static QuantityCommand getCodeByValue(final String value) {
            for(QuantityCommand command: QuantityCommand.values()) {
                if(command.name() == value)
                    return command;
            }
            return null;
        }
    }
}
