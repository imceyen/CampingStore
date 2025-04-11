package com.camping.model;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class RentalDAO {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String sql = null;

    private static RentalDAO instance = null;

    private RentalDAO() {}

    public static RentalDAO getInstance() {
        if (instance == null) {
            instance = new RentalDAO();
        }
        return instance;
    }

    public void openConn() {
        try {
            Context initCtx = new InitialContext();
            Context ctx = (Context) initCtx.lookup("java:comp/env");
            DataSource ds = (DataSource) ctx.lookup("jdbc/myoracle");
            con = ds.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeConn(ResultSet rs, PreparedStatement pstmt, Connection con) {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConn(PreparedStatement pstmt, Connection con) {
        try {
            if (pstmt != null) pstmt.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 대여 목록 조회
    public List<RentalDTO> getRentalList() {
        List<RentalDTO> list = new ArrayList<>();
        openConn();

        String sql = "SELECT rental_no, customer_no, product_no, rental_qty, rental_date, return_date, rental_status FROM rental";

        try {
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                RentalDTO dto = new RentalDTO();
                dto.setRental_no(rs.getInt("rental_no"));
                dto.setCustomer_no(rs.getInt("customer_no"));
                dto.setProduct_no(rs.getInt("product_no"));
                dto.setRental_qty(rs.getInt("rental_qty"));
                dto.setRental_date(rs.getString("rental_date"));
                dto.setReturn_date(rs.getString("return_date"));
                dto.setRental_status(rs.getString("rental_status"));

                // 날짜 계산 (반납 완료인 경우 무조건 0)
                int remainDays = 0;
                String status = rs.getString("rental_status");
                Date returnSqlDate = rs.getDate("return_date");  // java.sql.Date

                if (!"반납완료".equals(status) && returnSqlDate != null) {
                    LocalDate today = LocalDate.now();
                    LocalDate returnDate = returnSqlDate.toLocalDate();
                    long daysLeft = ChronoUnit.DAYS.between(today, returnDate);
                    remainDays = (int) Math.max(daysLeft, 0);
                }

                dto.setRemaining_days(remainDays);  // ✅ 수정된 부분
                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConn(rs, pstmt, con);
        }

        return list;
    }

    // 대여 상품 재고 현황 조회 (남은 수량 많은 순서로 정렬)
    public List<RentalStockDTO> getRentalStockStatus() {
        List<RentalStockDTO> list = new ArrayList<>();
        openConn();
        try {
            sql = """
                SELECT
                    p.product_no,
                    p.product_name,
                    p.rental_stock,
                    NVL(SUM(r.rental_qty), 0) AS rented_qty,
                    (p.rental_stock - NVL(SUM(r.rental_qty), 0)) AS available_qty
                FROM
                    cam_product p
                LEFT JOIN
                    rental r ON p.product_no = r.product_no AND r.rental_status = '대여중'
                GROUP BY
                    p.product_no, p.product_name, p.rental_stock
                ORDER BY
                    available_qty DESC
            """;
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                RentalStockDTO dto = new RentalStockDTO();
                dto.setProduct_no(rs.getInt("product_no"));
                dto.setProduct_name(rs.getString("product_name"));
                dto.setRental_stock(rs.getInt("rental_stock"));
                dto.setRented_qty(rs.getInt("rented_qty"));
                dto.setAvailable_qty(rs.getInt("available_qty"));
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConn(rs, pstmt, con);
        }
        return list;
    }

    // 대여 등록 시 재고 차감
    public int decreaseRentalStock(int productNo, int qty) {
        int result = 0;
        openConn();
        try {
            sql = "UPDATE cam_product SET rental_stock = rental_stock - ? WHERE product_no = ? AND rental_stock >= ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, qty);
            pstmt.setInt(2, productNo);
            pstmt.setInt(3, qty);
            result = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConn(pstmt, con);
        }
        return result;
    }

    // 반납 시 재고 복구
    public int increaseRentalStock(int productNo, int qty) {
        int result = 0;
        openConn();
        try {
            sql = "UPDATE cam_product SET rental_stock = rental_stock + ? WHERE product_no = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, qty);
            pstmt.setInt(2, productNo);
            result = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConn(pstmt, con);
        }
        return result;
    }

    // 반납 처리: rental_status = '반납완료'로 변경
    public int updateRentalStatusToReturned(int rentalNo) {
        int result = 0;
        openConn();
        try {
            sql = "UPDATE rental SET rental_status = '반납완료' WHERE rental_no = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, rentalNo);
            result = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConn(pstmt, con);
        }
        return result;
    }
}
