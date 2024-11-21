package com.global.event.model;

import java.sql.Date;

public class UserCoupon {
	private int userCouponNo;
    private int userNo;
    private int couponNo;
    private Date receivedAt;
    private String isRedeemed;
    private Date redeemedAt;
	public UserCoupon() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserCoupon(int userCouponNo, int userNo, int couponNo, Date receivedAt, String isRedeemed,
			Date redeemedAt) {
		super();
		this.userCouponNo = userCouponNo;
		this.userNo = userNo;
		this.couponNo = couponNo;
		this.receivedAt = receivedAt;
		this.isRedeemed = isRedeemed;
		this.redeemedAt = redeemedAt;
	}
	public int getUserCouponNo() {
		return userCouponNo;
	}
	public void setUserCouponNo(int userCouponNo) {
		this.userCouponNo = userCouponNo;
	}
	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	public int getCouponNo() {
		return couponNo;
	}
	public void setCouponNo(int couponNo) {
		this.couponNo = couponNo;
	}
	public Date getReceivedAt() {
		return receivedAt;
	}
	public void setReceivedAt(Date receivedAt) {
		this.receivedAt = receivedAt;
	}
	public String getIsRedeemed() {
		return isRedeemed;
	}
	public void setIsRedeemed(String isRedeemed) {
		this.isRedeemed = isRedeemed;
	}
	public Date getRedeemedAt() {
		return redeemedAt;
	}
	public void setRedeemedAt(Date redeemedAt) {
		this.redeemedAt = redeemedAt;
	}
    
    
}
