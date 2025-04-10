package com.camping.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class RentalDAO {
	// DB와 연결하는 객체
		Connection con = null;
		
		// DB에 SQL문을 전송하는 객체.
		PreparedStatement pstmt = null;
		
		// SQL문을 실행한 후에 결과값을 가지고 있는 객체.
		ResultSet rs = null;
		
		// SQL문을 저장할 변수
		String sql = null;
		
		
		// AdminDAO 객체를 싱글턴 방식으로 만들어 보자.
		// 1단계 : AdminDAO 객체를 정적(static) 멤버로
		//        선언을 해 주어야 한다.
		private static RentalDAO instance = null;
		
		// 2단계 : 싱글턴 방식으로 객체를 만들기 위해서는 우선저긍로
		//        기본생성자의 접근제어자를 public이 아닌
		//        private으로 바꾸어 주어야 한다.
		//        즉, 외부에서 직접적으로 기본생성자를 접근하여
		//        호출하지 못하도록 하는 방법이다.
		private RentalDAO() {  }  // 기본 생성자
		
		// 3단계 : 기본 생성자 대신에 싱글턴 객체를 return 해 주는
		//        getInstance() 라는 메서드를 만들어서 해당
		//        getInstance() 메서드를 외부에서 접근할 수 
		//        있도록 해 주면 됨.
		public static RentalDAO getInstance() {
			
			if(instance == null) {
				instance = new RentalDAO();
			}
			
			return instance;
		}  // getInstance() 메서드 end
		
		
		
		// DB 연동하는 작업을 진행하는 메서드.
		// JDBC 방식이 아닌 DBCP 방식으로 DB와 연동 작업 진행.
		public void openConn() {
			
			try {
				// 1단계 : JNDI 서버 객체 생성.
				// 자바의 네이밍 서비스(JNDI)에서 이름과 실제 객체를
				// 연결해 주는 개념이 Context 객체이며, InitialContext
				// 객체는 네이밍 서비스를 이용하기 위한 시작점이 됨. 
				Context initCtx = new InitialContext();
				
				// 2단계 : Context 객체를 얻어와야 함.
				// "java:comp/env"라는 이름의 인수로 Context 객체를 얻어옴.
				// "java:comp/env"는 현제 웹 애플리케이션에서
				// 네이밍 서비스를 이용 시 루트 디렉토리라고 생각하면 됨.
				// 즉, 현재 웹 애플리케이션이 사용할 수 있는 모든 자원은
				// "java:comp/env" 아래에 위치를 하게 됨.
				Context ctx =
						(Context)initCtx.lookup("java:comp/env");
				
				// 3단계 : lookup() 메서드를 이용하여 매칭되는 커넥션을 찾아옴.
				// "java:comp/env" 아래에 위치한 "jdbc/myoracle" 자원을
				// 얻어옴. 이 자원이 바로 데이터 소스(커넥션풀)임.
				// 여기서 "jdbc/myoracle" 은 context.xml 파일에 추가했던
				// <Resource> 태그 안에 있던 name 속성의 값임.
				DataSource ds = (DataSource)ctx.lookup("jdbc/myoracle");
				
				// 4단계 : DataSource 객체를 이용하여 커넥션을 하나 가져오면 됨.
				con = ds.getConnection();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		}  // openConn() 메서드 end
		
		
		
		// DB에 연결된 자원을 종료하는 메서드.
		public void closeConn(ResultSet rs, 
				PreparedStatement pstmt, Connection con) {
			
				try {
					if(rs != null) rs.close();
					
					if(pstmt != null) pstmt.close();
					
					if(con != null) con.close();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		}  // closeConn() 메서드 end
		
		
		
		// DB에 연결된 자원을 종료하는 메서드.
		public void closeConn(
				PreparedStatement pstmt, Connection con) {
			
			try {
				
				if(pstmt != null) pstmt.close();
				
				if(con != null) con.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}  // closeConn() 메서드 end
		
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

		            // 문자열 형태로 받되, LocalDate로 파싱은 substring으로 처리
		            String rentalDateStr = rs.getString("rental_date");
		            String returnDateStr = rs.getString("return_date");

		            dto.setRental_date(rentalDateStr);
		            dto.setReturn_date(returnDateStr);

		            dto.setRental_status(rs.getString("rental_status"));

		            // 날짜 계산 (시간이 포함된 경우 substring 처리 후 LocalDate 변환)
		            LocalDate today = LocalDate.now();
		            LocalDate returnDate = LocalDate.parse(returnDateStr.substring(0, 10));

		            long daysLeft = ChronoUnit.DAYS.between(today, returnDate);
		            dto.setRemain_qty((int) (daysLeft < 0 ? 0 : daysLeft)); // 음수는 0으로 처리

		            list.add(dto);
		        }

		    } catch (Exception e) {
		        e.printStackTrace();
		    } finally {
		        closeConn(rs, pstmt, con);
		    }

		    return list;
		}



		
		
		public List<RentalStockDTO> getRentalStockStatus() {
		    List<RentalStockDTO> list = new ArrayList<>();
		    openConn();
		    try {
		        String sql = """
		            SELECT
		                p.product_no,
		                p.product_name,
		                p.stock_qty AS total_qty,
		                NVL(SUM(r.rental_qty), 0) AS rented_qty,
		                (p.stock_qty - NVL(SUM(r.rental_qty), 0)) AS available_qty
		            FROM
		                cam_product p
		            LEFT JOIN
		                rental r ON p.product_no = r.product_no AND r.rental_status = '대여중'
		            GROUP BY
		                p.product_no, p.product_name, p.stock_qty
		            ORDER BY
		                available_qty ASC
		        """;
		        pstmt = con.prepareStatement(sql);
		        rs = pstmt.executeQuery();
		        while (rs.next()) {
		            RentalStockDTO dto = new RentalStockDTO();
		            dto.setProduct_no(rs.getInt("product_no"));
		            dto.setProduct_name(rs.getString("product_name"));
		            dto.setTotal_qty(rs.getInt("total_qty"));
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

	
}



