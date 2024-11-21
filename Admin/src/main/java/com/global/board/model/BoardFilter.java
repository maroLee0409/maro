package com.global.board.model;

import com.global.utils.Filter;

public class BoardFilter extends Filter {
    private String categoryNo;    // 카테고리 번호
    private Integer userNo;       // 사용자 번호
    private Integer minViews;     // 최소 조회수
    private Integer maxViews;     // 최대 조회수
    private Boolean sortByCommentCount;  // 댓글 수 기준 정렬 옵션 추가
    
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
	public Boolean getSortByCommentCount() {
		return sortByCommentCount;
	}
	public void setSortByCommentCount(Boolean sortByCommentCount) {
		this.sortByCommentCount = sortByCommentCount;
	}
    
    
}
