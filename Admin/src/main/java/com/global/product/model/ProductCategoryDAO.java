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

import com.global.board.model.BoardCategoryDTO;


public class ProductCategoryDAO {

	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = null;
	
	// 싱글톤
	public static ProductCategoryDAO instance=null;

	// 기본생성자
	public ProductCategoryDAO() {}


	public static ProductCategoryDAO getInstance() {

		if (instance == null) {
			instance = new ProductCategoryDAO();
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



public int insertCategory(ProductCategoryDTO dto) {
		
		int result = 0;
		
		try {
		
		openConn();
		
		sql = "INSERT INTO PRODUCT_CATEGORY VALUES(?,?,?,'N')";
		
		pstmt = con.prepareStatement(sql);
		
		pstmt.setString(1, dto.getCategory_No());
		pstmt.setString(2, dto.getName());
		pstmt.setString(3, dto.getDescription());
		
		result = pstmt.executeUpdate();
		
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		
		} finally {
			closeConn(pstmt, con);
		}
		
		return result;
	} // 카테고리 등록 종료

		
	public List<ProductCategoryDTO> getCategoryList() {

		List<ProductCategoryDTO> list = new ArrayList<ProductCategoryDTO>();
		
		try {
			/* 수정사항 */
		openConn();
		
		sql = "SELECT * FROM PRODUCT_CATEGORY";
		
		pstmt = con.prepareStatement(sql);
		
		rs = pstmt.executeQuery();
		
		while (rs.next()) {
			
			ProductCategoryDTO dto = new ProductCategoryDTO();
			
			dto.setCategory_No(rs.getString("category_no"));
			dto.setName(rs.getString("name"));
			dto.setDescription(rs.getString("description"));
			
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


	public ProductCategoryDTO getCategoryContent(String category_no) {

		ProductCategoryDTO dto = null;
		
		try {

		openConn();
		
		sql = "SELECT * FROM PRODUCT_CATEGORY p FULL OUTER JOIN CATEGORY_IMAGE i on p.CATEGORY_NO = i.CATEGORY_NO WHERE p.CATEGORY_NO = ? ";
		
		pstmt = con.prepareStatement(sql);
		
		pstmt.setString(1, category_no);
		
		rs = pstmt.executeQuery();
		
		if(rs.next()) {
			
			dto = new ProductCategoryDTO();
			
			dto.setCategory_No(rs.getString("category_no"));
			dto.setName(rs.getString("name"));
			dto.setDescription(rs.getString("description"));
			dto.setAlt_text(rs.getString("ALT_TEXT"));
			dto.setImage_url(rs.getString("IMAGE_URL"));
			dto.setUpload_date(rs.getDate("UPLOAD_DATE"));
			
		}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con); 
		}
		
		
		
		
		return dto;
	}

	
	public int getProductCategoryListCount() {
		int count = 0;
		
		try {
		
			openConn();
			
			sql = "SELECT COUNT(*) FROM PRODUCT_CATEGORY ORDER BY 1";
			
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
			
		
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeConn(rs, pstmt, con);
		}
		return count;
	}


	public List<ProductCategoryDTO> getProductCategory(int currentPage, int boardLimit) {
		
		List<ProductCategoryDTO> list = new ArrayList<>();
		
		sql = "SELECT * FROM ( SELECT ROW_NUMBER() OVER (ORDER BY CATEGORY_NO  ASC) AS rnum, B.* FROM PRODUCT_CATEGORY B )LEFT JOIN CATEGORY_IMAGE USING(CATEGORY_NO)  WHERE rnum BETWEEN ? AND ?";
		
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
			ProductCategoryDTO dto = new ProductCategoryDTO();

			dto.setCategory_No(rs.getString("CATEGORY_NO"));
			dto.setName(rs.getString("NAME"));
			dto.setDescription(rs.getString("DESCRIPTION"));
			dto.setAlt_text(rs.getString("ALT_TEXT"));
			dto.setImage_url(rs.getString("IMAGE_URL"));
			dto.setPcimage_no(rs.getInt("PCIMAGE_NO"));
			dto.setUpload_date(rs.getDate("UPLOAD_DATE"));

			list.add(dto);
		}
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		closeConn(rs, pstmt, con);
	}
	return list;
}


	public int ModifyCategory(ProductCategoryDTO dto) {
		
		int result = 0;
		
		try {
		
		openConn();
		
		sql = "UPDATE PRODUCT_CATEGORY SET NAME = ?, DESCRIPTION = ? WHERE CATEGORY_NO = ?";
		
		pstmt = con.prepareStatement(sql);

		pstmt.setString(1, dto.getName());
		pstmt.setString(2, dto.getDescription());
		pstmt.setString(3, dto.getCategory_No());
		
		result = pstmt.executeUpdate();
		
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConn(pstmt, con);
		}
		
		
		
		return result;
	}


		 public int DeleteCategory(String category_no) {
			 
			 int result = 0;

			 try {
			 
			 openConn();
			  
			 sql = "SELECT * FROM PRODUCT_CATEGORY WHERE CATEGORY_NO = ?";
			 
			
			 pstmt = con.prepareStatement(sql);
			 
			 pstmt.setString(1, category_no);
			 
			 rs = pstmt.executeQuery();
			 
			 if(rs.next()) {
				 sql = "DELETE FROM PRODUCT_CATEGORY WHERE CATEGORY_NO = ?";
				 
				 pstmt = con.prepareStatement(sql);
				 
				 pstmt.setString(1, category_no);
				 
				 result = pstmt.executeUpdate();
			 }
			 
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				closeConn(rs, pstmt, con);
			}
			 
		return result;
	}


		public List<ProductCategoryDTO> selectProductCategoryList(int currentPage, int boardLimit, char status) {
			List<ProductCategoryDTO> list = new ArrayList<>();
			
			sql = "SELECT * FROM ( SELECT row_number() OVER (ORDER BY CATEGORY_NO ASC) AS rnum, P.* FROM PRODUCT_CATEGORY P)JOIN CATEGORY_IMAGE USING(CATEGORY_NO)WHERE rnum BETWEEN ? AND ?";

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
					ProductCategoryDTO category = new ProductCategoryDTO();
					category.setCategory_No(rs.getString("CATEGORY_NO"));
					category.setName(rs.getString("NAME"));
					category.setDescription(rs.getString("DESCRIPTION"));
					category.setAlt_text(rs.getString("ALT_TEXT"));
					category.setImage_url(rs.getString("IMAGE_URL"));
					category.setPcimage_no(rs.getInt("PCIMAGE_NO"));
					category.setUpload_date(rs.getDate("UPLOAD_DATE"));


					list.add(category);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConn(rs, pstmt, con);
			}
			return list;
		}


		public int getProductCategoryCount(char status) {

			int count = 0;

			try {
				openConn();
				sql = "select count(*) from PRODUCT_CATEGORY";
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


		
	}