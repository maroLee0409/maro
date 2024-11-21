package com.global.review.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ProductReviewDAO {
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = null;
	
	public static ProductReviewDAO instance = null;
	
	public ProductReviewDAO() {}
	
	public static ProductReviewDAO getInstance() {
		
		if(instance == null) {
			instance = new ProductReviewDAO();
		}
		
		return instance;
		
	}
	
	public void openConn() {
		
		try {

			Context initCtx = new InitialContext();

			Context ctx = (Context) initCtx.lookup("java:comp/env");

			DataSource ds = (DataSource) ctx.lookup("jdbc/myoracle");

			con = ds.getConnection();

		} catch (Exception e) {
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
			e.printStackTrace();
		}

	} // closeConn() end
	
	public void closeConn(PreparedStatement pstmt, Connection con) {
		
		try {
			
			if(pstmt != null) pstmt.close();
			
			if(con != null) con.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	} // closeConn() 메서드 end

	public int getReviewCount() {
		
		int count = 0;
		
		try {
			openConn();
			
			sql = "select count(*) from product_review";
			
			pstmt = con.prepareStatement(sql);
			
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
		
	} // getReviewCount() end

	// 전체 리뷰 수를 확인하는 메서드
	public List<ProductReviewDTO> selectReviewList(int currentPage, int boardLimit) {
		
		List<ProductReviewDTO> list = new ArrayList<ProductReviewDTO>();
		
		try {
			openConn();
			
			sql = "select * from (select row_number() over(order by review_no desc) as rnum, b.* from product_review b) where rnum >= ? and rnum <= ?";
			
			int paramIndex = 1;
			int startRow = (currentPage - 1) * boardLimit + 1;
			int endRow = startRow + boardLimit - 1;
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(paramIndex++, startRow);
			pstmt.setInt(paramIndex++, endRow);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
			
				ProductReviewDTO review = new ProductReviewDTO();
				
				review.setReview_No(rs.getInt("Review_No"));
				review.setProduct_No(rs.getInt("Product_No"));
				review.setUserNo(null);
				review.setRating(rs.getInt("Rating"));
				/* review.setcomm(rs.getString("Comm")); */
				review.setCreateAt(null);
				review.setUpdateAt(null);
				review.setIsDeleted(sql);
				
				list.add(review);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		return list;
		
	} // selectReviewList() end
	
	// 리뷰 작성 메서드 (예: 성공 or 실패)
	public int insertReview(ProductReviewDTO review, int userNo) {
		
		int result = 0; // 게시글 등록유무 확인 변수
		int count = 0; // 게시글 중 가장 큰 확인 변수
		
		try {
			openConn();

			sql = "insert into product_review values(SEQ_REVIEW_NO.nextval,SEQ_BOARD_NO.nextval,?,?)";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, count + 1);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		return result;
		
	} // insertReview() end
	
}
