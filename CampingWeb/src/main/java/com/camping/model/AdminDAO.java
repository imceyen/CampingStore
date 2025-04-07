package com.camping.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.camping.model.AdminDAO;

public class AdminDAO {

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
	// 선언을 해 주어야 한다.
	private static AdminDAO instance = null;

	// 2단계 : 싱글턴 방식으로 객체를 만들기 위해서는 우선저긍로
	// 기본생성자의 접근제어자를 public이 아닌
	// private으로 바꾸어 주어야 한다.
	// 즉, 외부에서 직접적으로 기본생성자를 접근하여
	// 호출하지 못하도록 하는 방법이다.
	public AdminDAO() {
	} // 기본 생성자

	// 3단계 : 기본 생성자 대신에 싱글턴 객체를 return 해 주는
	// getInstance() 라는 메서드를 만들어서 해당
	// getInstance() 메서드를 외부에서 접근할 수
	// 있도록 해 주면 됨.
	public static AdminDAO getInstance() {

		if (instance == null) {
			instance = new AdminDAO();
		}

		return instance;
	} // getInstance() 메서드 end

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
			Context ctx = (Context) initCtx.lookup("java:comp/env");

			// 3단계 : lookup() 메서드를 이용하여 매칭되는 커넥션을 찾아옴.
			// "java:comp/env" 아래에 위치한 "jdbc/myoracle" 자원을
			// 얻어옴. 이 자원이 바로 데이터 소스(커넥션풀)임.
			// 여기서 "jdbc/myoracle" 은 context.xml 파일에 추가했던
			// <Resource> 태그 안에 있던 name 속성의 값임.
			DataSource ds = (DataSource) ctx.lookup("jdbc/myoracle");

			// 4단계 : DataSource 객체를 이용하여 커넥션을 하나 가져오면 됨.
			con = ds.getConnection();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	} // openConn() 메서드 end

	// DB에 연결된 자원을 종료하는 메서드.
	public void closeConn(ResultSet rs, PreparedStatement pstmt, Connection con) {

		try {
			if (rs != null)
				rs.close();

			if (pstmt != null)
				pstmt.close();

			if (con != null)
				con.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	} // closeConn() 메서드 end

	// DB에 연결된 자원을 종료하는 메서드.
	public void closeConn(PreparedStatement pstmt, Connection con) {

		try {

			if (pstmt != null)
				pstmt.close();

			if (con != null)
				con.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	} // closeConn() 메서드 end

	public boolean adminLogin(String adminId, String adminPw) {
	    boolean result = false;
	    openConn();

	    try {
	        sql = "SELECT COUNT(*) FROM admin WHERE admin_id = ? AND admin_pw = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, adminId);
	        pstmt.setString(2, adminPw);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            result = rs.getInt(1) == 1;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        closeConn(rs, pstmt, con);
	    }

	    return result;
	}
	
	// 관리자 로그인 확인
	public AdminDTO loginAdmin(String adminId, String password) {
	    AdminDTO dto = null;

	    try {
	        openConn();
	        sql = "SELECT * FROM admin WHERE admin_id = ? AND password = ?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, adminId);
	        pstmt.setString(2, password);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	        	dto = new AdminDTO();
	        	dto.setAdminId(rs.getString("admin_id"));
	        	dto.setPassword(rs.getString("password"));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        closeConn(rs, pstmt, con);
	    }

	    return dto;
	}
	
	// 가장 큰 상품 번호 조회
    public int getMaxProductNo() {
        int maxProductNo = 0;
        String sql = "SELECT MAX(product_no) FROM cam_product"; // cam_product 테이블에서 가장 큰 product_no 조회
        try {
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                maxProductNo = rs.getInt(1); // 첫 번째 컬럼(가장 큰 상품 번호)
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConn(rs, pstmt, con);
        }
        return maxProductNo;
    }

    // 상품 추가 메서드
    public boolean insertProduct(ProductDTO productDTO) {
        boolean success = false;
        String sql = "INSERT INTO cam_product (product_no, category_no, product_name, input_price, output_price, stock_qty, sold_qty, is_sold_out, product_image, detail_image1, detail_image2, detail_image3, detail_image4) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, productDTO.getProduct_no());
            pstmt.setInt(2, productDTO.getCategory_no());
            pstmt.setString(3, productDTO.getProduct_name());
            pstmt.setInt(4, productDTO.getInput_price());
            pstmt.setInt(5, productDTO.getOutput_price());
            pstmt.setInt(6, productDTO.getStock_qty());
            pstmt.setInt(7, productDTO.getSold_qty());
            pstmt.setString(8, productDTO.getIs_sold_out());
            pstmt.setString(9, productDTO.getProduct_image());
            pstmt.setString(10, productDTO.getDetail_image1());
            pstmt.setString(11, productDTO.getDetail_image2());
            pstmt.setString(12, productDTO.getDetail_image3());
            pstmt.setString(13, productDTO.getDetail_image4());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConn(pstmt, con);
        }
        return success;
    }
	
	
}
