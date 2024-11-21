package com.global.order.model;

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
import com.global.board.model.BoardDTO;
import com.global.product.model.ProductDTO;

public class OrderDAO {
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = null;
	
	// 싱글톤
	public static OrderDAO instance=null;

	// 기본생성자
	public OrderDAO() {}


	public static OrderDAO getInstance() {

		if (instance == null) {
			instance = new OrderDAO();
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
	
	// 전체 주문 목록 조회 메서드.
	public List<OrderDTO> getOrderList(){
		
		List<OrderDTO> list = new ArrayList<OrderDTO>();
		
		
		
		try {
			
			openConn();
			
			sql = "SELECT ORDER_NO, u.user_no, ORDER_DATE, STATUS, TOTAL_AMOUNT "
			           + "FROM orders o "
			           + "JOIN users u ON o.user_no = u.user_no "
			           + "ORDER BY ORDER_NO ASC";
			
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				OrderDTO dto = new OrderDTO();
				
				dto.setOrder_no(rs.getInt("order_no"));
				dto.setUser_no(rs.getInt("user_no"));
				dto.setOrder_date(rs.getDate("order_date"));
				dto.setStatus(rs.getString("status"));
				dto.setTotal_amount(rs.getInt("total_amount"));
				
				list.add(dto);
				
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConn(rs, pstmt, con);
		}
		
		
		return list;
	}
 
	public int DeleteOrder(int no) {
		
		int result = 0;

		 try {
		 
		 openConn();
		  
		 sql = "SELECT * FROM orders WHERE order_no = ?";
		 
		
		 pstmt = con.prepareStatement(sql);
		 
		 pstmt.setInt(1, no);
		 
		 rs = pstmt.executeQuery();
		 
		 if(rs.next()) {
			 sql = "DELETE FROM orders WHERE order_no = ?";
			 
			 pstmt = con.prepareStatement(sql);
			 
			 pstmt.setInt(1, no);
			 
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
		
	public OrderDTO getOrder(int no) {
		OrderDTO dto =null;
		

		
		try {
			openConn();
			
			sql = "select * from orders where order_no = ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, no);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new OrderDTO();
				
				dto.setOrder_no(rs.getInt("order_no"));
				dto.setUser_no(rs.getInt("user_no"));
				dto.setOrder_date(rs.getDate("order_date"));
				dto.setStatus(rs.getString("status"));
				dto.setTotal_amount(rs.getInt("total_amount"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConn(rs, pstmt, con);
		}
		
		return dto;
	}
	
	public int ModifyOrder(OrderDTO dto) {
		int result = 0;
		
	
		
		try {
			
			openConn();
			
			sql = "update orders set status = ? where order_no = ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, dto.getStatus());
			pstmt.setInt(2, dto.getOrder_no());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConn(pstmt, con);
		}
		
		return result;
	}
	
	public int getOrderCount(String st) {
		
		int count = 0;
		try {
			openConn();
			sql = "SELECT COUNT(*) FROM orders";

			if (st != null) {
				sql += " WHERE is_deleted = ?";
			}

			pstmt = con.prepareStatement(sql);

			if (st != null) {
				pstmt.setString(1, st);
			}

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


	public List<OrderDTO> selectOrderList(int currentPage, int boardLimit, char st) {
		
		List<OrderDTO> list = new ArrayList<>();
		/*
		 * boardList페이지에서 BOARD_DETAIL을 호출할때
		 * USER_TYPE,USER_ID,USER_EMAIL,BOARD_CATEGORY테이블에 대한 정보를 포함하고 있습니다.
		 */
		sql = "SELECT * FROM ( SELECT row_number() OVER (ORDER BY order_no ASC) AS rnum, b.* FROM orders b ) WHERE rnum BETWEEN ? AND ?";

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
				OrderDTO order = new OrderDTO();

				order.setOrder_no(rs.getInt("order_no"));
				order.setUser_no(rs.getInt("user_no"));
				order.setOrder_date(rs.getDate("order_date"));
				order.setStatus(rs.getString("status"));
				order.setTotal_amount(rs.getInt("total_amount"));
				

				list.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return list;
	}


	public List<OrderDTO> selectOrderList(int currentPage, int boardLimit) {
		List<OrderDTO> list = new ArrayList<>();
		/*
		 * boardList페이지에서 BOARD_DETAIL을 호출할때
		 * USER_TYPE,USER_ID,USER_EMAIL,BOARD_CATEGORY테이블에 대한 정보를 포함하고 있습니다.
		 */
		sql = "SELECT * FROM ( SELECT row_number() OVER (ORDER BY order_no ASC) AS rnum, b.* FROM orders b ) WHERE rnum BETWEEN ? AND ?";

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
				OrderDTO order = new OrderDTO();

				order.setOrder_no(rs.getInt("order_no"));
				order.setUser_no(rs.getInt("user_no"));
				order.setOrder_date(rs.getDate("order_date"));
				order.setStatus(rs.getString("status"));
				order.setTotal_amount(rs.getInt("total_amount"));
				

				list.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return list;
	}

		
	//cart 테이블 정보 삭제시 CART_NO 순서를 재작업하는 메서드
		public void updateSequenceOrder(int no) {

			try {
				openConn();
				
				sql="update orders set order_no = order_no -1 where order_no > ?";
				
				pstmt = con.prepareStatement(sql);
				
				pstmt.setInt(1, no);
				
				pstmt.executeUpdate();
				
				sql="update order_item set order_no = order_no -1 where order_no > ?";
				
				pstmt = con.prepareStatement(sql);
				
				pstmt.setInt(1, no);
				
				pstmt.executeUpdate();
				
			} catch (SQLException e) {
					e.printStackTrace();
				} finally {
						closeConn(pstmt, con);
					
				}
		}//updateSequence end


		public int InsertOrder(OrderDTO ordto, int user_no, int product_no, int quantity, int price) {
		    int result = 0;
		    int orderNo = 0;
		    int orderitemNo = 0;

		    
		    
		    try {
		        openConn();

		        // Get existing order number
		        String sql = "SELECT order_no FROM orders WHERE user_no = ?";
		        pstmt = con.prepareStatement(sql);
		        pstmt.setInt(1, user_no);
		        rs = pstmt.executeQuery();

		        if (rs.next()) {
		            orderNo = rs.getInt("order_no");
		        }

	            // Get the next order number
	            sql = "SELECT MAX(order_no) FROM orders";
	            pstmt = con.prepareStatement(sql);
	            rs = pstmt.executeQuery();
	            if (rs.next()) {
	                orderNo = rs.getInt(1) + 1;
	            } else {
	                orderNo = 1;
	            }

	            // Insert new order
	            sql = "INSERT INTO orders (order_no, user_no, order_date, total_amount, status) VALUES (?, ?, SYSDATE,? , 'PENDING')";
	            pstmt = con.prepareStatement(sql);
	            pstmt.setInt(1, orderNo);
	            pstmt.setInt(2, user_no);
	            pstmt.setInt(3, quantity * price);
	            result = pstmt.executeUpdate();
	            // 재고 업데이트
	            sql = "UPDATE PRODUCT SET STOCK_QUANTITY = STOCK_QUANTITY - ? WHERE PRODUCT_NO = ?";
	            pstmt = con.prepareStatement(sql);
	            pstmt.setInt(1, quantity);
	            pstmt.setInt(2, product_no);
	            pstmt.executeUpdate();
		        // Get the next order item number
		        sql = "SELECT MAX(order_item_no) FROM order_item";
		        pstmt = con.prepareStatement(sql);
		        rs = pstmt.executeQuery();
		        if (rs.next()) {
		            orderitemNo = rs.getInt(1) + 1;
		        } else {
		            orderitemNo = 1;
		        }
		        sql = "select price from product where product_no = ?";
		        
		        pstmt = con.prepareStatement(sql);
		        pstmt.setInt(1, product_no);
		   
		        rs= pstmt.executeQuery();
		        
		        rs.next();
		        
		        // Insert new order item
		        sql = "INSERT INTO order_item (order_item_no, order_no, product_no, quantity, price) VALUES (?, ?, ?, ?, ?)";
		        pstmt = con.prepareStatement(sql);
		        pstmt.setInt(1, orderitemNo);
		        pstmt.setInt(2, orderNo);
		        pstmt.setInt(3, product_no);
		        pstmt.setInt(4, 100); // quantity
		        pstmt.setInt(5, rs.getInt(1)); // price
		        result = pstmt.executeUpdate();

		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        closeConn(rs, pstmt, con);
		    }

		    return result;
		}

		}

