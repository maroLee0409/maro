package com.global.orderitem.model;

public class OrderItemDTO {
	private int order_item_no;			// 주문 항목 번호
	private int oreder_no;		// 주문
	private int product_no;	   // 상품 번호
	private int quantity;   // 수량 
	private int price; 		// 가격
	
	public int getOrder_item_no() {
		return order_item_no;
	}
	public void setOrder_item_no(int order_item_no) {
		this.order_item_no = order_item_no;
	}
	public int getOreder_no() {
		return oreder_no;
	}
	public void setOreder_no(int oreder_no) {
		this.oreder_no = oreder_no;
	}
	public int getProduct_no() {
		return product_no;
	}
	public void setProduct_no(int product_no) {
		this.product_no = product_no;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	
	
}
