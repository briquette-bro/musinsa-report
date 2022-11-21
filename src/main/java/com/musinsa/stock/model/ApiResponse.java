package com.musinsa.stock.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * api response
 * @param <T>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    // 메시지
    private String message;
    // 응답 본문
    private T payload;
}
