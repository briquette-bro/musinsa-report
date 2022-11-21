create table if not exists stock (
    product_name varchar(10) not null comment '상품명',
    product_option_name varchar(10) not null comment '옵션명',
    product_quantity int not null default 0 comment '수량',
    constraint stock_pk
        primary key(product_name, product_option_name)
);
comment on table stock is '상품 수량 정보';