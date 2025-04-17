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

	// 상품 매출 및 매입원가 계산 로직
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

	            // 확인용 로그
	            System.out.println("product_no: " + product_no + ", input_price: " + input_price + ", output_price: " + output_price);

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
	            total_cost = order_qty * input_price; // 판매된 상품의 매입원가만 계산
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

	            // 대여에는 매입원가가 없으므로 매입원가는 그대로 판매된 상품의 매입원가만 반영
	            if (rental_sales > 0) {
	                total_cost += 0; // 대여 매출에는 매입원가가 0으로 반영
	            }

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





	
	// 매출 데이터를 포함한 상품 리스트 반환
	public List<SalesReportDTO> getTotalSalesForChart() {
	    List<SalesReportDTO> list = new ArrayList<>();
	    try {
	        openConn();

	        // 모든 상품 기준으로 매출 조회
	        sql = "SELECT cp.product_name, COALESCE(ss.total_sales, 0) AS total_sales " +
	              "FROM cam_product cp " +
	              "LEFT JOIN sales_statistics ss ON cp.product_no = ss.product_no";

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
	
	// 매출 리스트에 매출이 없는 상품도 포함
	public List<SalesReportDTO> getTotalSalesList() {
	    List<SalesReportDTO> list = new ArrayList<>();
	    try {
	        openConn();
	        // 매출이 없는 상품도 포함하여 전체 상품을 조회합니다.
	        sql = "SELECT p.product_no, p.product_name, COALESCE(SUM(s.total_sales), 0) AS total_sales " +
	              "FROM cam_product p " +
	              "LEFT JOIN sales_statistics s ON p.product_no = s.product_no " +
	              "GROUP BY p.product_no, p.product_name";

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

	        // 판매 매출 조회
	        String orderSql = 
	            "SELECT cp.product_no, cp.product_name, " +
	            "SUM(od.quantity * cp.output_price) AS total_sales, " +
	            "SUM(od.quantity * cp.input_price) AS total_cost, " +
	            "SUM(od.quantity * (cp.output_price - cp.input_price)) AS total_profit " +
	            "FROM orders o " +
	            "JOIN order_detail od ON o.order_no = od.order_no " +
	            "JOIN cam_product cp ON od.product_no = cp.product_no " +
	            "JOIN customer c ON o.customer_no = c.customer_no ";

	        if (!gender.equals("all")) {
	            orderSql += "WHERE c.gender = ? ";
	        }

	        orderSql += "GROUP BY cp.product_no, cp.product_name";

	        pstmt = con.prepareStatement(orderSql);
	        if (!gender.equals("all")) {
	            pstmt.setString(1, gender);
	        }

	        rs = pstmt.executeQuery();
	        Map<Integer, SalesReportDTO> map = new HashMap<>();

	        while (rs.next()) {
	            SalesReportDTO dto = new SalesReportDTO();
	            int product_no = rs.getInt("product_no");
	            dto.setProduct_name(rs.getString("product_name"));
	            dto.setTotal_sales(rs.getInt("total_sales"));
	            dto.setTotal_cost(rs.getInt("total_cost"));
	            dto.setTotal_profit(rs.getInt("total_profit"));
	            map.put(product_no, dto);
	        }

	        rs.close();
	        pstmt.close();

	        // 대여 매출 추가
	        String rentalSql = 
	            "SELECT r.product_no, SUM(r.rental_price) AS rental_sales " +
	            "FROM rental r " +
	            "JOIN customer c ON r.customer_no = c.customer_no ";

	        if (!gender.equals("all")) {
	            rentalSql += "WHERE c.gender = ? ";
	        }

	        rentalSql += "GROUP BY r.product_no";

	        pstmt = con.prepareStatement(rentalSql);
	        if (!gender.equals("all")) {
	            pstmt.setString(1, gender);
	        }

	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	            int product_no = rs.getInt("product_no");
	            int rental_sales = rs.getInt("rental_sales");

	            if (map.containsKey(product_no)) {
	                SalesReportDTO dto = map.get(product_no);
	                dto.setTotal_sales(dto.getTotal_sales() + rental_sales);
	                dto.setTotal_profit(dto.getTotal_profit() + rental_sales);
	            } else {
	                // 대여만 있는 상품도 포함
	                SalesReportDTO dto = new SalesReportDTO();
	                dto.setProduct_name(getProductName(product_no)); // 아래 메서드 참고
	                dto.setTotal_sales(rental_sales);
	                dto.setTotal_cost(0); // 대여는 원가 없음
	                dto.setTotal_profit(rental_sales);
	                map.put(product_no, dto);
	            }
	        }

	        list.addAll(map.values());

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        closeConn(rs, pstmt, con);
	    }

	    return list;
	}

	// 보조 메서드: product_no로 product_name 가져오기
	private String getProductName(int product_no) throws SQLException {
	    String name = "";
	    String sql = "SELECT product_name FROM cam_product WHERE product_no = ?";
	    PreparedStatement ps = con.prepareStatement(sql);
	    ps.setInt(1, product_no);
	    ResultSet rs = ps.executeQuery();
	    if (rs.next()) {
	        name = rs.getString("product_name");
	    }
	    rs.close();
	    ps.close();
	    return name;
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

	        // 판매 매출 조회
	        String orderSql = 
	            "SELECT cp.product_no, cp.product_name, " +
	            "SUM(od.quantity * cp.output_price) AS total_sales, " +
	            "SUM(od.quantity * cp.input_price) AS total_cost, " +
	            "SUM(od.quantity * (cp.output_price - cp.input_price)) AS total_profit " +
	            "FROM orders o " +
	            "JOIN order_detail od ON o.order_no = od.order_no " +
	            "JOIN cam_product cp ON od.product_no = cp.product_no " +
	            "JOIN customer c ON o.customer_no = c.customer_no " +
	            "WHERE TO_NUMBER(SUBSTR(TO_CHAR(c.birth_date, 'YYYY-MM-DD'), 1, 4)) BETWEEN ? AND ? " +
	            "GROUP BY cp.product_no, cp.product_name";

	        pstmt = con.prepareStatement(orderSql);
	        pstmt.setInt(1, startYear);
	        pstmt.setInt(2, endYear);

	        rs = pstmt.executeQuery();
	        Map<Integer, SalesReportDTO> map = new HashMap<>();

	        while (rs.next()) {
	            SalesReportDTO dto = new SalesReportDTO();
	            int product_no = rs.getInt("product_no");
	            dto.setProduct_name(rs.getString("product_name"));
	            dto.setTotal_sales(rs.getInt("total_sales"));
	            dto.setTotal_cost(rs.getInt("total_cost"));
	            dto.setTotal_profit(rs.getInt("total_profit"));
	            map.put(product_no, dto);
	        }

	        rs.close();
	        pstmt.close();

	        // 대여 매출 추가
	        String rentalSql = 
	            "SELECT r.product_no, SUM(r.rental_price) AS rental_sales " +
	            "FROM rental r " +
	            "JOIN customer c ON r.customer_no = c.customer_no " +
	            "WHERE TO_NUMBER(SUBSTR(TO_CHAR(c.birth_date, 'YYYY-MM-DD'), 1, 4)) BETWEEN ? AND ? " +
	            "GROUP BY r.product_no";

	        pstmt = con.prepareStatement(rentalSql);
	        pstmt.setInt(1, startYear);
	        pstmt.setInt(2, endYear);

	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	            int product_no = rs.getInt("product_no");
	            int rental_sales = rs.getInt("rental_sales");

	            if (map.containsKey(product_no)) {
	                SalesReportDTO dto = map.get(product_no);
	                dto.setTotal_sales(dto.getTotal_sales() + rental_sales);
	                dto.setTotal_profit(dto.getTotal_profit() + rental_sales);
	            } else {
	                // 대여만 있는 상품도 포함
	                SalesReportDTO dto = new SalesReportDTO();
	                dto.setProduct_name(getProductName(product_no)); // 아래 메서드 참고
	                dto.setTotal_sales(rental_sales);
	                dto.setTotal_cost(0); // 대여는 원가 없음
	                dto.setTotal_profit(rental_sales);
	                map.put(product_no, dto);
	            }
	        }

	        list.addAll(map.values());

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

	        // 판매 매출 조회
	        String orderSql = 
	            "SELECT cp.product_no, cp.product_name, " +
	            "SUM(od.quantity * cp.output_price) AS total_sales, " +
	            "SUM(od.quantity * cp.input_price) AS total_cost, " +
	            "SUM(od.quantity * (cp.output_price - cp.input_price)) AS total_profit " +
	            "FROM orders o " +
	            "JOIN order_detail od ON o.order_no = od.order_no " +
	            "JOIN cam_product cp ON od.product_no = cp.product_no " +
	            "WHERE o.order_date BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD') " +
	            "GROUP BY cp.product_no, cp.product_name";

	        pstmt = con.prepareStatement(orderSql);
	        pstmt.setString(1, startDate);
	        pstmt.setString(2, endDate);

	        rs = pstmt.executeQuery();
	        Map<Integer, SalesReportDTO> map = new HashMap<>();

	        while (rs.next()) {
	            SalesReportDTO dto = new SalesReportDTO();
	            int product_no = rs.getInt("product_no");
	            dto.setProduct_name(rs.getString("product_name"));
	            dto.setTotal_sales(rs.getInt("total_sales"));
	            dto.setTotal_cost(rs.getInt("total_cost"));
	            dto.setTotal_profit(rs.getInt("total_profit"));
	            map.put(product_no, dto);
	        }

	        rs.close();
	        pstmt.close();

	        // 대여 매출 추가
	        String rentalSql = 
	            "SELECT r.product_no, SUM(r.rental_price) AS rental_sales " +
	            "FROM rental r " +
	            "WHERE r.rental_date BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD') " +
	            "GROUP BY r.product_no";

	        pstmt = con.prepareStatement(rentalSql);
	        pstmt.setString(1, startDate);
	        pstmt.setString(2, endDate);

	        rs = pstmt.executeQuery();
	        while (rs.next()) {
	            int product_no = rs.getInt("product_no");
	            int rental_sales = rs.getInt("rental_sales");

	            if (map.containsKey(product_no)) {
	                SalesReportDTO dto = map.get(product_no);
	                dto.setTotal_sales(dto.getTotal_sales() + rental_sales);
	                dto.setTotal_profit(dto.getTotal_profit() + rental_sales);
	            } else {
	                // 대여만 있는 상품도 포함
	                SalesReportDTO dto = new SalesReportDTO();
	                dto.setProduct_name(getProductName(product_no)); // 아래 메서드 참고
	                dto.setTotal_sales(rental_sales);
	                dto.setTotal_cost(0); // 대여는 원가 없음
	                dto.setTotal_profit(rental_sales);
	                map.put(product_no, dto);
	            }
	        }

	        list.addAll(map.values());

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        closeConn(rs, pstmt, con);
	    }

	    return list;
	}



}  


