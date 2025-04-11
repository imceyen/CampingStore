CREATE TABLE cam_category (                     -- [카테고리 테이블]
    category_no NUMBER PRIMARY KEY,             -- 카테고리 번호
    category_name VARCHAR2(50) NOT NULL         -- 카테고리명
);

CREATE TABLE cam_product (                                  -- [상품 테이블]
    product_no NUMBER PRIMARY KEY,                          -- 상품 번호
    category_no NUMBER REFERENCES cam_category(category_no),-- 카테고리 번호
    product_name VARCHAR2(100) NOT NULL,                    -- 상품명
    input_price NUMBER NOT NULL,                            -- 입고액
    output_price NUMBER NOT NULL,                           -- 출고액
    stock_qty NUMBER DEFAULT 0,                             -- 재고수량
    sold_qty NUMBER DEFAULT 0,                              -- 판매수량
    is_sold_out CHAR(1) DEFAULT 'N'                         -- 품절여부 'Y' or 'N'
);

CREATE TABLE customer (                         -- [고객 테이블]
    customer_no NUMBER PRIMARY KEY,             -- 고객 번호
    customer_id VARCHAR2(30) UNIQUE NOT NULL,   -- 고객 아이디 (아이디 중복 방지)
    password VARCHAR2(100) NOT NULL,            -- 고객 비밀번호
    name VARCHAR2(50) NOT NULL,                 -- 고객 이름
    birth_date DATE,                            -- 고객 생년월일
    gender CHAR(1),                             -- 고객 성별 'M' or 'F'         
    phone VARCHAR2(20),                         -- 고객 전화번호
    address VARCHAR2(200)                       -- 고객 주소
);

CREATE TABLE cart (                                             -- [장바구니 테이블]
    customer_no NUMBER,                                         -- 고객 번호
    product_no NUMBER,                                          -- 상품 번호
    quantity NUMBER DEFAULT 1,                                  -- 상품 수량 (기본 1개)
    PRIMARY KEY (customer_no, product_no),                      -- 같은 상품 중복 방지, 수량만 올라가게 해줌
    FOREIGN KEY (customer_no) REFERENCES customer(customer_no), -- 고객 테이블의 고객 번호와 연결
    FOREIGN KEY (product_no) REFERENCES cam_product(product_no) -- 상품 테이블의 상품 번호와 연결
);

CREATE TABLE orders (                                       -- [주문 테이블]
    order_no NUMBER PRIMARY KEY,                            -- 주문 번호
    customer_no NUMBER REFERENCES customer(customer_no),    -- 고객 번호
    order_date DATE DEFAULT SYSDATE,                        -- 주문 날짜
    order_status VARCHAR2(20) DEFAULT '주문완료'              -- 주문 상태
);

CREATE TABLE order_detail (                                     -- [주문 상세 정보 테이블]
    order_no NUMBER,                                            -- 주문 번호
    product_no NUMBER,                                          -- 상품 번호
    quantity NUMBER NOT NULL,                                   -- 상품 수량
    price NUMBER NOT NULL,                                      -- 구매 가격
    PRIMARY KEY (order_no, product_no),                         -- 상품 중복 방지, 수량만 올라가게 함
    FOREIGN KEY (order_no) REFERENCES orders(order_no),         -- 주문 테이블의 주문 번호와 연결
    FOREIGN KEY (product_no) REFERENCES cam_product(product_no) -- 상품 테이블의 상품 번호와 연결
);

CREATE TABLE rental (
  rental_no NUMBER PRIMARY KEY,               -- 대여 번호
  customer_no NUMBER REFERENCES customer(customer_no), -- 고객 번호
  product_no NUMBER REFERENCES cam_product(product_no), -- 상품 번호
  rental_quantity NUMBER NOT NULL,            -- 대여 수량
  remain_quantity NUMBER NOT NULL,            -- 잔여 수량
  rental_price NUMBER NOT NULL,               -- 총 대여 금액
  rental_date DATE NOT NULL,                  -- 대여 시작일
  return_date DATE NOT NULL,                  -- 반납 예정일
  actual_return_date DATE,                    -- 실제 반납일
  rental_status VARCHAR2(20) DEFAULT '대여중' -- 대여 상태 (대여중, 반납완료 등)
);


CREATE TABLE product_review (                               -- [상품 후기 테이블]
    review_no NUMBER PRIMARY KEY,                           -- 후기 번호
    customer_no NUMBER REFERENCES customer(customer_no),    -- 고객 번호
    product_no NUMBER REFERENCES cam_product(product_no),   -- 상품 번호
    content VARCHAR2(1000),                                 -- 후기 내용
    review_date DATE DEFAULT SYSDATE                        -- 후기 작성 날짜
);

