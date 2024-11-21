package com.global.product.model;

import com.global.utils.Filter;

public class ProductFilter extends Filter {
    private String categoryNo;    // 카테고리 번호
    private Integer userNo;       // 사용자 번호
    private Integer minViews;     // 최소 조회수
    private Integer maxViews;     // 최대 조회수
    private Integer minPrice;	  // 최소 가격
    private Integer maxPrice;	  // 최대 가격
    private Boolean sortByCommentCount;


	public Boolean getSortByCommentCount() {
		return sortByCommentCount;
	}
	public void setSortByCommentCount(Boolean sortByCommentCount) {
		this.sortByCommentCount = sortByCommentCount;
	}
	public String getCategoryNo() {
		return categoryNo;
	}
	public void setCategoryNo(String categoryNo) {
		this.categoryNo = categoryNo;
	}
	public Integer getUserNo() {
		return userNo;
	}
	public void setUserNo(Integer userNo) {
		this.userNo = userNo;
	}
	public Integer getMinViews() {
		return minViews;
	}
	public void setMinViews(Integer minViews) {
		this.minViews = minViews;
	}
	public Integer getMaxViews() {
		return maxViews;
	}
	public void setMaxViews(Integer maxViews) {
		this.maxViews = maxViews;
	}
	public Integer getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(Integer minPrice) {
		this.minPrice = minPrice;
	}
	public Integer getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(Integer maxPrice) {
		this.maxPrice = maxPrice;
	}

}