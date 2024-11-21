package com.global.usercoupon.model;

import java.sql.Date;

public class UserCouponDTO {
	private int userCoupon_no;				// 사용자 쿠폰
	private int userCoupon_userNo;			// 사용자 No.
	private int userCoupon_couponNo;		// 쿠폰 No.
	private Date userCoupon_receivedAt;	// 쿠폰 수신일
	private Date userCoupon_isRedeemed;	// 쿠폰 사용여부
	private Date userCoupon_redeemedAt;	// 쿠폰 사용일
	
	public int getUserCoupon_no() {
		return userCoupon_no;
	}
	
	public void setUserCoupon_no(int userCoupon_no) {
		this.userCoupon_no = userCoupon_no;
	}
	
	public int getUserCoupon_userNo() {
		return userCoupon_userNo;
	}
	
	public void setUserCoupon_userNo(int userCoupon_userNo) {
		this.userCoupon_userNo = userCoupon_userNo;
	}
	
	public int getUserCoupon_couponNo() {
		return userCoupon_couponNo;
	}
	
	public void setUserCoupon_couponNo(int userCoupon_couponNo) {
		this.userCoupon_couponNo = userCoupon_couponNo;
	}
	
	public Date getUserCoupon_receivedAt() {
		return userCoupon_receivedAt;
	}
	
	public void setUserCoupon_receivedAt(Date userCoupon_receivedAt) {
		this.userCoupon_receivedAt = userCoupon_receivedAt;
	}
	
	public Date getUserCoupon_isRedeemed() {
		return userCoupon_isRedeemed;
	}
	
	public void setUserCoupon_isRedeemed(Date userCoupon_isRedeemed) {
		this.userCoupon_isRedeemed = userCoupon_isRedeemed;
	}
	
	public Date getUserCoupon_redeemedAt() {
		return userCoupon_redeemedAt;
	}
	
	public void setUserCoupon_redeemedAt(Date userCoupon_redeemedAt) {
		this.userCoupon_redeemedAt = userCoupon_redeemedAt;
	}
	
}
