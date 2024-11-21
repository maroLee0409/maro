package com.global.product.model;

import java.sql.Date;

public class ProductCategoryImageDTO {

	private int pcimage_no;
	private String category_no;
	private String image_url;
	private String alt_text;
	private Date upload_date;
	public int getPcimage_no() {
		return pcimage_no;
	}
	public void setPcimage_no(int pcimage_no) {
		this.pcimage_no = pcimage_no;
	}
	public String getCategory_no() {
		return category_no;
	}
	public void setCategory_no(String category_no) {
		this.category_no = category_no;
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


}