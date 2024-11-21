package com.global.product.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.global.admin.model.AdminDTO;
import com.global.board.model.BoardCategoryDTO;
import com.global.board.model.BoardDTO;
import com.global.board.model.BoardFilter;

public class ProductDAO {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = null;

	// 싱글톤
	public static ProductDAO instance = null;

	// 기본생성자
	public ProductDAO() {
	}

	public static ProductDAO getInstance() {

		if (instance == null) {
			instance = new ProductDAO();
		}
		return instance;
	}

	public void openConn() {

		try {

			// 1단계 : JNDI 서버 객체 생성
			// 자바의 네이밍 서비스(JNDI)에서 이름과 실제 객체를
			// 연결해 주는 개념이 Context 객체이며, InitialContext 객체는
			// 네이밍 서비스를 이용하기 위한 시작점이 됨.
			Context initCtx = new InitialContext();

			// 2단계 : Context 객체를 얻어와야 함.
			// "java:comp/env"라는 이름의 인수로 Context 객체를 얻어옴.
			// "java:comp/env"는 현재 웹 애플리케이션에서
			// 네이밍 서비스를 이용 시 루트 디렉토리라고 생각하면 됨.
			// 즉, 현재 웹 애플리케이션이 사용할 수 있는 모든 자원은
			// "java:comp/env" 아래에 위치를 하게 됨.
			Context ctx = (Context) initCtx.lookup("java:comp/env");

			// 3단계 : lookup() 메서드를 이용하여 매칭되는 커넥션을 찾아옴.
			// "java:comp/env" 아래에 위치한 "jdbc/myoracle" 자원을
			// 얻어옴. 이 자원이 바로 데이터 소스(커넥션풀)임.
			// 여기서 "jdbc/myoracle" 은 context.xml 파일에 추가했던
			// <Resource> 태그 안에 있던 name 속성의 값임.
			DataSource ds = (DataSource) ctx.lookup("jdbc/myoracle");

			// 4단계 : DataSource 객체를 이용하여 커넥션을 하나 가져오면 됨.
			con = ds.getConnection();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	} // openConn() end

	// DB에 연결된 자원을 종료하는 메서드.
	public void closeConn(ResultSet rs, PreparedStatement pstmt, Connection con) {

		try {

			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (con != null)
				con.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	} // closeConn() end

	// jsp_bbs 테이블의 전체 게시물을 조회하는 메서드.

	public void closeConn(PreparedStatement pstmt, Connection con) {

		try {

			if (pstmt != null)
				pstmt.close();
			if (con != null)
				con.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	} // closeConn() end

	public int insertProduct(ProductDTO dto) {

		int result = 0;
		int paramIndex = 1;

		try {

			openConn();

			sql = "INSERT INTO PRODUCT VALUES(SEQ_PRODUCT_NO.NEXTVAL,?,?,?,?,?,DEFAULT,SYSDATE,null, 'N',DEFAULT,?)";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(paramIndex++, dto.getCategory_no());
			pstmt.setString(paramIndex++, dto.getName());
			pstmt.setString(paramIndex++, dto.getDescription());
			pstmt.setInt(paramIndex++, dto.getPrice());
			pstmt.setInt(paramIndex++, dto.getStock_quantity());
			pstmt.setInt(paramIndex++, dto.getUser_no());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(pstmt, con);
		}

		return result;
	}

	public ProductDTO getProduct(int product_no) {
		ProductDTO product = null;
		try {
			openConn();

			sql = "SELECT * FROM PRODUCT WHERE PRODUCT_NO = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, product_no);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				product = new ProductDTO();
				product.setProduct_no(rs.getInt(1));
				product.setCategory_no(rs.getString(2));
				product.setName(rs.getString(3));
				product.setDescription(rs.getString(4));
				product.setPrice(rs.getInt(5));
				product.setStock_quantity(rs.getInt(6));
				product.setViews(rs.getInt(7));
				product.setCreated_at(rs.getDate(8));
				product.setUpdated_at(rs.getDate(9));
				product.setIs_deleted(rs.getString(10));
				product.setTotal_sales(rs.getInt(11));
				product.setUser_no(rs.getInt(12));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return product;
	}

	public List<ProductDTO> getProudctList() {

		List<ProductDTO> list = new ArrayList<ProductDTO>();

		try {

			openConn();

			sql = "SELECT * " + "FROM PRODUCT " + "JOIN PRODUCT_IMAGE using(PRODUCT_NO) ";

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				ProductDTO dto = new ProductDTO();

				dto.setProduct_no(rs.getInt("product_no"));
				dto.setCategory_no(rs.getString("category_no"));
				dto.setImage_url(rs.getString("image_url"));
				dto.setName(rs.getString("name"));
				dto.setDescription(rs.getString("description"));
				dto.setPrice(rs.getInt("price"));
				dto.setStock_quantity(rs.getInt("stock_quantity"));
				dto.setViews(rs.getInt("views"));
				dto.setCreated_at(rs.getDate("created_at"));
				dto.setUpdated_at(rs.getDate("updated_at"));
				dto.setIs_deleted(rs.getString("is_deleted"));
				dto.setTotal_sales(rs.getInt("total_sales"));
				dto.setUser_no(rs.getInt("user_no"));

				list.add(dto);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);

		}

		return list;

	}

	public ProductDTO getProductContent(String product_no) {

		ProductDTO dto = null;

		try {

			openConn();

			sql = "SELECT B.PRODUCT_NO, B.CATEGORY_NO, B.NAME, B.DESCRIPTION AS PRODUCT_DESCRIPTION, B.PRICE, B.STOCK_QUANTITY, B.VIEWS, B.CREATED_AT, B.UPDATED_AT, B.IS_DELETED, B.TOTAL_SALES, B.USER_NO, PI.IMAGE_URL, PI.DESCRIPTION AS IMG_INFO FROM PRODUCT B JOIN PRODUCT_IMAGE PI ON B.PRODUCT_NO = PI.PRODUCT_NO WHERE B.PRODUCT_NO = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, product_no);

			rs = pstmt.executeQuery();

			if (rs.next()) {

				dto = new ProductDTO();

				dto.setProduct_no(rs.getInt("product_no"));
				dto.setCategory_no(rs.getString("category_no"));
				dto.setImage_url(rs.getString("image_url"));
				dto.setImg_description(rs.getString("IMG_INFO"));
				dto.setName(rs.getString("name"));
				dto.setDescription(rs.getString("PRODUCT_DESCRIPTION"));
				dto.setPrice(rs.getInt("price"));
				dto.setStock_quantity(rs.getInt("stock_quantity"));
				dto.setViews(rs.getInt("views"));
				dto.setCreated_at(rs.getDate("created_at"));
				dto.setUpdated_at(rs.getDate("updated_at"));
				dto.setIs_deleted(rs.getString("is_deleted"));
				dto.setTotal_sales(rs.getInt("total_sales"));
				dto.setUser_no(rs.getInt("user_no"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return dto;
	}

	public int getProductListCount() {
		int count = 0;

		try {

			openConn();

			sql = "SELECT COUNT(*) FROM PRODUCT ORDER BY 1";

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				count = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return count;

	}

	public List<ProductDTO> getProductPage(int currentPage, int boardLimit) {

		List<ProductDTO> list = new ArrayList<>();

		try {

			int startRow = (currentPage - 1) * boardLimit + 1;
			int endRow = startRow + boardLimit - 1;

			openConn();

			sql = "SELECT COUNT(*) FROM PRODUCT ORDER BY 1";

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);

			rs = pstmt.executeQuery();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return list;
	}

	public int modifyProduct(ProductDTO product) {

		int check = 0;
		int paramIndex = 1;

		try {

			openConn();

			sql = "UPDATE PRODUCT SET CATEGORY_NO = ?, NAME = ?, DESCRIPTION = ?, PRICE = ?, STOCK_QUANTITY = ?, UPDATED_AT = SYSDATE, IS_DELETED = ? WHERE PRODUCT_NO = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(paramIndex++, product.getCategory_no());
			pstmt.setString(paramIndex++, product.getName());
			pstmt.setString(paramIndex++, product.getDescription());
			pstmt.setInt(paramIndex++, product.getPrice());
			pstmt.setInt(paramIndex++, product.getStock_quantity());
			pstmt.setString(paramIndex++, product.getIs_deleted());
			pstmt.setInt(paramIndex++, product.getProduct_no());

			check = pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(pstmt, con);
		}

		return check;
	}

	public int DeleteProduct(int product_no) {

		int check = 0;

		try {

			openConn();

			sql = "SELECT * FROM PRODUCT WHERE PRODUCT_NO = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, product_no);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				sql = "DELETE FROM PRODUCT WHERE PRODUCT_NO = ?";

				pstmt = con.prepareStatement(sql);

				pstmt.setInt(1, product_no);

				check = pstmt.executeUpdate();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);

		}

		return check;
	}

	public List<ProductDTO> getProduct(int currentPage, int boardLimit) {

		List<ProductDTO> list = new ArrayList<>();

		sql = "SELECT"
				+ " B.PRODUCT_NO, B.CATEGORY_NO, B.NAME, B.DESCRIPTION AS PRODUCT_DESCRIPTION, B.PRICE, B.STOCK_QUANTITY, B.VIEWS, B.CREATED_AT, B.UPDATED_AT, B.IS_DELETED, B.TOTAL_SALES, B.USER_NO, PI.IMAGE_URL,"
				+ " PI.DESCRIPTION AS IMG_INFO FROM (SELECT ROW_NUMBER() OVER (ORDER BY CATEGORY_NO ASC) AS rnum, B.* FROM PRODUCT B) B JOIN PRODUCT_IMAGE PI ON B.PRODUCT_NO = PI.PRODUCT_NO WHERE rnum BETWEEN ? AND ?";

		try {

			openConn();

			int paramIndex = 1;
			int startRow = (currentPage - 1) * boardLimit + 1;
			int endRow = startRow + boardLimit - 1;

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(paramIndex++, startRow);
			pstmt.setInt(paramIndex++, endRow);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				ProductDTO dto = new ProductDTO();

				dto.setProduct_no(rs.getInt("product_no"));
				dto.setCategory_no(rs.getString("category_no"));
				dto.setImage_url(rs.getString("image_url"));
				dto.setImg_description(rs.getString("IMG_INFO"));
				dto.setName(rs.getString("name"));
				dto.setDescription(rs.getString("PRODUCT_DESCRIPTION"));
				dto.setPrice(rs.getInt("price"));
				dto.setStock_quantity(rs.getInt("stock_quantity"));
				dto.setViews(rs.getInt("views"));
				dto.setCreated_at(rs.getDate("created_at"));
				dto.setUpdated_at(rs.getDate("updated_at"));
				dto.setIs_deleted(rs.getString("is_deleted"));
				dto.setTotal_sales(rs.getInt("total_sales"));
				dto.setUser_no(rs.getInt("user_no"));

				list.add(dto);

			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			closeConn(rs, pstmt, con);

		}

		return list;
	}

	public List<ProductDTO> selectProductList(int currentPage, int boardLimit, char status) {

		List<ProductDTO> list = new ArrayList<>();

		sql = "SELECT"
				+ " B.PRODUCT_NO, B.CATEGORY_NO, B.NAME, B.DESCRIPTION AS PRODUCT_DESCRIPTION, B.PRICE, B.STOCK_QUANTITY, B.VIEWS, B.CREATED_AT, B.UPDATED_AT, B.IS_DELETED, B.TOTAL_SALES, B.USER_NO, PI.IMAGE_URL,"
				+ " PI.DESCRIPTION AS IMG_INFO FROM (SELECT ROW_NUMBER() OVER (ORDER BY CATEGORY_NO ASC) AS rnum, B.* FROM PRODUCT B) B JOIN PRODUCT_IMAGE PI ON B.PRODUCT_NO = PI.PRODUCT_NO WHERE rnum BETWEEN ? AND ?";

		try {

			openConn();

			int paramIndex = 1;
			int startRow = (currentPage - 1) * boardLimit + 1;
			int endRow = startRow + boardLimit - 1;

			pstmt = con.prepareStatement(sql);

			pstmt.setString(paramIndex++, String.valueOf(status));
			pstmt.setInt(paramIndex++, startRow);
			pstmt.setInt(paramIndex++, endRow);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				ProductDTO dto = new ProductDTO();

				dto.setProduct_no(rs.getInt("product_no"));
				dto.setCategory_no(rs.getString("category_no"));
				dto.setImage_url(rs.getString("image_url"));
				dto.setImg_description(rs.getString("IMG_INFO"));
				dto.setName(rs.getString("name"));
				dto.setDescription(rs.getString("PRODUCT_DESCRIPTION"));
				dto.setPrice(rs.getInt("price"));
				dto.setStock_quantity(rs.getInt("stock_quantity"));
				dto.setViews(rs.getInt("views"));
				dto.setCreated_at(rs.getDate("created_at"));
				dto.setUpdated_at(rs.getDate("updated_at"));
				dto.setIs_deleted(rs.getString("is_deleted"));
				dto.setTotal_sales(rs.getInt("total_sales"));
				dto.setUser_no(rs.getInt("user_no"));

				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return list;
	}

	public int getProductCount(char status) {

		int count = 0;

		try {
			openConn();
			sql = "select count(*) from PRODUCT";
			// 상태에 따라 SQL 조건 추가
			if (status == 'Y') {
				sql += " WHERE is_deleted = 'Y' order by 1 desc";
			} else if (status == 'N') {
				sql += " WHERE is_deleted = 'N' order by 1 desc";
			}
			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return count;
	}

	// 조회수 증가는 프론트 에서만!
	public int increaseViews(String product_no) {
		int result = 0;

		try {
			openConn();
			int paramIndex = 1;
			sql = "UPDATE PRODUCT SET VIEWS = VIEWS+1 WHERE PRODUCT_NO = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(paramIndex, product_no);

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(pstmt, con);
		}

		return result;
	}

	public ProductDTO selectProduct(String product_no, String userType) {
		ProductDTO dto = null;
		
//		try {
//			openConn();
//
//			if ("ADMIN".equals(userType)) {
//				sql = "SELECT P.*, bc.NAME AS CATEGORY_NAME, bc.DESCRIPTION, u.USER_ID, u.NAME AS USER_NAME, u.EMAIL AS USER_EMAIL, u.USER_TYPE, ar.ROLE_NAME "
//						+ "FROM PRODUCT P " + "JOIN USERS u ON P.USER_NO = u.USER_NO "
//						+ "JOIN ADMIN a ON u.USER_NO = a.USER_NO " + "JOIN ADMIN_ROLE ar ON a.ROLE_CODE = ar.ROLE_CODE "
//						+ "JOIN BOARD_CATEGORY bc ON b.CATEGORY_NO = bc.CATEGORY_NO " + "WHERE b.BOARD_NO = ?";
//			} else if ("CUSTOMER".equals(userType)) {
//				sql = "SELECT b.*, bc.NAME AS CATEGORY_NAME, bc.DESCRIPTION, u.USER_ID, u.NAME AS USER_NAME, u.EMAIL AS USER_EMAIL, u.USER_TYPE, c.AGE, c.JOB, c.LOCATION "
//						+ "FROM BOARD b " + "JOIN USERS u ON b.USER_NO = u.USER_NO "
//						+ "JOIN CUSTOMER c ON u.USER_NO = c.USER_NO "
//						+ "JOIN BOARD_CATEGORY bc ON b.CATEGORY_NO = bc.CATEGORY_NO " + "WHERE b.BOARD_NO = ?";
//			}
//			/* 만약 user정보 없이 삽입된 게시글일 경우 */
//			sql = "SELECT b.*, bc.NAME AS CATEGORY_NAME, bc.DESCRIPTION, u.USER_ID, u.NAME AS USER_NAME, u.EMAIL AS USER_EMAIL, u.USER_TYPE FROM BOARD b LEFT JOIN USERS u ON b.USER_NO = u.USER_NO LEFT JOIN BOARD_CATEGORY bc ON b.CATEGORY_NO = bc.CATEGORY_NO WHERE b.BOARD_NO = ?";
//
//			if (sql != null) {
//				int paramIndex = 1;
//				pstmt = con.prepareStatement(sql);
//				pstmt.setString(paramIndex++, product_no);
//
//				rs = pstmt.executeQuery();
//
//				if (rs.next()) {
//			dto.setProduct_no(rs.getInt("product_no"));
//			dto.setCategory_no(rs.getString("category_no"));
//			dto.setImage_url(rs.getString("image_url"));
//			dto.setImg_description(rs.getString("IMG_INFO"));
//			dto.setName(rs.getString("name"));
//			dto.setDescription(rs.getString("PRODUCT_DESCRIPTION"));
//			dto.setPrice(rs.getInt("price"));
//			dto.setStock_quantity(rs.getInt("stock_quantity"));
//			dto.setViews(rs.getInt("views"));
//			dto.setCreated_at(rs.getDate("created_at"));
//			dto.setUpdated_at(rs.getDate("updated_at"));
//			dto.setIs_deleted(rs.getString("is_deleted"));
//			dto.setTotal_sales(rs.getInt("total_sales"));
//			dto.setUser_no(rs.getInt("user_no"));
//				}
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			closeConn(rs, pstmt, con);
//		}
		// 모르겠음.

		return dto;
	}

	public int getProductCount(ProductFilter filter) {

		int count = 0;
		
		sql = "SELECT COUNT (*) FROM PRODUCT p LEFT JOIN USERS u ON p.USER_NO = u.USER_NO WHERE 1=1"; // 이거 질문하기
		
		List<Object> params = new ArrayList<>();
		if (filter.getCategoryNo() != null && !filter.getCategoryNo().isEmpty()) {
			sql += " AND p.CATEGORY_NO = ?";
			params.add(filter.getCategoryNo());
		}
		if (filter.getUserNo() != null) {
			sql += " AND p.USER_NO = ?";
			params.add(filter.getUserNo());
		}
		if (filter.getMinViews() != null) {
			sql += " AND p.VIEWS >= ?";
			params.add(filter.getMinViews());
		}
		if (filter.getMaxViews() != null) {
			sql += " AND p.VIEWS <= ?";
			params.add(filter.getMaxViews());
		}
		if (filter.getMinPrice() != null) {
			sql += " AND p.PRICE >= ?";
			params.add(filter.getMinViews());
		}
		if (filter.getMaxPrice() != null) {
			sql += " AND p.PRICE <= ?";
			params.add(filter.getMaxViews());
		}
		if (filter.getStartDate() != null) {
			sql += " AND p.CREATED_AT >= ?";
			params.add(filter.getStartDate());
		}
		if (filter.getEndDate() != null) {
			sql += " AND p.CREATED_AT <= ?";
			params.add(filter.getEndDate());
		}
		if (filter.getIsDeleted() != null && !filter.getIsDeleted().isEmpty()) {
			sql += " AND p.IS_DELETED = ?";
			params.add(filter.getIsDeleted());
		}
		try {

		openConn();
		
		pstmt = con.prepareStatement(sql);
		
		for (int i = 0; i < params.size(); i++) {
			pstmt.setObject(i + 1, params.get(i));
		}
		
		rs = pstmt.executeQuery();
		if(rs.next()) {
			count = rs.getInt(1);
		}
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		
		return count;
	}

	public List<ProductDTO> searchProductList(int currentPage, int boardLimit, String searchKeyword, ProductFilter filter) {
		List<ProductDTO> list = new ArrayList<>();
		
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT * FROM (SELECT row_number() OVER (ORDER BY p.PRODUCT_NO ASC) AS rnum, p.*, u.USER_TYPE, u.USER_ID, u.NAME AS USER_NAME, u.EMAIL AS USER_EMAIL, pc.NAME AS CATEGORY_NAME ");
		sql.append("FROM PRODUCT p ");
		sql.append("LEFT JOIN USERS u ON p.USER_NO = u.USER_NO ");
		sql.append("JOIN PRODUCT_CATEGORY pc ON p.CATEGORY_NO = pc.CATEGORY_NO ");
		sql.append("WHERE 1=1 ");
		
		// 검색어(제목) 필터 추가
		if (searchKeyword != null && !searchKeyword.isEmpty()) {
			sql.append(" AND p.NAME LIKE ?");
		}

		// 필터링 조건 추가
		if (filter.getCategoryNo() != null && !filter.getCategoryNo().isEmpty()) {
			sql.append(" AND p.CATEGORY_NO = ?");
		}
		if (filter.getUserNo() != null) {
			sql.append(" AND p.USER_NO = ?");
		}
		if (filter.getMinViews() != null) {
			sql.append(" AND p.VIEWS >= ?");
		}
		if (filter.getMaxViews() != null) {
			sql.append(" AND p.VIEWS <= ?");
		}
		if (filter.getMinPrice() != null) {
			sql.append(" AND p.PRICE >= ?");
		}
		if (filter.getMaxPrice() != null) {
			sql.append(" AND p.PRICE <= ?");
		}
		if (filter.getStartDate() != null) {
			sql.append(" AND p.CREATED_AT >= ?");
		}
		if (filter.getEndDate() != null) {
			sql.append(" AND p.CREATED_AT <= ?");
		}
		if (filter.getIsDeleted() != null && !filter.getIsDeleted().isEmpty()) {
			sql.append(" AND p.IS_DELETED = ?");
		}

		// 페이지네이션을 위한 서브 쿼리 조건 추가
		sql.append(") WHERE rnum BETWEEN ? AND ?");

		try {
		
		openConn();
		
		pstmt = con.prepareStatement(sql.toString());
		
		int paramIndex = 1;
		
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
		if (filter.getMinPrice() != null) {
			pstmt.setInt(paramIndex++, filter.getMinPrice());
		}
		if (filter.getMaxPrice() != null) {
			pstmt.setInt(paramIndex++, filter.getMaxPrice());
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

		int startRow = (currentPage - 1) * boardLimit + 1;
		int endRow = startRow + boardLimit - 1;
		pstmt.setInt(paramIndex++, startRow);
		pstmt.setInt(paramIndex++, endRow);

		rs = pstmt.executeQuery();

		// 결과를 리스트에 담음
		while (rs.next()) {
			ProductDTO product = new ProductDTO();
			product.setProduct_no(rs.getInt("PRODUCT_NO"));
			product.setName(rs.getString("NAME"));
			product.setDescription(rs.getString("DESCRIPTION"));
			product.setViews(rs.getInt("VIEWS"));
			product.setPrice(rs.getInt("PRICE"));
			product.setCreated_at(rs.getDate("CREATED_AT"));
			product.setUpdated_at(rs.getDate("UPDATED_AT"));
			product.setIs_deleted(rs.getString("IS_DELETED"));
			product.setCategory_no(rs.getString("CATEGORY_NO"));
			product.setCategory_name(rs.getString("CATEGORY_NAME"));
			
			// 아직 회원 관련 로직이 구현되지 않았으므로
			int userNo = rs.getInt("USER_NO");
			if (rs.wasNull()) {
				product.setUser_no((Integer) null); // USER_NO가 NULL이면 null 설정
			} else {
				product.setUser_no(userNo); // USER_NO가 NULL이 아니면 해당 값 설정
			}

			product.setUserId(rs.getString("USER_ID"));
			product.setUserEmail(rs.getString("USER_EMAIL"));
			product.setUserType(rs.getString("USER_TYPE"));
			list.add(product);
		}
		
		for(ProductDTO product: list) {
			System.out.println(product.getProduct_no());
		}
			
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConn(rs, pstmt, con);
		}

		return list;
	}

	public List<ProductCategoryDTO> selectProductCategoryList() {
		List<ProductCategoryDTO> list = new ArrayList<>();
		try {
			sql = "select * from PRODUCT_CATEGORY order by 1 asc";
			openConn();
			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				ProductCategoryDTO category = new ProductCategoryDTO();
				category.setCategory_No(rs.getString("CATEGORY_NO"));
				category.setName(rs.getString("NAME"));
				category.setDescription(rs.getString("DESCRIPTION"));
				list.add(category);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return list;
	}

	public ProductDTO getProductBuyContent(String product_no) {

		ProductDTO dto = null;

		try {

			openConn();

			sql = "SELECT B.PRODUCT_NO, B.NAME, B.PRICE, PI.IMAGE_URL FROM PRODUCT B JOIN PRODUCT_IMAGE PI ON B.PRODUCT_NO = PI.PRODUCT_NO WHERE B.PRODUCT_NO = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, product_no);

			rs = pstmt.executeQuery();

			if (rs.next()) {

				dto = new ProductDTO();

				dto.setProduct_no(rs.getInt("PRODUCT_NO"));
				dto.setImage_url(rs.getString("image_url"));
				dto.setName(rs.getString("name"));
				dto.setPrice(rs.getInt("price"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}

		return dto;
	}


}

	
