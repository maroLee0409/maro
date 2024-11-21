package com.global.cartitem.model;

import java.sql.Date;

public class CartItemDTO {
	
	private int cartItem_no;			// 장바구니 항목 번호
	private int cart_no;		// 장바구니
	private int product_no;		// 장바구니에 담긴 상품
	private int quantity;		// 수량
	private Date added_at;		// 항목 추가일
	private Date updated_at;	// 항목 수정일
	
	private String product_name;
	private int product_price;
	
	private int user_no;
	private String user_name;
	public int getCartItem_no() {
		return cartItem_no;
	}
	public void setCartItem_no(int cartItem_no) {
		this.cartItem_no = cartItem_no;
	}
	public int getCart_no() {
		return cart_no;
	}
	public void setCart_no(int cart_no) {
		this.cart_no = cart_no;
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
	public Date getAdded_at() {
		return added_at;
	}
	public void setAdded_at(Date added_at) {
		this.added_at = added_at;
	}
	public Date getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public int getProduct_price() {
		return product_price;
	}
	public void setProduct_price(int product_price) {
		this.product_price = product_price;
	}
	public int getUser_no() {
		return user_no;
	}
	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	
}
