package com.global.emaillog.model;

import java.sql.Date;

public class EmailLogDTO {
	
	private int emailLog_no;				// 이메일 로그 No
	private String emailLog_emailAddress;	// 이메일 주소
	private int emailLog_couponNo;			// 쿠폰 No
	private Date emailLog_sentAt;			// 이메일 발송일
	private String emailLog_status;			// 발송상태
	private String emailLog_errorMessage;	// 에러 메세지
	
	public int getEmailLog_no() {
		return emailLog_no;
	}
	
	public void setEmailLog_no(int emailLog_no) {
		this.emailLog_no = emailLog_no;
	}
	
	public String getEmailLog_emailAddress() {
		return emailLog_emailAddress;
	}
	
	public void setEmailLog_emailAddress(String emailLog_emailAddress) {
		this.emailLog_emailAddress = emailLog_emailAddress;
	}
	
	public int getEmailLog_couponNo() {
		return emailLog_couponNo;
	}
	
	public void setEmailLog_couponNo(int emailLog_couponNo) {
		this.emailLog_couponNo = emailLog_couponNo;
	}
	
	public Date getEmailLog_sentAt() {
		return emailLog_sentAt;
	}
	
	public void setEmailLog_sentAt(Date emailLog_sentAt) {
		this.emailLog_sentAt = emailLog_sentAt;
	}
	
	public String getEmailLog_status() {
		return emailLog_status;
	}
	
	public void setEmailLog_status(String emailLog_status) {
		this.emailLog_status = emailLog_status;
	}
	
	public String getEmailLog_errorMessage() {
		return emailLog_errorMessage;
	}
	
	public void setEmailLog_errorMessage(String emailLog_errorMessage) {
		this.emailLog_errorMessage = emailLog_errorMessage;
	}
	
}