CREATE TABLE product_inquiry (                              -- [상품 문의 테이블]
    inquiry_no NUMBER PRIMARY KEY,                          -- 문의 번호
    customer_no NUMBER REFERENCES customer(customer_no),    -- 고객 번호
    product_no NUMBER REFERENCES cam_product(product_no),   -- 상품 번호
    content VARCHAR2(1000),                                 -- 문의 내용
    inquiry_date DATE DEFAULT SYSDATE,                      -- 문의 작성 날짜
    answer_content VARCHAR2(1000),                          -- 문의 답변 내용
    answer_date DATE                                        -- 문의 답변 작성 날짜
);

CREATE TABLE sales_statistics (         -- [매출 확인 테이블]
    product_no NUMBER PRIMARY KEY,      -- 상품 번호
    total_sales NUMBER,                 -- 매출액
    total_cost NUMBER,                  -- 매입액
    total_profit NUMBER,                -- 순수익
    FOREIGN KEY (product_no) REFERENCES cam_product(product_no) -- 상품 테이블의 상품 번호와 연결
);

CREATE TABLE admin (                     -- [관리자 테이블]
    admin_id VARCHAR2(30) UNIQUE NOT NULL,   -- 관리자 아이디
    password VARCHAR2(100) NOT NULL          -- 관리자 비밀번호
);


INSERT INTO cam_category (category_no, category_name) VALUES (1, '체어');
INSERT INTO cam_category (category_no, category_name) VALUES (2, '테이블');
INSERT INTO cam_category (category_no, category_name) VALUES (3, '코트');
INSERT INTO cam_category (category_no, category_name) VALUES (4, '텐트');

INSERT INTO cam_product (product_no, category_no, product_name, input_price, output_price, stock_qty, sold_qty, is_sold_out) VALUES (1, 1, '체어원(코요테탄)', 50000, 100000, 5, 0, 'N');
INSERT INTO cam_product (product_no, category_no, product_name, input_price, output_price, stock_qty, sold_qty, is_sold_out) VALUES (2, 1, '체어원홈(베이지)', 70000, 140000, 3, 2, 'N');
INSERT INTO cam_product (product_no, category_no, product_name, input_price, output_price, stock_qty, sold_qty, is_sold_out) VALUES (3, 1, '체어원하이백(블랙)', 75000, 150000, 0, 5, 'Y');
INSERT INTO cam_product (product_no, category_no, product_name, input_price, output_price, stock_qty, sold_qty, is_sold_out) VALUES (4, 2, '테이블원(엑스레이 타이거 카모)', 80000, 160000, 5, 0, 'N');
INSERT INTO cam_product (product_no, category_no, product_name, input_price, output_price, stock_qty, sold_qty, is_sold_out) VALUES (5, 2, '택티컬 데이블 미디움(밀리터리 올리브)', 75000, 150000, 2, 3, 'N');
INSERT INTO cam_product (product_no, category_no, product_name, input_price, output_price, stock_qty, sold_qty, is_sold_out) VALUES (6, 2, '오발탑(글래식 월넛)', 70000, 140000, 1, 4, 'N');
INSERT INTO cam_product (product_no, category_no, product_name, input_price, output_price, stock_qty, sold_qty, is_sold_out) VALUES (7, 3, '라이트 코트', 150000, 300000, 5, 0, 'N');
INSERT INTO cam_product (product_no, category_no, product_name, input_price, output_price, stock_qty, sold_qty, is_sold_out) VALUES (8, 3, '코트레그', 50000, 100000, 5, 0, 'N');
INSERT INTO cam_product (product_no, category_no, product_name, input_price, output_price, stock_qty, sold_qty, is_sold_out) VALUES (9, 3, '택티컬 필드 테이블', 125000, 250000, 2, 3, 'N');
INSERT INTO cam_product (product_no, category_no, product_name, input_price, output_price, stock_qty, sold_qty, is_sold_out) VALUES (10, 4, '노나놈4.0', 1250000, 2500000, 4, 1, 'N');
INSERT INTO cam_product (product_no, category_no, product_name, input_price, output_price, stock_qty, sold_qty, is_sold_out) VALUES (11, 4, '알파인돔1.5P', 400000, 800000, 0, 5, 'Y');
INSERT INTO cam_product (product_no, category_no, product_name, input_price, output_price, stock_qty, sold_qty, is_sold_out) VALUES (12, 4, '택티컬노나돔4.0베드룸', 575000, 1150000, 5, 0, 'N');


