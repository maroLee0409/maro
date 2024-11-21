package com.global.order.model;


import java.sql.Date;
public class OrderDTO {

	private int order_no;			// 주문 번호
	private int user_no;		// 주문한 고객
	private Date order_date;		// 주문 일자
	private String status;	// 주문 상태
	private int total_amount;	// 총 주문 금액
	public int getOrder_no() {
		return order_no;
	}
	public void setOrder_no(int order_no) {
		this.order_no = order_no;
	}
	public int getUser_no() {
		return user_no;
	}
	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}
	public Date getOrder_date() {
		return order_date;
	}
	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(int total_amount) {
		this.total_amount = total_amount;
	}
	
	
	
}