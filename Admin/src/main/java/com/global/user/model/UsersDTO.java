package com.global.user.model;

import java.sql.Date;

public class UsersDTO {
	
	private int userNo;
    private String userId;
    private String password;
    private String name;
    private String email;
    private String userType; // "CUSTOMER" 또는 "ADMIN"
    private String isDeleted;
    private Date createdAt;
    private Date updatedAt;
    
	public UsersDTO() {
		super();
	}
	
	public UsersDTO(int userNo, String userId, String password, String name, String email, String userType,
			String isDeleted, Date createdAt, Date updatedAt) {
		super();
		this.userNo = userNo;
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.email = email;
		this.userType = userType;
		this.isDeleted = isDeleted;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	public int getUserNo() {
		return userNo;
	}
	
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getUserType() {
		return userType;
	}
	
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	public String getIsDeleted() {
		return isDeleted;
	}
	
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	public Date getUpdatedAt() {
		return updatedAt;
	}
	
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}