INSERT INTO customer (customer_no, customer_id, password, name, birth_date, gender, phone, address) VALUES (1, 'camping_lee', 'lee1234!', '김영수', TO_DATE('1990-01-01', 'YYYY-MM-DD'), 'M', '010-1234-5678', '서울시 강남구');
INSERT INTO customer (customer_no, customer_id, password, name, birth_date, gender, phone, address) VALUES (2, 'sunny_park', 'park5678@', '이민지', TO_DATE('1985-12-15', 'YYYY-MM-DD'), 'F', '010-2345-6789', '서울시 송파구');
INSERT INTO customer (customer_no, customer_id, password, name, birth_date, gender, phone, address) VALUES (3, 'cool_han', 'han1995$', '박준형', TO_DATE('1995-04-23', 'YYYY-MM-DD'), 'M', '010-3456-7890', '경기도 수원시');
INSERT INTO customer (customer_no, customer_id, password, name, birth_date, gender, phone, address) VALUES (4, 'jennie_choi', 'choi@2020', '정지은', TO_DATE('1987-08-09', 'YYYY-MM-DD'), 'F', '010-4567-8901', '서울시 마포구');
INSERT INTO customer (customer_no, customer_id, password, name, birth_date, gender, phone, address) VALUES (5, 'zizo_jihoon', 'jihoon99*', '이지훈', TO_DATE('1993-05-30', 'YYYY-MM-DD'), 'M', '010-5678-9012', '서울시 용산구');
INSERT INTO customer (customer_no, customer_id, password, name, birth_date, gender, phone, address) VALUES (6, 'soccer_hong', 'hong1234@', '홍석민', TO_DATE('1990-12-12', 'YYYY-MM-DD'), 'M', '010-6789-0123', '부산시 해운대구');
INSERT INTO customer (customer_no, customer_id, password, name, birth_date, gender, phone, address) VALUES (7, 'star_kim', 'kim5678#', '김서윤', TO_DATE('1998-02-02', 'YYYY-MM-DD'), 'F', '010-7890-1234', '서울시 구로구');
INSERT INTO customer (customer_no, customer_id, password, name, birth_date, gender, phone, address) VALUES (8, 'happy_kimmy', 'kimmy2020!', '김지혜', TO_DATE('1997-06-15', 'YYYY-MM-DD'), 'F', '010-8901-2345', '서울시 동대문구');
INSERT INTO customer (customer_no, customer_id, password, name, birth_date, gender, phone, address) VALUES (9, 'sky_hanmin', 'hanmin@99', '한재민', TO_DATE('1991-03-01', 'YYYY-MM-DD'), 'M', '010-9012-3456', '경기도 성남시');
INSERT INTO customer (customer_no, customer_id, password, name, birth_date, gender, phone, address) VALUES (10, 'cherry_choi', 'choi1992@', '최지영', TO_DATE('1992-10-17', 'YYYY-MM-DD'), 'F', '010-0123-4567', '서울시 서초구');
INSERT INTO customer (customer_no, customer_id, password, name, birth_date, gender, phone, address) VALUES (11, 'queen_sujeong', 'sujeong@88', '박수정', TO_DATE('1989-03-04', 'YYYY-MM-DD'), 'F', '010-2345-6789', '경기도 안양시');
INSERT INTO customer (customer_no, customer_id, password, name, birth_date, gender, phone, address) VALUES (12, 'joon_jeonghun', 'jeonghun1996', '홍정훈', TO_DATE('1996-08-06', 'YYYY-MM-DD'), 'M', '010-3456-7890', '서울시 강북구');
INSERT INTO customer (customer_no, customer_id, password, name, birth_date, gender, phone, address) VALUES (13, 'blue_yoon', 'yoon1234@', '윤미정', TO_DATE('1983-11-21', 'YYYY-MM-DD'), 'F', '010-4567-8901', '서울시 금천구');
INSERT INTO customer (customer_no, customer_id, password, name, birth_date, gender, phone, address) VALUES (14, 'taehun_kim', 'kimtaehun@', '김태훈', TO_DATE('1994-07-14', 'YYYY-MM-DD'), 'M', '010-5678-9012', '서울시 성동구');
INSERT INTO customer (customer_no, customer_id, password, name, birth_date, gender, phone, address) VALUES (15, 'minah_jeong', 'jeong1992*', '정민아', TO_DATE('1992-01-25', 'YYYY-MM-DD'), 'F', '010-6789-0123', '인천시 연수구');
INSERT INTO customer (customer_no, customer_id, password, name, birth_date, gender, phone, address) VALUES (16, 'hyunsoo_lee', 'hyunsoo!77', '이현수', TO_DATE('1997-11-07', 'YYYY-MM-DD'), 'M', '010-7890-1234', '경기도 화성시');
INSERT INTO customer (customer_no, customer_id, password, name, birth_date, gender, phone, address) VALUES (17, 'dohyun_kim', 'kim1995#', '김도현', TO_DATE('1995-06-20', 'YYYY-MM-DD'), 'M', '010-8901-2345', '서울시 양천구');
INSERT INTO customer (customer_no, customer_id, password, name, birth_date, gender, phone, address) VALUES (18, 'jihoon_choi', 'choi1234*', '최지훈', TO_DATE('1999-10-30', 'YYYY-MM-DD'), 'M', '010-9012-3456', '서울시 동작구');
INSERT INTO customer (customer_no, customer_id, password, name, birth_date, gender, phone, address) VALUES (19, 'seungmin_oh', 'oh1992@', '오승민', TO_DATE('1987-12-18', 'YYYY-MM-DD'), 'M', '010-0123-4567', '서울시 강서구');
INSERT INTO customer (customer_no, customer_id, password, name, birth_date, gender, phone, address) VALUES (20, 'yuseong_yoon', 'yuseong2020', '윤석영', TO_DATE('1998-04-12', 'YYYY-MM-DD'), 'M', '010-1234-5678', '대전광역시 유성구');
INSERT INTO customer (customer_no, customer_id, password, name, birth_date, gender, phone, address) VALUES (21, 'soyeon_lee', 'soyeon@89', '이소연', TO_DATE('1986-12-11', 'YYYY-MM-DD'), 'F', '010-2345-6789', '서울시 중랑구');
INSERT INTO customer (customer_no, customer_id, password, name, birth_date, gender, phone, address) VALUES (22, 'hye_jeong', 'jeong1992!', '장혜영', TO_DATE('1992-09-07', 'YYYY-MM-DD'), 'F', '010-3456-7890', '부산시 기장군');
INSERT INTO customer (customer_no, customer_id, password, name, birth_date, gender, phone, address) VALUES (23, 'jongsoo_kim', 'jongsoo@95', '김정수', TO_DATE('1993-05-10', 'YYYY-MM-DD'), 'M', '010-4567-8901', '서울시 강남구');
INSERT INTO customer (customer_no, customer_id, password, name, birth_date, gender, phone, address) VALUES (24, 'sora_shin', 'shin@1999', '신유미', TO_DATE('1999-08-08', 'YYYY-MM-DD'), 'F', '010-5678-9012', '경기도 평택시');
INSERT INTO customer (customer_no, customer_id, password, name, birth_date, gender, phone, address) VALUES (25, 'jung_hyun', 'hyun@1991', '윤정훈', TO_DATE('1991-10-22', 'YYYY-MM-DD'), 'M', '010-6789-0123', '서울시 서대문구');
INSERT INTO customer (customer_no, customer_id, password, name, birth_date, gender, phone, address) VALUES (26, 'sangwoo_kim', 'kim2020@', '김상우', TO_DATE('1994-02-14', 'YYYY-MM-DD'), 'M', '010-7890-1234', '경기도 고양시');
INSERT INTO customer (customer_no, customer_id, password, name, birth_date, gender, phone, address) VALUES (27, 'sora_jeong', 'jeong1998@', '정소라', TO_DATE('1998-04-25', 'YYYY-MM-DD'), 'F', '010-8901-2345', '서울시 송파구');
INSERT INTO customer (customer_no, customer_id, password, name, birth_date, gender, phone, address) VALUES (28, 'jiyoung_park', 'park1997*', '박지영', TO_DATE('1997-05-18', 'YYYY-MM-DD'), 'F', '010-9012-3456', '서울시 동대문구');
INSERT INTO customer (customer_no, customer_id, password, name, birth_date, gender, phone, address) VALUES (29, 'seonghoon_choi', 'choi1995#', '최성훈', TO_DATE('1995-06-01', 'YYYY-MM-DD'), 'M', '010-0123-4567', '서울시 강북구');
INSERT INTO customer (customer_no, customer_id, password, name, birth_date, gender, phone, address) VALUES (30, 'gwangho_lee', 'lee1993$', '이광호', TO_DATE('1993-12-10', 'YYYY-MM-DD'), 'M', '010-1234-5678', '서울시 영등포구');

