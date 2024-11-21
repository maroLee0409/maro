package com.global.board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class BoardVisitDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = null;

	private static BoardVisitDAO instance = null;

	public static BoardVisitDAO getInstance() {
		if (instance == null)
			instance = new BoardVisitDAO();
		return instance;
	}

	// DB를 연동하는 작업을 위한 매서드
	// JDBC방식이 아닌 DBCP 방식으로 DB와 연동하는 작업 진행
	public void openConn() {

		try {
			// 1단계 : JNDI 서버 객체 생성
			// 자바의 네이밍 서비스(JNDI)에서 이름과 실제 객체를
			// 연결해 주는 개념이 Context 객체이며, InitialContext 객체는
			// 네이밍 서비스를 이용하기 위한 시작점이 된다.

			Context initCtx = new InitialContext();

			// 2단계 : Context 객체를 얻어와야 함.
			// "java:comp/env"라는 이름의 인수로 Context 객체를 얻어옴.
			// "java:comp/env"는 현제 웹 애플리케이션에서
			// 네이밍 서비스를 이용 시 루트 디렉토리라고 생각하면 됨.
			// 즉, 현재 웹 애플리케이션이 사용할 수 있는 모든 자원은
			// "java:comp/env" 아래에 위치를 하게 됨
			Context envctx = (Context) initCtx.lookup("java:comp/env");

			// 3단계 : lookup() 메서드를 이용하여 매칭되는 커넥션을 찾아옴.
			// "java:comp/env" 아래에 위치한 "jdbc/myoracle" 자원을
			// 얻어옴. 이 자원이 바로 데이터 소스(커넥션풀)임.
			// 여기서 "jdbc/myoracle" 은 context.xml 파일에 추가했던
			// <Resource> 태그 안에 있던 name 속성의 값임.

			DataSource ds = (DataSource) envctx.lookup("jdbc/myoracle");

			// 4 단계 : DataSoure 객체를 이요하여 컨넥션을 하나 가져오면 됨.
			conn = ds.getConnection();

		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void closeConn(ResultSet rs, Statement stmt, Connection conn) {
		try {
			if (rs != null && !rs.isClosed())
				rs.close();
			if (stmt != null && !stmt.isClosed())
				stmt.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void closeConn(Statement stmt, Connection conn) {
		try {
			if (stmt != null && !stmt.isClosed())
				stmt.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	// 방문 기록 저장
    public void saveVisit(int userNo, int boardNo) {
        try {
            openConn();
            sql = "INSERT INTO BOARD_VISIT (VISIT_ID, USER_NO, BOARD_NO, VISIT_DATE) VALUES (SEQ_BOARD_VISIT_NO.NEXTVAL, ?, ?, SYSDATE)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userNo);
            pstmt.setInt(2, boardNo);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConn(rs, pstmt, conn);
        }
    }

    // 특정 사용자가 방문한 게시글 번호 리스트 조회
    public List<Integer> getVisitedBoardNos(int userNo) {
        List<Integer> visitedBoardNos = new ArrayList<>();
        try {
            openConn();
            sql = "SELECT BOARD_NO FROM BOARD_VISIT WHERE USER_NO = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userNo);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                visitedBoardNos.add(rs.getInt("BOARD_NO"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConn(rs, pstmt, conn);
        }
        return visitedBoardNos;
    }
	
}
