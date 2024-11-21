    -- 게시글 생성
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
            '안녕하세요, 새로운 공지가 있습니다',
            '중요: 필독 공지사항 안내',
            '업데이트: 필수 항목 변경 안내',
            '이벤트 소식: 참여하세요!',
            '긴급 공지: 서버 점검 안내'
        );
        
        v_contents t_lorem_array := t_lorem_array(
            '이 공지사항은 중요한 정보를 담고 있습니다. 자세한 내용을 확인하시고 필요한 조치를 취해주세요.',
            '여러분, 이번 공지사항에서는 새로운 업데이트에 대해 다루고 있습니다. 반드시 읽어주시기 바랍니다.',
            '안녕하세요, 필수 항목에 대한 변경 사항을 안내드립니다. 이 변경 사항은 다음 주부터 적용됩니다.',
            '이벤트가 곧 시작됩니다! 많은 참여 부탁드리며, 참여 방법은 아래와 같습니다.',
            '긴급: 이번 서버 점검은 예정된 시간보다 길어질 수 있습니다. 미리 양해 부탁드립니다.'
        );
    BEGIN
        FOR i IN 1..100 LOOP
            -- USER_NO와 CATEGORY_NO를 랜덤하게 선택
            v_user_no := CASE WHEN MOD(i, 2) = 0 THEN 1 ELSE 2 END;
            v_category_no := CASE 
                WHEN MOD(i, 2) = 0 THEN 'B.C1000' 
                ELSE 'B.C1010' 
            END;

            -- 랜덤한 제목과 내용 선택
            v_random_index := TRUNC(DBMS_RANDOM.VALUE(1, 6));  -- 1부터 5까지의 값 중 랜덤하게 선택
            v_title := v_titles(v_random_index);
            v_random_index := TRUNC(DBMS_RANDOM.VALUE(1, 6));
            v_content := v_contents(v_random_index);

            -- CREATED_AT 날짜를 랜덤하게 생성 (지난 30일 내의 날짜)
            v_created_at := TRUNC(SYSDATE) - DBMS_RANDOM.VALUE(0, 30);

            -- 데이터 삽입
            INSERT INTO BOARD (BOARD_NO, USER_NO, CATEGORY_NO, TITLE, CONTENT, CREATED_AT, UPDATED_AT, IS_DELETED)
            VALUES (SEQ_BOARD_NO.NEXTVAL, v_user_no, v_category_no, v_title, v_content, v_created_at, NULL, 'N');
        END LOOP;
    END;

    -- 변경 사항 커밋
    COMMIT;
END;
/

