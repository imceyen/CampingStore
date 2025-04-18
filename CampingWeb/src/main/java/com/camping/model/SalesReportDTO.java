package com.camping.model;

public class SalesReportDTO {
	private String category_name;   // 카테고리별 매출 구분에 필요
    private String gender;         // 'M', 'F' 또는 null
    private int age_group;          // 10, 20, 30, ...
    private String order_date;        // 일자별 매출
    private String product_name;       
    private int total_sales;        // 해당 조건에 대한 매출합계
    private int total_profit;       // 해당 조건에 대한 순이익
    private int total_cost;        // 상품별 총 원가 (매입가 * 수량)
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

	public int getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(int total_cost) {
        this.total_cost = total_cost;
    }

	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getAge_group() {
		return age_group;
	}
	public void setAge_group(int age_group) {
		this.age_group = age_group;
	}
	public String getOrder_date() {
		return order_date;
	}
	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}
	public int getTotal_sales() {
		return total_sales;
	}
	public void setTotal_sales(int total_sales) {
		this.total_sales = total_sales;
	}
	public int getTotal_profit() {
		return total_profit;
	}
	public void setTotal_profit(int total_profit) {
		this.total_profit = total_profit;
	}
}
