package com.camping.model;

import java.time.LocalDate;

public class RentalDTO {
	private int rental_no;
    private int customer_no;
    private int product_no;
    private int rental_qty;
    private int remain_qty;
    private int rental_price;
    private LocalDate rental_date;
    private LocalDate return_date;
    private LocalDate actual_return_date;
    private String rental_status;
    private int remaining_days;  // 남은 일수
    private String name;
    private String product_name;
    private String product_image;
    
    
    

    public String getProduct_image() {
		return product_image;
	}
	public void setProduct_image(String product_image) {
		this.product_image = product_image;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public int getRemaining_days() {
        return remaining_days;
    }
    public void setRemaining_days(int remaining_days) {
        this.remaining_days = remaining_days;
    }
    
    
	public int getRental_no() {
		return rental_no;
	}
	public void setRental_no(int rental_no) {
		this.rental_no = rental_no;
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
	public int getRental_qty() {
		return rental_qty;
	}
	public void setRental_qty(int rental_qty) {
		this.rental_qty = rental_qty;
	}
	public int getRemain_qty() {
		return remain_qty;
	}
	public void setRemain_qty(int remain_qty) {
		this.remain_qty = remain_qty;
	}
	public int getRental_price() {
		return rental_price;
	}
	public void setRental_price(int rental_price) {
		this.rental_price = rental_price;
	}
	
	public String getRental_status() {
		return rental_status;
	}
	public void setRental_status(String rental_status) {
		this.rental_status = rental_status;
	}
	public LocalDate getRental_date() {
		return rental_date;
	}
	public void setRental_date(LocalDate rental_date) {
		this.rental_date = rental_date;
	}
	public LocalDate getReturn_date() {
		return return_date;
	}
	public void setReturn_date(LocalDate return_date) {
		this.return_date = return_date;
	}
	public LocalDate getActual_return_date() {
		return actual_return_date;
	}
	public void setActual_return_date(LocalDate actual_return_date) {
		this.actual_return_date = actual_return_date;
	}

}