INSERT INTO orders (order_no, customer_no, order_date, order_status) VALUES (1, 7, TO_DATE('20250301', 'YYYYMMDD'), '주문완료');
INSERT INTO orders (order_no, customer_no, order_date, order_status) VALUES (2, 2, TO_DATE('20250302', 'YYYYMMDD'), '주문완료');
INSERT INTO orders (order_no, customer_no, order_date, order_status) VALUES (3, 5, TO_DATE('20250302', 'YYYYMMDD'), '주문완료');
INSERT INTO orders (order_no, customer_no, order_date, order_status) VALUES (4, 8, TO_DATE('20250303', 'YYYYMMDD'), '주문완료');
INSERT INTO orders (order_no, customer_no, order_date, order_status) VALUES (5, 4, TO_DATE('20250303', 'YYYYMMDD'), '주문완료');
INSERT INTO orders (order_no, customer_no, order_date, order_status) VALUES (6, 9, TO_DATE('20250304', 'YYYYMMDD'), '주문완료');
INSERT INTO orders (order_no, customer_no, order_date, order_status) VALUES (7, 11, TO_DATE('20250304', 'YYYYMMDD'), '주문완료');
INSERT INTO orders (order_no, customer_no, order_date, order_status) VALUES (8, 1, TO_DATE('20250305', 'YYYYMMDD'), '주문완료');
INSERT INTO orders (order_no, customer_no, order_date, order_status) VALUES (9, 3, TO_DATE('20250305', 'YYYYMMDD'), '주문완료');
INSERT INTO orders (order_no, customer_no, order_date, order_status) VALUES (10, 6, TO_DATE('20250306', 'YYYYMMDD'), '주문완료');
INSERT INTO orders (order_no, customer_no, order_date, order_status) VALUES (11, 10, TO_DATE('20250306', 'YYYYMMDD'), '주문완료');


