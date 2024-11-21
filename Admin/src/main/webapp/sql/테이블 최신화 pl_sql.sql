BEGIN
    -- 1. 테이블 삭제 (존재할 경우에만 삭제)
    FOR rec IN (
        SELECT table_name FROM user_tables 
        WHERE table_name IN ('USER_COUPON', 'EMAIL_LOG', 'COUPON_EVENT', 'BANNER_EVENT', 'EVENT_IMAGE', 'EVENT', 
                             'DELIVERY', 'ORDER_ITEM', 'ORDERS', 'CART_ITEM', 
                             'CART', 'PRODUCT_REVIEW_REPLY', 'PRODUCT_REVIEW', 'PRODUCT_IMAGE', 
                             'PRODUCT', 'PRODUCT_CATEGORY', 'BOARD_REPLY', 'BOARD', 
                             'BOARD_CATEGORY', 'ADMIN', 'ADMIN_ROLE', 'CUSTOMER', 
                             'USERS', 'USER_LOG', 'USER_WALLET', 'COMPANY_REVENUE', 
                             'BOARD_VISIT', 'BOARD_FILEUPLOADS', 'CATEGORY_IMAGE')
    ) LOOP
        EXECUTE IMMEDIATE 'DROP TABLE ' || rec.table_name || ' CASCADE CONSTRAINTS';
    END LOOP;
    
    -- 2. 시퀀스 삭제 (존재할 경우에만 삭제)
    FOR rec IN (
        SELECT sequence_name FROM user_sequences 
        WHERE sequence_name IN ('SEQ_USER_NO', 'SEQ_BOARD_NO', 'SEQ_BOARD_REPLY_NO', 
                                'SEQ_PRODUCT_NO', 'SEQ_BOARD_FILEUPLOADS_NO', 'SEQ_REVIEW_NO', 
                                'SEQ_PRODUCT_REVIEW_REPLY_NO', 'SEQ_CART_NO', 'SEQ_CART_ITEM_NO', 
                                'SEQ_ORDER_NO', 'SEQ_ORDER_ITEM_NO', 'SEQ_DELIVERY_NO', 
                                'SEQ_EVENT_NO', 'SEQ_COUPON_NO', 'SEQ_EMAIL_LOG_NO', 
                                'SEQ_USER_COUPON_NO', 'SEQ_USER_LOG_NO', 'SEQ_BOARD_VISIT_NO', 
                                'SEQ_WALLET_NO', 'SEQ_REVENUE_NO', 'SEQ_PCIMAGE_NO',
                                'SEQ_EVENT_IMAGE_NO', 'SEQ_BANNER_EVENT_NO', 'SEQ_COUPON_EVENT_NO')
    ) LOOP
        EXECUTE IMMEDIATE 'DROP SEQUENCE ' || rec.sequence_name;
    END LOOP;
    -- 3. USERS 테이블 생성
    EXECUTE IMMEDIATE '
        CREATE TABLE USERS (
            USER_NO NUMBER PRIMARY KEY,  
            USER_ID VARCHAR2(50) UNIQUE NOT NULL,  
            PASSWORD VARCHAR2(100) NOT NULL,  
            NAME VARCHAR2(100) NOT NULL,  
            EMAIL VARCHAR2(100) NOT NULL,  
            USER_TYPE VARCHAR2(20) NOT NULL CHECK (USER_TYPE IN (''CUSTOMER'', ''ADMIN'')),  
            IS_DELETED CHAR(1) DEFAULT ''N'' CHECK (IS_DELETED IN (''Y'', ''N'')),  
            CREATED_AT DATE DEFAULT SYSDATE,  
            UPDATED_AT DATE  
        )';

    -- 4. CUSTOMER 테이블 생성 (USERS 테이블 참조)
    EXECUTE IMMEDIATE '
        CREATE TABLE CUSTOMER (
            USER_NO NUMBER PRIMARY KEY,  
            AGE NUMBER,  
            JOB VARCHAR2(100),  
            LOCATION VARCHAR2(100),  
            MILEAGE NUMBER DEFAULT 0,  
            LAST_LOGIN_DATE DATE,  
            TOTAL_PURCHASE_AMOUNT NUMBER DEFAULT 0,  
            FOREIGN KEY (USER_NO) REFERENCES USERS(USER_NO)  
        )';

    -- 5. ADMIN_ROLE 테이블 생성
    EXECUTE IMMEDIATE '
        CREATE TABLE ADMIN_ROLE (
            ROLE_CODE VARCHAR2(300) PRIMARY KEY,  
            ROLE_NAME VARCHAR2(100) NOT NULL  
        )';

    -- 6. ADMIN 테이블 생성 (USERS 및 ADMIN_ROLE 테이블 참조)
    EXECUTE IMMEDIATE '
        CREATE TABLE ADMIN (
            USER_NO NUMBER PRIMARY KEY,  
            ROLE_CODE VARCHAR2(300),  
            FOREIGN KEY (USER_NO) REFERENCES USERS(USER_NO),  
            FOREIGN KEY (ROLE_CODE) REFERENCES ADMIN_ROLE(ROLE_CODE)  
        )';

    -- 7. BOARD_CATEGORY 테이블 생성
    EXECUTE IMMEDIATE '
        CREATE TABLE BOARD_CATEGORY (
            CATEGORY_NO VARCHAR2(300) PRIMARY KEY,  
            NAME VARCHAR2(100) NOT NULL,  
            DESCRIPTION VARCHAR2(255),
            IS_DELETED CHAR(1) DEFAULT ''N'' CHECK (IS_DELETED IN (''Y'', ''N''))
        )';

    -- 8. BOARD 테이블 생성 (USERS 및 BOARD_CATEGORY 테이블 참조)
    EXECUTE IMMEDIATE '
        CREATE TABLE BOARD (
            BOARD_NO NUMBER PRIMARY KEY,  
            USER_NO NUMBER,  
            CATEGORY_NO VARCHAR2(300),  
            TITLE VARCHAR2(200) NOT NULL,  
            CONTENT VARCHAR2(4000),  
            VIEWS NUMBER DEFAULT 0,  
            CREATED_AT DATE DEFAULT SYSDATE,  
            UPDATED_AT DATE,  
            IS_DELETED CHAR(1) DEFAULT ''N'',  
            FOREIGN KEY (USER_NO) REFERENCES USERS(USER_NO),  
            FOREIGN KEY (CATEGORY_NO) REFERENCES BOARD_CATEGORY(CATEGORY_NO)  
        )';

    -- 9. BOARD_REPLY 테이블 생성 (BOARD 및 USERS 테이블 참조)
    EXECUTE IMMEDIATE '
        CREATE TABLE BOARD_REPLY (
            REPLY_NO NUMBER PRIMARY KEY,  
            BOARD_NO NUMBER,  
            USER_NO NUMBER,  
            CONTENT VARCHAR2(4000),  
            LEFT_VAL NUMBER,  
            RIGHT_VAL NUMBER,  
            NODE_LEVEL NUMBER,  
            PARENT_REPLY_NO NUMBER,  
            CREATED_AT DATE DEFAULT SYSDATE,  
            UPDATED_AT DATE,  
            IS_DELETED CHAR(1) DEFAULT ''N'' CHECK (IS_DELETED IN (''Y'', ''N'')),  
            FOREIGN KEY (BOARD_NO) REFERENCES BOARD(BOARD_NO),  
            FOREIGN KEY (USER_NO) REFERENCES USERS(USER_NO),
            FOREIGN KEY (PARENT_REPLY_NO) REFERENCES BOARD_REPLY(REPLY_NO) ON DELETE CASCADE 
        )';

    -- 10. BOARD_FILEUPLOADS 테이블 생성 (BOARD 테이블 참조)
    EXECUTE IMMEDIATE '
        CREATE TABLE BOARD_FILEUPLOADS (
            FILE_NO NUMBER PRIMARY KEY,
            BOARD_NO NUMBER,
            FILE_URL VARCHAR2(255) NOT NULL,
            FILE_NAME VARCHAR2(255) NOT NULL,
            FILE_SIZE NUMBER NOT NULL,
            FILE_TYPE VARCHAR2(100) NOT NULL,
            DESCRIPTION VARCHAR2(255),
            UPLOADED_AT DATE DEFAULT SYSDATE,
            FOREIGN KEY (BOARD_NO) REFERENCES BOARD(BOARD_NO) ON DELETE CASCADE
        )';

    -- 11. PRODUCT_CATEGORY 테이블 생성
    EXECUTE IMMEDIATE '
        CREATE TABLE PRODUCT_CATEGORY (
            CATEGORY_NO VARCHAR2(300) PRIMARY KEY,  
            NAME VARCHAR2(100) NOT NULL,  
            DESCRIPTION VARCHAR2(255),
            IS_DELETED CHAR(1) DEFAULT ''N'' CHECK (IS_DELETED IN (''Y'', ''N''))  
        )';

    -- 12. CATEGORY_IMAGE 테이블 생성 (PRODUCT_CATEGORY 테이블 참조)
    EXECUTE IMMEDIATE '
        CREATE TABLE CATEGORY_IMAGE (
            PCIMAGE_NO NUMBER PRIMARY KEY, 
            CATEGORY_NO VARCHAR2(300),           
            IMAGE_URL VARCHAR2(500) NOT NULL,    
            ALT_TEXT VARCHAR2(255),              
            UPLOAD_DATE DATE DEFAULT SYSDATE,    
            CONSTRAINT FK_CATEGORY_NO FOREIGN KEY (CATEGORY_NO)
            REFERENCES PRODUCT_CATEGORY(CATEGORY_NO) 
            ON DELETE CASCADE                       
        )';

    -- 13. PRODUCT 테이블 생성 (PRODUCT_CATEGORY 및 USERS 테이블 참조)
    EXECUTE IMMEDIATE '
        CREATE TABLE PRODUCT (
            PRODUCT_NO NUMBER PRIMARY KEY,  
            CATEGORY_NO VARCHAR2(300),  
            NAME VARCHAR2(100) NOT NULL,  
            DESCRIPTION VARCHAR2(4000),  
            PRICE NUMBER NOT NULL,  
            STOCK_QUANTITY NUMBER,  
            VIEWS NUMBER DEFAULT 0,  
            CREATED_AT DATE DEFAULT SYSDATE,  
            UPDATED_AT DATE,  
            IS_DELETED CHAR(1) DEFAULT ''N'',  
            TOTAL_SALES NUMBER DEFAULT 0,  
            USER_NO NUMBER,  
            FOREIGN KEY (CATEGORY_NO) REFERENCES PRODUCT_CATEGORY(CATEGORY_NO),  
            FOREIGN KEY (USER_NO) REFERENCES USERS(USER_NO)  
        )';

    -- 14. PRODUCT_IMAGE 테이블 생성 (PRODUCT 테이블 참조)
    EXECUTE IMMEDIATE '
        CREATE TABLE PRODUCT_IMAGE (
            IMAGE_NO NUMBER PRIMARY KEY,  
            PRODUCT_NO NUMBER,  
            IMAGE_URL VARCHAR2(255) NOT NULL,  
            DESCRIPTION VARCHAR2(255),  
            FOREIGN KEY (PRODUCT_NO) REFERENCES PRODUCT(PRODUCT_NO)  
        )';

    -- 15. PRODUCT_REVIEW 테이블 생성 (BOARD 및 PRODUCT 테이블 참조)
    EXECUTE IMMEDIATE '
        CREATE TABLE PRODUCT_REVIEW (
            REVIEW_NO NUMBER PRIMARY KEY,  
            BOARD_NO NUMBER,  
            PRODUCT_NO NUMBER,  
            RATING NUMBER CHECK (RATING BETWEEN 1 AND 5),
            LIKE_COUNT NUMBER DEFAULT 0,
            FOREIGN KEY (BOARD_NO) REFERENCES BOARD(BOARD_NO),  
            FOREIGN KEY (PRODUCT_NO) REFERENCES PRODUCT(PRODUCT_NO)  
        )';

    -- 16. PRODUCT_REVIEW_REPLY 테이블 생성 (PRODUCT_REVIEW 및 USERS 테이블 참조)
    EXECUTE IMMEDIATE '
        CREATE TABLE PRODUCT_REVIEW_REPLY (
            REPLY_NO NUMBER PRIMARY KEY,  
            REVIEW_NO NUMBER,  
            USER_NO NUMBER,  
            CONTENT VARCHAR2(4000),  
            CREATED_AT DATE DEFAULT SYSDATE,  
            UPDATED_AT DATE,  
            IS_DELETED CHAR(1) DEFAULT ''N'' CHECK (IS_DELETED IN (''Y'', ''N'')),  
            FOREIGN KEY (REVIEW_NO) REFERENCES PRODUCT_REVIEW(REVIEW_NO),  
            FOREIGN KEY (USER_NO) REFERENCES USERS(USER_NO)  
        )';

    -- 17. CART 테이블 생성 (USERS 테이블 참조)
    EXECUTE IMMEDIATE '
        CREATE TABLE CART (
            CART_NO NUMBER PRIMARY KEY,  
            USER_NO NUMBER,  
            CREATED_AT DATE DEFAULT SYSDATE,  
            FOREIGN KEY (USER_NO) REFERENCES USERS(USER_NO)  
        )';

    -- 18. CART_ITEM 테이블 생성 (CART 및 PRODUCT 테이블 참조)
    EXECUTE IMMEDIATE '
        CREATE TABLE CART_ITEM (
            CART_ITEM_NO NUMBER PRIMARY KEY,  
            CART_NO NUMBER,  
            PRODUCT_NO NUMBER,  
            QUANTITY NUMBER NOT NULL,  
            ADDED_AT DATE DEFAULT SYSDATE,  
            UPDATED_AT DATE,  
            FOREIGN KEY (CART_NO) REFERENCES CART(CART_NO),  
            FOREIGN KEY (PRODUCT_NO) REFERENCES PRODUCT(PRODUCT_NO)  
        )';

    -- 19. ORDERS 테이블 생성 (USERS 테이블 참조)
    EXECUTE IMMEDIATE '
        CREATE TABLE ORDERS (
            ORDER_NO NUMBER PRIMARY KEY,  
            USER_NO NUMBER,  
            ORDER_DATE DATE DEFAULT SYSDATE,  
            TOTAL_AMOUNT NUMBER NOT NULL,  
            STATUS VARCHAR2(20) CHECK (STATUS IN (''PENDING'', ''SHIPPED'', ''DELIVERED'', ''CANCELLED'')),  
            FOREIGN KEY (USER_NO) REFERENCES USERS(USER_NO)  
        )';

    -- 20. ORDER_ITEM 테이블 생성 (ORDERS 및 PRODUCT 테이블 참조)
    EXECUTE IMMEDIATE '
        CREATE TABLE ORDER_ITEM (
            ORDER_ITEM_NO NUMBER PRIMARY KEY,  
            ORDER_NO NUMBER,  
            PRODUCT_NO NUMBER,  
            QUANTITY NUMBER NOT NULL,  
            PRICE NUMBER NOT NULL,  
            FOREIGN KEY (ORDER_NO) REFERENCES ORDERS(ORDER_NO),  
            FOREIGN KEY (PRODUCT_NO) REFERENCES PRODUCT(PRODUCT_NO)  
        )';

    -- 21. DELIVERY 테이블 생성 (ORDERS 테이블 참조)
    EXECUTE IMMEDIATE '
        CREATE TABLE DELIVERY (
            DELIVERY_NO NUMBER PRIMARY KEY,  
            ORDER_NO NUMBER,  	
            DELIVERY_DATE DATE,  
            DELIVERY_STATUS VARCHAR2(50) DEFAULT ''PENDING'' CHECK (DELIVERY_STATUS IN (''PENDING'', ''SHIPPED'', ''DELIVERED'', ''CANCELLED'')),  
            FOREIGN KEY (ORDER_NO) REFERENCES ORDERS(ORDER_NO)  
        )';

    -- 22. EVENT 테이블 생성 (최상위 테이블)
    EXECUTE IMMEDIATE '
		CREATE TABLE EVENT (
		    EVENT_NO NUMBER PRIMARY KEY,  
		    NAME VARCHAR2(255) NOT NULL,  
		    DESCRIPTION VARCHAR2(1000),
		    VIEWS NUMBER DEFAULT 0,
		    START_DATE DATE,  
		    END_DATE DATE,
		    CREATED_AT DATE DEFAULT SYSDATE,  
		    UPDATED_AT DATE,  
		    STATUS VARCHAR2(20) DEFAULT ''ACTIVE'' CHECK (STATUS IN (''ACTIVE'', ''ENDED'', ''SCHEDULED'')),
		    EVENT_TYPE VARCHAR2(50) NOT NULL
		)';
    -- 25. EVENT_IMAGE 테이블 생성 (EVENT 테이블 참조)
    EXECUTE IMMEDIATE '
        CREATE TABLE EVENT_IMAGE (
            IMAGE_NO NUMBER PRIMARY KEY,  
            EVENT_NO NUMBER,  
            FILE_PATH VARCHAR2(500) NOT NULL,
            FILE_NAME VARCHAR2(255) NOT NULL,
            FILE_SIZE NUMBER, 
            CREATED_AT DATE DEFAULT SYSDATE,
            UPDATED_AT DATE,
            FOREIGN KEY (EVENT_NO) REFERENCES EVENT(EVENT_NO)
        )';
    -- 23. COUPON_EVENT 테이블 생성 (EVENT 및 EVENT_IMAGE 테이블 참조)
    EXECUTE IMMEDIATE '
		CREATE TABLE COUPON_EVENT (
		    COUPON_EVENT_NO NUMBER PRIMARY KEY,
		    EVENT_NO NUMBER REFERENCES EVENT(EVENT_NO) ON DELETE CASCADE,  -- 부모 테이블인 EVENT와 연결
		    COUPON_CODE VARCHAR2(50) UNIQUE NOT NULL,
		    DISCOUNT_AMOUNT NUMBER,
		    DISCOUNT_PERCENT NUMBER,
		    EXPIRY_DATE DATE,
		    IMAGE_NO NUMBER REFERENCES EVENT_IMAGE(IMAGE_NO) ON DELETE CASCADE  -- 이미지 정보 참조
		)';

    -- 24. BANNER_EVENT 테이블 생성 (EVENT 및 EVENT_IMAGE 테이블 참조)
    EXECUTE IMMEDIATE '
		CREATE TABLE BANNER_EVENT (
		    BANNER_EVENT_NO NUMBER PRIMARY KEY,
		    EVENT_NO NUMBER REFERENCES EVENT(EVENT_NO) ON DELETE CASCADE,  -- 부모 테이블인 EVENT와 연결
		    IMAGE_NO NUMBER REFERENCES EVENT_IMAGE(IMAGE_NO) ON DELETE CASCADE,  -- 이미지 정보 참조
		    LINK_URL VARCHAR2(500),  -- 배너 클릭 시 이동할 URL
		    DISPLAY_START_DATE DATE,  -- 배너 표시 시작 날짜
		    DISPLAY_END_DATE DATE  -- 배너 표시 종료 날짜
		)';



    -- 26. USER_COUPON 테이블 생성 (USERS 및 COUPON_EVENT 테이블 참조)
    EXECUTE IMMEDIATE '
        CREATE TABLE USER_COUPON (
            USER_COUPON_NO NUMBER PRIMARY KEY,  
            USER_NO NUMBER,
            COUPON_EVENT_NO NUMBER,
            DISCOUNT_AMOUNT NUMBER,
            DISCOUNT_PERCENT NUMBER CHECK (DISCOUNT_PERCENT BETWEEN 0 AND 100),
            EXPIRY_DATE DATE,
            IS_REDEEMED CHAR(1) DEFAULT ''N'' CHECK (IS_REDEEMED IN (''Y'', ''N'')),
            REDEEMED_AT DATE,
            RECEIVED_AT DATE DEFAULT SYSDATE,
            FOREIGN KEY (USER_NO) REFERENCES USERS(USER_NO) ON DELETE CASCADE,
            FOREIGN KEY (COUPON_EVENT_NO) REFERENCES COUPON_EVENT(COUPON_EVENT_NO) ON DELETE CASCADE
        )';

    -- 27. EMAIL_LOG 테이블 생성 (COUPON_EVENT 테이블 참조)
    EXECUTE IMMEDIATE '
        CREATE TABLE EMAIL_LOG (
            EMAIL_LOG_NO NUMBER PRIMARY KEY,  
            EMAIL_ADDRESS VARCHAR2(255) NOT NULL,  
            COUPON_EVENT_NO NUMBER,  
            SENT_AT DATE DEFAULT SYSDATE,  
            STATUS VARCHAR2(50) DEFAULT ''SENT'' CHECK (STATUS IN (''SENT'', ''FAILED'')),  
            ERROR_MESSAGE VARCHAR2(1000),  
            FOREIGN KEY (COUPON_EVENT_NO) REFERENCES COUPON_EVENT(COUPON_EVENT_NO)  
        )';

    -- 28. USER_LOG 테이블 생성 (USERS 테이블 참조)
    EXECUTE IMMEDIATE '
        CREATE TABLE USER_LOG (
            LOG_NO NUMBER PRIMARY KEY,  
            USER_NO NUMBER,  
            ACTION VARCHAR2(255) NOT NULL,  
            DESCRIPTION VARCHAR2(4000),  
            CREATED_AT DATE DEFAULT SYSDATE,  
            FOREIGN KEY (USER_NO) REFERENCES USERS(USER_NO)  
        )';

    -- 29. BOARD_VISIT 테이블 생성 (USERS 및 BOARD 테이블 참조)
    EXECUTE IMMEDIATE '
        CREATE TABLE BOARD_VISIT (
            VISIT_ID NUMBER PRIMARY KEY,  
            USER_NO NUMBER,  
            BOARD_NO NUMBER,  
            VISIT_DATE DATE DEFAULT SYSDATE,  
            FOREIGN KEY (USER_NO) REFERENCES USERS(USER_NO) ON DELETE CASCADE,  
            FOREIGN KEY (BOARD_NO) REFERENCES BOARD(BOARD_NO) ON DELETE CASCADE  
        )';

    -- 30. USER_WALLET 테이블 생성 (USERS 테이블 참조)
    EXECUTE IMMEDIATE '
        CREATE TABLE USER_WALLET (
            WALLET_NO NUMBER PRIMARY KEY,  
            USER_NO NUMBER NOT NULL,  
            BALANCE NUMBER DEFAULT 0,  
            LAST_UPDATED DATE DEFAULT SYSDATE,  
            FOREIGN KEY (USER_NO) REFERENCES USERS(USER_NO)  
        )';

    -- 31. COMPANY_REVENUE 테이블 생성 (ORDERS 테이블 참조)
    EXECUTE IMMEDIATE '
        CREATE TABLE COMPANY_REVENUE (
            REVENUE_NO NUMBER PRIMARY KEY,  
            ORDER_NO NUMBER,  
            AMOUNT NUMBER NOT NULL,  
            REVENUE_DATE DATE DEFAULT SYSDATE,  
            FOREIGN KEY (ORDER_NO) REFERENCES ORDERS(ORDER_NO)  
        )';

    -- 시퀀스 생성
    -- USERS 테이블의 USER_NO 시퀀스
    EXECUTE IMMEDIATE 'CREATE SEQUENCE SEQ_USER_NO START WITH 1 INCREMENT BY 1 NOCACHE';

    -- BOARD 테이블의 BOARD_NO 시퀀스
    EXECUTE IMMEDIATE 'CREATE SEQUENCE SEQ_BOARD_NO START WITH 1 INCREMENT BY 1 NOCACHE';

    -- BOARD_REPLY 테이블의 REPLY_NO 시퀀스
    EXECUTE IMMEDIATE 'CREATE SEQUENCE SEQ_BOARD_REPLY_NO START WITH 1 INCREMENT BY 1 NOCACHE';

    -- PRODUCT 테이블의 PRODUCT_NO 시퀀스
    EXECUTE IMMEDIATE 'CREATE SEQUENCE SEQ_PRODUCT_NO START WITH 1 INCREMENT BY 1 NOCACHE';

    -- PRODUCT_REVIEW 테이블의 REVIEW_NO 시퀀스
    EXECUTE IMMEDIATE 'CREATE SEQUENCE SEQ_REVIEW_NO START WITH 1 INCREMENT BY 1 NOCACHE';

    -- PRODUCT_REVIEW_REPLY 테이블의 REPLY_NO 시퀀스
    EXECUTE IMMEDIATE 'CREATE SEQUENCE SEQ_PRODUCT_REVIEW_REPLY_NO START WITH 1 INCREMENT BY 1 NOCACHE';

    -- CART 테이블의 CART_NO 시퀀스
    EXECUTE IMMEDIATE 'CREATE SEQUENCE SEQ_CART_NO START WITH 1 INCREMENT BY 1 NOCACHE';

    -- CART_ITEM 테이블의 CART_ITEM_NO 시퀀스
    EXECUTE IMMEDIATE 'CREATE SEQUENCE SEQ_CART_ITEM_NO START WITH 1 INCREMENT BY 1 NOCACHE';

    -- ORDERS 테이블의 ORDER_NO 시퀀스
    EXECUTE IMMEDIATE 'CREATE SEQUENCE SEQ_ORDER_NO START WITH 1 INCREMENT BY 1 NOCACHE';

    -- ORDER_ITEM 테이블의 ORDER_ITEM_NO 시퀀스
    EXECUTE IMMEDIATE 'CREATE SEQUENCE SEQ_ORDER_ITEM_NO START WITH 1 INCREMENT BY 1 NOCACHE';

    -- DELIVERY 테이블의 DELIVERY_NO 시퀀스
    EXECUTE IMMEDIATE 'CREATE SEQUENCE SEQ_DELIVERY_NO START WITH 1 INCREMENT BY 1 NOCACHE';

    -- EVENT 테이블의 EVENT_NO 시퀀스
    EXECUTE IMMEDIATE 'CREATE SEQUENCE SEQ_EVENT_NO START WITH 1 INCREMENT BY 1 NOCACHE';

    -- EVENT_IMAGE 테이블의 EVENT_IMAGE_NO 시퀀스
    EXECUTE IMMEDIATE 'CREATE SEQUENCE SEQ_EVENT_IMAGE_NO START WITH 1 INCREMENT BY 1 NOCACHE';

    -- COUPON 테이블의 COUPON_NO 시퀀스
    EXECUTE IMMEDIATE 'CREATE SEQUENCE SEQ_COUPON_NO START WITH 1 INCREMENT BY 1 NOCACHE';

    -- EMAIL_LOG 테이블의 EMAIL_LOG_NO 시퀀스
    EXECUTE IMMEDIATE 'CREATE SEQUENCE SEQ_EMAIL_LOG_NO START WITH 1 INCREMENT BY 1 NOCACHE';

    -- USER_COUPON 테이블의 USER_COUPON_NO 시퀀스
    EXECUTE IMMEDIATE 'CREATE SEQUENCE SEQ_USER_COUPON_NO START WITH 1 INCREMENT BY 1 NOCACHE';

    -- USER_LOG 테이블의 LOG_NO 시퀀스
    EXECUTE IMMEDIATE 'CREATE SEQUENCE SEQ_USER_LOG_NO START WITH 1 INCREMENT BY 1 NOCACHE';

    -- BOARD_VISIT 테이블의 VISIT_ID 시퀀스
    EXECUTE IMMEDIATE 'CREATE SEQUENCE SEQ_BOARD_VISIT_NO START WITH 1 INCREMENT BY 1 NOCACHE';

    -- USER_WALLET 테이블의 WALLET_NO 시퀀스
    EXECUTE IMMEDIATE 'CREATE SEQUENCE SEQ_WALLET_NO START WITH 1 INCREMENT BY 1 NOCACHE';

    -- COMPANY_REVENUE 테이블의 REVENUE_NO 시퀀스
    EXECUTE IMMEDIATE 'CREATE SEQUENCE SEQ_REVENUE_NO START WITH 1 INCREMENT BY 1 NOCACHE';

    -- COMPANY_REVENUE 테이블의 REVENUE_NO 시퀀스
    EXECUTE IMMEDIATE 'CREATE SEQUENCE SEQ_BOARD_FILEUPLOADS_NO START WITH 1 INCREMENT BY 1 NOCACHE';

    -- COMPANY_REVENUE 테이블의 REVENUE_NO 시퀀스
    EXECUTE IMMEDIATE 'CREATE SEQUENCE SEQ_PCIMAGE_NO START WITH 1 INCREMENT BY 1 NOCACHE';

    -- BANNER_EVENT 테이블의 BANNER_EVENT_NO 시퀀스
    EXECUTE IMMEDIATE 'CREATE SEQUENCE SEQ_BANNER_EVENT_NO START WITH 1 INCREMENT BY 1 NOCACHE';

    -- COUPON_EVENT 테이블의 COUPON_EVENT_NO 시퀀스
    EXECUTE IMMEDIATE 'CREATE SEQUENCE SEQ_COUPON_EVENT_NO START WITH 1 INCREMENT BY 1 NOCACHE';

	DECLARE
	    v_user_no NUMBER;
	    v_category_no VARCHAR2(300);
	    v_title VARCHAR2(200);
	    v_content VARCHAR2(4000);
	    v_created_at DATE;
		v_random_index NUMBER;
	    -- 제목 및 내용 예시
	    TYPE t_lorem_array IS TABLE OF VARCHAR2(4000);
	    v_titles t_lorem_array := t_lorem_array(
	        '안녕하세요, 새로운 공지가 있습니다', '중요: 필독 공지사항 안내', '업데이트: 필수 항목 변경 안내',
	        '이벤트 소식: 참여하세요!', '긴급 공지: 서버 점검 안내', '자유 게시판: 오늘의 이야기',
	        '사진 게시판: 새로운 사진이 추가되었습니다', 'QnA 게시판: 자주 묻는 질문', '이벤트 안내: 지금 바로 참여하세요!',
	        '업데이트 알림: 새로운 기능이 추가되었습니다', '공지사항: 새로운 정책 안내', '자유 게시판: 의견을 공유하세요!',
	        '사진 게시판: 아름다운 순간을 담아주세요', 'QnA 게시판: 궁금한 점을 물어보세요', '이벤트 마감 임박: 지금 참여하세요!',
	        '필수 업데이트: 보안 패치 안내', '공지사항: 서비스 점검 안내', '자유 게시판: 오늘의 이슈',
	        '사진 게시판: 사진 업로드 완료', 'QnA 게시판: 답변을 기다립니다', '이벤트 참여 기회: 선물을 받아가세요!',
	        '이벤트 특가: 오늘만의 할인!', '이벤트 종료 안내: 마지막 기회를 잡으세요!'
	    );
	
	    v_contents t_lorem_array := t_lorem_array(
	        '이 공지사항은 중요한 정보를 담고 있습니다. 자세한 내용을 확인하시고 필요한 조치를 취해주세요.',
	        '여러분, 이번 공지사항에서는 새로운 업데이트에 대해 다루고 있습니다. 반드시 읽어주시기 바랍니다.',
	        '안녕하세요, 필수 항목에 대한 변경 사항을 안내드립니다. 이 변경 사항은 다음 주부터 적용됩니다.',
	        '이벤트가 곧 시작됩니다! 많은 참여 부탁드리며, 참여 방법은 아래와 같습니다.',
	        '긴급: 이번 서버 점검은 예정된 시간보다 길어질 수 있습니다. 미리 양해 부탁드립니다.',
	        '자유 게시판에 새로운 글이 등록되었습니다. 의견을 나누어 보세요.',
	        '사진 게시판에 멋진 사진이 추가되었습니다. 함께 감상해보세요!',
	        'QnA 게시판에서 자주 묻는 질문들을 확인해보세요.',
	        '이벤트에 참여하시면 다양한 혜택이 주어집니다. 지금 바로 참여하세요!',
	        '필수 업데이트가 완료되었습니다. 새로운 기능을 확인해보세요.',
	        '새로운 정책이 시행됩니다. 공지사항을 확인해 주세요.',
	        '오늘의 자유 게시판 이슈를 확인해보세요. 다양한 의견을 나눌 수 있습니다.',
	        '사진 게시판에 오늘의 멋진 순간을 공유해주세요.',
	        '궁금한 점이 있다면 QnA 게시판에 질문을 남겨주세요. 답변을 드리겠습니다.',
	        '이벤트 마감이 임박했습니다. 마지막 기회를 놓치지 마세요!',
	        '보안을 위해 필수 업데이트가 진행 중입니다. 최신 패치를 적용하세요.',
	        '서비스 점검이 예정되어 있습니다. 공지사항을 참고해 주세요.',
	        '오늘의 이슈를 자유 게시판에서 확인해보세요.',
	        '사진 업로드가 완료되었습니다. 사진 게시판에서 확인하세요.',
	        'QnA 게시판에 답변이 달렸습니다. 확인해보세요.',
	        '이번 이벤트는 선물을 제공합니다. 많은 참여를 부탁드립니다.',
	        '오늘 하루 특가 이벤트를 진행합니다. 할인을 놓치지 마세요!',
	        '이벤트가 곧 종료됩니다. 마지막으로 참여하실 수 있는 기회입니다!'
	    );
	BEGIN
	    -- 기본 사용자 및 관리자 삽입
	    EXECUTE IMMEDIATE '
	        INSERT INTO USERS (USER_NO, USER_ID, PASSWORD, NAME, EMAIL, USER_TYPE, IS_DELETED, CREATED_AT, UPDATED_AT)
	        VALUES (SEQ_USER_NO.NEXTVAL, ''admin'', ''1234'', ''관리자'', ''admin@example.com'', ''ADMIN'', ''N'', SYSDATE, SYSDATE)';
	
	    EXECUTE IMMEDIATE '
	        INSERT INTO ADMIN_ROLE (ROLE_CODE, ROLE_NAME)
	        VALUES (''ADMIN001'', ''최고 관리자'')';
	
	    EXECUTE IMMEDIATE '
	        INSERT INTO ADMIN (USER_NO, ROLE_CODE)
	        VALUES (1, ''ADMIN001'')';
	
	    EXECUTE IMMEDIATE '
	        INSERT INTO USERS (USER_NO, USER_ID, PASSWORD, NAME, EMAIL, USER_TYPE, IS_DELETED, CREATED_AT, UPDATED_AT)
	        VALUES (SEQ_USER_NO.NEXTVAL, ''Tester'', ''1234'', ''고객1'', ''tester@example.com'', ''CUSTOMER'', ''N'', SYSDATE, SYSDATE)';
	
	    EXECUTE IMMEDIATE '
	        INSERT INTO CUSTOMER (USER_NO, AGE, JOB, LOCATION, MILEAGE, LAST_LOGIN_DATE, TOTAL_PURCHASE_AMOUNT)
	        VALUES (2, 30, ''프로그래머'', ''서울'', 1000, SYSDATE, 50000)';
	
	    -- 게시판 카테고리 삽입
	    EXECUTE IMMEDIATE '
	        INSERT INTO BOARD_CATEGORY (CATEGORY_NO, NAME, DESCRIPTION)
	        VALUES (''B.C1000'', ''공지사항'', ''공지사항 게시판 입니다.'')';
	
	    EXECUTE IMMEDIATE '
	        INSERT INTO BOARD_CATEGORY (CATEGORY_NO, NAME, DESCRIPTION)
	        VALUES (''B.C1010'', ''필수 게시판'', ''필수 게시판 입니다.'')';
	
	    EXECUTE IMMEDIATE '
	        INSERT INTO BOARD_CATEGORY (CATEGORY_NO, NAME, DESCRIPTION)
	        VALUES (''B.C1020'', ''자유 게시판'', ''자유 게시판 입니다.'')';
	
	    EXECUTE IMMEDIATE '
	        INSERT INTO BOARD_CATEGORY (CATEGORY_NO, NAME, DESCRIPTION)
	        VALUES (''B.C1030'', ''사진 게시판'', ''사진 게시판 입니다.'')';
	
	    EXECUTE IMMEDIATE '
	        INSERT INTO BOARD_CATEGORY (CATEGORY_NO, NAME, DESCRIPTION)
	        VALUES (''B.C1040'', ''QnA 게시판'', ''QnA 게시판 입니다.'')';
	
	    EXECUTE IMMEDIATE '
	        INSERT INTO BOARD_CATEGORY (CATEGORY_NO, NAME, DESCRIPTION)
	        VALUES (''B.C1050'', ''기타 게시판'', ''기타 게시판 입니다.'')';
	
	    EXECUTE IMMEDIATE '
	        INSERT INTO BOARD_CATEGORY (CATEGORY_NO, NAME, DESCRIPTION)
	        VALUES (''B.C1060'', ''리뷰 게시판'', ''상품 리뷰 게시판 입니다.'')';
	
	    -- 게시글 100개 삽입
	    FOR i IN 1..100 LOOP
	        -- USER_NO와 CATEGORY_NO를 랜덤하게 선택
	        v_user_no := CASE WHEN MOD(i, 2) = 0 THEN 1 ELSE 2 END;
	        v_category_no := CASE 
	            WHEN MOD(i, 7) = 0 THEN 'B.C1000' -- 공지사항
	            WHEN MOD(i, 7) = 1 THEN 'B.C1020' -- 이벤트 게시판
	            WHEN MOD(i, 7) = 2 THEN 'B.C1030' -- 자유 게시판
	            WHEN MOD(i, 7) = 3 THEN 'B.C1040' -- 사진 게시판
	            WHEN MOD(i, 7) = 4 THEN 'B.C1050' -- QnA 게시판
	            WHEN MOD(i, 7) = 5 THEN 'B.C1060' -- 기타 게시판
	            ELSE 'B.C1010'                    -- 필수 게시판
	        END;
	
	        -- 랜덤한 제목과 내용 선택
	        v_random_index := TRUNC(DBMS_RANDOM.VALUE(1, 23));  -- 1부터 22까지의 값 중 랜덤하게 선택
	        v_title := v_titles(v_random_index);
	        v_random_index := TRUNC(DBMS_RANDOM.VALUE(1, 23));
	        v_content := v_contents(v_random_index);
	
	        -- CREATED_AT 날짜를 랜덤하게 생성 (지난 30일 내의 날짜)
	        v_created_at := TRUNC(SYSDATE) - DBMS_RANDOM.VALUE(0, 30);
	
	        -- 데이터 삽입
	        EXECUTE IMMEDIATE '
	            INSERT INTO BOARD (BOARD_NO, USER_NO, CATEGORY_NO, TITLE, CONTENT, CREATED_AT, UPDATED_AT, IS_DELETED)
	            VALUES (SEQ_BOARD_NO.NEXTVAL, :v_user_no, :v_category_no, :v_title, :v_content, :v_created_at, NULL, ''N'')'
	            USING v_user_no, v_category_no, v_title, v_content, v_created_at;
	    END LOOP;

        -- 변경 사항 커밋
        COMMIT;
    END;
END;