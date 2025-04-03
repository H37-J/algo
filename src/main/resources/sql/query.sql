CREATE TABLE TEST
(
    TEST1 varchar(30),
    TEST2 varchar(30),
    TEST3 varchar(30)
)


CREATE TABLE LANG
(
    STR_ID   VARCHAR(30),
    STR_TEXT VARCHAR(30)
);

INSERT INTO TEST(TEST1, TEST2, TEST3)
VALUES ('TEST1', 'TEST2', 'TEST3')

INSERT INTO LANG(STR_ID, STR_TEXT)
VALUES ('TEST4', 'TEST-4')

UPDATE LANG
SET STR_TEXT = CASE
                   WHEN STR_ID = (SELECT TEST1 FROM TEST) THEN 'TEST-111'
                   WHEN STR_ID = (SELECT TEST2 FROM TEST) THEN 'TEST-222'
                   WHEN STR_ID = (SELECT TEST3 FROM TEST) THEN 'TEST-333'
                   ELSE STR_TEXT
    END
WHERE STR_ID IN (SELECT TEST1
                 FROM TEST
                 UNION
                 SELECT TEST2
                 FROM TEST
                 UNION
                 SELECT TEST3
                 FROM TEST)


CREATE TABLE products
(
    product_id   INT PRIMARY KEY,
    product_name VARCHAR(100),
    price        INT,
    stock        INT
);

-- 데이터 소스 테이블 생성
CREATE TABLE new_products
(
    product_id   INT PRIMARY KEY,
    product_name VARCHAR(100),
    price        INT,
    stock        INT
);

-- 초기 데이터 삽입
INSERT INTO products
VALUES (1, '노트북', 1200000, 10);
INSERT INTO products
VALUES (2, '스마트폰', 800000, 15);
INSERT INTO products
VALUES (3, '태블릿', 600000, 20);

INSERT INTO new_products
VALUES (2, '스마트폰', 750000, 25); -- 가격 변경, 재고 증가
INSERT INTO new_products
VALUES (3, '태블릿', 600000, 5); -- 재고 감소
INSERT INTO new_products
VALUES (4, '스마트워치', 300000, 30); -- 새 제품


MERGE INTO products p
USING new_products np
ON (p.product_id = np.product_id)
WHEN MATCHED THEN
    UPDATE
    SET p.product_name = np.product_name,
        p.price        = np.price,
        p.stock        = np.stock
WHEN NOT MATCHED THEN
    INSERT (product_id, product_name, price, stock)
    VALUES (np.product_id, np.product_name, np.price, np.stock);