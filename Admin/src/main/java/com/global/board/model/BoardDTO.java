package com.global.board.model;

import java.sql.Date;

public class BoardDTO {
	private int boardNo;
	private Integer userNo;
	private String title;
	private String content;
	private int views; // 조회수
	private Date createAt;
	private Date updateAt;
	private String isDeleted;
	
	
	/* boardCategory Join용*/
	private String categoryNo;
	private String categoryName;
	private String description;
	
	/* USERS 정보를 함께 담을 필요가있음.*/
	
	private String userId;
	private String userName;
	private String userEmail;
	private String userType;
	
	/* 각 게시글의 댓글 수 (IS_DELETED = 'N')*/
	private int commentCount;

    
	public BoardDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BoardDTO(int boardNo, Integer userNo, String title, String content, int views, Date createAt, Date updateAt,
			String isDeleted, String categoryNo, String categoryName, String description, String userId,
			String userName, String userEmail, String userType, int commentCount, int imageNo, String imageUrl,
			Date uploadedAt) {
		super();
		this.boardNo = boardNo;
		this.userNo = userNo;
		this.title = title;
		this.content = content;
		this.views = views;
		this.createAt = createAt;
		this.updateAt = updateAt;
		this.isDeleted = isDeleted;
		this.categoryNo = categoryNo;
		this.categoryName = categoryName;
		this.description = description;
		this.userId = userId;
		this.userName = userName;
		this.userEmail = userEmail;
		this.userType = userType;
		this.commentCount = commentCount;
	}
	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
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
	public String getCategoryNo() {
		return categoryNo;
	}
	public void setCategoryNo(String categoryNo) {
		this.categoryNo = categoryNo;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	
	
	
}
