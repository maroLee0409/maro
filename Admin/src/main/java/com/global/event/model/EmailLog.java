package com.global.event.model;

import java.sql.Date;

public class EmailLog {
	private int emailLogNo;
	private String emailAddress;
	private int couponNo;
	private Date sentAt;
	private String status;
	private String errorMessage;
	
	public EmailLog() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmailLog(int emailLogNo, String emailAddress, int couponNo, Date sentAt, String status,
			String errorMessage) {
		super();
		this.emailLogNo = emailLogNo;
		this.emailAddress = emailAddress;
		this.couponNo = couponNo;
		this.sentAt = sentAt;
		this.status = status;
		this.errorMessage = errorMessage;
	}

	public int getEmailLogNo() {
		return emailLogNo;
	}

	public void setEmailLogNo(int emailLogNo) {
		this.emailLogNo = emailLogNo;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public int getCouponNo() {
		return couponNo;
	}

	public void setCouponNo(int couponNo) {
		this.couponNo = couponNo;
	}

	public Date getSentAt() {
		return sentAt;
	}

	public void setSentAt(Date sentAt) {
		this.sentAt = sentAt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	
}
