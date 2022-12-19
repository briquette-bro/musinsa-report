
## git clone

```bash
git clone https://github.com/briquette-bro/musinsa-report.git

```

## run application

```bash
cd musinsa-report
./mvnw spring-boot:run
```

## API 정보

### 공통

- accept-type : application/json
- content-type : application/json
- Response

| 이름 | 타입 | 설명 | 필수 |
| --- | --- | --- | --- |
| message | String | 결과 메세지(오류일 경우에만 출력) | O |
| payload | Node | 본문, 실제 응답 구조 | O |

### 재고 현황 조회(상품정보 조회)

```bash
curl --location --request GET 'http://localhost:8080/product-stock/{productName}'
```

- Parameter

| 이름 | 타입 | 설명 | 파라미터 위치 | 필수 |
| --- | --- | --- | --- | --- |
| productName | String | 상품명 | uri path | O |
- Response

```json
{
    "message": null,
    "payload": { 
        "productName": "prd-a",
        "productOptions": [
            {
                "name": "opt-aa",
                "quantity": 0
            },
            {
                "name": "opt-ab",
                "quantity": 100
            }, {...}, ...
        ],
        "totalQuantity": 100
    }
}
```

| 이름 | 타입 | 설명 |
| --- | --- | --- |
| productName | String | 상품명 |
| totalQuantity | Integer | 총 상품 수량 |
| productOptions.name | String | 옵션명 |
| productOptions.quantity | Integer | 옵션에 해당하는 수량 |

### 재고 현황 조회(상품정보 조회-옵션명 포함)

```bash
curl --location --request GET 'http://localhost:8080/product-stock/{productName}?productOptionName=${옵션명}'
```

- Parameter

| 이름 | 타입 | 설명 | 파라미터 위치 | 필수 |
| --- | --- | --- | --- | --- |
| productName | String | 상품명 | uri path | O |
| productOptionName | String | 상품 옵션명 | parameter | O |
- Response

```json
{
    "message": null,
    "payload": { 
        "productName": "prd-a",
        "productOptions": [
            {
                "name": "opt-aa",
                "quantity": 0
            }
        ],
        "totalQuantity": 0
    }
}
```

| 이름 | 타입 | 설명 |
| --- | --- | --- |
| productName | String | 상품명 |
| totalQuantity | Integer | 총 상품 수량 |
| productOptions.name | String | 옵션명 |
| productOptions.quantity | Integer | 옵션에 해당하는 수량 |

### 재고 증가/차감 처리

```bash
curl --location --request PUT 'http://localhost:8080/product-stock/{productName}/quantity' \
--header 'Content-Type: application/json' \
--data-raw '{
    "productOptionName": ${상품명},
    "quantity": ${수량},
    "command": ${증가/차감 명령}
}'
```

- Parameter

| 이름 | 타입 | 설명 | 파라미터 위치 | 필수 |
| --- | --- | --- | --- | --- |
| productName | String | 상품명 | uri path | O |
| productOptionName | String | 상품 옵션명 | body | O |
| quantity | Integer | 수량 (양수, 1 이상만 가능) | body | O |
| command | String | 증가/차감 명령(증가: INCREASE, 차감: DECREASE) | body | O |
- Response : 없음(http status 204)
