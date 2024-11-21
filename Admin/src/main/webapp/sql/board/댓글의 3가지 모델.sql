1. 중첩 집합 모델 (Nested Set Model)
중첩 집합 모델은 각 노드(댓글/대댓글)에 "왼쪽"과 "오른쪽" 값을 할당하여 계층 구조를 표현하는 방법입니다. 이 방법을 사용하면 부모-자식 관계를 효율적으로 쿼리할 수 있습니다.

테이블 구조:
sql
코드 복사
CREATE TABLE BOARD_REPLY (
    REPLY_NO NUMBER PRIMARY KEY,  
    BOARD_NO NUMBER,  
    USER_NO NUMBER NULL,  -- 추후에 null 삭제 예정
    CONTENT VARCHAR2(4000),  
    LEFT_VAL NUMBER,  -- 중첩 집합에서의 왼쪽 값
    RIGHT_VAL NUMBER,  -- 중첩 집합에서의 오른쪽 값
    NODE_LEVEL NUMBER,  -- 계층 수준 (루트: 1, 자식: 2 등)
    PARENT_REPLY_NO NUMBER,  -- 부모 댓글 번호 (NULL이면 최상위 댓글)
    CREATED_AT DATE DEFAULT SYSDATE,  
    UPDATED_AT DATE,  
    IS_DELETED CHAR(1) DEFAULT 'N' CHECK (IS_DELETED IN ('Y', 'N')),  
    FOREIGN KEY (BOARD_NO) REFERENCES BOARD(BOARD_NO),  
    FOREIGN KEY (USER_NO) REFERENCES USERS(USER_NO),
    FOREIGN KEY (PARENT_REPLY_NO) REFERENCES BOARD_REPLY(REPLY_NO)  -- 자기 자신을 참조하는 외래 키
);


주요 장점:
빠른 조회: 부모나 자식, 혹은 전체 트리를 조회하는 쿼리가 매우 빠름.
트리 구조를 한 번에 조회 가능: 특정 댓글의 모든 자식 댓글을 쉽게 조회할 수 있음.
주요 단점:
복잡한 삽입/삭제: 새 노드를 삽입하거나 삭제할 때, 다른 노드의 좌우 값을 조정해야 하므로 복잡함.
트리 구조 변경 시 비용이 큼: 노드의 삽입/삭제 시 전체 트리의 재계산이 필요.
2. 경로 열거 모델 (Path Enumeration Model)
경로 열거 모델은 각 노드에 그 노드까지의 경로를 저장하여 계층 구조를 표현하는 방법입니다.

테이블 구조:
sql
코드 복사
CREATE TABLE BOARD_REPLY (
    REPLY_NO NUMBER PRIMARY KEY,  
    BOARD_NO NUMBER,  
    USER_NO NUMBER,  
    CONTENT VARCHAR2(4000),  
    PATH VARCHAR2(1000),  -- 루트부터 해당 노드까지의 경로
    LEVEL NUMBER,  -- 계층 수준 (루트: 1, 자식: 2 등)
    CREATED_AT DATE DEFAULT SYSDATE,  
    UPDATED_AT DATE,  
    IS_DELETED CHAR(1) DEFAULT 'N' CHECK (IS_DELETED IN ('Y', 'N')),  
    FOREIGN KEY (BOARD_NO) REFERENCES BOARD(BOARD_NO),  
    FOREIGN KEY (USER_NO) REFERENCES USERS(USER_NO)
);
경로 예시:
PATH 컬럼 값이 "/1/3/7"이라면, 이 댓글은 루트 댓글 1의 자식인 3의 자식인 7이 됩니다.
주요 장점:
빠른 조회: 특정 노드와 그 하위 노드를 쉽게 조회할 수 있음.
간단한 쿼리: 트리 구조를 간단한 LIKE 연산자로 조회 가능.
주요 단점:
경로 길이 제한: PATH 컬럼의 길이가 길어질 수 있음.
삽입/삭제 시 경로 업데이트 필요: 노드를 삽입하거나 삭제할 때, 경로를 업데이트해야 함.
3. Closure Table (폐쇄 테이블)
폐쇄 테이블은 계층 구조를 모든 가능한 조합으로 저장하는 테이블을 추가로 만들어 관리하는 방법입니다.

테이블 구조:
BOARD_REPLY 테이블: 댓글/대댓글 정보를 관리.
BOARD_REPLY_CLOSURE 테이블: 모든 부모-자식 관계를 관리.
sql
코드 복사
CREATE TABLE BOARD_REPLY (
    REPLY_NO NUMBER PRIMARY KEY,  
    BOARD_NO NUMBER,  
    USER_NO NUMBER,  
    CONTENT VARCHAR2(4000),  
    CREATED_AT DATE DEFAULT SYSDATE,  
    UPDATED_AT DATE,  
    IS_DELETED CHAR(1) DEFAULT 'N' CHECK (IS_DELETED IN ('Y', 'N')),  
    FOREIGN KEY (BOARD_NO) REFERENCES BOARD(BOARD_NO),  
    FOREIGN KEY (USER_NO) REFERENCES USERS(USER_NO)
);

CREATE TABLE BOARD_REPLY_CLOSURE (
    ANCESTOR_REPLY_NO NUMBER,  -- 부모 노드
    DESCENDANT_REPLY_NO NUMBER,  -- 자식 노드
    DEPTH NUMBER,  -- 부모-자식 관계의 깊이 (직접 부모-자식 관계면 1, 그 외는 그 이상)
    PRIMARY KEY (ANCESTOR_REPLY_NO, DESCENDANT_REPLY_NO),
    FOREIGN KEY (ANCESTOR_REPLY_NO) REFERENCES BOARD_REPLY(REPLY_NO),
    FOREIGN KEY (DESCENDANT_REPLY_NO) REFERENCES BOARD_REPLY(REPLY_NO)
);
주요 장점:
복잡한 계층 쿼리 처리 가능: 부모와 자식의 관계를 효율적으로 쿼리할 수 있음.
유연한 구조: 트리 구조가 복잡해져도 잘 작동함.
주요 단점:
중복 데이터: 같은 관계가 여러 번 저장되므로 데이터 중복이 발생.
복잡한 데이터 관리: 삽입, 삭제, 업데이트 시 폐쇄 테이블을 적절히 관리해야 함.
결론:
**경로 열거 모델 (Path Enumeration Model)**은 상대적으로 간단하면서도 조회 성능이 좋지만, 트리 구조가 복잡해질 경우 데이터 길이의 제한 문제가 있을 수 있습니다.
**중첩 집합 모델 (Nested Set Model)**은 조회 성능이 뛰어나지만, 트리 구조의 변경이 빈번할 경우 관리가 어려울 수 있습니다.
Closure Table은 가장 유연하고 복잡한 트리 구조를 처리하기 적합하지만, 테이블 크기가 커질 수 있고, 관리가 복잡해집니다.
효율성과 유지보수를 고려하여, 트리 구조의 변경이 빈번하지 않은 경우 중첩 집합 모델이나 경로 열거 모델이 적합할 수 있으며, 복잡한 트리 구조를 관리해야 한다면 Closure Table을 사용하는 것이 좋습니다.