package com.global.delivery.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.global.order.model.OrderDTO;


public class DeliveryDAO {
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = null;
	
	// 싱글톤
	public static DeliveryDAO instance=null;

	// 기본생성자
	public DeliveryDAO() {}


	public static DeliveryDAO getInstance() {

		if (instance == null) {
			instance = new DeliveryDAO();
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
		

	}// closeConn() end
	
	public List<DeliveryDTO> getDeliveryList(){
		
		List<DeliveryDTO> list = new ArrayList<DeliveryDTO>();
		
		
		
		try {
			openConn();
			
			sql = "SELECT d.DELIVERY_NO, o.ORDER_NO, d.DELIVERY_DATE, d.DELIVERY_STATUS "
				    + "FROM ORDERS o "
				    + "LEFT JOIN DELIVERY d ON o.ORDER_NO = d.ORDER_NO "
				    + "ORDER BY o.ORDER_DATE DESC, d.DELIVERY_DATE ASC";

			
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				DeliveryDTO dto = new DeliveryDTO();
				
				dto.setDelivery_no(rs.getInt("delivery_no"));
				dto.setOrder_no(rs.getInt("order_no"));
				dto.setDelivery_date(rs.getDate("delivery_date"));
				dto.setDelivery_status(rs.getString("delivery_status"));
				
				list.add(dto);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConn(rs, pstmt, con);
		}
		
	 return list;
	}// end
	
	public int DeleteDelivery(int no) {
		
		int result = 0;

		 try {
		 
		 openConn();
		  
		 sql = "SELECT * FROM delivery WHERE order_no = ?";
		 
		
		 pstmt = con.prepareStatement(sql);
		 
		 pstmt.setInt(1, no);
		 
		 rs = pstmt.executeQuery();
		 
		 if(rs.next()) {
			 sql = "DELETE FROM delivery WHERE order_no = ?";
			 
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


	public DeliveryDTO getDelivery(int no) {
		DeliveryDTO dto = null;
		try {
			openConn();
			
			sql = "select * from delivery where order_no = ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, no);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new DeliveryDTO();
				
				dto.setDelivery_no(rs.getInt("delivery_no"));
				dto.setOrder_no(rs.getInt("order_no"));
				dto.setDelivery_date(rs.getDate("delivery_date"));
				dto.setDelivery_status(rs.getString("delivery_status"));
			}
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConn(rs, pstmt, con);
		}
		
		return dto;
	}


	public int getDelivery(DeliveryDTO dto) {
			int result = 0;
			
	
		
		try {
			
			openConn();
			
			sql = "update delivery set delivery_status = ? where order_no = ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, dto.getDelivery_status());
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


	public int getDeliveryCount(String st) {
		int count = 0;
		try {
			openConn();
			sql = "SELECT COUNT(*) FROM delivery";

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


	public List<DeliveryDTO> selectDeliveryList(int currentPage, int boardLimit, char c) {
		List<DeliveryDTO> list = new ArrayList<>();
		/*
		 * boardList페이지에서 BOARD_DETAIL을 호출할때
		 * USER_TYPE,USER_ID,USER_EMAIL,BOARD_CATEGORY테이블에 대한 정보를 포함하고 있습니다.
		 */
		sql = "SELECT * FROM ( SELECT row_number() OVER (ORDER BY order_no ASC) AS rnum, b.* delivery b ) WHERE rnum BETWEEN ? AND ?";

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
				DeliveryDTO dto = new DeliveryDTO();

				dto.setDelivery_no(rs.getInt("delivery_no"));
				dto.setOrder_no(rs.getInt("order_no"));
				dto.setDelivery_date(rs.getDate("delivery_date"));
				dto.setDelivery_status(rs.getString("delivery_status"));
				

				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return list;
	}


	public List<DeliveryDTO> selectDeliveryList(int currentPage, int boardLimit) {
		
		List<DeliveryDTO> list = new ArrayList<>();
		/*
		 * boardList페이지에서 BOARD_DETAIL을 호출할때
		 * USER_TYPE,USER_ID,USER_EMAIL,BOARD_CATEGORY테이블에 대한 정보를 포함하고 있습니다.
		 */
		sql = "SELECT * FROM ( SELECT row_number() OVER (ORDER BY order_no ASC) AS rnum, b.* FROM delivery b ) WHERE rnum BETWEEN ? AND ?";

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
				DeliveryDTO dto = new DeliveryDTO();

				dto.setDelivery_no(rs.getInt("delivery_no"));
				dto.setOrder_no(rs.getInt("order_no"));
				dto.setDelivery_date(rs.getDate("delivery_date"));
				dto.setDelivery_status(rs.getString("delivery_status"));
				

				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return list;
	}
	
	} 
	
	


