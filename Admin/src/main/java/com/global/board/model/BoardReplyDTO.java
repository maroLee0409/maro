package com.global.board.model;
import java.util.Date;

public class BoardReplyDTO {
    private Integer replyNo;
    private Integer boardNo;
    private Integer userNo;
    private String content;
    private Integer leftVal;
    private Integer rightVal;
    private Integer nodeLevel;
    private Integer parentReplyNo;
    private Date createdAt;     // 생성일
    private Date updatedAt;     // 수정일
    private String isDeleted;   // 삭제 여부 ('N'은 삭제 X, 'Y'는 삭제 O)

    
	/* USERS 정보를 함께 담을 필요가있음.*/
	
	private String userId;
	private String userName;
	private String userEmail;
	private String userType;
	public Integer getReplyNo() {
		return replyNo;
	}
	public void setReplyNo(Integer replyNo) {
		this.replyNo = replyNo;
	}
	public Integer getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(Integer boardNo) {
		this.boardNo = boardNo;
	}
	public Integer getUserNo() {
		return userNo;
	}
	public void setUserNo(Integer userNo) {
		this.userNo = userNo;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getLeftVal() {
		return leftVal;
	}
	public void setLeftVal(Integer leftVal) {
		this.leftVal = leftVal;
	}
	public Integer getRightVal() {
		return rightVal;
	}
	public void setRightVal(Integer rightVal) {
		this.rightVal = rightVal;
	}
	public Integer getNodeLevel() {
		return nodeLevel;
	}
	public void setNodeLevel(Integer nodeLevel) {
		this.nodeLevel = nodeLevel;
	}
	public Integer getParentReplyNo() {
		return parentReplyNo;
	}
	public void setParentReplyNo(Integer parentReplyNo) {
		this.parentReplyNo = parentReplyNo;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
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
    
    

}