INSERT INTO order_detail (order_no, product_no, quantity, price) VALUES (1, 5, 1, 150000);
INSERT INTO order_detail (order_no, product_no, quantity, price) VALUES (1, 6, 2, 140000);
INSERT INTO order_detail (order_no, product_no, quantity, price) VALUES (2, 2, 1, NULL);
INSERT INTO order_detail (order_no, product_no, quantity, price) VALUES (3, 3, 2, 150000);
INSERT INTO order_detail (order_no, product_no, quantity, price) VALUES (3, 9, 1, 250000);
INSERT INTO order_detail (order_no, product_no, quantity, price) VALUES (4, 5, 1, 150000);
INSERT INTO order_detail (order_no, product_no, quantity, price) VALUES (4, 8, 1, 100000);
INSERT INTO order_detail (order_no, product_no, quantity, price) VALUES (5, 6, 1, 140000);
INSERT INTO order_detail (order_no, product_no, quantity, price) VALUES (6, 5, 1, 150000);
INSERT INTO order_detail (order_no, product_no, quantity, price) VALUES (7, 11, 5, 800000);
INSERT INTO order_detail (order_no, product_no, quantity, price) VALUES (8, 9, 1, 250000);
INSERT INTO order_detail (order_no, product_no, quantity, price) VALUES (9, 3, 3, 150000);
INSERT INTO order_detail (order_no, product_no, quantity, price) VALUES (10, 2, 1, 140000);
INSERT INTO order_detail (order_no, product_no, quantity, price) VALUES (10, 10, 1, 2500000);
INSERT INTO order_detail (order_no, product_no, quantity, price) VALUES (11, 6, 1, 140000);
INSERT INTO order_detail (order_no, product_no, quantity, price) VALUES (11, 9, 1, 250000);

INSERT INTO product_review (review_no, customer_no, product_no, content, review_date) VALUES (1, 7, 5, '잘 썼어요~~ 재질이 좋네요.', TO_DATE('2025-03-03', 'YYYY-MM-DD'));
INSERT INTO product_review (review_no, customer_no, product_no, content, review_date) VALUES (2, 2, 2, '편안하고 튼튼한 디자인, 마음에 들어요!', TO_DATE('2025-03-04', 'YYYY-MM-DD'));
INSERT INTO product_review (review_no, customer_no, product_no, content, review_date) VALUES (3, 5, 9, '가격 대비 품질이 아주 만족스럽습니다.', TO_DATE('2025-03-05', 'YYYY-MM-DD'));
INSERT INTO product_review (review_no, customer_no, product_no, content, review_date) VALUES (4, 8, 8, '이 제품 덕분에 캠핑이 더 즐거워졌어요.', TO_DATE('2025-03-06', 'YYYY-MM-DD'));
INSERT INTO product_review (review_no, customer_no, product_no, content, review_date) VALUES (5, 4, 6, '색상도 예쁘고 기능성도 좋네요.', TO_DATE('2025-03-07', 'YYYY-MM-DD'));
INSERT INTO product_review (review_no, customer_no, product_no, content, review_date) VALUES (6, 9, 5, '튼튼하고 쉽게 설치할 수 있어 좋아요.', TO_DATE('2025-03-08', 'YYYY-MM-DD'));
INSERT INTO product_review (review_no, customer_no, product_no, content, review_date) VALUES (7, 11, 11, '완전 만족! 친구들한테 추천할게요.', TO_DATE('2025-03-09', 'YYYY-MM-DD'));
INSERT INTO product_review (review_no, customer_no, product_no, content, review_date) VALUES (8, 1, 9, '캠핑 용품으로 딱이에요! 가볍고 편리해요.', TO_DATE('2025-03-10', 'YYYY-MM-DD'));
INSERT INTO product_review (review_no, customer_no, product_no, content, review_date) VALUES (9, 3, 3, '디자인이 세련되고, 사용하기 편리하네요.', TO_DATE('2025-03-11', 'YYYY-MM-DD'));
INSERT INTO product_review (review_no, customer_no, product_no, content, review_date) VALUES (10, 6, 10, '재구매 의사 100%! 캠핑에 꼭 필요한 아이템!', TO_DATE('2025-03-12', 'YYYY-MM-DD'));

