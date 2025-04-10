package com.camping.model;

public class RentalDTO {
	private int rental_no;
    private int customer_no;
    private int product_no;
    private int rental_qty;
    private int remain_qty;
    private int rental_price;
    private String rental_date;
    private String return_date;
    private String actual_return_date;
    private String rental_status;
    private int remaining_days;  // 남은 일수

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
	public String getRental_date() {
		return rental_date;
	}
	public void setRental_date(String rental_date) {
		this.rental_date = rental_date;
	}
	public String getReturn_date() {
		return return_date;
	}
	public void setReturn_date(String return_date) {
		this.return_date = return_date;
	}
	public String getActual_return_date() {
		return actual_return_date;
	}
	public void setActual_return_date(String actual_return_date) {
		this.actual_return_date = actual_return_date;
	}
	public String getRental_status() {
		return rental_status;
	}
	public void setRental_status(String rental_status) {
		this.rental_status = rental_status;
	}

}
