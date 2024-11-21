package com.global.user.model;

import java.sql.Date;

public class CustomerDTO extends UsersDTO {
    private Integer age;
    private String job;
    private String location;
    private Integer mileage;
    private Date lastLoginDate;
    private Integer totalPurchaseAmount;
    
	public CustomerDTO() {
		super();
	}
	
	public CustomerDTO(int userNo, String userId, String password, String name, String email, String userType,
			String isDeleted, Date createdAt, Date updatedAt,
			Integer age, String job, String location, Integer mileage, Date lastLoginDate, Integer totalPurchaseAmount) {
		
		super(userNo, userId, password, name, email, userType, isDeleted, createdAt, updatedAt);

        this.age = age;
        this.job = job;
        this.location = location;
        this.mileage = mileage;
        this.lastLoginDate = lastLoginDate;
        this.totalPurchaseAmount = totalPurchaseAmount;
		
	}
	
	public Integer getAge() {
		return age;
	}
	
	public void setAge(Integer age) {
		this.age = age;
	}
	
	public String getJob() {
		return job;
	}
	
	public void setJob(String job) {
		this.job = job;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public Integer getMileage() {
		return mileage;
	}
	
	public void setMileage(Integer mileage) {
		this.mileage = mileage;
	}
	
	public Date getLastLoginDate() {
		return lastLoginDate;
	}
	
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	
	public Integer getTotalPurchaseAmount() {
		return totalPurchaseAmount;
	}
	
	public void setTotalPurchaseAmount(Integer totalPurchaseAmount) {
		this.totalPurchaseAmount = totalPurchaseAmount;
	}
}