package com.global.event.model;

import java.sql.Date;

public class BannerEvent extends Event {
    private int imageId;
    private String linkUrl;
    private Date displayStartDate;
    private Date displayEndDate;
    
	public BannerEvent() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BannerEvent(int eventNo, String name, String description, int views, Date startDate, Date endDate,
			Date createdAt, Date updatedAt, String status, String eventType) {
		super(eventNo, name, description, views, startDate, endDate, createdAt, updatedAt, status, eventType);
		// TODO Auto-generated constructor stub
	}
	public BannerEvent(int imageId, String linkUrl, Date displayStartDate, Date displayEndDate) {
		super();
		this.imageId = imageId;
		this.linkUrl = linkUrl;
		this.displayStartDate = displayStartDate;
		this.displayEndDate = displayEndDate;
	}
	public int getImageId() {
		return imageId;
	}
	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public Date getDisplayStartDate() {
		return displayStartDate;
	}
	public void setDisplayStartDate(Date displayStartDate) {
		this.displayStartDate = displayStartDate;
	}
	public Date getDisplayEndDate() {
		return displayEndDate;
	}
	public void setDisplayEndDate(Date displayEndDate) {
		this.displayEndDate = displayEndDate;
	}
    
    
}
