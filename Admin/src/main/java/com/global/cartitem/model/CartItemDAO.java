package com.global.cartitem.model;

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

public class CartItemDAO {
	
	//DB를 연결해주는 객체
	Connection con = null;
	//DB에 SQL문을 전송하는 객체
	PreparedStatement pstmt = null;
	//DB 실행한 후 SQL문 값을 가지고 있는 객체
	ResultSet rs = null;
	//SQL문을 저장하는 변수
	String sql = null;
	
	private static CartItemDAO instance = null;
	
	public CartItemDAO() {} //기본생성자
	
	// 외부에서 접근할 수 있도록 해 주는 메서드
	public static CartItemDAO getInstanceCartItem() {
		
		if(instance==null) {
			instance = new CartItemDAO();
		}
		
		return instance; 
		
	}//getInstanceCartItem end
	
	//DB를 연결해주는 메서드
	public void openConn() {
		try {
			Context intntCtx = new InitialContext();
			
			Context ctx = (Context)intntCtx.lookup("java:comp/env");
			
			DataSource ds = (DataSource) ctx.lookup("jdbc/myoracle");
			
			con = ds.getConnection();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}//opConn end
	
	//DB를 종료해주는 메서드 ( rs,pstmt,con )
	public void closeConn(ResultSet rs, Statement stmt, Connection con) {
		
			try {
				
				if(rs != null && !rs.isClosed()) rs.close();
				if(stmt != null && !stmt.isClosed()) stmt.close();
				if(con != null && !con.isClosed()) con.close(); 
				
			} catch (SQLException e) {
				
					e.printStackTrace();
				}
		
	}//closeConn(rs, pstmt, con) end
	
	////DB를 종료해주는 메서드 ( pstmt, con )
	public void closeConn(Statement stmt, Connection con) {
		 
			
				try {
					
					if(stmt != null && !stmt.isClosed()) stmt.close();
					if(con != null && !con.isClosed()) con.close();
					
				} catch (SQLException e) {
					
						e.printStackTrace();
					}
	}//closeConn(pstmt, con)end
	
	//cartItem 테이블 정보 전체 조회하는 메서드 
		public List<CartItemDTO> getCartItemList(int no){
			
			List<CartItemDTO> list = new ArrayList<CartItemDTO>();
			
			try {
				openConn();
				
				sql ="select u.USER_NO , ci.CART_ITEM_NO, c.CART_NO,p.PRODUCT_NO,ci.QUANTITY,ci.ADDED_AT,ci.UPDATED_AT,p.NAME,p.PRICE "
						+ "from USERS u "
						+ "join cart c on (c.USER_NO = u.USER_NO) "
						+ "join cart_item ci on(c.cart_no=ci.cart_no) "
						+ "join product p on(ci.product_no = p.product_no) "
						+ "join product_category pc on(p.CATEGORY_NO = pc.CATEGORY_NO) "
						+ "where u.user_no = ? "
						+ "order by CART_ITEM_NO desc";
			
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, no);
				
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					CartItemDTO dto = new CartItemDTO();
					
		
					dto.setCartItem_no(rs.getInt("CART_ITEM_NO"));
					dto.setCart_no(rs.getInt("CART_NO"));
					dto.setProduct_no(rs.getInt("PRODUCT_NO"));
					dto.setQuantity(rs.getInt("QUANTITY"));
					dto.setAdded_at(rs.getDate("ADDED_AT"));
					dto.setUpdated_at(rs.getDate("UPDATED_AT"));
					
					dto.setProduct_name(rs.getString("NAME"));
					dto.setProduct_price(rs.getInt("PRICE"));
					
					dto.setUser_no(rs.getInt("user_no"));
					
					list.add(dto);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				closeConn(rs, pstmt, con);
			}
			
			return list;
			
		}//getCartItemList end
		
		public int getCartItemCount() {
			int result = 0;
			try {
				openConn();
				sql = "SELECT COUNT(*) FROM cart_item LEFT JOIN users USING(user_no)";
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				closeConn(rs, pstmt, con);
			}
			return result;
		}
		
		//cartItem 테이블 CART_ITEM_NO에 해당하는 정보를 삭제하는 메서드 
		public int deleteCartItem(int no) {
			int result =0;
			
		
			try {
				openConn();
				
				sql ="delete from cart_item where CART_ITEM_NO =? ";
				
				pstmt = con.prepareStatement(sql);
				
				pstmt.setInt(1, no);
				
				result =pstmt.executeUpdate();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
					}finally {
							closeConn(pstmt, con);
						}
		
			return result;
		}//deleteCartItem end
		
		//삭제한 번호를 재작업 해주는 메서드
		public void updateSequenceCartItem(int no) {
			try {
				openConn();
				
				sql = "update cart_item set CART_ITEM_NO = CART_ITEM_NO-1 where CART_ITEM_NO >?";
				
				pstmt.setInt(1, no);
				
				pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				closeConn(pstmt, con);
			}
			
		}//updateSequenceCartItem end
		
		// 해당하는 cartItem 정보를 조회하는 메서드
		public CartItemDTO getContentCartItem(int no) {
			CartItemDTO dto = null;
			
			try {
				
				openConn();
				
				sql ="select u.USER_NO , ci.CART_ITEM_NO, c.CART_NO,p.PRODUCT_NO,ci.QUANTITY,ci.ADDED_AT,ci.UPDATED_AT,p.NAME,p.PRICE "
						+ "from USERS u "
						+ "join cart c on (c.USER_NO = u.USER_NO) "
						+ "join cart_item ci on(c.cart_no=ci.cart_no) "
						+ "join product p on(ci.product_no = p.product_no) "
						+ "join product_category pc on(p.CATEGORY_NO = pc.CATEGORY_NO) "
						+ "where ci.CART_ITEM_NO = ? "
						+ "order by CART_ITEM_NO desc";
			
				
				pstmt = con.prepareStatement(sql);
				
				pstmt.setInt(1, no);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					dto = new CartItemDTO();
				
					dto.setCartItem_no(rs.getInt("CART_ITEM_NO"));
					dto.setCart_no(rs.getInt("CART_NO"));
					dto.setProduct_no(rs.getInt("PRODUCT_NO"));
					dto.setQuantity(rs.getInt("QUANTITY"));
					dto.setAdded_at(rs.getDate("ADDED_AT"));
					dto.setUpdated_at(rs.getDate("UPDATED_AT"));
					
					dto.setProduct_name(rs.getString("NAME"));
					dto.setProduct_price(rs.getInt("PRICE"));
					
					dto.setUser_no(rs.getInt("USER_NO"));
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				closeConn(rs, pstmt, con);
			}
			
			return dto;
			
		}//contentCartItem end
		
		// cartItem 테이블의 정보를 수정하는 메서드
		public int modifyCartItem(CartItemDTO dto) {
			int result =0;
			
			try {
				
				openConn();
				
				sql ="update CART_ITEM set QUANTITY =? where CART_ITEM_NO = ?";
				
				pstmt = con.prepareStatement(sql);
				
				pstmt.setInt(1, dto.getQuantity());
				
				pstmt.setInt(2, dto.getCartItem_no());
				
				result = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				closeConn(rs, pstmt, con);
			}
			
			return result;
			
		}//modifyCartItem end
		
	}//class end