package com.global.board.model;

public class BoardCategoryDTO {
	
	private String categoryNo;
	private String name;
	private String description;
	private String isDeleted;
	
	public BoardCategoryDTO() {
	}

	public BoardCategoryDTO(String categoryNo, String name, String description, String isDeleted) {
		super();
		this.categoryNo = categoryNo;
		this.name = name;
		this.description = description;
		this.isDeleted = isDeleted;
	}

	public String getCategoryNo() {
		return categoryNo;
	}

	public void setCategoryNo(String categoryNo) {
		this.categoryNo = categoryNo;
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

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public String toString() {
		return "BoardCategoryDTO [categoryNo=" + categoryNo + ", name=" + name + ", description=" + description
				+ ", isDeleted=" + isDeleted + "]";
	}
		
	
}
