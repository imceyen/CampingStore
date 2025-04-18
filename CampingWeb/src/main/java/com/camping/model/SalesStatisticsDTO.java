package com.camping.model;

public class SalesStatisticsDTO {
	private int product_no;
	private String product_name;
    private int total_sales;
    private int total_cost;
    private int total_profit;
    private int sold_qty;
    private int rental_qty;
    
    
    
    public int getSold_qty() {
		return sold_qty;
	}

	public void setSold_qty(int sold_qty) {
		this.sold_qty = sold_qty;
	}

	public int getRental_qty() {
		return rental_qty;
	}

	public void setRental_qty(int rental_qty) {
		this.rental_qty = rental_qty;
	}
    
    
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public int getProduct_no() {
		return product_no;
	}
	public void setProduct_no(int product_no) {
		this.product_no = product_no;
	}
	public int getTotal_sales() {
		return total_sales;
	}
	public void setTotal_sales(int total_sales) {
		this.total_sales = total_sales;
	}
	public int getTotal_cost() {
		return total_cost;
	}
	public void setTotal_cost(int total_cost) {
		this.total_cost = total_cost;
	}
	public int getTotal_profit() {
		return total_profit;
	}
	public void setTotal_profit(int total_profit) {
		this.total_profit = total_profit;
	}
    
	
    
}
