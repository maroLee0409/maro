package com.global.product.model;

import java.sql.Date;

public class ProductCategoryDTO {
    private String category_no;
    private String name;
    private String description;
	
    /*category_image join용*/
    private int pcimage_no;
	private String image_url;
	private String alt_text;
	
	public int getPcimage_no() {
		return pcimage_no;
	}
	public void setPcimage_no(int pcimage_no) {
		this.pcimage_no = pcimage_no;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public String getAlt_text() {
		return alt_text;
	}
	public void setAlt_text(String alt_text) {
		this.alt_text = alt_text;
	}
	public Date getUpload_date() {
		return upload_date;
	}
	public void setUpload_date(Date upload_date) {
		this.upload_date = upload_date;
	}
	private Date upload_date;
    
    
	public String getCategory_No() {
		return category_no;
	}
	public void setCategory_No(String category_No) {
		this.category_no = category_No;
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

    
    

}