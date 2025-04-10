package com.camping.model;

public class RentalStockDTO {
    private int product_no;
    private String product_name;
    private int total_qty;
    private int rented_qty;
    private int available_qty;

    // Getter/Setter
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

    public int getTotal_qty() {
        return total_qty;
    }

    public void setTotal_qty(int total_qty) {
        this.total_qty = total_qty;
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
