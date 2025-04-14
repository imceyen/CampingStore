package com.camping.model;

import java.sql.*;
import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class SalesReportDAO {

	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = null;
	
	private static SalesReportDAO instance = null;
	
	private SalesReportDAO() {}
	
	public static SalesReportDAO getInstance() {
		if(instance == null) {
			instance = new SalesReportDAO();
		}
		return instance;
	}

	public void openConn() {
		try {
			Context initCtx = new InitialContext();
			Context ctx = (Context)initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource)ctx.lookup("jdbc/myoracle");
			con = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void closeConn(ResultSet rs, PreparedStatement pstmt, Connection con) {
		try {
			if(rs != null) rs.close();
			if(pstmt != null) pstmt.close();
			if(con != null) con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void closeConn(PreparedStatement pstmt, Connection con) {
		try {
			if(pstmt != null) pstmt.close();
			if(con != null) con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// sales_statistics 테이블에 매출 데이터 계산 후 저장
	// 대여 매출도 계산하여 sales_statistics에 반영
	public int calculateAndInsertSalesStatistics() {
	    int result = 0;
	    try {
	        openConn();

	        // 모든 상품 기준으로 시작
	        sql = "SELECT product_no, input_price, output_price FROM cam_product";
	        pstmt = con.prepareStatement(sql);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            int product_no = rs.getInt("product_no");
	            int input_price = rs.getInt("input_price");
	            int output_price = rs.getInt("output_price");

	            int order_qty = 0;
	            int total_sales = 0;
	            int total_cost = 0;
	            int total_profit = 0;

	            // 주문 수량 조회
	            String orderSql = "SELECT SUM(quantity) AS total_qty FROM order_detail WHERE product_no = ?";
	            PreparedStatement orderPstmt = con.prepareStatement(orderSql);
	            orderPstmt.setInt(1, product_no);
	            ResultSet orderRs = orderPstmt.executeQuery();
	            if (orderRs.next()) {
	                order_qty = orderRs.getInt("total_qty");
	            }
	            orderRs.close();
	            orderPstmt.close();

	            // 기본 매출 계산
	            total_sales = order_qty * output_price;
	            total_cost = order_qty * input_price;
	            total_profit = (output_price - input_price) * order_qty;

	            // 대여 매출 조회
	            String rentalSql = "SELECT SUM(rental_price) AS rental_sales FROM rental WHERE product_no = ?";
	            PreparedStatement rentalPstmt = con.prepareStatement(rentalSql);
	            rentalPstmt.setInt(1, product_no);
	            ResultSet rentalRs = rentalPstmt.executeQuery();

	            int rental_sales = 0;
	            if (rentalRs.next()) {
	                rental_sales = rentalRs.getInt("rental_sales");
	            }
	            rentalRs.close();
	            rentalPstmt.close();

	            total_sales += rental_sales;
	            total_profit += rental_sales; // 대여 수익은 순이익으로 계산

	            // 기존 데이터 삭제
	            String deleteSql = "DELETE FROM sales_statistics WHERE product_no = ?";
	            PreparedStatement deletePstmt = con.prepareStatement(deleteSql);
	            deletePstmt.setInt(1, product_no);
	            deletePstmt.executeUpdate();
	            deletePstmt.close();

	            // 새 데이터 삽입
	            String insertSql = "INSERT INTO sales_statistics (product_no, total_sales, total_cost, total_profit) VALUES (?, ?, ?, ?)";
	            PreparedStatement insertPstmt = con.prepareStatement(insertSql);
	            insertPstmt.setInt(1, product_no);
	            insertPstmt.setInt(2, total_sales);
	            insertPstmt.setInt(3, total_cost);
	            insertPstmt.setInt(4, total_profit);
	            result += insertPstmt.executeUpdate();
	            insertPstmt.close();
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        closeConn(rs, pstmt, con);
	    }
	    return result;
	}



	
	public List<SalesReportDTO> getTotalSalesForChart() {
	    List<SalesReportDTO> list = new ArrayList<>();
	    try {
	        openConn();

	        sql = "SELECT cp.product_name, ss.total_sales " +
	              "FROM sales_statistics ss " +
	              "JOIN cam_product cp ON ss.product_no = cp.product_no";

	        pstmt = con.prepareStatement(sql);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            SalesReportDTO dto = new SalesReportDTO();
	            dto.setProduct_name(rs.getString("product_name"));
	            dto.setTotal_sales(rs.getInt("total_sales"));
	            list.add(dto);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        closeConn(rs, pstmt, con);
	    }
	    return list;
	}
	
	public List<SalesReportDTO> getTotalSalesList() {
	    List<SalesReportDTO> list = new ArrayList<>();
	    try {
	        openConn();
	        sql = "SELECT s.product_no, p.product_name, s.total_sales " +
	              "FROM sales_statistics s JOIN cam_product p ON s.product_no = p.product_no";

	        pstmt = con.prepareStatement(sql);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            SalesReportDTO dto = new SalesReportDTO();
	            dto.setProduct_name(rs.getString("product_name"));
	            dto.setTotal_sales(rs.getInt("total_sales"));
	            list.add(dto);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        closeConn(rs, pstmt, con);
	    }
	    return list;
	}

	public int getTotalSales() {
	    int total = 0;

	    try {
	        openConn();
	        sql = "SELECT SUM(total_sales) FROM sales_statistics";
	        pstmt = con.prepareStatement(sql);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            total = rs.getInt(1);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        closeConn(rs, pstmt, con);
	    }

	    return total;
	}
	public int getTotalProfit() {
	    int profit = 0;

	    try {
	        openConn();
	        sql = "SELECT SUM(total_profit) FROM sales_statistics";
	        pstmt = con.prepareStatement(sql);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            profit = rs.getInt(1);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        closeConn(rs, pstmt, con);
	    }

	    return profit;
	}

	// SalesReportDAO.java 안에 추가
	public List<SalesStatisticsDTO> getAllSalesStatistics() {
	    List<SalesStatisticsDTO> list = new ArrayList<>();

	    try {
	        openConn();
	        sql = "SELECT ss.product_no, cp.product_name, ss.total_sales, ss.total_cost, ss.total_profit " +
	              "FROM sales_statistics ss " +
	              "JOIN cam_product cp ON ss.product_no = cp.product_no";

	        pstmt = con.prepareStatement(sql);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            SalesStatisticsDTO dto = new SalesStatisticsDTO();
	            dto.setProduct_no(rs.getInt("product_no"));
	            dto.setProduct_name(rs.getString("product_name"));
	            dto.setTotal_sales(rs.getInt("total_sales"));
	            dto.setTotal_cost(rs.getInt("total_cost"));
	            dto.setTotal_profit(rs.getInt("total_profit"));
	            list.add(dto);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        closeConn(rs, pstmt, con);
	    }

	    return list;
	}

	public List<SalesReportDTO> getSalesByGender(String gender) {
	    List<SalesReportDTO> list = new ArrayList<>();

	    try {
	        openConn();

	        // 전체일 경우 조건 없이
	        if (gender.equals("all")) {
	            sql = "SELECT cp.product_name, " +
	                  "SUM(od.quantity * cp.output_price) AS total_sales, " +
	                  "SUM(od.quantity * cp.input_price) AS total_cost, " +
	                  "SUM(od.quantity * (cp.output_price - cp.input_price)) AS total_profit " +
	                  "FROM orders o " +
	                  "JOIN order_detail od ON o.order_no = od.order_no " +
	                  "JOIN cam_product cp ON od.product_no = cp.product_no " +
	                  "JOIN customer c ON o.customer_no = c.customer_no " +
	                  "GROUP BY cp.product_name";
	        } else {
	            sql = "SELECT cp.product_name, " +
	                  "SUM(od.quantity * cp.output_price) AS total_sales, " +
	                  "SUM(od.quantity * cp.input_price) AS total_cost, " +
	                  "SUM(od.quantity * (cp.output_price - cp.input_price)) AS total_profit " +
	                  "FROM orders o " +
	                  "JOIN order_detail od ON o.order_no = od.order_no " +
	                  "JOIN cam_product cp ON od.product_no = cp.product_no " +
	                  "JOIN customer c ON o.customer_no = c.customer_no " +
	                  "WHERE c.gender = ? " +
	                  "GROUP BY cp.product_name";
	        }

	        pstmt = con.prepareStatement(sql);

	        if (!gender.equals("all")) {
	            pstmt.setString(1, gender);
	        }

	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            SalesReportDTO dto = new SalesReportDTO();
	            dto.setProduct_name(rs.getString("product_name"));
	            dto.setTotal_sales(rs.getInt("total_sales"));
	            dto.setTotal_cost(rs.getInt("total_cost"));
	            dto.setTotal_profit(rs.getInt("total_profit"));
	            list.add(dto);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        closeConn(rs, pstmt, con);
	    }

	    return list;
	}

	public List<SalesReportDTO> getSalesByAgeGroup(int ageGroup) {
	    List<SalesReportDTO> list = new ArrayList<>();

	    try {
	        openConn();

	        // 현재 연도 구하기 (예: 2025)
	        sql = "SELECT TO_NUMBER(TO_CHAR(SYSDATE, 'YYYY')) FROM dual";
	        pstmt = con.prepareStatement(sql);
	        rs = pstmt.executeQuery();
	        int currentYear = 2025;
	        if (rs.next()) {
	            currentYear = rs.getInt(1);
	        }
	        rs.close();
	        pstmt.close();

	        int startYear = currentYear - ageGroup - 9;
	        int endYear = currentYear - ageGroup;

	        sql = "SELECT cp.product_name, " +
	              "       SUM(od.quantity * cp.output_price) AS total_sales, " +
	              "       SUM(od.quantity * cp.input_price) AS total_cost, " +
	              "       SUM((od.quantity * cp.output_price) - (od.quantity * cp.input_price)) AS total_profit " +
	              "FROM orders o " +
	              "JOIN order_detail od ON o.order_no = od.order_no " +
	              "JOIN cam_product cp ON od.product_no = cp.product_no " +
	              "JOIN customer c ON o.customer_no = c.customer_no " +
	              "WHERE TO_NUMBER(SUBSTR(TO_CHAR(c.birth_date, 'YYYY-MM-DD'), 1, 4)) BETWEEN ? AND ? " +
	              "GROUP BY cp.product_name " +
	              "ORDER BY total_sales DESC";

	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, startYear);
	        pstmt.setInt(2, endYear);

	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	            SalesReportDTO dto = new SalesReportDTO();
	            dto.setProduct_name(rs.getString("product_name"));
	            dto.setTotal_sales(rs.getInt("total_sales"));
	            dto.setTotal_cost(rs.getInt("total_cost"));
	            dto.setTotal_profit(rs.getInt("total_profit"));
	            list.add(dto);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        closeConn(rs, pstmt, con);
	    }

	    return list;
	}
	public List<SalesReportDTO> getSalesByDateRange(String startDate, String endDate) {
	    List<SalesReportDTO> list = new ArrayList<>();

	    try {
	        openConn();

	        sql = "SELECT cp.product_name, " +
	              "       SUM(od.quantity * cp.output_price) AS total_sales, " +
	              "       SUM(od.quantity * cp.input_price) AS total_cost, " +
	              "       SUM((od.quantity * cp.output_price) - (od.quantity * cp.input_price)) AS total_profit " +
	              "FROM orders o " +
	              "JOIN order_detail od ON o.order_no = od.order_no " +
	              "JOIN cam_product cp ON od.product_no = cp.product_no " +
	              "WHERE o.order_date BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD') " +
	              "GROUP BY cp.product_name " +
	              "ORDER BY total_sales DESC";

	        pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, startDate);
	        pstmt.setString(2, endDate);

	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	            SalesReportDTO dto = new SalesReportDTO();
	            dto.setProduct_name(rs.getString("product_name"));
	            dto.setTotal_sales(rs.getInt("total_sales"));
	            dto.setTotal_cost(rs.getInt("total_cost"));
	            dto.setTotal_profit(rs.getInt("total_profit"));
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