INSERT INTO product_inquiry (inquiry_no, customer_no, product_no, content, inquiry_date, answer_content, answer_date) VALUES (1, 7, 5, '크기가 어떻게 되나요?', TO_DATE('2025-02-27', 'YYYY-MM-DD'), NULL, NULL);
INSERT INTO product_inquiry (inquiry_no, customer_no, product_no, content, inquiry_date, answer_content, answer_date) VALUES (2, 2, 2, '몇 키로까지 앉을 수 있나요?', TO_DATE('2025-03-01', 'YYYY-MM-DD'), NULL, NULL);
INSERT INTO product_inquiry (inquiry_no, customer_no, product_no, content, inquiry_date, answer_content, answer_date) VALUES (3, 5, 9, '2인용은 없나용', TO_DATE('2025-03-02', 'YYYY-MM-DD'), NULL, NULL);
INSERT INTO product_inquiry (inquiry_no, customer_no, product_no, content, inquiry_date, answer_content, answer_date) VALUES (4, 8, 8, '색상은 하나밖에 없나요~~', TO_DATE('2025-03-03', 'YYYY-MM-DD'), NULL, NULL);
INSERT INTO product_inquiry (inquiry_no, customer_no, product_no, content, inquiry_date, answer_content, answer_date) VALUES (5, 4, 6, '높이가 어느정도죠????', TO_DATE('2025-03-04', 'YYYY-MM-DD'), NULL, NULL);

INSERT INTO admin (admin_id, password) VALUES ('admin', '1234');


ALTER TABLE cam_product ADD (is_rent_available CHAR(1) DEFAULT 'Y'); -- 대여 가능여부 'Y' or 'N'
ALTER TABLE cam_product ADD (rental_unit_price NUMBER); -- 1개당 하루 대여금액

CREATE TABLE rental_statistics (   -- 관리자 페이지에서 회원별 반납일 등 대여 이력 통계용 테이블
  stat_no NUMBER PRIMARY KEY,      -- 통계 번호 (기본키, 구분자 역할)
  customer_no NUMBER REFERENCES customer(customer_no),  -- 고객 번호 (customer 테이블 참조, 외래키)
  customer_name VARCHAR2(50),      -- 고객 이름 (조회용으로 저장)
  product_name VARCHAR2(100),      -- 상품 이름 (대여한 제품명)
  rental_date DATE,                -- 대여 시작일
  return_date DATE,                -- 반납 예정일 또는 실제 반납일 (기준에 따라 사용)
  rental_status VARCHAR2(20)       -- 대여 상태 (예: '대여중', '반납완료')
);


ALTER TABLE rental RENAME COLUMN rental_quantity TO rental_qty;
ALTER TABLE rental RENAME COLUMN remain_quantity TO remain_qty;


INSERT INTO rental (
  rental_no, customer_no, product_no, rental_qty, remain_qty,
  rental_price, rental_date, return_date,
  actual_return_date, rental_status
) VALUES (
  1, 30, 1, 2, 3, 20000,
  TO_DATE('2025-03-01', 'YYYY-MM-DD'),
  TO_DATE('2025-03-03', 'YYYY-MM-DD'),
  TO_DATE('2025-03-03', 'YYYY-MM-DD'),
  '반납완료'
);

INSERT INTO rental (
  rental_no, customer_no, product_no, rental_qty, remain_qty,
  rental_price, rental_date, return_date,
  actual_return_date, rental_status
) VALUES (
  2, 19, 2, 4, 1, 14000,
  TO_DATE('2025-03-12', 'YYYY-MM-DD'),
  TO_DATE('2025-03-13', 'YYYY-MM-DD'),
  TO_DATE('2025-03-13', 'YYYY-MM-DD'),
  '반납완료'
);

