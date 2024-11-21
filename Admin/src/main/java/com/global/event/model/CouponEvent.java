package com.global.event.model;

import java.sql.Date;

public class CouponEvent  extends Event {
    private String couponCode;
    private double discountAmount;
    private double discountPercent;
    private Date expiryDate;
    private int imageId;
	public CouponEvent() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CouponEvent(int eventNo, String name, String description, int views, Date startDate, Date endDate,
			Date createdAt, Date updatedAt, String status, String eventType) {
		super(eventNo, name, description, views, startDate, endDate, createdAt, updatedAt, status, eventType);
		// TODO Auto-generated constructor stub
	}
	public CouponEvent(String couponCode, double discountAmount, double discountPercent, Date expiryDate,
			int imageId) {
		super();
		this.couponCode = couponCode;
		this.discountAmount = discountAmount;
		this.discountPercent = discountPercent;
		this.expiryDate = expiryDate;
		this.imageId = imageId;
	}
	public String getCouponCode() {
		return couponCode;
	}
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	public double getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(double discountAmount) {
		this.discountAmount = discountAmount;
	}
	public double getDiscountPercent() {
		return discountPercent;
	}
	public void setDiscountPercent(double discountPercent) {
		this.discountPercent = discountPercent;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public int getImageId() {
		return imageId;
	}
	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
    
    
    
	
}
