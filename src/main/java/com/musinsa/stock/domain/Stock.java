package com.musinsa.stock.domain;

import com.musinsa.stock.exception.StockQuantityValidException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Getter
@Setter
@Entity
@IdClass(StockId.class)
public class Stock {
    @Id
    private String productName;
    @Id
    private String productOptionName;
    private int productQuantity;
    public void increase(int quantity) {
        productQuantity += quantity;
    }
    public void decrease(int quantity) {
        if((productQuantity - quantity) < 0)
            throw new StockQuantityValidException("재고수량은 음수가 될 수 없습니다.(현재 재고 0)");

        productQuantity -= quantity;
    }
}
