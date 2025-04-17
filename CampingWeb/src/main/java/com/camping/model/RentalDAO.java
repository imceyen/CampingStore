package com.camping.model;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * RentalDAO - 대여 관련 DB 작업을 처리하는 DAO 클래스
 */
public class RentalDAO {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String sql = null;

    private static RentalDAO instance = null;

    // 날짜 포맷터 (DB에서 가져온 문자열을 날짜로 변환하기 위함)
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Singleton 패턴 적용
    private RentalDAO() {}

    public static RentalDAO getInstance() {
        if (instance == null) {
            instance = new RentalDAO();
        }
        return instance;
    }

    // DB 연결
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

    // DB 리소스 닫기 (ResultSet 포함)
    public void closeConn(ResultSet rs, PreparedStatement pstmt, Connection con) {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DB 리소스 닫기 (PreparedStatement만)
    public void closeConn(PreparedStatement pstmt, Connection con) {
        try {
            if (pstmt != null) pstmt.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 전체 대여 목록 조회 (대여자, 상품 정보 포함)
     */
    public List<RentalDTO> getRentalList() {
        List<RentalDTO> list = new ArrayList<>();
        openConn();

        String sql = """
            SELECT
                r.rental_no,
                r.customer_no,
                r.product_no,
                r.rental_qty,
                r.rental_date,
                r.return_date,
                r.rental_status,
                c.name,
                p.product_name,
                p.product_image
            FROM
                rental r
            JOIN
                customer c ON r.customer_no = c.customer_no
            JOIN
                cam_product p ON r.product_no = p.product_no
            ORDER BY
                r.rental_status ASC, r.return_date ASC
        """;

        try {
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                RentalDTO dto = new RentalDTO();
                dto.setRental_no(rs.getInt("rental_no"));
                dto.setCustomer_no(rs.getInt("customer_no"));
                dto.setProduct_no(rs.getInt("product_no"));
                dto.setRental_qty(rs.getInt("rental_qty"));

                // 날짜 변환
                String rentalDateStr = rs.getString("rental_date");
                String returnDateStr = rs.getString("return_date");

                LocalDate returnDate = null;
                if (rentalDateStr != null && !rentalDateStr.isEmpty()) {
                    dto.setRental_date(LocalDateTime.parse(rentalDateStr, formatter).toLocalDate());
                }
                if (returnDateStr != null && !returnDateStr.isEmpty()) {
                    returnDate = LocalDateTime.parse(returnDateStr, formatter).toLocalDate();
                    dto.setReturn_date(returnDate);
                }

                dto.setRental_status(rs.getString("rental_status"));
                dto.setName(rs.getString("name"));
                dto.setProduct_name(rs.getString("product_name"));

                // 남은 일수 계산 및 상태 처리
                int remainDays = 0;
                String status = dto.getRental_status();
                LocalDate today = LocalDate.now();

                if (status != null && returnDate != null) {
                	remainDays = (int) ChronoUnit.DAYS.between(today, returnDate);
                    if ("대여중".equals(status.trim())) {
                        if (remainDays >= 0) {
                        } else if (remainDays < 0) {
                            dto.setRental_status("연체");
                        }
                    } else if ("연체".equals(status.trim())) {
                    } else if ("반납완료".equals(status.trim())) {
                        remainDays = 0;
                    }
                }

                dto.setRemaining_days(remainDays);
                System.out.println("반납일: " + returnDate + " → 오늘: " + today + " 남은 일수: " + remainDays + ", 상태: " + dto.getRental_status());
                list.add(dto);
            }

            // 남은 일수 오름차순 정렬
            list.sort(Comparator.comparingInt(RentalDTO::getRemaining_days));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConn(rs, pstmt, con);
        }

        return list;
    }

    /**
     * 재고 감소 처리 (대여 시)
     */
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

    /**
     * 재고 증가 처리 (반납 시)
     */
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

    /**
     * 전체 상품의 대여 재고 현황 조회
     */
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

    /**
     * 대여 상태를 반납완료로 변경
     */
    public int updateRentalStatusToReturned(int rentalNo, int qtyToReturn) {
        int result = 0;
        openConn();
        try {
            // 현재 수량 조회
            String checkSql = "SELECT rental_qty FROM rental WHERE rental_no = ?";
            pstmt = con.prepareStatement(checkSql);
            pstmt.setInt(1, rentalNo);
            rs = pstmt.executeQuery();

            int currentQty = 0;
            if (rs.next()) {
                currentQty = rs.getInt("rental_qty");
            }

            rs.close();
            pstmt.close();

            if (qtyToReturn >= currentQty) {
                // 전량 반납일 경우 상태 변경
                sql = "UPDATE rental SET rental_status = '반납완료' WHERE rental_no = ?";
                pstmt = con.prepareStatement(sql);
                pstmt.setInt(1, rentalNo);
            } else {
                // 일부 반납일 경우 수량만 감소
                sql = "UPDATE rental SET rental_qty = rental_qty - ? WHERE rental_no = ?";
                pstmt = con.prepareStatement(sql);
                pstmt.setInt(1, qtyToReturn);
                pstmt.setInt(2, rentalNo);
            }

            result = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConn(pstmt, con);
        }

        return result;
    }

    /**
     * 특정 상품의 대여중 목록 조회
     */
    public List<RentalDTO> getRentedListByProduct(String productNo) {
        List<RentalDTO> list = new ArrayList<>();
        openConn();

        try {
            sql = """
                SELECT * FROM rental
                WHERE product_no = ? AND rental_status = '대여중'
                ORDER BY rental_date ASC
            """;
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(productNo));
            rs = pstmt.executeQuery();

            while (rs.next()) {
                RentalDTO dto = new RentalDTO();
                dto.setRental_no(rs.getInt("rental_no"));
                dto.setCustomer_no(rs.getInt("customer_no"));
                dto.setProduct_no(rs.getInt("product_no"));
                dto.setRental_qty(rs.getInt("rental_qty"));

                String rentalDateStr = rs.getString("rental_date");
                if (rentalDateStr != null) {
                    dto.setRental_date(LocalDateTime.parse(rentalDateStr, formatter).toLocalDate());
                }

                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConn(rs, pstmt, con);
        }

        return list;
    }
}
