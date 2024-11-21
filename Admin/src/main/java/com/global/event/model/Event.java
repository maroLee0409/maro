package com.global.event.model;

import java.sql.Date;

public class Event {
	
		private EventImage eventImage; // 이미지가 없을 때는 null
	
	   	private int eventNo;
	    private String name;
	    private String description;
	    private int views;
	    private Date startDate;
	    private Date endDate;
	    private Date createdAt;
	    private Date updatedAt;
	    private String status;
	    private String eventType;
	    
		public Event() {
			super();
			// TODO Auto-generated constructor stub
		}

		public Event(int eventNo, String name, String description, int views, Date startDate, Date endDate,
				Date createdAt, Date updatedAt, String status, String eventType) {
			super();
			this.eventNo = eventNo;
			this.name = name;
			this.description = description;
			this.views = views;
			this.startDate = startDate;
			this.endDate = endDate;
			this.createdAt = createdAt;
			this.updatedAt = updatedAt;
			this.status = status;
			this.eventType = eventType;
		}

		public int getEventNo() {
			return eventNo;
		}

		public void setEventNo(int eventNo) {
			this.eventNo = eventNo;
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

		public int getViews() {
			return views;
		}

		public void setViews(int views) {
			this.views = views;
		}

		public Date getStartDate() {
			return startDate;
		}

		public void setStartDate(Date startDate) {
			this.startDate = startDate;
		}

		public Date getEndDate() {
			return endDate;
		}

		public void setEndDate(Date endDate) {
			this.endDate = endDate;
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

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getEventType() {
			return eventType;
		}

		public void setEventType(String eventType) {
			this.eventType = eventType;
		}
		
		/* Image에 대한 getter, setter*/
		public EventImage getEventImage() {
	        return eventImage;
	    }

	    public void setEventImage(EventImage eventImage) {
	        this.eventImage = eventImage;
	    }

	    public boolean hasImage() {
	        return this.eventImage != null;
	    }
	    
	
}
