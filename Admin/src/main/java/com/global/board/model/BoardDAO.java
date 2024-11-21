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

public class BoardDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = null;

	private static BoardDAO instance = null;

	public static BoardDAO getInstance() {
		if (instance == null)
			instance = new BoardDAO();
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

	public int getBoardCount(String status) {
		int count = 0;
		try {
			openConn();
			sql = "SELECT COUNT(*) FROM board LEFT JOIN users USING(user_no)";

			if (status != null) {
				sql += " WHERE is_deleted = ?";
			}

			pstmt = conn.prepareStatement(sql);

			if (status != null) {
				pstmt.setString(1, status);
			}

			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, conn);
		}

		return count;
	}

	public List<BoardDTO> selectBoardList(int currentPage, int boardLimit, char status) {
		List<BoardDTO> list = new ArrayList<>();
		/*
		 * sql =
		 * "SELECT * FROM (SELECT row_number() OVER (ORDER BY b.BOARD_NO ASC) AS rnum, b.*, u.USER_TYPE, u.USER_ID, u.NAME AS USER_NAME, u.EMAIL AS USER_EMAIL, bc.NAME AS CATEGORY_NAME FROM BOARD b JOIN USERS u ON b.USER_NO = u.USER_NO JOIN BOARD_CATEGORY bc ON b.CATEGORY_NO = bc.CATEGORY_NO WHERE b.IS_DELETED = ?) WHERE rnum BETWEEN ? AND ?"
		 * ;
		 */
		// 현재 회원 관련된 로직이 미구현 상태이므로 임시로 아래 쿼리를 사용합니다.
		sql = "SELECT * FROM (SELECT row_number() OVER (ORDER BY b.BOARD_NO ASC) AS rnum, b.*, u.USER_TYPE, u.USER_ID, u.NAME AS USER_NAME, u.EMAIL AS USER_EMAIL, bc.NAME AS CATEGORY_NAME FROM BOARD b LEFT JOIN USERS u ON b.USER_NO = u.USER_NO JOIN BOARD_CATEGORY bc ON b.CATEGORY_NO = bc.CATEGORY_NO WHERE b.IS_DELETED = ?) WHERE rnum BETWEEN ? AND ?";

		try {
			openConn();
			int paramIndex = 1;
			int startRow = (currentPage - 1) * boardLimit + 1;
			int endRow = startRow + boardLimit - 1;

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(paramIndex++, String.valueOf(status));
			pstmt.setInt(paramIndex++, startRow);
			pstmt.setInt(paramIndex++, endRow);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				BoardDTO board = new BoardDTO();
				board.setBoardNo(rs.getInt("BOARD_NO"));

				// 아직 회원 관련 로직이 구현되지 않았으므로
				int userNo = rs.getInt("USER_NO");
				if (rs.wasNull()) {
					board.setUserNo(null); // USER_NO가 NULL이면 null 설정
				} else {
					board.setUserNo(userNo); // USER_NO가 NULL이 아니면 해당 값 설정
				}

				board.setTitle(rs.getString("TITLE"));
				board.setContent(rs.getString("CONTENT"));
				board.setViews(rs.getInt("VIEWS")); // 조회수 컬럼 추가 20240830 by 두리
				board.setCreateAt(rs.getDate("CREATED_AT"));
				board.setUpdateAt(rs.getDate("UPDATED_AT"));
				board.setIsDeleted(rs.getString("IS_DELETED"));

				/* 카테고리 정보 */
				board.setCategoryNo(rs.getString("CATEGORY_NO"));
				board.setCategoryName(rs.getString("CATEGORY_NAME"));

				/* USERS 정보도 필요하므로 SET */
				board.setUserNo(rs.getInt("USER_NO"));
				board.setUserId(rs.getString("USER_ID"));
				board.setUserEmail(rs.getString("USEr_EMAIL"));
				board.setUserType(rs.getString("USER_TYPE"));

				list.add(board);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, conn);
		}
		return list;
	}

	public List<BoardDTO> selectBoardList(int currentPage, int boardLimit) {
		List<BoardDTO> list = new ArrayList<>();
		/*
		 * boardList페이지에서 BOARD_DETAIL을 호출할때
		 * USER_TYPE,USER_ID,USER_EMAIL,BOARD_CATEGORY테이블에 대한 정보를 포함하고 있습니다. sql =
		 * "SELECT * FROM (SELECT row_number() OVER (ORDER BY b.BOARD_NO ASC) AS rnum, b.*, u.USER_TYPE, u.USER_ID, u.NAME AS USER_NAME, u.EMAIL AS USER_EMAIL, bc.NAME AS CATEGORY_NAME FROM BOARD b JOIN USERS u ON b.USER_NO = u.USER_NO JOIN BOARD_CATEGORY bc ON b.CATEGORY_NO = bc.CATEGORY_NO) WHERE rnum BETWEEN ? AND ?"
		 * ;
		 */
		// 현재 회원 관련된 로직이 미구현 상태이므로 임시로 아래 쿼리를 사용합니다.
		sql = "SELECT * FROM (SELECT row_number() OVER (ORDER BY b.BOARD_NO DESC) AS rnum, b.*, u.USER_TYPE, u.USER_ID, u.NAME AS USER_NAME, u.EMAIL AS USER_EMAIL, bc.NAME AS CATEGORY_NAME FROM BOARD b LEFT JOIN USERS u ON b.USER_NO = u.USER_NO JOIN BOARD_CATEGORY bc ON b.CATEGORY_NO = bc.CATEGORY_NO) WHERE rnum BETWEEN ? AND ?";

		try {
			openConn();
			int paramIndex = 1;
			int startRow = (currentPage - 1) * boardLimit + 1;
			int endRow = startRow + boardLimit - 1;

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(paramIndex++, startRow);
			pstmt.setInt(paramIndex++, endRow);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				BoardDTO board = new BoardDTO();
				board.setBoardNo(rs.getInt("BOARD_NO"));

				// 아직 회원 관련 로직이 구현되지 않았으므로
				int userNo = rs.getInt("USER_NO");
				if (rs.wasNull()) {
					board.setUserNo(null); // USER_NO가 NULL이면 null 설정
				} else {
					board.setUserNo(userNo); // USER_NO가 NULL이 아니면 해당 값 설정
				}

				board.setTitle(rs.getString("TITLE"));
				board.setContent(rs.getString("CONTENT"));
				board.setViews(rs.getInt("VIEWS")); // 조회수 컬럼 추가 20240830 by 두리
				board.setCreateAt(rs.getDate("CREATED_AT"));
				board.setUpdateAt(rs.getDate("UPDATED_AT"));
				board.setIsDeleted(rs.getString("IS_DELETED"));

				/* 카테고리 정보 */
				board.setCategoryNo(rs.getString("CATEGORY_NO"));
				board.setCategoryName(rs.getString("CATEGORY_NAME"));

				/* USERS 정보도 필요하므로 SET */

				board.setUserNo(rs.getInt("USER_NO"));

				board.setUserId(rs.getString("USER_ID"));
				board.setUserEmail(rs.getString("USEr_EMAIL"));
				board.setUserType(rs.getString("USER_TYPE"));

				list.add(board);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, conn);
		}
		return list;
	}

	public List<BoardCategoryDTO> selectBoardCategoryList(int currentPage, int boardLimit) {
		List<BoardCategoryDTO> list = new ArrayList<>();
		/*
		 * boardList페이지에서 BOARD_DETAIL을 호출할때
		 * USER_TYPE,USER_ID,USER_EMAIL,BOARD_CATEGORY테이블에 대한 정보를 포함하고 있습니다.
		 */
		sql = "SELECT * FROM ( SELECT row_number() OVER (ORDER BY category_no ASC) AS rnum, b.* FROM BOARD_CATEGORY b ) WHERE rnum BETWEEN ? AND ?";

		try {
			openConn();
			int paramIndex = 1;
			int startRow = (currentPage - 1) * boardLimit + 1;
			int endRow = startRow + boardLimit - 1;

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(paramIndex++, startRow);
			pstmt.setInt(paramIndex++, endRow);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				BoardCategoryDTO category = new BoardCategoryDTO();

				category.setCategoryNo(rs.getString("CATEGORY_NO"));
				category.setName(rs.getString("NAME"));
				category.setDescription(rs.getString("DESCRIPTION"));

				list.add(category);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, conn);
		}
		return list;
	}

	public int getBoardCategoryCount() {
		int count = 0;

		try {
			openConn();
			sql = "select count(*) from BOARD_CATEGORY";
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, conn);
		}

		return count;
	}

	public int getBoardCategoryCount(char status) {
		int count = 0;

		try {
			openConn();
			sql = "select count(*) from BOARD_CATEGORY";
			// 상태에 따라 SQL 조건 추가
			if (status == 'Y') {
				sql += " WHERE is_deleted = 'Y' order by 1 desc";
			} else if (status == 'N') {
				sql += " WHERE is_deleted = 'N' order by 1 desc";
			}
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, conn);
		}

		return count;
	}

	public List<BoardCategoryDTO> selectBoardCategoryList(int currentPage, int boardLimit, char status) {
		List<BoardCategoryDTO> list = new ArrayList<>();

		sql = "SELECT * FROM ( SELECT row_number() OVER (ORDER BY CATEGORY_NO ASC) AS rnum, b.* FROM BOARD_CATEGORY b) WHERE rnum BETWEEN ? AND ?";

		try {
			openConn();
			int paramIndex = 1;
			int startRow = (currentPage - 1) * boardLimit + 1;
			int endRow = startRow + boardLimit - 1;

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(paramIndex++, String.valueOf(status));
			pstmt.setInt(paramIndex++, startRow);
			pstmt.setInt(paramIndex++, endRow);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				BoardCategoryDTO category = new BoardCategoryDTO();
				category.setCategoryNo(rs.getString("CATEGORY_NO"));
				category.setName(rs.getString("NAME"));
				category.setDescription(rs.getString("DESCRIPTION"));

				list.add(category);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, conn);
		}
		return list;
	}

	public List<BoardCategoryDTO> selectBoardCategoryList() {
		List<BoardCategoryDTO> list = new ArrayList<>();
		try {
			sql = "select * from BOARD_CATEGORY order by 1 asc";
			openConn();
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				BoardCategoryDTO category = new BoardCategoryDTO();
				category.setCategoryNo(rs.getString(1));
				category.setName(rs.getString(2));
				category.setDescription(rs.getString(3));
				list.add(category);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, conn);
		}
		return list;
	}

	public List<BoardCategoryDTO> selectBoardCategoryList(String isDeleted) {
	    List<BoardCategoryDTO> list = new ArrayList<>();
	    try {
	        // 기본 SQL 쿼리
	        sql = "SELECT * FROM BOARD_CATEGORY WHERE 1=1";
	        
	        // IS_DELETED 조건 추가
	        if (isDeleted.equals("N")) {
	            sql += " AND IS_DELETED = 'N'";
	        } else if (isDeleted.equals("Y")) {
	            sql += " AND IS_DELETED = 'Y'";
	        }
	        
	        openConn(); // 데이터베이스 연결
	        
	        pstmt = conn.prepareStatement(sql);
	        
	        rs = pstmt.executeQuery();
	        
	        // 결과를 BoardCategoryDTO 객체로 변환
	        while (rs.next()) {
	            BoardCategoryDTO category = new BoardCategoryDTO();
	            category.setCategoryNo(rs.getString("CATEGORY_NO"));
	            category.setName(rs.getString("NAME"));
	            category.setDescription(rs.getString("DESCRIPTION"));
	            category.setIsDeleted(rs.getString("IS_DELETED"));
	            list.add(category); // 리스트에 추가
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        closeConn(rs, pstmt, conn); // 자원 해제
	    }
	    return list;
	}


	public int insertBoardCategory(BoardCategoryDTO category) {
		int res = 0;

		try {
			openConn();
			sql = "insert into BOARD_CATEGORY values(?,?,?,'N')";
			int paramIndex = 1;

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(paramIndex++, category.getCategoryNo());
			pstmt.setString(paramIndex++, category.getName());
			pstmt.setString(paramIndex++, category.getDescription());

			res = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(pstmt, conn);
		}

		return res;
	}

	public int insertBoard(BoardDTO board, Integer userNo) {
		int boardNo = 0;
		try {
			openConn();

			// 게시글 삽입
			sql = "INSERT INTO BOARD (BOARD_NO, USER_NO, CATEGORY_NO, TITLE, CONTENT, VIEWS, CREATED_AT, UPDATED_AT, IS_DELETED) "
					+ "VALUES (SEQ_BOARD_NO.nextval, ?, ?, ?, ?, default, sysdate, null, 'N')";
			pstmt = conn.prepareStatement(sql);
			int paramIndex = 1;
			pstmt.setInt(paramIndex++, userNo);
			pstmt.setString(paramIndex++, board.getCategoryNo());
			pstmt.setString(paramIndex++, board.getTitle());
			pstmt.setString(paramIndex++, board.getContent());

			int res = pstmt.executeUpdate();
			if (res > 0) {
				// 게시글 번호 가져오기
				sql = "SELECT SEQ_BOARD_NO.CURRVAL FROM dual";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					boardNo = rs.getInt(1);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, conn);
		}
		return boardNo;
	}

	public BoardDTO selectBoard(int no, String userType) {
		BoardDTO board = null;
		/*
		 * 만약 UserType이 Admin이고, admin_roll이 '최고 관리자' 일경우도 아래 setter해줘야함. 이건 추후에 추가할 예정
		 */
		try {
			openConn();

			if ("ADMIN".equals(userType)) {
				sql = "SELECT b.*, bc.NAME AS CATEGORY_NAME, bc.DESCRIPTION, u.USER_ID, u.NAME AS USER_NAME, u.EMAIL AS USER_EMAIL, u.USER_TYPE, ar.ROLE_NAME "
						+ "FROM BOARD b " + "JOIN USERS u ON b.USER_NO = u.USER_NO "
						+ "JOIN ADMIN a ON u.USER_NO = a.USER_NO " + "JOIN ADMIN_ROLE ar ON a.ROLE_CODE = ar.ROLE_CODE "
						+ "JOIN BOARD_CATEGORY bc ON b.CATEGORY_NO = bc.CATEGORY_NO " + "WHERE b.BOARD_NO = ?";
			} else if ("CUSTOMER".equals(userType)) {
				sql = "SELECT b.*, bc.NAME AS CATEGORY_NAME, bc.DESCRIPTION, u.USER_ID, u.NAME AS USER_NAME, u.EMAIL AS USER_EMAIL, u.USER_TYPE, c.AGE, c.JOB, c.LOCATION "
						+ "FROM BOARD b " + "JOIN USERS u ON b.USER_NO = u.USER_NO "
						+ "JOIN CUSTOMER c ON u.USER_NO = c.USER_NO "
						+ "JOIN BOARD_CATEGORY bc ON b.CATEGORY_NO = bc.CATEGORY_NO " + "WHERE b.BOARD_NO = ?";
			}
			/* 만약 user정보 없이 삽입된 게시글일 경우 */
			sql = "SELECT b.*, bc.NAME AS CATEGORY_NAME, bc.DESCRIPTION, u.USER_ID, u.NAME AS USER_NAME, u.EMAIL AS USER_EMAIL, u.USER_TYPE FROM BOARD b LEFT JOIN USERS u ON b.USER_NO = u.USER_NO LEFT JOIN BOARD_CATEGORY bc ON b.CATEGORY_NO = bc.CATEGORY_NO WHERE b.BOARD_NO = ?";

			if (sql != null) {
				int paramIndex = 1;
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(paramIndex++, no);

				rs = pstmt.executeQuery();

				if (rs.next()) {
					board = new BoardDTO();
					board.setBoardNo(rs.getInt("BOARD_NO"));
					board.setUserNo(rs.getInt("USER_NO"));
					board.setTitle(rs.getString("TITLE"));
					board.setContent(rs.getString("CONTENT"));
					board.setCreateAt(rs.getDate("CREATED_AT"));
					board.setUpdateAt(rs.getDate("UPDATED_AT"));
					board.setIsDeleted(rs.getString("IS_DELETED"));
					board.setCategoryNo(rs.getString("CATEGORY_NO"));
					board.setCategoryName(rs.getString("CATEGORY_NAME"));
					board.setDescription(rs.getString("DESCRIPTION"));
					board.setUserId(rs.getString("USER_ID"));
					board.setUserName(rs.getString("USER_NAME"));
					board.setUserEmail(rs.getString("USER_EMAIL"));
					board.setUserType(rs.getString("USER_TYPE"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, conn);
		}

		return board;
	}

	public int updateBoard(BoardDTO board) {
		int res = 0;
		try {
			openConn();
			sql = "update board set CATEGORY_NO = ?, TITLE = ?, CONTENT = ?, UPDATED_AT = SYSDATE WHERE BOARD_NO = ?";
			int paramIndex = 1;
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(paramIndex++, board.getCategoryNo());
			pstmt.setString(paramIndex++, board.getTitle());
			pstmt.setString(paramIndex++, board.getContent());
			pstmt.setInt(paramIndex++, board.getBoardNo());

			res = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(pstmt, conn);
		}

		return res;
	}

	public int deleteBoard(int boardNo) {
		int res = 0;
		try {
			openConn();
			sql = "UPDATE BOARD SET IS_DELETED = 'Y' WHERE BOARD_NO = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);

			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(pstmt, conn);
		}
		return res;
	}

	/*
	 * by 두리 조회수 증가 로직입니다.
	 * 
	 */
	public int increaseViews(int boardNo) {
		int res = 0;

		try {
			openConn();
			int paramIndex = 1;
			sql = "UPDATE BOARD SET VIEWS = VIEWS+1 WHERE BOARD_NO=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(paramIndex, boardNo);

			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(pstmt, conn);
		}

		return res;
	}

	public BoardCategoryDTO selectBoardCategory(String categoryNo) {
		BoardCategoryDTO category = null;
		try {
			openConn();
			sql = "select * from BOARD_CATEGORY where CATEGORY_NO = ?";
			int paramIndex = 1;
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(paramIndex++, categoryNo);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				category = new BoardCategoryDTO();

				category.setCategoryNo(rs.getString("CATEGORY_NO"));
				category.setName(rs.getString("NAME"));
				category.setDescription(rs.getString("DESCRIPTION"));
				category.setIsDeleted(rs.getString("IS_DELETED"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return category;
	}

	public int updateCategory(BoardCategoryDTO category) {
		int res = 0;
		int paramIndex = 1;

		try {
			openConn();
			sql = "update BOARD_CATEGORY set NAME = ?,IS_DELETED= ? where CATEGORY_NO = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(paramIndex++, category.getName());
			pstmt.setString(paramIndex++, category.getIsDeleted());
			pstmt.setString(paramIndex++, category.getCategoryNo());

			res = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(pstmt, conn);
		}

		return res;
	}

	public int getBoardCount(BoardFilter filter) {
		int count = 0;
		sql = "SELECT COUNT(*) FROM BOARD b LEFT JOIN USERS u ON b.USER_NO = u.USER_NO WHERE 1=1";

		// 필터 조건 추가
		List<Object> params = new ArrayList<>();
		if (filter.getCategoryNo() != null && !filter.getCategoryNo().isEmpty()) {
			sql += " AND b.CATEGORY_NO = ?";
			params.add(filter.getCategoryNo());
		}
		if (filter.getUserNo() != null) {
			sql += " AND b.USER_NO = ?";
			params.add(filter.getUserNo());
		}
		if (filter.getMinViews() != null) {
			sql += " AND b.VIEWS >= ?";
			params.add(filter.getMinViews());
		}
		if (filter.getMaxViews() != null) {
			sql += " AND b.VIEWS <= ?";
			params.add(filter.getMaxViews());
		}
		if (filter.getStartDate() != null) {
			sql += " AND b.CREATED_AT >= ?";
			params.add(filter.getStartDate());
		}
		if (filter.getEndDate() != null) {
			sql += " AND b.CREATED_AT <= ?";
			params.add(filter.getEndDate());
		}
		if (filter.getIsDeleted() != null && !filter.getIsDeleted().isEmpty()) {
			sql += " AND b.IS_DELETED = ?";
			params.add(filter.getIsDeleted());
		}

		try {
			openConn();
			pstmt = conn.prepareStatement(sql);

			// 파라미터 바인딩
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(i + 1, params.get(i));
			}

			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, conn);
		}

		return count;
	}

	// 현재 아래와 같은 로직은 사용되고 있지가 않음
	/*
	 * public List<BoardDTO> searchBoardList(int currentPage, int boardLimit,
	 * BoardFilter filter) { List<BoardDTO> list = new ArrayList<>();
	 * 
	 * // 동적 SQL을 구성하기 위해 기본 SELECT 쿼리 작성 StringBuilder sql = new StringBuilder();
	 * sql.append("SELECT * FROM (SELECT row_number() OVER (ORDER BY ");
	 * 
	 * // 댓글 수 정렬 조건 추가 if (filter.getSortByCommentCount() != null &&
	 * filter.getSortByCommentCount()) { sql.append("b.COMMENT_COUNT DESC, "); }
	 * 
	 * // 기본 정렬 조건 sql.
	 * append("b.BOARD_NO ASC) AS rnum, b.*, u.USER_TYPE, u.USER_ID, u.NAME AS USER_NAME, u.EMAIL AS USER_EMAIL, bc.NAME AS CATEGORY_NAME "
	 * ); sql.append("FROM BOARD b ");
	 * sql.append("LEFT JOIN USERS u ON b.USER_NO = u.USER_NO ");
	 * sql.append("JOIN BOARD_CATEGORY bc ON b.CATEGORY_NO = bc.CATEGORY_NO ");
	 * sql.append("WHERE 1=1 ");
	 * 
	 * // 필터링 조건 추가 if (filter.getCategoryNo() != null &&
	 * !filter.getCategoryNo().isEmpty()) { sql.append(" AND b.CATEGORY_NO = ?"); }
	 * if (filter.getUserNo() != null) { sql.append(" AND b.USER_NO = ?"); } if
	 * (filter.getMinViews() != null) { sql.append(" AND b.VIEWS >= ?"); } if
	 * (filter.getMaxViews() != null) { sql.append(" AND b.VIEWS <= ?"); } if
	 * (filter.getStartDate() != null) { sql.append(" AND b.CREATED_AT >= ?"); } if
	 * (filter.getEndDate() != null) { sql.append(" AND b.CREATED_AT <= ?"); } if
	 * (filter.getIsDeleted() != null && !filter.getIsDeleted().isEmpty()) {
	 * sql.append(" AND b.IS_DELETED = ?"); }
	 * 
	 * // 페이지네이션을 위한 서브 쿼리 조건 추가 sql.append(") WHERE rnum BETWEEN ? AND ?");
	 * 
	 * try { openConn(); pstmt = conn.prepareStatement(sql.toString());
	 * 
	 * int paramIndex = 1;
	 * 
	 * // 필터 파라미터 세팅 if (filter.getCategoryNo() != null &&
	 * !filter.getCategoryNo().isEmpty()) { pstmt.setString(paramIndex++,
	 * filter.getCategoryNo()); } if (filter.getUserNo() != null) {
	 * pstmt.setInt(paramIndex++, filter.getUserNo()); } if (filter.getMinViews() !=
	 * null) { pstmt.setInt(paramIndex++, filter.getMinViews()); } if
	 * (filter.getMaxViews() != null) { pstmt.setInt(paramIndex++,
	 * filter.getMaxViews()); } if (filter.getStartDate() != null) {
	 * pstmt.setDate(paramIndex++, filter.getStartDate()); } if (filter.getEndDate()
	 * != null) { pstmt.setDate(paramIndex++, filter.getEndDate()); } if
	 * (filter.getIsDeleted() != null && !filter.getIsDeleted().isEmpty()) {
	 * pstmt.setString(paramIndex++, filter.getIsDeleted()); }
	 * 
	 * // 페이지네이션 파라미터 세팅 int startRow = (currentPage - 1) * boardLimit + 1; int
	 * endRow = startRow + boardLimit - 1; pstmt.setInt(paramIndex++, startRow);
	 * pstmt.setInt(paramIndex++, endRow);
	 * 
	 * rs = pstmt.executeQuery();
	 * 
	 * // 결과를 리스트에 담음 while (rs.next()) { BoardDTO board = new BoardDTO();
	 * board.setBoardNo(rs.getInt("BOARD_NO"));
	 * board.setTitle(rs.getString("TITLE"));
	 * board.setContent(rs.getString("CONTENT"));
	 * board.setViews(rs.getInt("VIEWS"));
	 * board.setCreateAt(rs.getDate("CREATED_AT"));
	 * board.setUpdateAt(rs.getDate("UPDATED_AT"));
	 * board.setIsDeleted(rs.getString("IS_DELETED"));
	 * board.setCategoryNo(rs.getString("CATEGORY_NO"));
	 * board.setCategoryName(rs.getString("CATEGORY_NAME"));
	 * 
	 * // 아직 회원 관련 로직이 구현되지 않았으므로 int userNo = rs.getInt("USER_NO"); if
	 * (rs.wasNull()) { board.setUserNo(null); // USER_NO가 NULL이면 null 설정 } else {
	 * board.setUserNo(userNo); // USER_NO가 NULL이 아니면 해당 값 설정 }
	 * 
	 * board.setUserId(rs.getString("USER_ID"));
	 * board.setUserEmail(rs.getString("USER_EMAIL"));
	 * board.setUserType(rs.getString("USER_TYPE")); list.add(board); }
	 * 
	 * } catch (SQLException e) { e.printStackTrace(); } finally { closeConn(rs,
	 * pstmt, conn); }
	 * 
	 * return list; }
	 */

	public List<BoardDTO> searchBoardList(int currentPage, int boardLimit, String searchKeyword, BoardFilter filter) {
		List<BoardDTO> list = new ArrayList<>();

		// 동적 SQL을 구성하기 위해 기본 SELECT 쿼리 작성
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT * FROM (SELECT row_number() OVER (ORDER BY b.BOARD_NO ASC) AS rnum, b.*, u.USER_TYPE, u.USER_ID, u.NAME AS USER_NAME, u.EMAIL AS USER_EMAIL, bc.NAME AS CATEGORY_NAME ");
		sql.append("FROM BOARD b ");
		sql.append("LEFT JOIN USERS u ON b.USER_NO = u.USER_NO ");
		sql.append("JOIN BOARD_CATEGORY bc ON b.CATEGORY_NO = bc.CATEGORY_NO ");
		sql.append("WHERE 1=1 ");

		// 검색어(제목) 필터 추가
		if (searchKeyword != null && !searchKeyword.isEmpty()) {
			sql.append(" AND b.TITLE LIKE ?");
		}

		// 필터링 조건 추가
		if (filter.getCategoryNo() != null && !filter.getCategoryNo().isEmpty()) {
			sql.append(" AND b.CATEGORY_NO = ?");
		}
		if (filter.getUserNo() != null) {
			sql.append(" AND b.USER_NO = ?");
		}
		if (filter.getMinViews() != null) {
			sql.append(" AND b.VIEWS >= ?");
		}
		if (filter.getMaxViews() != null) {
			sql.append(" AND b.VIEWS <= ?");
		}
		if (filter.getStartDate() != null) {
			sql.append(" AND b.CREATED_AT >= ?");
		}
		if (filter.getEndDate() != null) {
			sql.append(" AND b.CREATED_AT <= ?");
		}
		if (filter.getIsDeleted() != null && !filter.getIsDeleted().isEmpty()) {
			sql.append(" AND b.IS_DELETED = ?");
		}

		// 페이지네이션을 위한 서브 쿼리 조건 추가
		sql.append(") WHERE rnum BETWEEN ? AND ?");

		try {
			openConn();
			pstmt = conn.prepareStatement(sql.toString());

			int paramIndex = 1;

			// 검색어(제목) 파라미터 세팅
			if (searchKeyword != null && !searchKeyword.isEmpty()) {
				pstmt.setString(paramIndex++, "%" + searchKeyword + "%");
			}

			// 필터 파라미터 세팅
			if (filter.getCategoryNo() != null && !filter.getCategoryNo().isEmpty()) {
				pstmt.setString(paramIndex++, filter.getCategoryNo());
			}
			if (filter.getUserNo() != null) {
				pstmt.setInt(paramIndex++, filter.getUserNo());
			}
			if (filter.getMinViews() != null) {
				pstmt.setInt(paramIndex++, filter.getMinViews());
			}
			if (filter.getMaxViews() != null) {
				pstmt.setInt(paramIndex++, filter.getMaxViews());
			}
			if (filter.getStartDate() != null) {
				pstmt.setDate(paramIndex++, filter.getStartDate());
			}
			if (filter.getEndDate() != null) {
				pstmt.setDate(paramIndex++, filter.getEndDate());
			}
			if (filter.getIsDeleted() != null && !filter.getIsDeleted().isEmpty()) {
				pstmt.setString(paramIndex++, filter.getIsDeleted());
			}

			// 페이지네이션 파라미터 세팅
			int startRow = (currentPage - 1) * boardLimit + 1;
			int endRow = startRow + boardLimit - 1;
			pstmt.setInt(paramIndex++, startRow);
			pstmt.setInt(paramIndex++, endRow);

			rs = pstmt.executeQuery();

			// 결과를 리스트에 담음
			while (rs.next()) {
				BoardDTO board = new BoardDTO();
				board.setBoardNo(rs.getInt("BOARD_NO"));
				board.setTitle(rs.getString("TITLE"));
				board.setContent(rs.getString("CONTENT"));
				board.setViews(rs.getInt("VIEWS"));
				board.setCreateAt(rs.getDate("CREATED_AT"));
				board.setUpdateAt(rs.getDate("UPDATED_AT"));
				board.setIsDeleted(rs.getString("IS_DELETED"));
				board.setCategoryNo(rs.getString("CATEGORY_NO"));
				board.setCategoryName(rs.getString("CATEGORY_NAME"));

				// 아직 회원 관련 로직이 구현되지 않았으므로
				int userNo = rs.getInt("USER_NO");
				if (rs.wasNull()) {
					board.setUserNo(null); // USER_NO가 NULL이면 null 설정
				} else {
					board.setUserNo(userNo); // USER_NO가 NULL이 아니면 해당 값 설정
				}

				board.setUserId(rs.getString("USER_ID"));
				board.setUserEmail(rs.getString("USER_EMAIL"));
				board.setUserType(rs.getString("USER_TYPE"));
				list.add(board);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, conn);
		}

		return list;
	}

	/* 댓글 로직 */
	public void insertReply(BoardReplyDTO reply) {
		try {
			openConn();

			// 게시글에 첫 댓글이 달리는 경우, leftVal과 rightVal을 1과 2로 초기화
			sql = "SELECT COUNT(*) FROM BOARD_REPLY WHERE BOARD_NO = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, reply.getBoardNo());
			rs = pstmt.executeQuery();

			int count = 0;
			if (rs.next()) {
				count = rs.getInt(1);
			}

			if (count == 0) {
				// 첫 댓글
				reply.setLeftVal(1);
				reply.setRightVal(2);
				reply.setNodeLevel(1);
			} else {
				// 게시글에 다른 댓글이 있을 때, 가장 큰 right_val을 찾는다
				sql = "SELECT MAX(RIGHT_VAL) FROM BOARD_REPLY WHERE BOARD_NO = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, reply.getBoardNo());
				rs = pstmt.executeQuery();

				int maxRightVal = 0;
				if (rs.next()) {
					maxRightVal = rs.getInt(1);
				}

				// 새로운 댓글의 left_val, right_val, node_level 설정
				reply.setLeftVal(maxRightVal + 1);
				reply.setRightVal(maxRightVal + 2);
				reply.setNodeLevel(1);
			}

			// 새로운 댓글 삽입
			sql = "INSERT INTO BOARD_REPLY (REPLY_NO, BOARD_NO, USER_NO, CONTENT, LEFT_VAL, RIGHT_VAL, NODE_LEVEL, CREATED_AT, IS_DELETED) "
					+ "VALUES (SEQ_BOARD_REPLY_NO.NEXTVAL, ?, ?, ?, ?, ?, ?, SYSDATE, 'N')";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, reply.getBoardNo());
			pstmt.setInt(2, reply.getUserNo());
			pstmt.setString(3, reply.getContent());
			pstmt.setInt(4, reply.getLeftVal());
			pstmt.setInt(5, reply.getRightVal());
			pstmt.setInt(6, reply.getNodeLevel());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, conn);
		}
	}

	/* 대댓글 로직 */
	public void insertSubReply(BoardReplyDTO reply) {
		try {
			openConn();

			// 부모 노드의 right_val을 가져옴
			sql = "SELECT RIGHT_VAL, NODE_LEVEL FROM BOARD_REPLY WHERE REPLY_NO = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, reply.getParentReplyNo());
			rs = pstmt.executeQuery();

			int parentRightVal = 0;
			int parentNodeLevel = 0;
			if (rs.next()) {
				parentRightVal = rs.getInt("RIGHT_VAL");
				parentNodeLevel = rs.getInt("NODE_LEVEL");
			}

			// 부모의 right_val 이후에 위치한 모든 노드의 left_val과 right_val을 2씩 증가시킴
			sql = "UPDATE BOARD_REPLY SET RIGHT_VAL = RIGHT_VAL + 2 WHERE RIGHT_VAL >= ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, parentRightVal);
			pstmt.executeUpdate();

			sql = "UPDATE BOARD_REPLY SET LEFT_VAL = LEFT_VAL + 2 WHERE LEFT_VAL > ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, parentRightVal);
			pstmt.executeUpdate();

			// 새로운 대댓글을 삽입
			sql = "INSERT INTO BOARD_REPLY (REPLY_NO, BOARD_NO, USER_NO, CONTENT, LEFT_VAL, RIGHT_VAL, NODE_LEVEL, PARENT_REPLY_NO, CREATED_AT, IS_DELETED) "
					+ "VALUES (SEQ_BOARD_REPLY_NO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, SYSDATE, 'N')";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, reply.getBoardNo());
			pstmt.setInt(2, reply.getUserNo());
			pstmt.setString(3, reply.getContent());
			pstmt.setInt(4, parentRightVal);
			pstmt.setInt(5, parentRightVal + 1);
			pstmt.setInt(6, parentNodeLevel + 1); // 부모의 level + 1
			pstmt.setInt(7, reply.getParentReplyNo());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, conn);
		}
	}

	// 최신 댓글 리스트를 갖고 와야함
	public List<BoardReplyDTO> getCommentsByBoardNo(Integer boardNo) {
		List<BoardReplyDTO> commentList = new ArrayList<>();

		try {
			openConn();

			// 게시글 번호에 해당하는 댓글들과 사용자 정보를 가져오되, LEFT_VAL 순으로 정렬하여 계층 구조를 유지함
			sql = "SELECT r.*, u.USER_ID, u.NAME AS USER_NAME, u.EMAIL AS USER_EMAIL, u.USER_TYPE "
					+ "FROM BOARD_REPLY r " + "JOIN USERS u ON r.USER_NO = u.USER_NO "
					+ "WHERE r.BOARD_NO = ? AND r.IS_DELETED = 'N' " + "ORDER BY r.LEFT_VAL ASC";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				BoardReplyDTO reply = new BoardReplyDTO();
				reply.setReplyNo(rs.getInt("REPLY_NO"));
				reply.setBoardNo(rs.getInt("BOARD_NO"));
				reply.setUserNo(rs.getInt("USER_NO"));
				reply.setContent(rs.getString("CONTENT"));
				reply.setLeftVal(rs.getInt("LEFT_VAL"));
				reply.setRightVal(rs.getInt("RIGHT_VAL"));
				reply.setNodeLevel(rs.getInt("NODE_LEVEL"));
				reply.setParentReplyNo(rs.getInt("PARENT_REPLY_NO"));
				reply.setCreatedAt(rs.getTimestamp("CREATED_AT"));
				reply.setUpdatedAt(rs.getTimestamp("UPDATED_AT"));
				reply.setIsDeleted(rs.getString("IS_DELETED"));

				// USERS 테이블에서 가져온 추가 정보 설정
				reply.setUserId(rs.getString("USER_ID"));
				reply.setUserName(rs.getString("USER_NAME"));
				reply.setUserEmail(rs.getString("USER_EMAIL"));
				reply.setUserType(rs.getString("USER_TYPE"));

				commentList.add(reply);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, conn);
		}

		return commentList;
	}

	public int updateReply(int replyNo, String newContent) {
		int res = 0;

		try {
			openConn();

			// 댓글이 존재하는지 확인
			sql = "SELECT COUNT(*) FROM BOARD_REPLY WHERE REPLY_NO = ? AND IS_DELETED = 'N'";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, replyNo);
			rs = pstmt.executeQuery();

			if (rs.next() && rs.getInt(1) > 0) {
				// 댓글이 존재하면 내용 업데이트
				sql = "UPDATE BOARD_REPLY SET CONTENT = ?, UPDATED_AT = SYSDATE WHERE REPLY_NO = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, newContent);
				pstmt.setInt(2, replyNo);

				res = pstmt.executeUpdate();
			} else {
				// 댓글이 존재하지 않거나 삭제된 경우
				System.out.println("댓글이 존재하지 않거나 이미 삭제되었습니다.");
				return -1;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, conn);
		}

		return res;
	}

	public int deleteReply(int replyNo) {
		int result = 0;
		try {
			openConn();
			// 댓글의 IS_DELETED 필드를 'Y'로 업데이트하여 논리 삭제
			sql = "UPDATE BOARD_REPLY SET IS_DELETED = 'Y' WHERE REPLY_NO = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, replyNo);

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(pstmt, conn);
		}
		return result;
	}

	public int getCommentCount(int boardNo) {
		int count = 0;

		try {
			// 데이터베이스 연결 열기
			openConn();

			// SQL 쿼리 작성: IS_DELETED가 'N'인 댓글의 수를 계산
			String sql = "SELECT COUNT(*) FROM BOARD_REPLY WHERE BOARD_NO = ? AND IS_DELETED = 'N'";

			// PreparedStatement를 사용하여 SQL 쿼리 실행 준비
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);

			// 쿼리 실행 및 결과 획득
			rs = pstmt.executeQuery();

			// 결과를 읽어 댓글 수를 count 변수에 저장
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 리소스 정리: ResultSet, PreparedStatement, Connection을 닫기
			closeConn(rs, pstmt, conn);
		}

		return count;
	}

	public int restoreBoard(int boardNo) {
		int result = 0;

		try {
			openConn();
			sql = "UPDATE BOARD SET IS_DELETED = 'N' WHERE BOARD_NO = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(pstmt, conn);
		}

		return result;
	}

	public List<BoardFileUploadDTO> getFilesByBoardNo(int boardNo) {
		List<BoardFileUploadDTO> files = new ArrayList<>();

		try {
			openConn();
			String sql = "SELECT * FROM BOARD_FILEUPLOADS WHERE BOARD_NO = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				BoardFileUploadDTO file = new BoardFileUploadDTO();
				file.setFileNo(rs.getInt("FILE_NO"));
				file.setBoardNo(rs.getInt("BOARD_NO"));
				file.setFileUrl(rs.getString("FILE_URL"));
				file.setFileName(rs.getString("FILE_NAME"));
				file.setFileSize(rs.getLong("FILE_SIZE"));
				file.setFileType(rs.getString("FILE_TYPE"));
				file.setDescription(rs.getString("DESCRIPTION"));
				file.setUploadedAt(rs.getDate("UPLOADED_AT"));
				files.add(file);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, conn);
		}

		return files;
	}

	// 게시글 및 연관된 댓글, 파일 삭제 메서드 (ON DELETE CASCADE 적용 시)
	public boolean deleteBoardAndReplies(int boardNo) {
		boolean result = false;

		try {
			openConn();

			// 1. 게시글 삭제 (연관된 댓글과 파일은 ON DELETE CASCADE로 자동 삭제)
			String deleteBoardSQL = "DELETE FROM BOARD WHERE BOARD_NO = ?";
			pstmt = conn.prepareStatement(deleteBoardSQL);
			pstmt.setInt(1, boardNo);
			pstmt.executeUpdate();

			result = true; // 삭제 성공 시 true 반환

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			closeConn(rs, pstmt, conn);
		}

		return result;
	}

	public void boardInsertFileUpload(int boardNo, List<BoardFileUploadDTO> uploadedFiles) {

		try {
			openConn(); // DB 연결

			// 파일 리스트가 비어있지 않은 경우에만 처리
			if (uploadedFiles != null && !uploadedFiles.isEmpty()) {
				// 파일 정보를 하나씩 DB에 저장
				for (BoardFileUploadDTO file : uploadedFiles) {
					// 파일 업로드 정보를 DB에 삽입하는 SQL 쿼리
					sql = "INSERT INTO BOARD_FILEUPLOADS (FILE_NO, BOARD_NO, FILE_URL, FILE_NAME, FILE_SIZE, FILE_TYPE, DESCRIPTION, UPLOADED_AT) "
							+ "VALUES (SEQ_BOARD_FILEUPLOADS_NO.NEXTVAL, ?, ?, ?, ?, ?, ?, SYSDATE)";
					pstmt = conn.prepareStatement(sql);

					// 쿼리 파라미터 설정
					pstmt.setInt(1, boardNo); // 게시글 번호
					pstmt.setString(2, file.getFileUrl()); // 파일 URL
					pstmt.setString(3, file.getFileName()); // 파일 이름
					pstmt.setLong(4, file.getFileSize()); // 파일 크기
					pstmt.setString(5, file.getFileType()); // 파일 타입
					pstmt.setString(6, file.getDescription()); // 파일 설명 (없을 수도 있음)

					// 실행 후 성공 여부 체크
					pstmt.executeUpdate();
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, conn); // 연결 종료
		}
	}

	public int updateBoardContent(int boardNo, String content) {
		int result = 0; // 결과를 저장할 변수

		try {
			openConn(); // DB 연결
			sql = "UPDATE BOARD SET CONTENT = ? WHERE BOARD_NO = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, content); // 변경된 content
			pstmt.setInt(2, boardNo); // 게시글 번호
			result += pstmt.executeUpdate(); // 업데이트 결과를 더해줌

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<BoardDTO> getDetailBoardList(int currentPage, int boardLimit, String categoryNo) {
		List<BoardDTO> list = new ArrayList<>();

		try {
			openConn();

			// rownum을 이용하여 페이징 처리 및 카테고리 필터링
			sql = "SELECT * FROM ( "
					+ "SELECT rownum rnum, b.*, u.USER_TYPE, u.USER_ID, u.NAME AS USER_NAME, u.EMAIL AS USER_EMAIL, bc.NAME AS CATEGORY_NAME "
					+ "FROM BOARD b " + "LEFT JOIN USERS u ON b.USER_NO = u.USER_NO "
					+ "JOIN BOARD_CATEGORY bc ON b.CATEGORY_NO = bc.CATEGORY_NO " + "WHERE b.CATEGORY_NO = ? "
					+ "AND b.IS_DELETED = 'N' " + // 삭제되지 않은 게시글만 가져옴
					"ORDER BY b.BOARD_NO DESC " + ") WHERE rnum BETWEEN ? AND ?";

			int startRow = (currentPage - 1) * boardLimit + 1;
			int endRow = startRow + boardLimit - 1;

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, categoryNo);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				BoardDTO board = new BoardDTO();
				board.setBoardNo(rs.getInt("BOARD_NO"));
				board.setUserNo(rs.getInt("USER_NO"));
				board.setTitle(rs.getString("TITLE"));
				board.setContent(rs.getString("CONTENT"));
				board.setViews(rs.getInt("VIEWS"));
				board.setCreateAt(rs.getDate("CREATED_AT"));
				board.setUpdateAt(rs.getDate("UPDATED_AT"));
				board.setIsDeleted(rs.getString("IS_DELETED"));

				// 카테고리 정보 설정
				board.setCategoryNo(rs.getString("CATEGORY_NO"));
				board.setCategoryName(rs.getString("CATEGORY_NAME"));

				// 사용자 정보 설정
				board.setUserId(rs.getString("USER_ID"));
				board.setUserEmail(rs.getString("USER_EMAIL"));
				board.setUserName(rs.getString("USER_NAME"));
				board.setUserType(rs.getString("USER_TYPE"));

				list.add(board);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, conn);
		}

		return list;
	}

	public int getBoardCountByCategory(String categoryNo) {
	    int count = 0;
    	try {
    		openConn();
    		sql = "SELECT COUNT(*) FROM board WHERE CATEGORY_NO = ? AND IS_DELETED = 'N'";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, categoryNo);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeConn(rs, pstmt, conn);
		}
	    return count;
	}

	public int deleteCategory(String categoryNo) {
		int res = 0;
		try {
			openConn();
			sql = "delete board_category where category_no=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, categoryNo);
			
			res = pstmt.executeUpdate();
			
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return res;
	}





	
	
}
