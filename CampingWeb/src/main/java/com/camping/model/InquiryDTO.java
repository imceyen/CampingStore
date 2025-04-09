package com.camping.model;

import java.sql.Date;

public class InquiryDTO {

	private int inquiry_no;      	// 문의 번호
    private int customer_no;     	// 고객 번호
    private int product_no;      	// 상품 번호
    private String content;     	// 문의 내용
    private Date inquiry_date;   	// 문의 작성 날짜
    private String answer_content; 	// 문의 답변 내용
    private Date answer_date;    	// 문의 답변 작성 날짜
	
    
    
    public int getInquiry_no() {
		return inquiry_no;
	}
	public void setInquiry_no(int inquiry_no) {
		this.inquiry_no = inquiry_no;
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
	
	
	public Date getInquiry_date() {
		return inquiry_date;
	}
	public void setInquiry_date(Date inquiry_date) {
		this.inquiry_date = inquiry_date;
	}
	
	
	public String getAnswer_content() {
		return answer_content;
	}
	public void setAnswer_content(String answer_content) {
		this.answer_content = answer_content;
	}
	
	
	public Date getAnswer_date() {
		return answer_date;
	}
	public void setAnswer_date(Date answer_date) {
		this.answer_date = answer_date;
	}
    
    
}