INSERT INTO rental (
  rental_no, customer_no, product_no, rental_qty, remain_qty,
  rental_price, rental_date, return_date,
  actual_return_date, rental_status
) VALUES (
  3, 26, 3, 1, 4, 30000,
  TO_DATE('2025-03-06', 'YYYY-MM-DD'),
  TO_DATE('2025-03-08', 'YYYY-MM-DD'),
  TO_DATE('2025-03-08', 'YYYY-MM-DD'),
  '반납완료'
);

INSERT INTO rental (
  rental_no, customer_no, product_no, rental_qty, remain_qty,
  rental_price, rental_date, return_date,
  actual_return_date, rental_status
) VALUES (
  4, 25, 4, 1, 4, 64000,
  TO_DATE('2025-03-02', 'YYYY-MM-DD'),
  TO_DATE('2025-03-06', 'YYYY-MM-DD'),
  TO_DATE('2025-03-06', 'YYYY-MM-DD'),
  '반납완료'
);

INSERT INTO rental (
  rental_no, customer_no, product_no, rental_qty, remain_qty,
  rental_price, rental_date, return_date,
  actual_return_date, rental_status
) VALUES (
  5, 23, 7, 1, 4, 15000,
  TO_DATE('2025-03-01', 'YYYY-MM-DD'),
  TO_DATE('2025-03-02', 'YYYY-MM-DD'),
  TO_DATE('2025-03-02', 'YYYY-MM-DD'),
  '반납완료'
);
INSERT INTO rental (
  rental_no, customer_no, product_no, rental_qty, remain_qty,
  rental_price, rental_date, return_date,
  rental_status
) VALUES (
 6,17,5,1,4,30000,
  TO_DATE('2025-04-09', 'YYYY-MM-DD'),
  TO_DATE('2025-04-11', 'YYYY-MM-DD'),
  '대여중'
);

INSERT INTO rental (
  rental_no, customer_no, product_no, rental_qty, remain_qty,
  rental_price, rental_date, return_date, rental_status
) VALUES (
  7, 9, 2, 1, 4,
  140000, TO_DATE('2025-04-01', 'YYYY-MM-DD'),
  TO_DATE('2025-04-03', 'YYYY-MM-DD'),
  '연체'
);

INSERT INTO rental (
  rental_no, customer_no, product_no, rental_qty, remain_qty,
  rental_price, rental_date, return_date, rental_status
) VALUES (
  8, 3, 4, 2, 3,
  64000, TO_DATE('2025-04-09', 'YYYY-MM-DD'),
  TO_DATE('2025-04-10', 'YYYY-MM-DD'),
  '연체'
);

INSERT INTO rental (
  rental_no, customer_no, product_no, rental_qty, remain_qty,
  rental_price, rental_date, return_date, rental_status
) VALUES (
  9, 12, 1, 2, 3,
  60000, TO_DATE('2025-04-08', 'YYYY-MM-DD'),
  TO_DATE('2025-04-10', 'YYYY-MM-DD'),
  '연체'
);

INSERT INTO rental (
  rental_no, customer_no, product_no, rental_qty, remain_qty,
  rental_price, rental_date, return_date, rental_status
) VALUES (
  10, 3, 2, 1, 3,
  14000, TO_DATE('2025-04-03', 'YYYY-MM-DD'),
  TO_DATE('2025-04-04', 'YYYY-MM-DD'),
  '대여중'
);

INSERT INTO rental (
  rental_no, customer_no, product_no, rental_qty, remain_qty,
  rental_price, rental_date, return_date, rental_status
) VALUES (
  11, 14, 1, 2, 1,
  40000, TO_DATE('2025-04-03', 'YYYY-MM-DD'),
  TO_DATE('2025-04-05', 'YYYY-MM-DD'),
  '대여중'
);

INSERT INTO rental (
  rental_no, customer_no, product_no, rental_qty, remain_qty,
  rental_price, rental_date, return_date, rental_status
) VALUES (
  12, 10, 6, 4, 1,
  600000, TO_DATE('2025-04-06', 'YYYY-MM-DD'),
  TO_DATE('2025-04-09', 'YYYY-MM-DD'),
  '연체'
);

INSERT INTO rental (
  rental_no, customer_no, product_no, rental_qty, remain_qty,
  rental_price, rental_date, return_date, rental_status
) VALUES (
  13, 18, 4, 3, 0,
  144000, TO_DATE('2025-04-05', 'YYYY-MM-DD'),
  TO_DATE('2025-04-08', 'YYYY-MM-DD'),
  '대여중'
);

