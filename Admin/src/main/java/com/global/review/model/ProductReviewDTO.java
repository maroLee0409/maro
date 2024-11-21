package com.global.review.model;

import java.sql.Date;

public class ProductReviewDTO {
	private int Review_No;
	private int Board_No;
	private int Product_No;
	private int Rating;
	
	/*board Category*/
	private String boardCategoryNo;
	private String boardCategoryName;
	
	/*상품 정보 조인용*/
	private String category_no;
	private String name;
	private String description;
	private int price;
	private int stock_quantity;
	
	/*상품 조인용*/
	private Integer userNo;
	private String title;
	private String content;
	private int views; // 조회수
	private Date createAt;
	private Date updateAt;
	private String isDeleted;
	
	public int getReview_No() {
		return Review_No;
	}
	
	public void setReview_No(int review_No) {
		Review_No = review_No;
	}
	
	public int getBoard_No() {
		return Board_No;
	}
	
	public void setBoard_No(int board_No) {
		Board_No = board_No;
	}
	
	public int getProduct_No() {
		return Product_No;
	}
	
	public void setProduct_No(int product_No) {
		Product_No = product_No;
	}
	
	public int getRating() {
		return Rating;
	}
	
	public void setRating(int rating) {
		Rating = rating;
	}
	
	public String getCategory_no() {
		return category_no;
	}
	
	public void setCategory_no(String category_no) {
		this.category_no = category_no;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public int getStock_quantity() {
		return stock_quantity;
	}
	
	public void setStock_quantity(int stock_quantity) {
		this.stock_quantity = stock_quantity;
	}
	
	public Integer getUserNo() {
		return userNo;
	}
	
	public void setUserNo(Integer userNo) {
		this.userNo = userNo;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public int getViews() {
		return views;
	}
	
	public void setViews(int views) {
		this.views = views;
	}
	
	public Date getCreateAt() {
		return createAt;
	}
	
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	public Date getUpdateAt() {
		return updateAt;
	}
	
	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}
	
	public String getIsDeleted() {
		return isDeleted;
	}
	
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getBoardCategoryNo() {
		return boardCategoryNo;
	}

	public void setBoardCategoryNo(String boardCategoryNo) {
		this.boardCategoryNo = boardCategoryNo;
	}

	public String getBoardCategoryName() {
		return boardCategoryName;
	}

	public void setBoardCategoryName(String boardCategoryName) {
		this.boardCategoryName = boardCategoryName;
	}
	
	
}
