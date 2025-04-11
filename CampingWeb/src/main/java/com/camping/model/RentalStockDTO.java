package com.camping.model;

public class RentalStockDTO {
	private int rental_no;
    private int product_no;
    private String product_name;
    private int rental_stock;   // 대여 가능한 총 재고
    private int rented_qty;     // 현재 대여 중 수량
    private int available_qty;  // 남은 수량

    public int getRental_no() {
        return rental_no;
    }
    public void setRental_no(int rental_no) {
        this.rental_no = rental_no;
    }
    public int getProduct_no() {
        return product_no;
    }

    public void setProduct_no(int product_no) {
        this.product_no = product_no;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getRental_stock() {
        return rental_stock;
    }

    public void setRental_stock(int rental_stock) {
        this.rental_stock = rental_stock;
    }

    public int getRented_qty() {
        return rented_qty;
    }

    public void setRented_qty(int rented_qty) {
        this.rented_qty = rented_qty;
    }

    public int getAvailable_qty() {
        return available_qty;
    }

    public void setAvailable_qty(int available_qty) {
        this.available_qty = available_qty;
    }

    // 대여 가능 상태 표현용
    public String getRental_status() {
        return available_qty > 0 ? "대여 가능" : "대여 불가";
    }
}