INSERT INTO rental (
  rental_no, customer_no, product_no, rental_qty, remain_qty,
  rental_price, rental_date, return_date, rental_status
) VALUES (
  14, 9, 1, 1, 0,
  60000, TO_DATE('2025-04-05', 'YYYY-MM-DD'),
  TO_DATE('2025-04-08', 'YYYY-MM-DD'),
  '연체'
);

INSERT INTO rental (
  rental_no, customer_no, product_no, rental_qty, remain_qty,
  rental_price, rental_date, return_date, rental_status
) VALUES (
  15, 6, 7, 2, 3,
  60000, TO_DATE('2025-04-03', 'YYYY-MM-DD'),
  TO_DATE('2025-04-04', 'YYYY-MM-DD'),
  '대여중'
);

INSERT INTO rental (
  rental_no, customer_no, product_no, rental_qty, remain_qty,
  rental_price, rental_date, return_date, rental_status
) VALUES (
  16, 13, 12, 3, 2,
  1380000, TO_DATE('2025-04-01', 'YYYY-MM-DD'),
  TO_DATE('2025-04-05', 'YYYY-MM-DD'),
  '대여중'
);

INSERT INTO rental (
  rental_no, customer_no, product_no, rental_qty, remain_qty,
  rental_price, rental_date, return_date, rental_status
) VALUES (
  17, 23, 2, 1, 2,
  70000, TO_DATE('2025-04-04', 'YYYY-MM-DD'),
  TO_DATE('2025-04-09', 'YYYY-MM-DD'),
  '대여중'
);

INSERT INTO rental (
  rental_no, customer_no, product_no, rental_qty, remain_qty,
  rental_price, rental_date, return_date, rental_status
) VALUES (
  18, 4, 3, 1, 4,
  45000, TO_DATE('2025-04-06', 'YYYY-MM-DD'),
  TO_DATE('2025-04-09', 'YYYY-MM-DD'),
  '연체'
);

INSERT INTO rental (
  rental_no, customer_no, product_no, rental_qty, remain_qty,
  rental_price, rental_date, return_date, rental_status
) VALUES (
  19, 8, 5, 2, 2,
  84000, TO_DATE('2025-04-04', 'YYYY-MM-DD'),
  TO_DATE('2025-04-06', 'YYYY-MM-DD'),
  '대여중'
);

INSERT INTO rental (
  rental_no, customer_no, product_no, rental_qty, remain_qty,
  rental_price, rental_date, return_date, rental_status
) VALUES (
  20, 17, 10, 1, 4,
  750000, TO_DATE('2025-04-09', 'YYYY-MM-DD'),
  TO_DATE('2025-04-11', 'YYYY-MM-DD'),
  '대여중'
);

INSERT INTO rental (
  rental_no, customer_no, product_no, rental_qty, remain_qty,
  rental_price, rental_date, return_date, rental_status
) VALUES (
  21, 22, 9, 2, 3,
  175000, TO_DATE('2025-04-07', 'YYYY-MM-DD'),
  TO_DATE('2025-04-10', 'YYYY-MM-DD'),
  '연체'
);

INSERT INTO rental (
  rental_no, customer_no, product_no, rental_qty, remain_qty,
  rental_price, rental_date, return_date, rental_status
) VALUES (
  22, 29, 8, 2, 3,
  20000, TO_DATE('2025-04-10', 'YYYY-MM-DD'),
  TO_DATE('2025-04-11', 'YYYY-MM-DD'),
  '대여중'
);

INSERT INTO rental (
  rental_no, customer_no, product_no, rental_qty, remain_qty,
  rental_price, rental_date, return_date, rental_status
) VALUES (
  23, 25, 11, 2, 3,
  320000, TO_DATE('2025-04-01', 'YYYY-MM-DD'),
  TO_DATE('2025-04-03', 'YYYY-MM-DD'),
  '연체'
);

INSERT INTO rental (
  rental_no, customer_no, product_no, rental_qty, remain_qty,
  rental_price, rental_date, return_date, rental_status
) VALUES (
  24, 30, 6, 1, 0,
  120000, TO_DATE('2025-04-09', 'YYYY-MM-DD'),
  TO_DATE('2025-04-11', 'YYYY-MM-DD'),
  '대여중'
);

INSERT INTO rental (
  rental_no, customer_no, product_no, rental_qty, remain_qty,
  rental_price, rental_date, return_date, rental_status
) VALUES (
  25, 11, 12, 2, 0,
  1150000, TO_DATE('2025-04-01', 'YYYY-MM-DD'),
  TO_DATE('2025-04-06', 'YYYY-MM-DD'),
  '연체'
);

DELETE FROM rental
WHERE rental_no = 6;
