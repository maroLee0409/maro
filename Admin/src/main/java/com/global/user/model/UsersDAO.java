package com.global.user.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.global.user.model.UsersDAO;
import com.global.user.model.UsersDTO;

public class UsersDAO {
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = null;
	
	private static UsersDAO instance = null;
	
	private UsersDAO() {}
	
	public static UsersDAO getInstance() {
		
		if(instance == null) {
			instance = new UsersDAO();
		}
		
		return instance;
	} // getInstance() 메서드 end
	
	public void openConn() {
		
		try {
			Context initCtx = new InitialContext();

			Context ctx = (Context)initCtx.lookup("java:comp/env");

			DataSource ds = (DataSource)ctx.lookup("jdbc/myoracle");
			
			con = ds.getConnection();
		} 
		
		catch (Exception e) {
			e.printStackTrace();
		}
	} // openConn() 메서드 end
	
	public void closeConn(ResultSet rs, PreparedStatement pstmt, Connection con) {
		
			try {
				if(rs != null) rs.close();
				
				if(pstmt != null) pstmt.close();
				
				if(con != null) con.close();
			} 
			
			catch (SQLException e) {
				e.printStackTrace();
			}
	} // closeConn() 메서드 end
	
	// 유저 전체 리스트를 조회하는 메서드
	public List<UsersDTO> getUsersList() {
			
		List<UsersDTO> list = new ArrayList<UsersDTO>();
			
		try {
			openConn();
				
			sql = "select * from users order by user_no desc";
				
			pstmt = con.prepareStatement(sql);
				
			rs = pstmt.executeQuery();
				
			while(rs.next()) {
				UsersDTO dto = new UsersDTO();
					
				dto.setUserNo(rs.getInt("user_no"));
				dto.setUserId(rs.getString("user_id"));
				dto.setPassword(rs.getString("password"));
				dto.setName(rs.getString("name"));
				dto.setEmail(rs.getString("email"));
					
				list.add(dto);
			}
		} 
			
		catch (SQLException e) {
			e.printStackTrace();
		} 
			
		finally {
			closeConn(rs, pstmt, con);
		}
			
		return list;
	} // getUsersList() 메서드 end
		
	// 유저 추가하는 메서드
	public int insertUser(UsersDTO dto) {
		
		int result = 0;
			
		try {
			openConn();
			
			sql = "insert into users values(?, ?, ?, ?)";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getPassword());
			pstmt.setString(3, dto.getName());
			pstmt.setString(4, dto.getEmail());
			
			result = pstmt.executeUpdate();
		} 
			
		catch (SQLException e) {
			e.printStackTrace();
		} 
			
		finally {
			closeConn(rs, pstmt, con);
		}
			
		return result;
	} // insertUser() 메서드 end
	
	/*
	// 관리자 직책의 정보를 조회하는 메서드
	public List<AdminDTO> searchCategoryAdminList(String search_field, String search_keyword) {

		AdminDTO dto = null;
		
		String code = null;
		
		try {
			openConn();
			
			sql = "select * from admin_role where role_code = ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, code);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new AdminDTO();
				
				dto.setRoleCode(rs.getString("code"));
				dto.setRoleName(rs.getString("name"));
			}
		} 
		
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			closeConn(rs, pstmt, con);
		}
		
		return dto;
	} // searchCategoryAdminList() 메서드 end
	*/
}