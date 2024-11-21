package com.global.coupon.model;

import java.sql.Date;

public class CouponDTO {
	
	private int coupon_no;
	private String coupon_code;
	private int coupon_discountAmount;
	private int coupon_discountPercent;
	private Date coupon_expiryDate;
	private String coupon_isUsed;
	private int coupon_eventNo;
	private Date coupon_createdAt;
	private Date coupon_updatedAt;
	
	public int getCoupon_no() {
		return coupon_no;
	}
	
	public void setCoupon_no(int coupon_no) {
		this.coupon_no = coupon_no;
	}
	
	public String getCoupon_code() {
		return coupon_code;
	}
	
	public void setCoupon_code(String coupon_code) {
		this.coupon_code = coupon_code;
	}
	
	public int getCoupon_discountAmount() {
		return coupon_discountAmount;
	}
	
	public void setCoupon_discountAmount(int coupon_discountAmount) {
		this.coupon_discountAmount = coupon_discountAmount;
	}
	
	public int getCoupon_discountPercent() {
		return coupon_discountPercent;
	}
	
	public void setCoupon_discountPercent(int coupon_discountPercent) {
		this.coupon_discountPercent = coupon_discountPercent;
	}
	
	public Date getCoupon_expiryDate() {
		return coupon_expiryDate;
	}
	
	public void setCoupon_expiryDate(Date coupon_expiryDate) {
		this.coupon_expiryDate = coupon_expiryDate;
	}
	
	public String getCoupon_isUsed() {
		return coupon_isUsed;
	}
	
	public void setCoupon_isUsed(String coupon_isUsed) {
		this.coupon_isUsed = coupon_isUsed;
	}
	
	public int getCoupon_eventNo() {
		return coupon_eventNo;
	}
	
	public void setCoupon_eventNo(int coupon_eventNo) {
		this.coupon_eventNo = coupon_eventNo;
	}
	
	public Date getCoupon_createdAt() {
		return coupon_createdAt;
	}
	
	public void setCoupon_createdAt(Date coupon_createdAt) {
		this.coupon_createdAt = coupon_createdAt;
	}
	
	public Date getCoupon_updatedAt() {
		return coupon_updatedAt;
	}
	
	public void setCoupon_updatedAt(Date coupon_updatedAt) {
		this.coupon_updatedAt = coupon_updatedAt;
	}
	
	
}
