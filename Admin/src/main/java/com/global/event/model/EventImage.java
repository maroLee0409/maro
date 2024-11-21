package com.global.event.model;

import java.sql.Date;

public class EventImage {
	private int imageId;
	private int eventNo;
	private String filePath;
	private String fileName;
	private int fileSize;
	private Date createdAt;
	private Date updatedAt;

	public EventImage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EventImage(int imageId, int eventNo, String filePath, String fileName, int fileSize, Date createdAt,
			Date updatedAt) {
		super();
		this.imageId = imageId;
		this.eventNo = eventNo;
		this.filePath = filePath;
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public int getEventNo() {
		return eventNo;
	}

	public void setEventNo(int eventNo) {
		this.eventNo = eventNo;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
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

	
	
}
