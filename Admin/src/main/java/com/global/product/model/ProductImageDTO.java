package com.global.product.model;

public class ProductImageDTO {

	
	private int image_no;
	private int proudct_no;
	private String image_url;
	private String description;
	public int getImage_no() {
		return image_no;
	}
	public void setImage_no(int image_no) {
		this.image_no = image_no;
	}
	public int getProudct_no() {
		return proudct_no;
	}
	public void setProudct_no(int proudct_no) {
		this.proudct_no = proudct_no;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
