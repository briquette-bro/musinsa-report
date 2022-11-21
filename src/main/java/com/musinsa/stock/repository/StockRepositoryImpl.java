package com.musinsa.stock.repository;

import com.musinsa.stock.domain.QStock;
import com.musinsa.stock.domain.Stock;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StockRepositoryImpl implements StockRepository {
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<Stock> findStockByProductName(String productName) {
        QStock stock = QStock.stock;

        return jpaQueryFactory.select(stock).from(stock)
                .where(stock.productName.eq(productName)).fetch();
    }

    @Override
    public Stock findStockByProductNameAndProductOptionName(String productName, String productOptionName, boolean isLock) {
        QStock stock = QStock.stock;
        return jpaQueryFactory.select(stock).from(stock)
                .where(stock.productName.eq(productName).and(stock.productOptionName.eq(productOptionName)))
                // 동시성을 제어하기 위해 pessimistic lock 설정
                .setLockMode(isLock ? LockModeType.PESSIMISTIC_WRITE: LockModeType.NONE).fetchOne();
    }
}
