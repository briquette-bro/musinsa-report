package com.musinsa.stock.exception.handler;

import com.musinsa.stock.exception.ProductNotFoundException;
import com.musinsa.stock.exception.StockQuantityValidException;
import com.musinsa.stock.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 * Exception handler
 * 전역에서 사용
 */
@RestControllerAdvice
public class ApplicationExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse> handleConstraintViolationException(MethodArgumentNotValidException methodArgumentNotValidException) {
        String message = methodArgumentNotValidException.getBindingResult().getAllErrors()
                .stream().findFirst().get().getDefaultMessage();
        ApiResponse body = ApiResponse.builder().message(message).build();
        return ResponseEntity.badRequest().body(body);
    }
    @ExceptionHandler(StockQuantityValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse> handleStockQuantityValidException(StockQuantityValidException stockQuantityValidException) {
        String message = stockQuantityValidException.getMessage();
        ApiResponse body = ApiResponse.builder().message(message).build();
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse> handleProductNotFoundException(ProductNotFoundException productNotFoundException) {
        String message = productNotFoundException.getMessage();
        ApiResponse body = ApiResponse.builder().message(message).build();
        return ResponseEntity.badRequest().body(body);
    }
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiResponse> handleException(Exception exception) {
        String message = exception.getMessage();
        ApiResponse body = ApiResponse.builder().message(message).build();
        return ResponseEntity.internalServerError().body(body);
    }
}
