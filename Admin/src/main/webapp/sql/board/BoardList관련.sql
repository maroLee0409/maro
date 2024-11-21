
-- USER_TYPE이 ADMIN인지 CUSTOMER인지 확인할 필요가있음
SELECT * FROM ( SELECT row_number() OVER (ORDER BY b.BOARD_NO ASC) AS rnum, b.*, u.USER_TYPE FROM BOARD b JOIN USERS u ON b.USER_NO = u.USER_NO WHERE b.IS_DELETED = 'N' ) WHERE rnum BETWEEN 1 AND 10;
-- boardList페이지에서 BOARD_DETAIL을 호출할때 USER_TYPE,USER_ID,USER_EMAIL,BOARD_CATEGORY테이블에 대한 정보를 포함하고 있습니다.이러한 정보를 함께 보내야 하므로 위의 쿼리를 추가.
-- 아래 쿼리는 BOARD_CATEGORY 테이블을 포함하고있습니다.
SELECT *
FROM (
    SELECT row_number() OVER (ORDER BY b.BOARD_NO ASC) AS rnum, 
           b.*, 
           u.USER_TYPE,
           u.USER_ID,
           u.NAME USER_NAME,
           u.EMAIL USER_EMAIL,
           bc.NAME AS CATEGORY_NAME  -- 카테고리 이름 추가
    FROM BOARD b
    JOIN USERS u ON b.USER_NO = u.USER_NO
    JOIN BOARD_CATEGORY bc ON b.CATEGORY_NO = bc.CATEGORY_NO  -- BOARD_CATEGORY 테이블과 조인
    WHERE b.IS_DELETED = 'N'
)
WHERE rnum BETWEEN 1 AND 10;

-- 위의 쿼리를 한줄로 표현하면 다음과 같습니다.
SELECT * FROM (SELECT row_number() OVER (ORDER BY b.BOARD_NO ASC) AS rnum, b.*, u.USER_TYPE, u.USER_ID, u.NAME AS USER_NAME, u.EMAIL AS USER_EMAIL, bc.NAME AS CATEGORY_NAME FROM BOARD b JOIN USERS u ON b.USER_NO = u.USER_NO JOIN BOARD_CATEGORY bc ON b.CATEGORY_NO = bc.CATEGORY_NO WHERE b.IS_DELETED = ?) WHERE rnum BETWEEN ? AND ?;
SELECT * FROM (SELECT row_number() OVER (ORDER BY b.BOARD_NO ASC) AS rnum, b.*, u.USER_TYPE, u.USER_ID, u.NAME AS USER_NAME, u.EMAIL AS USER_EMAIL, bc.NAME AS CATEGORY_NAME FROM BOARD b JOIN USERS u ON b.USER_NO = u.USER_NO JOIN BOARD_CATEGORY bc ON b.CATEGORY_NO = bc.CATEGORY_NO) WHERE rnum BETWEEN ? AND ?;


/*
 * 현재 이슈로 회원 관련된 로직이 구현이 안돼어있으므로 임시로 아래와 같은 쿼리를 사용할것임.
 * 
 * 
 * */

SELECT * FROM (
    SELECT 
        row_number() OVER (ORDER BY b.BOARD_NO ASC) AS rnum, 
        b.*, 
        u.USER_TYPE, 
        u.USER_ID, 
        u.NAME AS USER_NAME, 
        u.EMAIL AS USER_EMAIL, 
        bc.NAME AS CATEGORY_NAME 
    FROM 
        BOARD b 
    LEFT JOIN USERS u ON b.USER_NO = u.USER_NO 
    JOIN BOARD_CATEGORY bc ON b.CATEGORY_NO = bc.CATEGORY_NO
) 
WHERE rnum BETWEEN 1 AND 10;

SELECT * FROM (SELECT row_number() OVER (ORDER BY b.BOARD_NO ASC) AS rnum, b.*, u.USER_TYPE, u.USER_ID, u.NAME AS USER_NAME, u.EMAIL AS USER_EMAIL, bc.NAME AS CATEGORY_NAME FROM BOARD b LEFT JOIN USERS u ON b.USER_NO = u.USER_NO JOIN BOARD_CATEGORY bc ON b.CATEGORY_NO = bc.CATEGORY_NO) WHERE rnum BETWEEN ? AND ?;



/*
 * 
 * 아래는 가데이터입니다. TEST용
 * 
 * */


-- USERS 테이블에 데이터 삽입
INSERT INTO USERS (USER_NO, USER_ID, PASSWORD, NAME, EMAIL, USER_TYPE, IS_DELETED, CREATED_AT, UPDATED_AT)
VALUES (seq_user_no.NEXTVAL, 'admin', '1234', 'Admin User', 'admin@example.com', 'ADMIN', 'N', SYSDATE, SYSDATE);

-- ADMIN_ROLE 테이블에 최고관리자 역할 삽입
INSERT INTO ADMIN_ROLE (ROLE_CODE, ROLE_NAME)
VALUES ('A.R1000', '최고관리자');

-- ADMIN 테이블에 데이터 삽입
INSERT INTO ADMIN (USER_NO, ROLE_CODE)
VALUES (seq_user_no.CURRVAL, 'A.R1000');

-- 카테고리 공지사항 생성 (가데이터)
insert into board_category values('B.C1000','공지사항','공지사항입니다 카테고리 입니다.');
-- 임시 게시판 생성(공지사항) (가데이터)
insert into board values(seq_board_no.nextval,seq_user_no.CURRVAL,'B.C1000','안녕하세요','<p>첫번째 공지사항 입니다.</p>',default,sysdate,null,'N');

commit;

