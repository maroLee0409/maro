package com.global.cart.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class CartDAO {
	
	//DB와 연결하는 객체
	Connection con = null;
	//DB에 SQL문을 전송하는 객체
	PreparedStatement pstmt = null;
	//SQL문을 실행한 후 결과값을 가지고 있는 객체
	ResultSet rs = null;
	//SQL문을 저장하는 변수
	String sql = null;
	
	private static CartDAO instance =null;
	
	public CartDAO() {}; //기본생성자
	
	// 외부에서 접근할 수 있도록 해 주는 메서드
	public static CartDAO getInstanceCart() {
		
		if(instance==null) {
				instance = new CartDAO();
			}
		
		return instance;
		
	} //getInstance() end
	
	//DB연동하는 작업을 진행하는 메서드
	public void openConn() {
		try {
			
			// 1단계 : JNDI 서버 객체 생성.
			// 자바의 네이밍 서비스(JNDI)에서 이름과 실제 객체를
			// 연결해 주는 개념이 Context 객체이며, InitialContext
			// 객체는 네이밍 서비스를 이용하기 위한 시작점이 됨. 
			Context initCtx = new InitialContext();
			
			// 2단계 : Context 객체를 얻어와야 함.
			// "java:comp/env"라는 이름의 인수로 Context 객체를 얻어옴.
			// "java:comp/env"는 현제 웹 애플리케이션에서
			// 네이밍 서비스를 이용 시 루트 디렉토리라고 생각하면 됨.
			// 즉, 현재 웹 애플리케이션이 사용할 수 있는 모든 자원은
			// "java:comp/env" 아래에 위치를 하게 됨.
			Context ctx =
					(Context)initCtx.lookup("java:comp/env");
			
			// 3단계 : lookup() 메서드를 이용하여 매칭되는 커넥션을 찾아옴.
			// "java:comp/env" 아래에 위치한 "jdbc/myoracle" 자원을
			// 얻어옴. 이 자원이 바로 데이터 소스(커넥션풀)임.
			// 여기서 "jdbc/myoracle" 은 context.xml 파일에 추가했던
			// <Resource> 태그 안에 있던 name 속성의 값임.
			DataSource ds = (DataSource)ctx.lookup("jdbc/myoracle");
			
			// 4단계 : DataSource 객체를 이용하여 커넥션을 하나 가져오면 됨.
			con = ds.getConnection();
			
			} 
		catch (Exception e) {
				e.printStackTrace();
			}
		
	} //openConn() end
	
	//DB연동하는 작업을 종료하는 메서드 (rs,pstmt,con)
	public void closeConn(ResultSet rs, Statement stmt, Connection con) {
		
			try {
				
				if(rs != null && !rs.isClosed()) rs.close();
				if(stmt != null && !stmt.isClosed()) stmt.close();
				if(con != null && !con.isClosed()) con.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}//closeConn(rs,pstmt,con) end

	//DB연동하는 작업을 종료하는 메서드
	public void closeConn(Statement stmt, Connection con) {
			
				try {
					
					if(stmt != null && !stmt.isClosed()) stmt.close();
					if(con != null && ! con.isClosed()) con.close();
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
	}//closeConn(pstmt, con) end
	

	public int getCartCount() {
		int result = 0;
		try {
			
			openConn();
			
			sql = "SELECT COUNT(*) FROM cart LEFT JOIN users USING(user_no)";
			pstmt = con.prepareStatement(sql);
			
			rs= pstmt.executeQuery();
			
		} catch (SQLException e) {
			e.printStackTrace();
				}finally {
					closeConn(rs, pstmt, con);
						}
		
		return result;
		
	} //getCartCount end

	public List<CartDTO> selectCartList(int currentPage, int cartLimit) {
		List<CartDTO> list = new ArrayList<>();
		
		// 현재 회원 관련된 로직이 미구현 상태이므로 임시로 아래 쿼리를 사용합니다.
		sql = "SELECT * FROM (SELECT row_number() OVER (ORDER BY c.CART_NO DESC) AS rnum, c.*, u.USER_TYPE, u.NAME AS USER_NAME FROM CART c LEFT JOIN USERS u ON c.USER_NO = u.USER_NO ) WHERE rnum BETWEEN ? AND ?";

		try {
			openConn();
			int paramIndex = 1;
			int startRow = (currentPage - 1) * cartLimit + 1;
			int endRow = startRow + cartLimit - 1;

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(paramIndex++, startRow);
			pstmt.setInt(paramIndex++, endRow);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				CartDTO cart = new CartDTO();
				cart.setCart_no(rs.getInt("CART_NO"));

				// 아직 회원 관련 로직이 구현되지 않았으므로
				int userNo = rs.getInt("USER_NO");
				if (rs.wasNull()) {
					cart.setUser_no((Integer) null);; // USER_NO가 NULL이면 null 설정
				} else {
					cart.setUser_no(userNo);; // USER_NO가 NULL이 아니면 해당 값 설정
				}

				cart.setCart_no(rs.getInt("CART_NO"));

				cart.setCreated_at(rs.getDate("CREATED_AT"));


				/* USERS 정보도 필요하므로 SET */

				cart.setUser_no((rs.getInt("USER_NO")));;

				
				cart.setUser_name(rs.getString("USER_NAME"));
				cart.setUser_type(rs.getString("USER_TYPE"));

				list.add(cart);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return list;
	}
	




	//cart 테이블 정보를 삭제하는 메서드
	public int deleteCart(int no) {
		int result =0;
		
		try {
			
			openConn();
		
			sql=" DELETE FROM cart_item where CART_NO = ? ";
					
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, no);
			
			result = pstmt.executeUpdate();
			
			sql ="delete from cart where cart_no =?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, no);
			
			result = pstmt.executeUpdate();
		
		} catch (SQLException e) {
				e.printStackTrace();
			} finally {
						closeConn(pstmt, con);
				}
		
		return result;
		
	}//deleteCart end
	
	public int insertCart(CartDTO dto, int user_no, int product_no) {
	    int result = 0;
	    int cartNo = 0; 
	    int cartItemNo = 0;
	    
	    try {
	        openConn();
	        
	       
	        sql = "select cart_no from cart where user_no = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, user_no);
	        rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	           
	            cartNo = rs.getInt("cart_no");
	        } else {
	            
	            sql = "select max(cart_no) from cart";
	            pstmt = con.prepareStatement(sql);
	            rs = pstmt.executeQuery();
	            if (rs.next()) {
	                cartNo = rs.getInt(1) + 1;  
	            } else {
	                cartNo = 1;  
	            }

	           
	            sql = "insert into cart(cart_no, user_no, created_at) values(?, ?, sysdate)";
	            pstmt = con.prepareStatement(sql);
	            pstmt.setInt(1, cartNo);
	            pstmt.setInt(2, user_no);
	            result = pstmt.executeUpdate();
	        }

	       
	        sql = "select max(cart_item_no) from cart_item";
	        pstmt = con.prepareStatement(sql);
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            cartItemNo = rs.getInt(1) + 1;  
	        } else {
	            cartItemNo = 1;  
	        }

	       
	        sql = "insert into cart_item(cart_item_no, cart_no, product_no, quantity, added_at) "
	            + "values(?, ?, ?, 1, sysdate)";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, cartItemNo);
	        pstmt.setInt(2, cartNo);
	        pstmt.setInt(3, product_no);
	        result = pstmt.executeUpdate();
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        closeConn(rs, pstmt, con);
	    }
	    
	    return result;
	}

	//cart 테이블 정보 삭제시 CART_NO 순서를 재작업하는 메서드
	public void updateSequenceCart(int no) {

		try {
			openConn();
			
			sql="update cart set CART_NO = CART_NO -1 where CART_NO > ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, no);
			
			pstmt.executeUpdate();
			
			sql="update cart_item set CART_NO = CART_NO -1 where CART_NO > ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, no);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
				e.printStackTrace();
			} finally {
					closeConn(pstmt, con);
				
			}
	}//updateSequence end
	
	
}//class end

