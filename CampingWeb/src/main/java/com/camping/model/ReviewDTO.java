package com.camping.model;

import java.sql.Date;

public class ReviewDTO {
	
	private int review_no;       // 후기 번호
    private int customer_no;     // 고객 번호
    private int product_no;      // 상품 번호
    private String content;     // 후기 내용
    private Date review_date;    // 후기 작성 날짜
	
    
    
    public int getReview_no() {
		return review_no;
	}
	public void setReview_no(int review_no) {
		this.review_no = review_no;
	}
	
	
	public int getCustomer_no() {
		return customer_no;
	}
	public void setCustomer_no(int customer_no) {
		this.customer_no = customer_no;
	}
	
	
	public int getProduct_no() {
		return product_no;
	}
	public void setProduct_no(int product_no) {
		this.product_no = product_no;
	}
	
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	public Date getReview_date() {
		return review_date;
	}
	public void setReview_date(Date review_date) {
		this.review_date = review_date;
	}
	
	
	
	private String firstLine;

	public String getFirstLine() {
	    return firstLine;
	}

	public void setFirstLine(String firstLine) {
	    this.firstLine = firstLine;
	}
    
}
